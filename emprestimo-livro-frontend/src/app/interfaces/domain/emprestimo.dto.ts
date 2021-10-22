import { ExemplarDTO } from "./exemplar.dto";

export interface EmprestimoDTO {
    "id": number,
    "volumeId": number,
    "pessoaId": number,
    "retirada": Date,
    "retorno": Date,
    "observacao": string,
    "pessoa": {
        "id": number,
        "nome": string,
        "nascimento": Date
    },
    "exemplar": ExemplarDTO
}
