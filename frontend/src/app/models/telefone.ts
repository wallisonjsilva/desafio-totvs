import { Cliente } from "./cliente";

export interface Telefone {
    id?: number;
    contato?: string;
    cliente?: Cliente;
}