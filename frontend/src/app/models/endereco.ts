import { Cliente } from "./cliente";

export interface Endereco {
    id?: number;
    logradouro?: string;
    bairro?: string;
    cep?: string;
    cidade?: string;
    estado?: string;
    pais?: string;
    numero?: number;
    cliente?: Cliente;
}