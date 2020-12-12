import { ClientDTO } from './client.dto';
import { AddressDTO } from './adress.dto';
import { PaymentDTO } from './payment.dto';
import { OrderItemDTO } from './order-item.dto';
export interface OrderDTO {
	client: ClientDTO;
	shipmentAddress: AddressDTO;
	payment: PaymentDTO;
	items: OrderItemDTO[];
}
