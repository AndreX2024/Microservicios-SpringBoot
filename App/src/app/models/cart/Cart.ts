import { CartItems } from "./CartItems";

export interface Cart {
    idCarrito?: number;
    idUsuario: number;
    items: CartItems[]
}