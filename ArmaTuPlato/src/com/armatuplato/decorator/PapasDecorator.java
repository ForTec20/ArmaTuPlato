package com.armatuplato.decorator;

import com.armatuplato.modelo.IProducto;

/** PATRÓN DECORATOR - ConcreteDecorator: agrega "Papas". */
public class PapasDecorator extends ProductoDecorator {
    public static final String NOMBRE = "Papas";

    public PapasDecorator(IProducto producto, double precioExtra) {
        super(producto, precioExtra);
    }

    @Override
    public String getNombreExtra() {
        return NOMBRE;
    }
}
