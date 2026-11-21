package com.armatuplato.excepciones;

/** Excepción base (checked) de la aplicación. Las demás excepciones heredan de ella. */
public class ArmaTuPlatoException extends Exception {
    public ArmaTuPlatoException(String mensaje) {
        super(mensaje);
    }

    public ArmaTuPlatoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
