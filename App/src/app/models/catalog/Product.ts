import { ImageProduct } from "./ImageProduct";

export interface Product {
    proveedor: any;
    categoria: any;
    idProducto: number;
    nombre: string;
    descripcion: string;
    imagenes: ImageProduct[];
    precio: number;
    descuentoActivo: boolean;
    porcentajeDescuento: number;
}