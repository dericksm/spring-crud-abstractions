import { Injectable } from '@angular/core';
import { LocalUser } from '../models/local-user';
import { STORAGE_KEYS } from '../config/storage_keys.config';
import { Cart } from '../models/cart';

@Injectable()
export class StorageService {
	getLocalUser(): LocalUser {
		let user = localStorage.getItem(STORAGE_KEYS.localUser);
		if (user == null) {
			return null;
		}
		return JSON.parse(user);
	}

	setLocalUser(obj: LocalUser): void {
		if (obj == null) {
			localStorage.removeItem(STORAGE_KEYS.localUser);
		} else {
			localStorage.setItem(STORAGE_KEYS.localUser, JSON.stringify(obj));
		}
	}

	getCart(): Cart {
		let cart = localStorage.getItem(STORAGE_KEYS.cart);
		if (cart == null) {
			return null;
		}
		return JSON.parse(cart);
	}

	setCart(obj: Cart): void {
		if (obj == null) {
			localStorage.removeItem(STORAGE_KEYS.cart);
		} else {
			localStorage.setItem(STORAGE_KEYS.cart, JSON.stringify(obj));
		}
	}
}
