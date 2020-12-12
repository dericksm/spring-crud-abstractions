import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../../config/api.config';
import { OrderDTO } from '../../models/order.dto';
import { StorageService } from '../storage.service';

@Injectable()
export class OrderService {
	private resourceName: string = '/orders';
	private apiURL: string = API_CONFIG.baseUrl + this.resourceName;

	constructor(
		private http: HttpClient,
		private storageService: StorageService
	) {}

	create(order: OrderDTO) {
		return this.http.post(`${this.apiURL}/newOrder`, order, {
			observe: 'response',
			responseType: 'text',
		});
	}
}
