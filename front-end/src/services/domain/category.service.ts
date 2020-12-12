import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { API_CONFIG } from '../../config/api.config';
import { CategoryDTO } from '../../models/category.dto';

@Injectable()
export class CategoryService {
	private resourceName: string = '/categories';
	private apiURL: string = API_CONFIG.baseUrl + this.resourceName;
	constructor(private http: HttpClient) {}

	findAll(): Observable<CategoryDTO[]> {
		return this.http.get<CategoryDTO[]>(this.apiURL);
	}
}
