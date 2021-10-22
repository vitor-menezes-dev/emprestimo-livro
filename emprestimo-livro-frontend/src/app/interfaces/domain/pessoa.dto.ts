export interface PessoaDTO {
    "id": number,
    "nome": string,
    "login": string,
    "perfis": string[],
    "nascimento":Date,
    "senha":string,
    "desativado":boolean
}
