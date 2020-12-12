import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../../config/api.config';
import { ClientDTO } from '../../models/client.dto';
import { Observable } from 'rxjs/Rx';
import { StorageService } from '../storage.service';
import { ImageUtilService } from '../image-util.service';

@Injectable()
export class ClientService {
	private resourceName: string = '/clients';
	private apiURL: string = API_CONFIG.baseUrl + this.resourceName;

	constructor(
		private http: HttpClient,
		private storageService: StorageService,	
		private imageUtilService: ImageUtilService
	) {}

	findByEmail(email: String) {
		return this.http.get(`${this.apiURL}/email?email=${email}`);
	}

	findById(id: String) {
		return this.http.get(`${this.apiURL}/${id}`);
	}

	getImageFromBucket(id: string): Observable<any> {
		const url = `${API_CONFIG.s3BaseUrl}/cp${id}.jpg`;
		return this.http.get(url, { responseType: 'blob' });
	}

	create(client: ClientDTO) {
		return this.http.post<ClientDTO>(`${this.apiURL}/newClient`, client);
	}

	uploadPicture(picture) {
		let pictureBlob = this.imageUtilService.dataUriToBlob(picture);
		let formData: FormData = new FormData();
		formData.set('file', pictureBlob, 'file.png');
		return this.http.post(`${this.apiURL}/pictures`, formData);
	}
}
