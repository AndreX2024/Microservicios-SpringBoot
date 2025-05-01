import { Product } from "./Product";

export interface Category {
    idCategoria: number;
    nombre: string;
    descripcion: string;
    productos?: Product[];
}
