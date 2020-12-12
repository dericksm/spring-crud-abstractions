import { Injectable } from '@angular/core';
import { Cart } from '../../models/cart';
import { ProductDTO } from '../../models/product.dto';
import { StorageService } from '../storage.service';

@Injectable()
export class CartService {
	constructor(private storageService: StorageService) {}

	createOrClearCart(): Cart {
		const cart: Cart = { items: [] };
		this.storageService.setCart(cart);
		return cart;
	}

	getCart(): Cart {
		let cart: Cart = this.storageService.getCart();
		if (cart == null) {
			cart = this.createOrClearCart();
		}
		return cart;
	}

	addProduct(product: ProductDTO): Cart {
		let cart = this.getCart();
		let index = cart.items.findIndex((el) => el.product.id == product.id);
		if (index == -1) {
			cart.items.push({ quantity: 1, product: product });
		}
		this.storageService.setCart(cart);
		return cart;
	}

	removeProduct(product: ProductDTO): Cart {
		let cart = this.getCart();
		let index = cart.items.findIndex((el) => el.product.id == product.id);
		if (index != -1) {
			cart.items.splice(index, 1);
		}
		this.storageService.setCart(cart);
		return cart;
	}

	increaseProductQuantity(product: ProductDTO): Cart {
		let cart = this.getCart();
		let index = cart.items.findIndex((el) => el.product.id == product.id);
		if (index != -1) {
			cart.items[index].quantity++;
		}
		this.storageService.setCart(cart);
		return cart;
	}

	decreaseProductQuantity(product: ProductDTO): Cart {
		let cart = this.getCart();
		let index = cart.items.findIndex((el) => el.product.id == product.id);
		if (index != -1) {
			cart.items[index].quantity--;
			if (cart.items[index].quantity < 1) {
				cart = this.removeProduct(product);
			}
		}
		this.storageService.setCart(cart);
		return cart;
	}

	total(): number {
		const cart = this.getCart();
		let sum = 0;
		cart.items.forEach((item) => {
			sum += item.product.price * item.quantity;
		});
		return sum;
	}
}
