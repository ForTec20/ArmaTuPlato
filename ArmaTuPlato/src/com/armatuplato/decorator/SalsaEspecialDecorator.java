package com.armatuplato.decorator;

import com.armatuplato.modelo.IProducto;

/** PATRÓN DECORATOR - ConcreteDecorator: agrega "Salsa especial". */
public class SalsaEspecialDecorator extends ProductoDecorator {
    public static final String NOMBRE = "Salsa especial";

    public SalsaEspecialDecorator(IProducto producto, double precioExtra) {
        super(producto, precioExtra);
    }

    @Override
    public String getNombreExtra() {
        return NOMBRE;
    }
}
