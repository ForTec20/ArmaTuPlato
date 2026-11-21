package com.armatuplato.builder;

import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.modelo.Catalogo;
import com.armatuplato.modelo.Categoria;
import com.armatuplato.modelo.Hamburguesa;
import com.armatuplato.modelo.Producto;

/** PATRÓN BUILDER - ConcreteBuilder que arma hamburguesas. */
public class HamburguesaBuilder extends BaseProductoBuilder {

    public HamburguesaBuilder(Catalogo catalogo) {
        super(catalogo);
    }

    @Override
    public Producto construir() throws ArmaTuPlatoException {
        validar();
        return new Hamburguesa(getTamano(), tipo("Hamburguesa"), base(Categoria.BASE_HAMBURGUESA), salsaElegida(),
                quesoElegido(), proteinaElegida(), vegetalesElegidos());
    }
}
