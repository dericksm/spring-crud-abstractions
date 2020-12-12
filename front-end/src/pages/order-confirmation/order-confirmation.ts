import { SubSink } from 'subsink';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { OrderDTO } from '../../models/order.dto';
import { CartItem } from '../../models/cart-item';
import { CartService } from '../../services/domain/cart.service';
import { ClientDTO } from '../../models/client.dto';
import { AddressDTO } from '../../models/adress.dto';
import { ClientService } from '../../services/domain/client.service';
import { OrderService } from '../../services/domain/order.service';

@IonicPage()
@Component({
	selector: 'page-order-confirmation',
	templateUrl: 'order-confirmation.html',
})
export class OrderConfirmationPage {
	private subSink = new SubSink();
	public order: OrderDTO;
	public cartItems: CartItem[] = [];
	public client: ClientDTO;
	public address: AddressDTO;
	public orderRef: string;

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private cartService: CartService,
		private clientService: ClientService,
		private orderService: OrderService
	) {
		this.order = this.navParams.get('order');
		console.log(this.order);
	}

	ionViewDidLoad() {
		this.cartItems = this.cartService.getCart().items;
		this.subSink.sink = this.clientService
			.findById(this.order.client.id)
			.subscribe(
				(res) => {
					this.client = res as ClientDTO;
					this.address = res['addresses'].find(
						(x) => x.id == this.order.client.id
					);
				},
				(err) => {
					this.navCtrl.setRoot('HomePage');
				}
			);
	}

	ionViewWillLeave(): void {
		this.subSink.unsubscribe();
	}

	total(): number {
		return this.cartService.total();
	}

	checkout(): void {
		this.subSink.sink = this.orderService.create(this.order).subscribe(
			(res) => {
				this.orderRef = this.extractId(res.headers.get('location'));
				this.cartService.createOrClearCart();
			},
			(err) => {
				if (err.status == 403) {
					this.navCtrl.setRoot('HomePage');
				}
			}
		);
	}

	back(): void {
		this.navCtrl.setRoot('CartPage');
	}

	home(): void {
		this.navCtrl.setRoot('CategoriesPage');
	}

	private extractId(location: string): string {
		let position = location.lastIndexOf('/');
		return location.substring(position + 1, location.length);
	}
}
