package com.armatuplato.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PATRÓN DECORATOR - ConcreteComponent (clase abstracta).
 * PATRÓN BUILDER - Product: lo construyen los builders.
 * Herencia: Pizza y Hamburguesa extienden esta clase.
 */
public abstract class Producto implements IProducto {
    private final Tamano tamano;
    private final Ingrediente tipo;      // precio base del producto (Pizza / Hamburguesa)
    private final Ingrediente base;
    private final Ingrediente salsa;
    private final Ingrediente queso;
    private final Ingrediente proteina;
    private final List<Ingrediente> vegetales;

    protected Producto(Tamano tamano, Ingrediente tipo, Ingrediente base, Ingrediente salsa,
                       Ingrediente queso, Ingrediente proteina, List<Ingrediente> vegetales) {
        this.tamano = tamano;
        this.tipo = tipo;
        this.base = base;
        this.salsa = salsa;
        this.queso = queso;
        this.proteina = proteina;
        this.vegetales = new ArrayList<>(vegetales);
    }

    public Tamano getTamano() {
        return tamano;
    }

    public Ingrediente getBase() {
        return base;
    }

    public Ingrediente getSalsa() {
        return salsa;
    }

    public Ingrediente getQueso() {
        return queso;
    }

    public Ingrediente getProteina() {
        return proteina;
    }

    public List<Ingrediente> getVegetales() {
        return Collections.unmodifiableList(vegetales);
    }

    /** Precio del producto base según tamaño (redondeado a la centena) más sus ingredientes. */
    @Override
    public double getPrecio() {
        double total = Math.round(tipo.getPrecio() * tamano.getFactor() / 100.0) * 100.0;
        total += base.getPrecio() + salsa.getPrecio() + queso.getPrecio() + proteina.getPrecio();
        for (Ingrediente v : vegetales) {
            total += v.getPrecio();
        }
        return total;
    }

    @Override
    public List<String> getExtras() {
        return Collections.emptyList();
    }

    /** Texto común de ingredientes; cada subclase lo usa a su manera (polimorfismo). */
    protected String detalleIngredientes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Salsa ").append(salsa.getNombre());
        sb.append(", queso ").append(queso.getNombre());
        sb.append(", ").append(proteina.getNombre());
        if (!vegetales.isEmpty()) {
            sb.append(", con ");
            for (int i = 0; i < vegetales.size(); i++) {
                if (i > 0) {
                    sb.append(i == vegetales.size() - 1 ? " y " : ", ");
                }
                sb.append(vegetales.get(i).getNombre().toLowerCase());
            }
        }
        return sb.toString();
    }
}
