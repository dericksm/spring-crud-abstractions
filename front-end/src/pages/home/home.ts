import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { IonicPage, MenuController, NavController } from 'ionic-angular';
import { SubSink } from 'subsink';
import { Credentials } from '../../models/credentials.dto';
import { AuthService } from '../../services/auth.service';

@IonicPage()
@Component({
	selector: 'page-home',
	templateUrl: 'home.html',
})
export class HomePage {
	public formGroup: FormGroup;
	private subSink = new SubSink();
	constructor(
		public navCtrl: NavController,
		private menu: MenuController,
		private formBuilder: FormBuilder,
		private authService: AuthService
	) {
		this.createForm();
	}

	private createForm(): void {
		this.formGroup = this.formBuilder.group({
			email: [],
			password: [],
		});
	}

	public login(): void {
		const credentials: Credentials = this.formGroup.value;
		this.subSink.sink = this.authService
			.authenticate(credentials)
			.subscribe((res) => {
				this.authService.successfulLogin(res.headers.get('Authorization'));
				this.navCtrl.setRoot('CategoriesPage');
			});
	}

	public signUp(): void {
		this.navCtrl.push('SignUpPage')
	}

	ionViewWillEnter(): void {
		this.menu.swipeEnable(false);
	}

	ionViewDidLeave(): void {
		this.menu.swipeEnable(true);
	}

	ionViewWillLeave(): void {
		this.subSink.unsubscribe();
	}

	ionViewDidEnter(): void {
		this.subSink.sink = this.authService.refreshToken().subscribe((res) => {
			this.authService.successfulLogin(res.headers.get('Authorization'));
			this.navCtrl.setRoot('CategoriesPage');
		});
	}
}
