package com.armatuplato.decorator;

import com.armatuplato.modelo.IProducto;

import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN DECORATOR - Decorator (clase abstracta).
 * Implementa IProducto y envuelve a otro IProducto, sumando su descripción y su precio
 * sin modificar el producto base.
 */
public abstract class ProductoDecorator implements IProducto {
    private final IProducto producto;
    private final double precioExtra;

    protected ProductoDecorator(IProducto producto, double precioExtra) {
        this.producto = producto;
        this.precioExtra = precioExtra;
    }

    /** Nombre del extra que aporta este decorador. */
    public abstract String getNombreExtra();

    @Override
    public String getNombre() {
        return producto.getNombre();
    }

    @Override
    public String getDescripcion() {
        return producto.getDescripcion() + " + " + getNombreExtra();
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + precioExtra;
    }

    @Override
    public List<String> getExtras() {
        List<String> extras = new ArrayList<>(producto.getExtras());
        extras.add(getNombreExtra());
        return extras;
    }
}
