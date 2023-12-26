import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PoCheckboxGroupOption, PoDialogService, PoModalComponent, PoNotification, PoNotificationService, PoRadioGroupOption, PoTableAction, PoTableColumnSpacing, PoToasterOrientation } from '@po-ui/ng-components';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';
import { GeralService } from 'src/app/services/geral.service';

@Component({
  selector: 'app-cliente-view',
  templateUrl: './cliente-view.component.html',
  styleUrls: ['./cliente-view.component.css']
})
export class ClienteViewComponent implements OnInit {
  @ViewChild(PoModalComponent, { static: true }) poModal!: PoModalComponent;
  
  actions: Array<PoTableAction> = [
    { label: 'Editar Cliente', action: this.editarCliente.bind(this) },
    { label: 'Remover Cliente', action: this.confirmRemover.bind(this) }
  ];

  items: Cliente[] = [/* {
    id: 1,
    nome: 'Maria',
    sexo: 'Feminino',
    dataNascimento: '05/08/1978'
  }, {
    id: 1,
    nome: 'João',
    sexo: 'Masculino',
    dataNascimento: '23/01/1986'
  } */];

  columns = [
    { property: 'id', label: 'ID' },
    { property: 'nome', label: 'NOME' },
    { property: 'sexo', label: 'SEXO' },
    { property: 'dataNascimentoFormatada', label: 'DATA NASCIMENTO' }
  ];

  private _dataNascimento?: Date;

  constructor(
    private _datePipe: DatePipe,
    private _router: Router,
    private _geral: GeralService,
    private _clienteService: ClienteService,
    private _poNotification: PoNotificationService,
    private _poConfirm: PoDialogService
  ) { }

  ngOnInit() {
    this._geral.alterarTitulo("Clientes");

    this._clienteService.findAll().subscribe({
      next: (res) => {
        this.items = res ? res.map(cliente => ({ ...cliente, dataNascimentoFormatada: cliente.dataNascimento ? this._datePipe.transform(cliente.dataNascimento, 'dd-MM-yyyy') : null })) : [];
      }
    })
  }

  public novoCliente(): void {
    this._router.navigate(['/cliente-edit'], { replaceUrl: true });
  }

  public editarCliente(bind: any): void {
    this._router.navigate(['/cliente-edit', bind.id], { replaceUrl: true });
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
    
    this._clienteService.delete(id).subscribe({
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
