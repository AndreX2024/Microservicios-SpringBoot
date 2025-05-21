export interface CartItems {
    idItem?: number,
    idProducto: number,
    idTalla: number,
    idColor: number,
    cantidad: number,
    precioUnitario: number,
    carrito?: {
        idCarrito: number;
    };
}