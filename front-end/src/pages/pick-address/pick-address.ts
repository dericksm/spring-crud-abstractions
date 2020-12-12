import { SubSink } from 'subsink';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { AddressDTO } from '../../models/adress.dto';
import { StorageService } from '../../services/storage.service';
import { LocalUser } from '../../models/local-user';
import { ClientService } from '../../services/domain/client.service';
import { CartService } from '../../services/domain/cart.service';
import { OrderDTO } from '../../models/order.dto';

@IonicPage()
@Component({
	selector: 'page-pick-address',
	templateUrl: 'pick-address.html',
})
export class PickAddressPage {
	public items: AddressDTO[] = [];
	public order: OrderDTO;
	private subSink = new SubSink();

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private storageService: StorageService,
		private clientService: ClientService,
		private cartService: CartService
	) {}

	preProccerOrder(clientId: string): void {
		let cart = this.cartService.getCart();

		this.order = {
			client: { id: clientId, name: null, email: null },
			items: cart.items.map((x) => {
				return { quantity: x.quantity, product: { id: x.product.id } };
			}),
			payment: null,
			shipmentAddress: new AddressDTO(),
		};
	}

	ionViewDidLoad(): void {
		const user: LocalUser = this.storageService.getLocalUser();
		if (user && user.email) {
			this.subSink.sink = this.clientService.findByEmail(user.email).subscribe(
				(res) => {
					this.preProccerOrder(res['id']);
					this.items = res['addresses'];
				},
				(error) => {
					if (error.status == 403) {
						this.navCtrl.setRoot('HomePage');
					}
				}
			);
		} else {
			this.navCtrl.setRoot('HomePage');
		}
	}

	ionViewWillLeave(): void {
		this.subSink.unsubscribe();
	}

	nextPage(item: AddressDTO): void {
		this.order.shipmentAddress.id = item.id;
		this.navCtrl.push('PaymentPage', {
			order: this.order,
		});
	}
}
