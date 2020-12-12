import { RefDto } from './ref.dto';
export interface OrderItemDTO {
    quantity: number;
    product: RefDto;
}