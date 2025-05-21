import { SupportMessage } from "./SupportMessage";
import { TicketStatus } from "./TicketStatus";

export interface Ticket {
    idTicket?: number;
    idUsuario: number;
    asunto: string;
    descripcion: string;
    estado: TicketStatus;
    fechaCreacion?: Date;
    fechaCierre?: Date;
    mensajes?: SupportMessage[];
}