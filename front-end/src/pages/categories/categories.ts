import { CategoryDTO } from './../../models/category.dto';
import { Component } from '@angular/core';
import { CategoryService } from '../../services/domain/category.service';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { SubSink } from 'subsink';
import { API_CONFIG } from '../../config/api.config';

@IonicPage()
@Component({
	selector: 'page-categories',
	templateUrl: 'categories.html',
})
export class CategoriesPage {
	private subSink = new SubSink();
	public categories: CategoryDTO[] = [];
	public bucketUrl: string = API_CONFIG.s3BaseUrl;
	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private categoryService: CategoryService
	) {}

	ionViewDidLoad() {
		this.subSink.sink = this.categoryService.findAll().subscribe(
			(res) => {
				this.categories = res;
			},
			(error) => {
				console.log(error);
			}
		);
	}

	ionViewWillLeave() {
		this.subSink.unsubscribe();
	}

	showProducts(categoryId: string): void {
		this.navCtrl.push('ProductsPage',  {
			categoryId
		});
	}
}
