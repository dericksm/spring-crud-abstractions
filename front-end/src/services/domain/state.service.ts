import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { API_CONFIG } from '../../config/api.config';
import { CityDTO } from '../../models/city.dto';
import { StateDTO } from '../../models/state.dto';

@Injectable()
export class StateService {

    private resourceName: string = '/states';
	private apiURL: string = API_CONFIG.baseUrl + this.resourceName;
	constructor(private http: HttpClient) {}

	findAllCitiesByState(state): Observable<CityDTO[]> {
		return this.http.get<CityDTO[]>(`${this.apiURL}/${state}/cities`);
	}

	findAll(): Observable<StateDTO[]> {
		return this.http.get<StateDTO[]>(`${this.apiURL}`);
	}
}