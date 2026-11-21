package com.armatuplato.modelo;

import com.armatuplato.decorator.BebidaDecorator;
import com.armatuplato.decorator.PapasDecorator;
import com.armatuplato.decorator.QuesoExtraDecorator;
import com.armatuplato.decorator.SalsaEspecialDecorator;
import com.armatuplato.decorator.TocinetaDecorator;
import com.armatuplato.excepciones.CatalogoException;

import java.util.ArrayList;
import java.util.List;

/** Catálogo de ingredientes, extras y precios (F12). */
public class Catalogo {
    private final List<Ingrediente> ingredientes = new ArrayList<>();

    public Catalogo() {
        cargarPorDefecto();
    }

    private void cargarPorDefecto() {
        agregar(Categoria.PRODUCTO, "Pizza", 18000);
        agregar(Categoria.PRODUCTO, "Hamburguesa", 14000);

        agregar(Categoria.BASE_PIZZA, "Masa delgada", 0);
        agregar(Categoria.BASE_PIZZA, "Masa gruesa", 2000);
        agregar(Categoria.BASE_HAMBURGUESA, "Pan normal", 0);
        agregar(Categoria.BASE_HAMBURGUESA, "Pan integral", 1500);

        agregar(Categoria.SALSA, "Tomate", 0);
        agregar(Categoria.SALSA, "BBQ", 1000);
        agregar(Categoria.SALSA, "Ajo", 1000);
        agregar(Categoria.SALSA, "Picante", 1000);

        agregar(Categoria.QUESO, "Mozzarella", 3000);
        agregar(Categoria.QUESO, "Cheddar", 2500);
        agregar(Categoria.QUESO, "Cuatro quesos", 4500);

        agregar(Categoria.PROTEINA, "Pollo", 4000);
        agregar(Categoria.PROTEINA, "Carne", 5000);
        agregar(Categoria.PROTEINA, "Jamón", 3500);
        agregar(Categoria.PROTEINA, "Chorizo", 4500);

        agregar(Categoria.VEGETAL, "Tomate", 800);
        agregar(Categoria.VEGETAL, "Cebolla", 500);
        agregar(Categoria.VEGETAL, "Lechuga", 500);
        agregar(Categoria.VEGETAL, "Pimentón", 700);
        agregar(Categoria.VEGETAL, "Champiñones", 1500);
        agregar(Categoria.VEGETAL, "Maíz", 1000);

        agregar(Categoria.EXTRA, QuesoExtraDecorator.NOMBRE, 2500);
        agregar(Categoria.EXTRA, TocinetaDecorator.NOMBRE, 3000);
        agregar(Categoria.EXTRA, SalsaEspecialDecorator.NOMBRE, 1500);
        agregar(Categoria.EXTRA, PapasDecorator.NOMBRE, 5000);
        agregar(Categoria.EXTRA, BebidaDecorator.NOMBRE, 4000);
    }

    private void agregar(Categoria categoria, String nombre, double precio) {
        ingredientes.add(new Ingrediente(categoria, nombre, precio));
    }

    public List<Ingrediente> todos() {
        return new ArrayList<>(ingredientes);
    }

    public List<Ingrediente> porCategoria(Categoria categoria) {
        List<Ingrediente> resultado = new ArrayList<>();
        for (Ingrediente i : ingredientes) {
            if (i.getCategoria() == categoria) {
                resultado.add(i);
            }
        }
        return resultado;
    }

    public Ingrediente buscar(Categoria categoria, String nombre) throws CatalogoException {
        for (Ingrediente i : ingredientes) {
            if (i.getCategoria() == categoria && i.getNombre().equals(nombre)) {
                return i;
            }
        }
        throw new CatalogoException("No existe \"" + nombre + "\" en la categoría " + categoria + ".");
    }
}
