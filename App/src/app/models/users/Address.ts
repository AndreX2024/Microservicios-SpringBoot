import { TypeAddress } from "./TypeAddress";

export interface Address {
    id_direccion: number;
    calle: string;
    ciudad: string;
    departamento: string;
    codigo_postal: string;
    tipoDireccion: TypeAddress;
}