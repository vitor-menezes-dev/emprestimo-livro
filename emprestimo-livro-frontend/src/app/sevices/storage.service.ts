import { Injectable } from '@angular/core';
import { STORAGE_KEYS } from '../configs/storage_keys.config';
import { PessoaDTO } from '../interfaces/domain/pessoa.dto';
import { LocalUser } from '../interfaces/local_user';

@Injectable()
export class StorageService {
    getLocalUser(): LocalUser | null {
        let usr = localStorage.getItem(STORAGE_KEYS.localUser);
        if (usr == null) {
            return null;
        }
        else {
            return JSON.parse(usr);
        }
    };

    setLocalUser(obj: LocalUser | null) {
        if (obj == null) {
            localStorage.removeItem(STORAGE_KEYS.localUser);
        } else {
            localStorage.setItem(STORAGE_KEYS.localUser, JSON.stringify(obj));
        }
    };

    getPerfil(): PessoaDTO | null {
        let usr = localStorage.getItem(STORAGE_KEYS.perfil);
        if (usr == null) {
            return null;
        }
        else {
            return JSON.parse(usr);
        }
    };

    setPerfil(obj: PessoaDTO | null) {
        if (obj == null) {
            localStorage.removeItem(STORAGE_KEYS.perfil);
        } else {
            localStorage.setItem(STORAGE_KEYS.perfil, JSON.stringify(obj));
        }
    };

}