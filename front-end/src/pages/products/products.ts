import { SubSink } from 'subsink';
import { Component } from '@angular/core';
import {
	IonicPage,
	LoadingController,
	NavController,
	NavParams,
} from 'ionic-angular';
import { ProductDTO } from '../../models/product.dto';
import { ProductService } from '../../services/domain/product.service';
import { API_CONFIG } from '../../config/api.config';

@IonicPage()
@Component({
	selector: 'page-products',
	templateUrl: 'products.html',
})
export class ProductsPage {
	public items: ProductDTO[] = [];
	private subSink = new SubSink();
	public page: number = 0;

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private loadingController: LoadingController,
		private productService: ProductService
	) {}

	ionViewDidLoad() {
		this.loadData();
	}

	loadData(): void {
		let loader = this.presentLoading();
		this.subSink.sink = this.productService
			.findByCategory(this.navParams.get('categoryId'), this.page)
			.subscribe(
				(res) => {
					let start = this.items.length;
					this.items = this.items.concat(res['content']);
					let end = this.items.length - 1;
					this.loadImagesUrl(start, end);
					loader.dismiss();
				},
				(err) => {
					loader.dismiss();
				}
			);
	}

	loadImagesUrl(start: number, end: number): void {
		this.items.forEach((item, index) => {
			if (index >= start && index <= end) {
				this.subSink.sink = this.productService
					.getSmallImageFromBucket(item.id)
					.subscribe(
						(res) => {
							item.imageUrl = `${API_CONFIG.s3BaseUrl}/prod${item.id}-small.jpg`;
						},
						(err) => {}
					);
			}
		});
	}

	ionViewWillLeave() {
		this.subSink.unsubscribe();
	}

	showDetail(productId: string): void {
		this.navCtrl.push('ProductDetailPage', {
			productId,
		});
	}

	presentLoading() {
		let loader = this.loadingController.create({
			content: 'Aguarde...',
		});
		loader.present();
		return loader;
	}

	doRefresh(refresher) {
		this.page = 0;
		this.items = [];
		this.loadData();
		setTimeout(() => {
			refresher.complete();
		}, 2000);
	}

	doInfinite(infiteScroll) {
		this.page++;
		this.loadData();
		setTimeout(() => {
			infiteScroll.complete();
		}, 2000);
	}
}
