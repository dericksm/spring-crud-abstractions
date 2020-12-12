import { Injectable } from '@angular/core';
import {
	HttpEvent,
	HttpInterceptor,
	HttpHandler,
	HttpRequest,
	HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { LocalUser } from '../models/local-user';
import { StorageService } from '../services/storage.service';
import { API_CONFIG } from '../config/api.config';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
	constructor(private storageService: StorageService) {}
	intercept(
		req: HttpRequest<any>,
		next: HttpHandler
	): Observable<HttpEvent<any>> {
		const localUser: LocalUser = this.storageService.getLocalUser();
		const url = req.url;
		if (localUser && url.startsWith(API_CONFIG.baseUrl)) {
			const authReq = req.clone({
				headers: req.headers.set('Authorization', 'Bearer ' + localUser.token),
			});
			return next.handle(authReq);
		} else {
			return next.handle(req);
		}
	}
}

export const AuthInterceptorProvider = {
	provide: HTTP_INTERCEPTORS,
	useClass: AuthInterceptor,
	multi: true,
};
