package com.armatuplato.modelo;

/** Tamaño del producto. El factor multiplica el precio base del producto. */
public enum Tamano {
    PEQUENO("Pequeña", 1.0),
    MEDIANO("Mediana", 1.3),
    GRANDE("Grande", 1.6);

    private final String etiqueta;
    private final double factor;

    Tamano(String etiqueta, double factor) {
        this.etiqueta = etiqueta;
        this.factor = factor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getFactor() {
        return factor;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
