package com.armatuplato.modelo;

/** Estados posibles de un pedido. */
public enum EstadoPedido {
    PENDIENTE("Pendiente"),
    EN_PREPARACION("En preparación"),
    LISTO("Listo"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado");

    private final String etiqueta;

    EstadoPedido(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
