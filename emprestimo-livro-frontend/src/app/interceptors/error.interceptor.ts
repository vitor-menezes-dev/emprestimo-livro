
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Observable } from "rxjs/Rx";
import { StorageService } from "../sevices/storage.service";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(public storage: StorageService, private _snackBar: MatSnackBar) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        return next.handle(req).catch((error, cought) => {
            let errorObj = error;
            if (errorObj.error) {
                errorObj = errorObj.error;
            }
            if (!errorObj.status) {
                errorObj = JSON.parse(errorObj);
            }
            console.log("Erro detectado pelo interceptor!")
            console.log(errorObj);

            this.defaultHandler(errorObj);

            return Observable.throw(errorObj);
        }) as any;
    }

    defaultHandler(errorObj: { status: any; error: any; message: any; }) {
        this._snackBar.open(`Error ${errorObj.status}: \n${errorObj.error} \n${errorObj.message}`, "Ok", { panelClass: ['mat-toolbar', 'mat-warn','mat-snackBar'] });
    }
}

export const ErrorInterceptorProvider = {
    provide: HTTP_INTERCEPTORS,
    useClass: ErrorInterceptor,
    multi: true
}