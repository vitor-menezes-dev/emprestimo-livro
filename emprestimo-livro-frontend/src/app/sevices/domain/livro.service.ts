import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { EmprestimoDTO } from "src/app/interfaces/domain/emprestimo.dto";
import { LivroDTO } from "src/app/interfaces/domain/livro.dto";
import { PessoaDTO } from "src/app/interfaces/domain/pessoa.dto";
import { PageDTO } from "src/app/interfaces/page";
import { API_CONFIG } from '../../configs/api.config';

@Injectable({
  providedIn: 'root'
})
export class LivroService {

  constructor(public http: HttpClient) {
  }

  list(page: number = 0, limit: number = 24, sort: string = "retorno", order: string = "", pessoaId = "",volumeId=""): Observable<PageDTO[]> {
    let param = '?_expand=pessoa&_expand=volume&_page=' + page + '&_limit=' + limit;
    if (sort != "" || sort != undefined) {
      param += '&_sort=' + sort;
    }

    if (order != "") {
      param += '&_order=' + order;
    }
    if (pessoaId != null && pessoaId != "") {
      param += '&pessoaId=' + pessoaId;
    }
    if (volumeId != null && volumeId != "") {
      param += '&volumeId=' + volumeId;
    }

    return this.http.get<PageDTO[]>(`${API_CONFIG.baseUrl}/livros/page${param}`);
  }

  findById(id: number): Observable<LivroDTO> {
    return this.http.get<LivroDTO>(`${API_CONFIG.baseUrl}/livros/${id}`);
  }

  salvar(obj: LivroDTO) {
    if (obj.id) {
      return this.http.put<PessoaDTO>(`${API_CONFIG.baseUrl}/livros/${obj.id}`, obj);
    } else {
      return this.http.post<PessoaDTO>(`${API_CONFIG.baseUrl}/livros/`, obj);
    }
  }
}
