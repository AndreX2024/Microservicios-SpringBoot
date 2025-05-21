import { PayMethod } from "./PayMethod";
import { PayStatus } from "./PayStatus";

export interface Pay {
    idPago?: number;
    metodoPago: PayMethod;
    estadoPago: PayStatus;
}