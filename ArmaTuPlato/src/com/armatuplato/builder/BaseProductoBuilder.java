package com.armatuplato.builder;

import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.excepciones.ProductoIncompletoException;
import com.armatuplato.modelo.Catalogo;
import com.armatuplato.modelo.Categoria;
import com.armatuplato.modelo.Ingrediente;
import com.armatuplato.modelo.Tamano;

import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN BUILDER - estado y pasos comunes de los builders concretos
 * (los ingredientes se resuelven contra el catálogo al construir).
 */
public abstract class BaseProductoBuilder implements ProductoBuilder {
    private final Catalogo catalogo;
    private Tamano tamano;
    private String base;
    private String salsa;
    private String queso;
    private String proteina;
    private final List<String> vegetales = new ArrayList<>();

    protected BaseProductoBuilder(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    @Override
    public ProductoBuilder tamano(Tamano tamano) {
        this.tamano = tamano;
        return this;
    }

    @Override
    public ProductoBuilder base(String nombre) {
        this.base = nombre;
        return this;
    }

    @Override
    public ProductoBuilder salsa(String nombre) {
        this.salsa = nombre;
        return this;
    }

    @Override
    public ProductoBuilder queso(String nombre) {
        this.queso = nombre;
        return this;
    }

    @Override
    public ProductoBuilder proteina(String nombre) {
        this.proteina = nombre;
        return this;
    }

    @Override
    public ProductoBuilder agregarVegetal(String nombre) {
        if (nombre != null && !vegetales.contains(nombre)) {
            vegetales.add(nombre);
        }
        return this;
    }

    @Override
    public ProductoBuilder reset() {
        tamano = null;
        base = null;
        salsa = null;
        queso = null;
        proteina = null;
        vegetales.clear();
        return this;
    }

    // ---- ayudas para las subclases ----

    protected Catalogo getCatalogo() {
        return catalogo;
    }

    protected Tamano getTamano() {
        return tamano;
    }

    /** Verifica que estén todos los pasos obligatorios. */
    protected void validar() throws ProductoIncompletoException {
        if (tamano == null) {
            throw new ProductoIncompletoException("Falta elegir el tamaño.");
        }
        if (base == null) {
            throw new ProductoIncompletoException("Falta elegir la base.");
        }
        if (salsa == null) {
            throw new ProductoIncompletoException("Falta elegir la salsa.");
        }
        if (queso == null) {
            throw new ProductoIncompletoException("Falta elegir el queso.");
        }
        if (proteina == null) {
            throw new ProductoIncompletoException("Falta elegir la proteína.");
        }
    }

    protected Ingrediente tipo(String nombreProducto) throws ArmaTuPlatoException {
        return catalogo.buscar(Categoria.PRODUCTO, nombreProducto).copia();
    }

    protected Ingrediente base(Categoria categoriaBase) throws ArmaTuPlatoException {
        return catalogo.buscar(categoriaBase, base).copia();
    }

    protected Ingrediente salsaElegida() throws ArmaTuPlatoException {
        return catalogo.buscar(Categoria.SALSA, salsa).copia();
    }

    protected Ingrediente quesoElegido() throws ArmaTuPlatoException {
        return catalogo.buscar(Categoria.QUESO, queso).copia();
    }

    protected Ingrediente proteinaElegida() throws ArmaTuPlatoException {
        return catalogo.buscar(Categoria.PROTEINA, proteina).copia();
    }

    protected List<Ingrediente> vegetalesElegidos() throws ArmaTuPlatoException {
        List<Ingrediente> lista = new ArrayList<>();
        for (String v : vegetales) {
            lista.add(catalogo.buscar(Categoria.VEGETAL, v).copia());
        }
        return lista;
    }
}
