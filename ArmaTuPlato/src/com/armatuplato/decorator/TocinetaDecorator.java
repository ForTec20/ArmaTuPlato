package com.armatuplato.decorator;

import com.armatuplato.modelo.IProducto;

/** PATRÓN DECORATOR - ConcreteDecorator: agrega "Tocineta". */
public class TocinetaDecorator extends ProductoDecorator {
    public static final String NOMBRE = "Tocineta";

    public TocinetaDecorator(IProducto producto, double precioExtra) {
        super(producto, precioExtra);
    }

    @Override
    public String getNombreExtra() {
        return NOMBRE;
    }
}
