package com.armatuplato.builder;

import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.modelo.Producto;
import com.armatuplato.modelo.Tamano;

/**
 * PATRÓN BUILDER - Director.
 * Conoce recetas predefinidas ("sugeridos") y las arma usando cualquier ProductoBuilder.
 */
public class ProductoDirector {

    public Producto pizzaClasica(ProductoBuilder builder, Tamano tamano) throws ArmaTuPlatoException {
        return builder.reset()
                .tamano(tamano)
                .base("Masa delgada")
                .salsa("Tomate")
                .queso("Mozzarella")
                .proteina("Jamón")
                .agregarVegetal("Champiñones")
                .agregarVegetal("Maíz")
                .construir();
    }

    public Producto hamburguesaClasica(ProductoBuilder builder, Tamano tamano) throws ArmaTuPlatoException {
        return builder.reset()
                .tamano(tamano)
                .base("Pan normal")
                .salsa("BBQ")
                .queso("Cheddar")
                .proteina("Carne")
                .agregarVegetal("Lechuga")
                .agregarVegetal("Tomate")
                .agregarVegetal("Cebolla")
                .construir();
    }
}
