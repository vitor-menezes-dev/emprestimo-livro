import { PessoaDTO } from "./domain/pessoa.dto";

export interface LocalUser {
    token: string | null;
    login: string;
}