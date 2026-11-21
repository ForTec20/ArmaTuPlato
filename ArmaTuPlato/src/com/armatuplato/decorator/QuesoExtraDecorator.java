package com.armatuplato.decorator;

import com.armatuplato.modelo.IProducto;

/** PATRÓN DECORATOR - ConcreteDecorator: agrega "Queso extra". */
public class QuesoExtraDecorator extends ProductoDecorator {
    public static final String NOMBRE = "Queso extra";

    public QuesoExtraDecorator(IProducto producto, double precioExtra) {
        super(producto, precioExtra);
    }

    @Override
    public String getNombreExtra() {
        return NOMBRE;
    }
}
