import { Product } from "./Product";

export interface Supplier {
    idProveedor: number;
    nombre: string;
    telefono: string;
    productos?: Product[];
}