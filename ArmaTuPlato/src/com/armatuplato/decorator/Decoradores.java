package com.armatuplato.decorator;

import com.armatuplato.excepciones.CatalogoException;
import com.armatuplato.modelo.IProducto;
import com.armatuplato.modelo.Ingrediente;

/** Utilidad que envuelve un producto con el decorador que corresponde a un extra del catálogo. */
public final class Decoradores {
    private Decoradores() { }

    public static IProducto aplicar(IProducto producto, Ingrediente extra) throws CatalogoException {
        double precio = extra.getPrecio();
        switch (extra.getNombre()) {
            case QuesoExtraDecorator.NOMBRE:
                return new QuesoExtraDecorator(producto, precio);
            case TocinetaDecorator.NOMBRE:
                return new TocinetaDecorator(producto, precio);
            case SalsaEspecialDecorator.NOMBRE:
                return new SalsaEspecialDecorator(producto, precio);
            case PapasDecorator.NOMBRE:
                return new PapasDecorator(producto, precio);
            case BebidaDecorator.NOMBRE:
                return new BebidaDecorator(producto, precio);
            default:
                throw new CatalogoException("Extra desconocido: " + extra.getNombre());
        }
    }
}
