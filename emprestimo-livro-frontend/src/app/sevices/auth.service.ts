import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { JwtHelperService } from "@auth0/angular-jwt";
import { API_CONFIG } from "../configs/api.config";
import { Credenciais } from "../interfaces/credenciais";
import { PessoaDTO } from "../interfaces/domain/pessoa.dto";
import { LocalUser } from "../interfaces/local_user";
import { DataSharingService } from "./data-sharing.service";
import { PessoaService } from "./domain/pessoa.service";
import { StorageService } from "./storage.service";



@Injectable()
export class AuthService {

    jwtHelper: JwtHelperService = new JwtHelperService();

    constructor(public http: HttpClient, public storage: StorageService, public pessoaService: PessoaService,
        private dataSharingService: DataSharingService,
        private _snackBar: MatSnackBar,) {

    }



    authenticate(creds: Credenciais) {
        return this.http.post(`${API_CONFIG.baseUrl}/login`, creds, {
            observe: 'response',
            responseType: 'text'
        });
    }

    refreshToken() {
        return this.http.post(`${API_CONFIG.baseUrl}/auth/refresh_token`, {}, {
            observe: 'response',
            responseType: 'text'
        });
    }

    successfullLogin(authorizationValue: string | null) {
        if (authorizationValue) {
            let tok = authorizationValue.substring(7);
            let user: LocalUser = {
                token: tok,
                login: this.jwtHelper.decodeToken(tok).sub
            };
            this.storage.setLocalUser(user);
            this.pessoaService.findByLogin(user.login).subscribe(
                pessoa => {
                        this._snackBar.open("Usuário logado com sucesso", "ok", { panelClass: ['mat-toolbar', 'mat-success'] });
                        this.storage.setPerfil(pessoa);
                        this.dataSharingService.isUserLoggedIn.next(true);
                },
                error=>{
                    this._snackBar.open("Usuário não encontrado", "entendi!", { panelClass: ['mat-toolbar', 'mat-warn'] });
                }
            )
        }
    }

    logout() {        
        this.dataSharingService.isUserLoggedIn.next(false);
        this.storage.setLocalUser(null);
        this.storage.setPerfil( null);
    }
}