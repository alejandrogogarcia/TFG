package es.udc.tfg.app.util.exceptions;

public class InvoiceAttachedException extends Exception {

    public InvoiceAttachedException(String message) {
        super(message);
    }

    public InvoiceAttachedException() {
        super("No se puede modificar un albarán que ya tiene una factura asociada.");
    }
}