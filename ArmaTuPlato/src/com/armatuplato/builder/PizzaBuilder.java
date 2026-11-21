package com.armatuplato.builder;

import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.modelo.Catalogo;
import com.armatuplato.modelo.Categoria;
import com.armatuplato.modelo.Pizza;
import com.armatuplato.modelo.Producto;

/** PATRÓN BUILDER - ConcreteBuilder que arma pizzas. */
public class PizzaBuilder extends BaseProductoBuilder {

    public PizzaBuilder(Catalogo catalogo) {
        super(catalogo);
    }

    @Override
    public Producto construir() throws ArmaTuPlatoException {
        validar();
        return new Pizza(getTamano(), tipo("Pizza"), base(Categoria.BASE_PIZZA), salsaElegida(),
                quesoElegido(), proteinaElegida(), vegetalesElegidos());
    }
}
