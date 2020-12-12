import { CityDTO } from './city.dto';
export class AddressDTO {
	id: string;
	street: string;
	number: string;
	complement: string;
	district: string;
    zipCode: string; 
    city: CityDTO;
}
