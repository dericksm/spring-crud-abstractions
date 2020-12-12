import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../../config/api.config';
import { Observable } from 'rxjs/Rx';
import { ProductDTO } from '../../models/product.dto';

@Injectable()
export class ProductService {
	private resourceName: string = '/products';
	private apiURL: string = API_CONFIG.baseUrl + this.resourceName;
	constructor(private http: HttpClient) {}

	findById(id: string): Observable<ProductDTO> {
		return this.http.get<ProductDTO>(`${this.apiURL}/${id}`);
	}

	findByCategory(categoryId: string, page: number = 0, size: number = 10): Observable<ProductDTO[]> {
		return this.http.get<ProductDTO[]>(
			`${this.apiURL}/product-category?categories=${categoryId}&page=${page}&size=${size}`
		);
	}

	getSmallImageFromBucket(id: string): Observable<any> {
		const url = `${API_CONFIG.s3BaseUrl}/prod${id}-small.jpg`;
		return this.http.get(url, { responseType: 'blob' });
	}

	getImageFromBucket(id: string): Observable<any> {
		const url = `${API_CONFIG.s3BaseUrl}/prod${id}.jpg`;
		return this.http.get(url, { responseType: 'blob' });
	}
}
