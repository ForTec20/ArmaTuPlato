package com.armatuplato.decorator;

import com.armatuplato.modelo.IProducto;

/** PATRÓN DECORATOR - ConcreteDecorator: agrega "Bebida". */
public class BebidaDecorator extends ProductoDecorator {
    public static final String NOMBRE = "Bebida";

    public BebidaDecorator(IProducto producto, double precioExtra) {
        super(producto, precioExtra);
    }

    @Override
    public String getNombreExtra() {
        return NOMBRE;
    }
}
