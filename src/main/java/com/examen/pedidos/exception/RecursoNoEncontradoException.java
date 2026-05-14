package com.examen.pedidos.exception;

public class RecursoNoEncontradoException extends RuntimeException {

    private final String detalle;

    public RecursoNoEncontradoException(String mensaje, String detalle) {
        super(mensaje);
        this.detalle = detalle;
    }

    public String getDetalle() {
        return detalle;
    }
}
