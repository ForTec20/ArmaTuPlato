package com.armatuplato.modelo;

/** Categorías del catálogo de ingredientes. */
public enum Categoria {
    PRODUCTO("Producto base"),
    BASE_PIZZA("Base de pizza"),
    BASE_HAMBURGUESA("Base de hamburguesa"),
    SALSA("Salsa"),
    QUESO("Queso"),
    PROTEINA("Proteína"),
    VEGETAL("Vegetal"),
    EXTRA("Extra");

    private final String etiqueta;

    Categoria(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
