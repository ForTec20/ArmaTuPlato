package com.armatuplato.modelo;

import java.util.List;

/** Hamburguesa: subclase concreta de Producto (herencia + polimorfismo). */
public class Hamburguesa extends Producto {

    public Hamburguesa(Tamano tamano, Ingrediente tipo, Ingrediente base, Ingrediente salsa,
                       Ingrediente queso, Ingrediente proteina, List<Ingrediente> vegetales) {
        super(tamano, tipo, base, salsa, queso, proteina, vegetales);
    }

    @Override
    public String getNombre() {
        return "Hamburguesa";
    }

    @Override
    public String getDescripcion() {
        return "Hamburguesa " + getTamano() + " en " + getBase().getNombre().toLowerCase()
                + " | " + detalleIngredientes();
    }
}
