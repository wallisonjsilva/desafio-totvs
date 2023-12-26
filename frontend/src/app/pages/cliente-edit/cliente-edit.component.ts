import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PoDialogService, PoDropdownAction, PoModalComponent, PoNotification, PoNotificationService, PoSelectOption, PoToasterOrientation } from '@po-ui/ng-components';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';
import { GeralService } from 'src/app/services/geral.service';

@Component({
  selector: 'app-cliente-edit',
  templateUrl: './cliente-edit.component.html',
  styleUrls: ['./cliente-edit.component.css']
})
export class ClienteEditComponent implements OnInit {
  @ViewChild(PoModalComponent, { static: true }) poModal!: PoModalComponent;

  clienteForm!: UntypedFormGroup;

  action!: PoDropdownAction;

  maxDate!: Date;

  disabledButton: boolean = true;
  pattern: string = '^[a-zA-Z]+(?: [a-zA-Z]+)* *$';
  errorPattern: string = 'Somente letras, mínimo 11 caracteres!';
  minlength: number = 11;
  maxlength: number = 50;
  help: string = 'Campo nome é obrigatório!';

  size: string = 'small';

  public readonly sexoOptions: Array<PoSelectOption> = [
    { value: 'F', label: 'Feminino' },
    { value: 'M', label: 'Masculino' },
    { value: 'O', label: 'Não Binário' }
  ];

  constructor(
    private _geral: GeralService,
    private _clienteService: ClienteService,
    private _router: Router,
    private _route: ActivatedRoute,
    private _fb: UntypedFormBuilder,
    private _poConfirm: PoDialogService,
    private _poNotification: PoNotificationService) {
    this.createReactiveForm();

    this.maxDate = new Date();
  }

  ngOnInit(): void {
    this._geral.alterarTitulo("Cadastro de Cliente");

    if (this._route.snapshot.params['id']) {
      this._clienteService.findById(this._route.snapshot.params['id']).subscribe({
        next: (res) => {
          const sexoOption = this.sexoOptions.find(option => option.value === res.sexo)

          this.clienteForm.get('id')?.setValue(res.id);
          this.clienteForm.get('nome')?.setValue(res.nome);
          this.clienteForm.get('sexo')?.setValue(sexoOption?.value);
          let date = new Date(res.dataNascimento?.toString()!);
          this.clienteForm.get('dataNascimento')?.setValue(new Date(date));
        }
      })
    }
  }

  public createReactiveForm() {
    this.clienteForm = this._fb.group({
      id: [''],
      nome: ['', Validators.compose([Validators.required, Validators.minLength(11), Validators.maxLength(50)])],
      sexo: ['', Validators.maxLength(1)],
      dataNascimento: ['']
    });
  }

  public salvar() {
    this._poConfirm.confirm({
      literals: { confirm: 'Confirmar', cancel: 'Cancelar' },
      title: 'Salvar Cliente',
      message: 'Deseja salvar as informações do cliente?',
      confirm: () => this.confirm(),
      cancel: () => this.cancel()
    });
  }

  public voltar() {
    this._router.navigate(['/cliente-view'], { replaceUrl: true });
  }

  public novoEndereco(): void {
    this._router.navigate(['/endereco-edit/', this.clienteForm.get('id')?.value], { replaceUrl: true });
  }

  confirm() {
    let cliente: Cliente = {};

    cliente.dataNascimento = this.clienteForm.get('dataNascimento')?.value;
    cliente.sexo = this.clienteForm.get('sexo')?.value;
    cliente.nome = this.clienteForm.get('nome')?.value;
    cliente.id = this.clienteForm.get('id')?.value ? this.clienteForm.get('id')?.value : null;

    if (cliente.id != null)
      this._clienteService.update(cliente).subscribe({
        next: (res) => {
          this.confirmMessage()
        },
        error: () => {
          this.cancel()
        }
      });
    else
      this._clienteService.create(cliente).subscribe({
        next: (res) => {
          cliente.id = res.id
          this.confirmMessage()

          this._router.navigate(['/cliente-edit/', res.id], { replaceUrl: true });
        },
        error: () => {
          this.cancel()
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

  cancel() {
    const poNotification: PoNotification = {
      message: 'Ação não concluída, nenhuma informação salva!',
      orientation: PoToasterOrientation.Bottom,
      action: () => this.poModal.open(),
      duration: 2000
    }

    this._poNotification.information(poNotification);
  }
}
