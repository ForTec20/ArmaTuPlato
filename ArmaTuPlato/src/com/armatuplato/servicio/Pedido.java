package com.armatuplato.servicio;

import com.armatuplato.excepciones.EstadoInvalidoException;
import com.armatuplato.excepciones.PedidoInvalidoException;
import com.armatuplato.modelo.EstadoPedido;
import com.armatuplato.modelo.IProducto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Pedido de un cliente: items, estado, fecha y totales. */
public class Pedido {
    /** Impoconsumo aplicado al subtotal (ponerlo en 0 si el negocio no lo cobra). */
    public static final double TASA_IMPUESTO = 0.08;
    private static final int CANTIDAD_MAXIMA = 50;

    private int id;
    private String cliente = "";
    private final List<ItemPedido> items = new ArrayList<>();
    private EstadoPedido estado = EstadoPedido.PENDIENTE;
    private final LocalDateTime fecha = LocalDateTime.now();

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) throws PedidoInvalidoException {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new PedidoInvalidoException("Debe escribir el nombre del cliente.");
        }
        this.cliente = cliente.trim();
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<ItemPedido> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void agregarItem(IProducto producto, int cantidad) throws PedidoInvalidoException {
        verificarModificable();
        if (cantidad <= 0 || cantidad > CANTIDAD_MAXIMA) {
            throw new PedidoInvalidoException("La cantidad debe estar entre 1 y " + CANTIDAD_MAXIMA + ".");
        }
        items.add(new ItemPedido(producto, cantidad));
    }

    public void quitarItem(int indice) throws PedidoInvalidoException {
        verificarModificable();
        if (indice < 0 || indice >= items.size()) {
            throw new PedidoInvalidoException("Seleccione el producto que desea quitar.");
        }
        items.remove(indice);
    }

    private void verificarModificable() throws PedidoInvalidoException {
        if (estado != EstadoPedido.PENDIENTE) {
            throw new PedidoInvalidoException("El pedido ya no se puede modificar (estado: " + estado + ").");
        }
    }

    public double getSubtotal() {
        double total = 0;
        for (ItemPedido item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double getImpuesto() {
        return Math.round(getSubtotal() * TASA_IMPUESTO);
    }

    public double getTotal() {
        return getSubtotal() + getImpuesto();
    }

    /** Pasa al siguiente estado: Pendiente -> En preparación -> Listo -> Entregado. */
    public void avanzarEstado() throws EstadoInvalidoException {
        switch (estado) {
            case PENDIENTE:
                estado = EstadoPedido.EN_PREPARACION;
                break;
            case EN_PREPARACION:
                estado = EstadoPedido.LISTO;
                break;
            case LISTO:
                estado = EstadoPedido.ENTREGADO;
                break;
            default:
                throw new EstadoInvalidoException("El pedido #" + id + " está " + estado
                        + " y no puede avanzar de estado.");
        }
    }

    /** Solo se puede cancelar antes de enviarlo a cocina. */
    public void cancelar() throws EstadoInvalidoException {
        if (estado != EstadoPedido.PENDIENTE) {
            throw new EstadoInvalidoException("Solo se pueden cancelar pedidos pendientes (este está: "
                    + estado + ").");
        }
        estado = EstadoPedido.CANCELADO;
    }
}
