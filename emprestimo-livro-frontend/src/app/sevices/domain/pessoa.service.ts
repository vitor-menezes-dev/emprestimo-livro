import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { PessoaDTO } from "src/app/interfaces/domain/pessoa.dto";
import { PageDTO } from "src/app/interfaces/page";
import { API_CONFIG } from '../../configs/api.config';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {
  findByName(nome_like = ""): Observable<PessoaDTO[]> {
    let param = ""
    if (nome_like != null && nome_like != "") {
      param += '?nome_like=' + nome_like;
    }
    return this.http.get<PessoaDTO[]>(`${API_CONFIG.baseUrl}/pessoas${param}`);
  }

  constructor(public http: HttpClient) {
  }

  list(page: number = 0, limit: number = 24, sort: string = "", order: string = "",
    nome_like = "",
    nascimento = "",
    desativado = ""): Observable<PageDTO> {
    let param = '?_page=' + page + '&_limit=' + limit;
    if (sort != "" || sort != undefined) {
      param += '&_sort=' + sort;
    }

    if (order != "") {
      param += '&_order=' + order;
    }

    if (nome_like != null && nome_like != "") {
      param += '&nome_like=' + nome_like;
    }

    if (nascimento != null && nascimento != "") {
      param += '&nascimento=' + nascimento;
    }

    if (desativado != null && desativado != "") {
      param += '&desativado=' + desativado;
    }

    return this.http.get<PageDTO>(`${API_CONFIG.baseUrl}/pessoas/page${param}`);
  }

  findByLogin(login: string): Observable<PessoaDTO> {
    return this.http.get<PessoaDTO>(`${API_CONFIG.baseUrl}/pessoas/login?value=${login}`);
  }

  findByUserId(id: number): Observable<PessoaDTO> {
    return this.http.get<PessoaDTO>(`${API_CONFIG.baseUrl}/pessoas/${id}`);
  }

  salvar(obj: PessoaDTO) {
    if (obj.id) {
      return this.http.put<PessoaDTO>(`${API_CONFIG.baseUrl}/pessoas/${obj.id}`, obj);
    } else {
      return this.http.post<PessoaDTO>(`${API_CONFIG.baseUrl}/pessoas/`, obj);
    }
  }
}
