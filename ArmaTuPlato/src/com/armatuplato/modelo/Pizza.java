package com.armatuplato.modelo;

import java.util.List;

/** Pizza: subclase concreta de Producto (herencia + polimorfismo). */
public class Pizza extends Producto {

    public Pizza(Tamano tamano, Ingrediente tipo, Ingrediente base, Ingrediente salsa,
                 Ingrediente queso, Ingrediente proteina, List<Ingrediente> vegetales) {
        super(tamano, tipo, base, salsa, queso, proteina, vegetales);
    }

    @Override
    public String getNombre() {
        return "Pizza";
    }

    @Override
    public String getDescripcion() {
        return "Pizza " + getTamano() + " en " + getBase().getNombre().toLowerCase()
                + " | " + detalleIngredientes();
    }
}
