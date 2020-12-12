import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicPageModule } from 'ionic-angular';
import { HomePage } from './home';

@NgModule({
    declarations: [HomePage],
    imports: [ 
        IonicPageModule.forChild(HomePage),
        ReactiveFormsModule
     ]
})
export class HomeModule {}