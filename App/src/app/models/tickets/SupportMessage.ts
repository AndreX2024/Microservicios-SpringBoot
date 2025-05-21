export interface SupportMessage {
    idMensaje?: number;
    mensaje: string;
    fechaEnvio?: Date;
    ticket: { idTicket: number; }
}