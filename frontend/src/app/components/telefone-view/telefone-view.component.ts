import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PoDialogService, PoModalAction, PoModalComponent, PoNotification, PoNotificationService, PoTableAction, PoToasterOrientation } from '@po-ui/ng-components';
import { Telefone } from 'src/app/models/telefone';
import { ClienteService } from 'src/app/services/cliente.service';
import { TelefoneService } from 'src/app/services/telefone.service';

@Component({
  selector: 'desafio-telefone-view',
  templateUrl: './telefone-view.component.html',
  styleUrls: ['./telefone-view.component.css']
})
export class TelefoneViewComponent implements OnInit {
  @ViewChild(PoModalComponent, { static: true }) poModal!: PoModalComponent;

  size: string = 'sm';

  close: PoModalAction = {
    action: () => {
      this.closeModal();
    },
    label: 'Fechar',
    danger: true
  };

  confirm: PoModalAction = {
    action: () => {
      this.salvarTelefone();
    },
    label: 'Salvar'
  };

  telefoneForm!: UntypedFormGroup;

  telefone: Telefone = {};

  pattern: string = '^\(\d{2}\) (9 \d{4}-\d{4}|9 \d{4}-\d{3}| \d{4}-\d{4})$';
  errorPattern: string = 'Ex.: (99) 9 9999-999 ou (99) 9 9999-999';
  help: string = 'Informe Telefone do Cliente!';
  mask: string = '(99) 9 9999-9999'

  actions: Array<PoTableAction> = [
    { label: 'Editar Telefone', action: this.editarTelefone.bind(this) },
    { label: 'Remover Telefone', action: this.confirmRemover.bind(this) }
  ];

  items: Telefone[] = [/* {
    id: 1,
    telefone: '(99) 9 9999-9999'
  }, {
    id: 2,
    telefone: '(88) 8 8888-8888',
  } */];

  columns = [
    { property: 'id', label: 'ID' },
    { property: 'contato', label: 'TELEFONE' }
  ];

  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private _telefoneService: TelefoneService,
    private _clienteService: ClienteService,
    private _fb: UntypedFormBuilder,
    private _poConfirm: PoDialogService,
    private _poNotification: PoNotificationService
  ) {
    this.createReactiveForm();
  }

  ngOnInit() {
    if (this._route.snapshot.params['id'])
      this.findAll();
  }

  public createReactiveForm() {
    this.telefoneForm = this._fb.group({
      telefone: ['', Validators.compose([Validators.required])],
    });
  }

  public editarTelefone(bind: any): void {
    this._telefoneService.findById(bind.id).subscribe({
      next: (telefone) => {
        this._clienteService.findById(this._route.snapshot.params['id']).subscribe({
          next: (cliente) => {
            this.telefone.cliente = cliente ? cliente : {}

            this.telefone.contato = telefone ? telefone.contato : ''

            this.telefoneForm.get('telefone')?.setValue(this.telefone.contato);

            this.poModal?.open();
          }
        })
      }
    });

  }

  public confirmRemover(bind: any): void {
    this._poConfirm.confirm({
      literals: { confirm: 'Confirmar', cancel: 'Cancelar' },
      title: 'Salvar Cliente',
      message: 'Deseja salvar as informações do telefone do cliente?',
      confirm: () => this.confirmDelete(bind.id),
      cancel: () => this.cancel()
    });
  }

  public confirmDelete(id: number) {
    const poNotification: PoNotification = {
      message: 'Telefone deletado com sucesso!',
      orientation: PoToasterOrientation.Bottom,
      action: () => this.poModal.open(),
      duration: 2000
    }

    this._telefoneService.delete(id).subscribe({
      next: (res) => {
        this._poNotification.success(poNotification);
      },
      error: () => {
        this.cancel();
      }
    })

    this.items = this.items.filter(item => item.id !== id);
  }

  public salvarTelefone(): void {
    this._poConfirm.confirm({
      literals: { confirm: 'Confirmar', cancel: 'Cancelar' },
      title: 'Salvar Cliente',
      message: 'Deseja salvar as informações do telefone do cliente?',
      confirm: () => this.confirmSalvar(),
      cancel: () => this.cancel()
    });

    this.poModal?.close();
  }

  confirmSalvar(): void {
    this._clienteService.findById(
      this._route.snapshot.params['id']
    ).subscribe({
      next: (res) => {
        this.telefone.contato = this.formatarTelefone(this.telefoneForm.get('telefone')?.value);

        this.telefone.cliente = res ? res : {}

        if (this.telefone.id != null)
          this._telefoneService.update(this.telefone).subscribe({
            next: (res) => {
              this.confirmMessage()

              this.findAll();
            },
            error: () => {
              this.cancel()
            }
          });
        else
          this._telefoneService.create(this.telefone).subscribe({
            next: (res) => {
              this.confirmMessage()

              this.findAll();
            },
            error: () => {
              this.cancel()
            }
          })
      }
    })
  }

  confirmMessage() {
    const poNotification: PoNotification = {
      message: 'Dados salvos com sucesso!',
      orientation: PoToasterOrientation.Bottom,
      action: () => this.poModal.open(),
      duration: 2000
    }

    this._poNotification.success(poNotification);
  }

  public findAll(): void {
    this._telefoneService.findByClienteId(this._route.snapshot.params['id']).subscribe({
      next: (res) => {
        console.log(res)
        this.items = res ? res : [];
      }
    })
  }

  public novoTelefone(): void {
    this.poModal?.open();
  }

  public cancel() {
    const poNotification: PoNotification = {
      message: 'Ação não concluída, nenhuma informação salva!',
      orientation: PoToasterOrientation.Bottom,
      action: () => this.poModal.open(),
      duration: 2000
    }

    this._poNotification.information(poNotification);
  }

  public closeModal(): void {
    this.poModal?.close();
  }

  private formatarTelefone(numero: string): string {
    const numerosApenas = numero.replace(/\D/g, '');

    if (numerosApenas.length < 10) {
      return 'Número de telefone inválido';
    }

    return `(${numerosApenas.substring(0, 2)}) ${numerosApenas.substring(2, 3)} ${numerosApenas.substring(3, 7)}-${numerosApenas.substring(7)}`;
  }
}
