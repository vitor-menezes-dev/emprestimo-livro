import { LivroDTO } from "./livro.dto";

export interface ExemplarDTO {
    "id": number,
    "livro":LivroDTO,
    "adquirido":Date,
    "dispensado":Date,
}
