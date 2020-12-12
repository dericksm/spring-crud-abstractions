import { Injectable } from '@angular/core';
import {
	HttpEvent,
	HttpInterceptor,
	HttpHandler,
	HttpRequest,
	HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { StorageService } from '../services/storage.service';
import { AlertController } from 'ionic-angular';
import { FieldMessage } from '../models/field-message';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
	constructor(
		private storageService: StorageService,
		private alertController: AlertController
	) {}
	intercept(
		req: HttpRequest<any>,
		next: HttpHandler
	): Observable<HttpEvent<any>> {
		return next.handle(req).catch((error, next) => {
			let errorObj = error;
			if (errorObj.error) {
				errorObj = errorObj.error;
			}
			if (!errorObj.status) {
				errorObj = JSON.parse(errorObj);
			}

			switch (errorObj.status) {
				case 401:
					this.handle401();
					break;
				case 403:
					this.handle403();
					break;
				case 422:
					this.handle422(errorObj);
					break;
				default:
					this.handleDefaultError(errorObj);
			}

			return Observable.throw(errorObj);
		}) as any;
	}

	private handle403(): void {
		this.storageService.setLocalUser(null);
	}

	private handle422(error): void {
		const alert = this.alertController.create({
			title: 'Erro de validação',
			message: this.listErrors(error.errors),
			buttons: [
				{
					text: 'Ok',
				},
			],
		});
		alert.present();
	}

	private handle401(): void {
		const alert = this.alertController.create({
			title: 'Falha de autenticação',
			message: 'E-mail ou senha incorretos',
			buttons: [
				{
					text: 'Ok',
				},
			],
		});
		alert.present();
	}

	private handleDefaultError(error): void {
		const alert = this.alertController.create({
			title: `Erro: ${error.status}: ${error.error}`,
			message: error.message,
			buttons: [
				{
					text: 'Ok',
				},
			],
		});
		alert.present();
	}

	private listErrors(messages: FieldMessage[]): string {
		let s: string = '';
		messages.forEach((error) => {
			s = s + `<p><strong> ${error.fieldName} </strong> ${error.message} </p>`;
		});
		return s;
	}
}

export const ErrorInterceptorProvider = {
	provide: HTTP_INTERCEPTORS,
	useClass: ErrorInterceptor,
	multi: true,
};
