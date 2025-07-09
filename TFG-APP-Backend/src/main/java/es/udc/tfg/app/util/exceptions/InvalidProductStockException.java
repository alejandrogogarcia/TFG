package es.udc.tfg.app.util.exceptions;

public class InvalidProductStockException extends Exception{

    public InvalidProductStockException() {
        super("No es posible actualizar el stock del producto.");
    }
}
