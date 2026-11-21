package com.armatuplato.modelo;

import java.util.List;

/**
 * PATRÓN DECORATOR - Component.
 * Interfaz común del producto base y de todos sus decoradores (extras).
 */
public interface IProducto {
    String getNombre();

    String getDescripcion();

    double getPrecio();

    /** Nombres de los extras agregados (vacío en el producto base). */
    List<String> getExtras();
}
