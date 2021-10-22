import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { EmprestimoDTO } from "src/app/interfaces/domain/emprestimo.dto";
import { PessoaDTO } from "src/app/interfaces/domain/pessoa.dto";
import { PageDTO } from "src/app/interfaces/page";
import { API_CONFIG } from '../../configs/api.config';

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {

  constructor(public http: HttpClient) {
  }

  list(page: number = 0, limit: number = 24, sort: string = "", order: string = "",
    pessoa_ids: number[],
    livro_ids: number[],
    retirada_gte: string,
    retirada_lte: string,
    devolvido_gte: string,
    devolvido_lte: string,
    sem_devolucao: number = -1): Observable<PageDTO> {
    let param = '?_page=' + page + '&_limit=' + limit;
    if (sort != "" || sort != undefined) {
      param += '&_sort=' + sort;
    }

    if (order != "") {
      param += '&_order=' + order;
    }

    if (pessoa_ids != null && pessoa_ids.length > 0) {
      param += '&pessoa_ids='
      for (let i = 0; i < pessoa_ids.length; i++) {
        param += pessoa_ids[i] + ",";
      }
    }

    if (livro_ids != null && livro_ids.length > 0) {
      param += '&livro_ids='
      for (let i = 0; i < livro_ids.length; i++) {
        param += livro_ids[i] + ",";
      }
    }

    if (retirada_gte != null && retirada_gte != "") {
      param += '&retirada_gte=' + retirada_gte;
    }

    if (retirada_lte != null && retirada_lte != "") {
      param += '&retirada_lte=' + retirada_lte;
    }

    if (devolvido_gte != null && devolvido_gte != "") {
      param += '&devolvido_gte=' + devolvido_gte;
    }

    if (devolvido_lte != null && devolvido_lte != "") {
      param += '&devolvido_lte=' + devolvido_lte;
    }

    if (sem_devolucao != null) {
      param += '&sem_devolucao=' + sem_devolucao;
    }

    return this.http.get<PageDTO>(`${API_CONFIG.baseUrl}/emprestimos/page${param}`);
  }

  findById(id: string): Observable<EmprestimoDTO> {
    return this.http.get<EmprestimoDTO>(`${API_CONFIG.baseUrl}/emprestimos/${id}`);
  }

  salvar(obj: EmprestimoDTO) {
    if (obj.id) {
      return this.http.put<EmprestimoDTO>(`${API_CONFIG.baseUrl}/emprestimos/${obj.id}`, obj);
    } else {
      return this.http.post<EmprestimoDTO>(`${API_CONFIG.baseUrl}/emprestimos/`, obj);
    }
  }
}
