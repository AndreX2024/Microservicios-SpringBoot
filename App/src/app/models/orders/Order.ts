import { OrderDetails } from "./OrderDetails";
import { OrderStatus } from "./OrderStatus";
import { Pay } from "./Pay";

export interface Order {
    idPedido?: number;
    idUsuario: number;
    fecha: Date;
    estado: OrderStatus;
    pago?: Pay;
    detalles: OrderDetails[];
    total: number
}