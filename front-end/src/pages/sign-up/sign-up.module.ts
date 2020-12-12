import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicPageModule } from 'ionic-angular';
import { SignUpPage } from './sign-up';
import { StateService } from '../../services/domain/state.service';
import { CityService } from './../../services/domain/city.service';

@NgModule({
  declarations: [
    SignUpPage
  ],
  imports: [
    ReactiveFormsModule,
    IonicPageModule.forChild(SignUpPage),
  ],
  providers: [
    StateService,
    CityService
  ]
})
export class SignUpPageModule {}
