import { Component } from '@angular/core';
import { Camera, CameraOptions } from '@ionic-native/camera';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { SubSink } from 'subsink';
import { API_CONFIG } from '../../config/api.config';
import { ClientDTO } from '../../models/client.dto';
import { LocalUser } from '../../models/local-user';
import { ClientService } from '../../services/domain/client.service';
import { StorageService } from '../../services/storage.service';

@IonicPage()
@Component({
	selector: 'page-profile',
	templateUrl: 'profile.html',
})
export class ProfilePage {
	public client: ClientDTO;
	private subSink = new SubSink();
	public picture: string;
	public cameraOn: boolean = false;

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		private camera: Camera,
		private storageService: StorageService,
		private clientService: ClientService
	) {}

	ionViewDidLoad(): void {
		this.loadData();
	}

	loadData(): void {
		const user: LocalUser = this.storageService.getLocalUser();
		if (user && user.email) {
			this.subSink.sink = this.clientService.findByEmail(user.email).subscribe(
				(res) => {
					this.client = res as ClientDTO;
					this.getImageIfExists();
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

	getImageIfExists(): void {
		this.subSink.sink = this.clientService
			.getImageFromBucket(this.client.id)
			.subscribe((res) => {
				this.client.imageUrl = `${API_CONFIG.s3BaseUrl}/cp${this.client.id}.jpg`;
			});
	}

	ionViewWillLeave(): void {
		this.subSink.unsubscribe();
	}

	getCameraPicture() {
		this.cameraOn = true;
		const options: CameraOptions = {
			quality: 100,
			destinationType: this.camera.DestinationType.FILE_URI,
			encodingType: this.camera.EncodingType.JPEG,
			mediaType: this.camera.MediaType.PICTURE,
		};
		this.camera.getPicture(options).then(
			(imageData) => {
				this.picture = 'data:image/png;base64,' + imageData;
				this.cameraOn = false;
			},
			(err) => {
				// Handle error
			}
		);
	}

	sendPicture() {
		this.subSink.sink = this.clientService
			.uploadPicture(this.picture)
			.subscribe(
				(res) => {
					this.picture = null;
					this.loadData();
				},
				(err) => {}
			);
	}

	cancel(): void {
		this.picture = null;
	}
}
