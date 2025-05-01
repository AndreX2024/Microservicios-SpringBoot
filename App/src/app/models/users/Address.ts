import { TypeAddress } from "./TypeAddress";

export interface Address {
    idDireccion: number;
    idUsuario: number;
    calle: string;
    ciudad: string;
    departamento: string;
    codigoPostal: string;
    tipoDireccion: TypeAddress;
}