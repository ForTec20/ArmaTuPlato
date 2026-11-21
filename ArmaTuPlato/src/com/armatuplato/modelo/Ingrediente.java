package com.armatuplato.modelo;

import com.armatuplato.excepciones.CatalogoException;

/** Ingrediente (o extra) del catálogo. Atributos privados: encapsulamiento. */
public class Ingrediente {
    private final Categoria categoria;
    private final String nombre;
    private double precio;

    public Ingrediente(Categoria categoria, String nombre, double precio) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    /** Cambia el precio validando que no sea negativo. */
    public void setPrecio(double precio) throws CatalogoException {
        if (Double.isNaN(precio) || precio < 0) {
            throw new CatalogoException("El precio de \"" + nombre + "\" no puede ser negativo.");
        }
        this.precio = precio;
    }

    /** Copia con el precio actual: así un pedido no cambia si luego se edita el catálogo. */
    public Ingrediente copia() {
        return new Ingrediente(categoria, nombre, precio);
    }
}
