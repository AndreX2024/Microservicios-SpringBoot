import { Category } from "./Category";
import { ImageProduct } from "./ImageProduct";
import { Inventory } from "./Inventory";
import { Supplier } from "./Supplier";

export interface Product {
    proveedor: Supplier;
    categoria: Category;
    idProducto: number;
    nombre: string;
    descripcion: string;
    imagenes: ImageProduct[];
    inventarios: Inventory[];
    precio: number;
    descuentoActivo: boolean;
    porcentajeDescuento: number;
}