import { Address } from "./Address";
import { Role } from "./Role";

export interface User {
    id_usuario: number,
    nombre: string,
    apellido: string,
    email: string,
    telefono: string,
    contraseña: string,
    rol: Role,
    direcciones: Address[]
}