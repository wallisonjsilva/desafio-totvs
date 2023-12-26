import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PoDialogService, PoModalComponent, PoNotification, PoNotificationService, PoSelectOption, PoToasterOrientation } from '@po-ui/ng-components';
import { Cliente } from 'src/app/models/cliente';
import { Endereco } from 'src/app/models/endereco';
import { ClienteService } from 'src/app/services/cliente.service';
import { EnderecoService } from 'src/app/services/endereco.service';
import { GeralService } from 'src/app/services/geral.service';

@Component({
  selector: 'app-endereco-edit',
  templateUrl: './endereco-edit.component.html',
  styleUrls: ['./endereco-edit.component.css']
})
export class EnderecoEditComponent implements OnInit {
  @ViewChild(PoModalComponent, { static: true }) poModal!: PoModalComponent;

  mask: string = '99999-999';

  enderecoForm!: UntypedFormGroup;

  disabledButton: boolean = true;

  endereco: Endereco = {};

  public readonly estadoOptions: Array<PoSelectOption> = [
    { value: 'AC', label: 'Acre' },
    { value: 'AL', label: 'Alagoas' },
    { value: 'AP', label: 'Amapá' },
    { value: 'AM', label: 'Amazonas' },
    { value: 'BA', label: 'Bahia' },
    { value: 'CE', label: 'Ceará' },
    { value: 'DF', label: 'Distrito Federal' },
    { value: 'ES', label: 'Espírito Santo' },
    { value: 'GO', label: 'Goiás' },
    { value: 'MA', label: 'Maranhão' },
    { value: 'MT', label: 'Mato Grosso' },
    { value: 'MS', label: 'Mato Grosso do Sul' },
    { value: 'MG', label: 'Minas Gerais' },
    { value: 'PA', label: 'Pará' },
    { value: 'PB', label: 'Paraíba' },
    { value: 'PR', label: 'Paraná' },
    { value: 'PE', label: 'Pernambuco' },
    { value: 'PI', label: 'Piauí' },
    { value: 'RJ', label: 'Rio de Janeiro' },
    { value: 'RN', label: 'Rio Grande do Norte' },
    { value: 'RS', label: 'Rio Grande do Sul' },
    { value: 'RO', label: 'Rondônia' },
    { value: 'RR', label: 'Roraima' },
    { value: 'SC', label: 'Santa Catarina' },
    { value: 'SP', label: 'São Paulo' },
    { value: 'SE', label: 'Sergipe' },
    { value: 'TO', label: 'Tocantins' }
  ];

  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private _geral: GeralService,
    private _enderecoService: EnderecoService,
    private _clienteService: ClienteService,
    private _poConfirm: PoDialogService,
    private _poNotification: PoNotificationService,
    private _fb: UntypedFormBuilder) {
    this.createReactiveForm();
  }

  ngOnInit(): void {
    this._geral.alterarTitulo("Cadastro Endereço Cliente");

    if (this._route.snapshot.params['enderecoId']) {
      this._enderecoService.findById(this._route.snapshot.params['enderecoId']).subscribe({
        next: (res) => {
          this.disabledButton = false;

          this.enderecoForm.get('bairro')?.setValue(res.bairro);
          this.enderecoForm.get('cep')?.setValue(res.cep);
          this.enderecoForm.get('cidade')?.setValue(res.cidade);
          this.enderecoForm.get('estado')?.setValue(res.estado);
          this.enderecoForm.get('logradouro')?.setValue(res.logradouro);
          this.enderecoForm.get('numero')?.setValue(res.numero);
          this.enderecoForm.get('pais')?.setValue(res.pais);
          this.enderecoForm.get('id')?.setValue(res.id);

          this.endereco.id = res.id;
        }
      })
    }
  }

  public createReactiveForm() {
    this.enderecoForm = this._fb.group({
      logradouro: [''],
      bairro: [''],
      cep: [''],
      cidade: [''],
      estado: [''],
      pais: [''],
      numero: [''],
    });
  }

  public salvar(): void {
    this._poConfirm.confirm({
      literals: { confirm: 'Confirmar', cancel: 'Cancelar' },
      title: 'Salvar Endereço',
      message: 'Deseja salvar as informações do endereço do cliente?',
      confirm: () => this.confirm(),
      cancel: () => this.cancel()
    });
  }

  public voltar(): void {
    this._router.navigate(['/cliente-edit/', this._route.snapshot.params['clienteId']], { replaceUrl: true });
  }

  confirm() {
    this._clienteService.findById(
      this._route.snapshot.params['clienteId']
    ).subscribe({
      next: (res) => {
        this.endereco.bairro = this.enderecoForm.get('bairro')?.value;
        this.endereco.cep = this.enderecoForm.get('cep')?.value;
        this.endereco.cidade = this.enderecoForm.get('cidade')?.value;
        this.endereco.estado = this.enderecoForm.get('estado')?.value;
        this.endereco.logradouro = this.enderecoForm.get('logradouro')?.value;
        this.endereco.numero = this.enderecoForm.get('numero')?.value;
        this.endereco.pais = this.enderecoForm.get('pais')?.value;
        if (this.endereco.id == null)
          this.endereco.id = this.enderecoForm.get('id')?.value ? this.enderecoForm.get('id')?.value : null;

        this.endereco.cliente = res ? res : {}

        if (this.endereco.id != null)
          this._enderecoService.update(this.endereco).subscribe({
            next: (res) => {
              this.confirmMessage()
            },
            error: () => {
              this.cancel()
            }
          });
        else
          this._enderecoService.create(this.endereco).subscribe({
            next: (res) => {
              this.confirmMessage()
            },
            error: () => {
              this.cancel()
            }
          })
      },
      error: () => {
        this.cancel();
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


  public buscarCep(): void {
    this.disabledButton = false;

    this._enderecoService.getCEP(this.enderecoForm.get('cep')?.value).subscribe({
      next: (res) => {
        if (res?.erro) {
          return;
        }
        const estadoEncontrado = this.estadoOptions.find(option => option.value === res.uf);

        this.enderecoForm.get('logradouro')?.setValue(res.logradouro);
        this.enderecoForm.get('cidade')?.setValue(res.localidade);
        this.enderecoForm.get('estado')?.setValue(estadoEncontrado?.value);
        this.enderecoForm.get('bairro')?.setValue(res.bairro);
      }
    })
  }

}
