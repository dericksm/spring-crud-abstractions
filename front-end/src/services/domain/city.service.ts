import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../../config/api.config';

@Injectable()
export class CityService {

    private resourceName: string = '/cities';
	private apiURL: string = API_CONFIG.baseUrl + this.resourceName;
	constructor(private http: HttpClient) {}
}