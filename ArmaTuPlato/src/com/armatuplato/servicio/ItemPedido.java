package com.armatuplato.servicio;

import com.armatuplato.modelo.IProducto;

/** Línea de un pedido: un producto (con sus extras) y su cantidad. */
public class ItemPedido {
    private final IProducto producto;
    private final int cantidad;

    public ItemPedido(IProducto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public IProducto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}
