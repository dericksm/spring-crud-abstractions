import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';

import { MyApp } from './app.component';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { CategoryService } from '../services/domain/category.service';
import { ErrorInterceptorProvider } from '../interceptors/error-interceptor';
import { AuthService } from '../services/auth.service';
import { StorageService } from '../services/storage.service';
import { ClientService } from '../services/domain/client.service';
import { AuthInterceptorProvider } from '../interceptors/auth-interceptor';
import { ProductService } from '../services/domain/product.service';
import { CartService } from '../services/domain/cart.service';
import { ImageUtilService } from '../services/image-util.service';

@NgModule({
	declarations: [MyApp],
	imports: [BrowserModule, HttpClientModule, IonicModule.forRoot(MyApp)],
	bootstrap: [IonicApp],
	entryComponents: [MyApp],
	providers: [
		StatusBar,
		SplashScreen,
		CategoryService,
		AuthService,
		StorageService,
		ClientService,
		ProductService,
		CartService,
		ImageUtilService,
		AuthInterceptorProvider,
		ErrorInterceptorProvider,
		{ provide: ErrorHandler, useClass: IonicErrorHandler },
	],
})
export class AppModule {}
