import { Color } from "./Color";
import { Product } from "./Product";
import { Size } from "./Size";

export interface Inventory {
    idInventario: number;
    producto: Product;
    talla: Size;
    color: Color;
    stock: number;
}