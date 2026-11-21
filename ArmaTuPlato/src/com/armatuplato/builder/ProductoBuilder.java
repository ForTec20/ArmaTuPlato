package com.armatuplato.builder;

import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.modelo.Producto;
import com.armatuplato.modelo.Tamano;

/**
 * PATRÓN BUILDER - Builder (interfaz).
 * Define los pasos para armar un producto. Los métodos devuelven el mismo builder (interfaz fluida).
 */
public interface ProductoBuilder {
    ProductoBuilder tamano(Tamano tamano);

    ProductoBuilder base(String nombre);

    ProductoBuilder salsa(String nombre);

    ProductoBuilder queso(String nombre);

    ProductoBuilder proteina(String nombre);

    ProductoBuilder agregarVegetal(String nombre);

    /** Deja el builder listo para armar otro producto. */
    ProductoBuilder reset();

    /** Devuelve el producto terminado o lanza una excepción si faltan pasos. */
    Producto construir() throws ArmaTuPlatoException;
}
