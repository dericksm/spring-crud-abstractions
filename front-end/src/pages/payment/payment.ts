import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { OrderDTO } from '../../models/order.dto';

@IonicPage()
@Component({
	selector: 'page-payment',
	templateUrl: 'payment.html',
})
export class PaymentPage {
	public order: OrderDTO;
	public installments: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
	public formGroup: FormGroup;

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private formBuilder: FormBuilder
	) {
		this.order = this.navParams.get('order');
		this.createForm();
	}

	ionViewDidLoad() {}

	createForm(): void {
		this.formGroup = this.formBuilder.group({
			installmentNumber: [1, Validators.required],
			'@class': ['PaymentCard'],
		});
	}

	nextPage(): void {
		this.order.payment = this.formGroup.value;
		this.navCtrl.setRoot('OrderConfirmationPage', {
			order: this.order,
		});
	}
}
