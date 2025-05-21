import { TypeAddress } from "./TypeAddress";
import { User } from "./User";

export interface Address {
    idDireccion: number;
    idUsuario: User["idUsuario"];
    calle: string;
    ciudad: string;
    departamento: string;
    codigoPostal: string;
    tipoDireccion: TypeAddress;
}