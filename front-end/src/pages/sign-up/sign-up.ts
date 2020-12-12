import { SubSink } from 'subsink';
import { CityService } from './../../services/domain/city.service';
import { Component } from '@angular/core';
import {
	AlertController,
	IonicPage,
	NavController,
	NavParams,
} from 'ionic-angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StateService } from '../../services/domain/state.service';
import { CityDTO } from '../../models/city.dto';
import { StateDTO } from '../../models/state.dto';
import { ClientDTO } from '../../models/client.dto';
import { ClientService } from '../../services/domain/client.service';

@IonicPage()
@Component({
	selector: 'page-sign-up',
	templateUrl: 'sign-up.html',
})
export class SignUpPage {
	private subSink = new SubSink();
	public formGroup: FormGroup;
	public cities: CityDTO[] = [];
	public states: StateDTO[] = [];

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private formBuilder: FormBuilder,
		private stateService: StateService,
		private clientService: ClientService,
		private alertControler: AlertController
	) {
		this.createForm();
	}

	private createForm(): void {
		this.formGroup = this.formBuilder.group({
			name: [
				'Joaquim',
				[
					Validators.required,
					Validators.minLength(5),
					Validators.maxLength(120),
				],
			],
			email: ['joaquim@gmail.com', [Validators.required, Validators.email]],
			clientType: ['1', [Validators.required]],
			identifier: [
				'06134596280',
				[
					Validators.required,
					Validators.minLength(11),
					Validators.maxLength(14),
				],
			],
			password: ['123', [Validators.required]],
			street: ['Rua Via', [Validators.required]],
			number: ['25', [Validators.required]],
			complement: ['Apto 3', []],
			district: ['Copacabana', []],
			zipCode: ['10828333', [Validators.required]],
			phone1: ['977261827', [Validators.required]],
			phone2: ['', []],
			phone3: ['', []],
			cityId: [null, [Validators.required]],
			stateId: [null, [Validators.required]],
		});
	}

	signupUser(): void {
		const client: ClientDTO = this.formGroup.value;
		this.subSink.sink = this.clientService.create(client).subscribe((res) => {
			this.showSuccessMessage();
		},
		err => {});
	}

	showSuccessMessage(): void {
		const alert = this.alertControler.create({
			title: 'Sucesso',
			message: 'Cadastro realizado com sucesso',
			buttons: [
				{
					text: 'Ok',
					handler: () => {
						this.navCtrl.pop();
					},
				},
			],
		});
		alert.present();
	}

	ionViewDidLoad(): void {
		this.subSink.sink = this.stateService.findAll().subscribe((res) => {
			this.states = res;
			this.formGroup.controls.stateId.setValue(this.states[0].id);
			this.updateCities();
		});
	}

	updateCities(): void {
		const stateId = this.formGroup.value.stateId;
		this.subSink.sink = this.stateService
			.findAllCitiesByState(stateId)
			.subscribe((res) => {
				this.cities = res;
				this.formGroup.controls.cityId.setValue(null);
			});
	}
}
