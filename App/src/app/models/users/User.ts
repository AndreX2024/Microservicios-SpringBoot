import { Address } from "./Address";
import { Role } from "./Role";

export interface User {
    idUsuario: number,
    documento: string,
    nombre: string,
    apellido: string,
    email: string,
    telefono: string,
    contraseña: string,
    rol: Role,
    direcciones: Address[]
}