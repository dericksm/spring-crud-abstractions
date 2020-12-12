import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { SubSink } from 'subsink';
import { API_CONFIG } from '../../config/api.config';
import { CartItem } from '../../models/cart-item';
import { CartService } from '../../services/domain/cart.service';
import { ProductService } from '../../services/domain/product.service';
import { ProductDetailPage } from '../product-detail/product-detail';
import { ProductDTO } from '../../models/product.dto';

@IonicPage()
@Component({
	selector: 'page-cart',
	templateUrl: 'cart.html',
})
export class CartPage {
	public items: CartItem[] = [];
	private subSink = new SubSink();

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private cartService: CartService,
		private productService: ProductService
	) {}

	ionViewDidLoad() {
		this.items = this.cartService.getCart().items;
		this.loadImagesUrl();
	}

	ionViewWillLeave() {
		this.subSink.unsubscribe();
	}

	loadImagesUrl(): void {
		this.items.forEach((item) => {
			this.subSink.sink = this.productService
				.getSmallImageFromBucket(item.product.id)
				.subscribe(
					(res) => {
						item.product.imageUrl = `${API_CONFIG.s3BaseUrl}/prod${item.product.id}-small.jpg`;
					},
					(err) => {}
				);
		});
	}

	removeItem(product: ProductDTO) {
		this.items = this.cartService.removeProduct(product).items;
	}

	increaseQuantity(product: ProductDTO) {
		this.items = this.cartService.increaseProductQuantity(product).items;
	}

	decreaseQuantity(product: ProductDTO) {
		this.items = this.cartService.decreaseProductQuantity(product).items;
	}

	total(): number {
		return this.cartService.total();
	}

	goOn(): void {
		this.navCtrl.setRoot('CategoriesPage');
	}

	checkout(): void {
		this.navCtrl.push('PickAddressPage');
	}
}
