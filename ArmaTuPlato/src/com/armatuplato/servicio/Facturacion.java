package com.armatuplato.servicio;

import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.util.Formato;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Módulo de facturación: comprobante y comanda de cocina (texto). */
public final class Facturacion {
    private static final String LINEA = "------------------------------------------------------------";

    private Facturacion() { }

    public static String generarComprobante(Pedido pedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("ARMATUPLATO - COMPROBANTE DE PEDIDO\n").append(LINEA).append('\n');
        sb.append("Pedido #").append(pedido.getId()).append('\n');
        sb.append("Cliente: ").append(pedido.getCliente()).append('\n');
        sb.append("Fecha:   ").append(Formato.fechaHora(pedido.getFecha())).append('\n');
        sb.append(LINEA).append('\n');
        for (ItemPedido item : pedido.getItems()) {
            sb.append(item.getCantidad()).append(" x ").append(item.getProducto().getDescripcion()).append('\n');
            sb.append("     ").append(Formato.moneda(item.getProducto().getPrecio()))
                    .append(" c/u = ").append(Formato.moneda(item.getSubtotal())).append('\n');
        }
        sb.append(LINEA).append('\n');
        sb.append(String.format("Subtotal:%n  %s%n", Formato.moneda(pedido.getSubtotal())));
        sb.append(String.format("Impoconsumo (%d%%):%n  %s%n", Math.round(Pedido.TASA_IMPUESTO * 100),
                Formato.moneda(pedido.getImpuesto())));
        sb.append("TOTAL A PAGAR: ").append(Formato.moneda(pedido.getTotal())).append('\n');
        return sb.toString();
    }

    public static String generarComanda(Pedido pedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("COMANDA DE COCINA - Pedido #").append(pedido.getId()).append('\n').append(LINEA).append('\n');
        sb.append("Cliente: ").append(pedido.getCliente()).append("   Hora: ")
                .append(Formato.hora(pedido.getFecha())).append('\n').append(LINEA).append('\n');
        int n = 1;
        for (ItemPedido item : pedido.getItems()) {
            sb.append(n++).append(") ").append(item.getCantidad()).append(" x ")
                    .append(item.getProducto().getDescripcion()).append("\n\n");
        }
        return sb.toString();
    }

    /** Guarda el comprobante en la carpeta "comprobantes". */
    public static Path guardarComprobante(Pedido pedido) throws ArmaTuPlatoException {
        try {
            Path carpeta = Paths.get("comprobantes");
            Files.createDirectories(carpeta);
            Path archivo = carpeta.resolve("pedido_" + pedido.getId() + ".txt");
            Files.write(archivo, generarComprobante(pedido).getBytes(StandardCharsets.UTF_8));
            return archivo;
        } catch (IOException e) {
            throw new ArmaTuPlatoException("No se pudo guardar el comprobante: " + e.getMessage(), e);
        }
    }
}
