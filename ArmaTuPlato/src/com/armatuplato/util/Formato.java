package com.armatuplato.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Utilidades de formato (moneda colombiana y fechas). */
public final class Formato {
    private static final DecimalFormat MONEDA =
            new DecimalFormat("$#,##0", DecimalFormatSymbols.getInstance(Locale.forLanguageTag("es-CO")));
    private static final DateTimeFormatter FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    private Formato() { }

    public static String moneda(double valor) {
        return MONEDA.format(valor);
    }

    public static String fechaHora(LocalDateTime fecha) {
        return FECHA_HORA.format(fecha);
    }

    public static String hora(LocalDateTime fecha) {
        return HORA.format(fecha);
    }
}
