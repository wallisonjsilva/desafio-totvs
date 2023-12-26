import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PoDialogService, PoModalComponent, PoNotification, PoNotificationService, PoTableAction, PoToasterOrientation } from '@po-ui/ng-components';
import { Endereco } from 'src/app/models/endereco';
import { EnderecoService } from 'src/app/services/endereco.service';

@Component({
  selector: 'desafio-endereco-view',
  templateUrl: './endereco-view.component.html',
  styleUrls: ['./endereco-view.component.sass']
})
export class EnderecoViewComponent implements OnInit {
  @ViewChild(PoModalComponent, { static: true }) poModal!: PoModalComponent;

  actions: Array<PoTableAction> = [
    { label: 'Editar Endereço', action: this.editarEndereco.bind(this) },
    { label: 'Remover Endereço', action: this.confirmRemover.bind(this) }
  ];

  items: Endereco[] = [/* {
    id: 1,
    logradouro: 'Rua X Qd 99 Lt 99/99',
    bairro: 'Bairro Y',
    cep: '99999-999',
    cidade: 'Cidade A',
    estado: 'A',
    pais: 'Brasil',
    numero: 0
  }, {
    id: 2,
    logradouro: 'Rua B Qd 99 Lt 99/99',
    bairro: 'Bairro X',
    cep: '88888-888',
    cidade: 'Cidade B',
    estado: 'B',
    pais: 'Brasil',
    numero: 0
  } */];

  columns = [
    { property: 'id', label: 'ID' },
    { property: 'logradouro', label: 'LOGRADOURO' },
    { property: 'bairro', label: 'BAIRRO' },
    { property: 'cep', label: 'CEP' },
    { property: 'cidade', label: 'CIDADE' },
    { property: 'estado', label: 'ESTADO' },
    { property: 'pais', label: 'PAÍS' },
    { property: 'numero', label: 'Nº' },
  ];

  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private _enderecoService: EnderecoService,
    private _poConfirm: PoDialogService,
    private _poNotification: PoNotificationService
  ) { }

  ngOnInit(): void {
    if (this._route.snapshot.params['id'])
      this._enderecoService.findByClienteId(this._route.snapshot.params['id']).subscribe({
        next: (res) => {
          this.items = res ? res : [];
        }
      })
  }

  public editarEndereco(bind: any): void {
    this._router.navigate(['/endereco-edit/', this._route.snapshot.params['id'], bind.id], { replaceUrl: true });
  }

  public confirmRemover(bind: any): void {
    this._poConfirm.confirm({
      literals: { confirm: 'Confirmar', cancel: 'Cancelar' },
      title: 'Salvar Cliente',
      message: 'Deseja salvar as informações do cliente?',
      confirm: () => this.confirm(bind.id),
      cancel: () => this.cancel()
    });
  }

  public confirm(id: number) {
    //Terminar aqui depoiss
    const poNotification: PoNotification = {
      message: 'Cliente deletado com sucesso!',
      orientation: PoToasterOrientation.Bottom,
      action: () => this.poModal.open(),
      duration: 2000
    }
    
    this._enderecoService.delete(id).subscribe({
      next: (res) => {
        this._poNotification.success(poNotification);
      },
      error: () => {
        this.cancel();
      }
    })

    this.items = this.items.filter(item => item.id !== id);
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
}
