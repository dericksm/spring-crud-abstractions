import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { SubSink } from 'subsink';
import { API_CONFIG } from '../../config/api.config';
import { ProductDTO } from '../../models/product.dto';
import { CartService } from '../../services/domain/cart.service';
import { ProductService } from '../../services/domain/product.service';

@IonicPage()
@Component({
	selector: 'page-product-detail',
	templateUrl: 'product-detail.html',
})
export class ProductDetailPage {
	public item: ProductDTO;
	private subSink = new SubSink();
	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private productService: ProductService,
		private cartService: CartService
	) {}

	ionViewDidLoad() {
		this.subSink.sink = this.productService
			.findById(this.navParams.get('productId'))
			.subscribe(
				(res) => {
					this.item = res;
					this.getImageUrlIfExists();
				},
				(err) => {}
			);
	}

	ionViewWillLeave() {
		this.subSink.unsubscribe();
	}

	getImageUrlIfExists(): void {
		this.subSink.sink = this.productService
			.getImageFromBucket(this.item.id)
			.subscribe(
				(res) => {
					this.item.imageUrl = `${API_CONFIG.s3BaseUrl}/prod${this.item.id}.jpg`;
				},
				(err) => {}
			);
	}

	addToCart(product: ProductDTO) {
		this.cartService.addProduct(product);
		this.navCtrl.setRoot('CartPage');
	}
}
