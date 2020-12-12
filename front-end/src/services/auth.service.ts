import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelper } from 'angular2-jwt';
import { API_CONFIG } from '../config/api.config';
import { Credentials } from '../models/credentials.dto';
import { LocalUser } from '../models/local-user';
import { StorageService } from './storage.service';
import { CartService } from './domain/cart.service';

@Injectable()
export class AuthService {
	private jwtHelper = new JwtHelper();

	constructor(
		private http: HttpClient,
		private storageService: StorageService,
		private cartService: CartService
	) {}

	authenticate(cred: Credentials) {
		return this.http.post(API_CONFIG.baseUrl + '/login', cred, {
			observe: 'response',
			responseType: 'text'
		});
	}

	refreshToken() {
		return this.http.post(API_CONFIG.baseUrl + '/auth/refresh-token', {}, {
			observe: 'response',
			responseType: 'text'
		});
	}

	successfulLogin(authorizationValue: string): void {
		let token = authorizationValue.substring(7);
		let user: LocalUser = {
			token: token,
			email: this.jwtHelper.decodeToken(token).sub

		};
		this.storageService.setLocalUser(user);
		this.cartService.createOrClearCart();
    }
    
    logout(): void {
        this.storageService.setLocalUser(null);
    }
}
