package com.armatuplato.servicio;

import com.armatuplato.excepciones.EstadoInvalidoException;
import com.armatuplato.excepciones.PedidoInvalidoException;
import com.armatuplato.modelo.EstadoPedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Módulo de gestión de pedidos y de reportes (en memoria). */
public class GestorPedidos {
    private final List<Pedido> pedidos = new ArrayList<>();
    private int siguienteId = 1;

    public Pedido registrar(Pedido pedido) throws PedidoInvalidoException {
        if (pedido.getCliente().isEmpty()) {
            throw new PedidoInvalidoException("Debe escribir el nombre del cliente.");
        }
        if (pedido.getItems().isEmpty()) {
            throw new PedidoInvalidoException("El pedido no tiene productos.");
        }
        pedido.setId(siguienteId++);
        pedidos.add(pedido);
        return pedido;
    }

    public Pedido buscar(int id) throws PedidoInvalidoException {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new PedidoInvalidoException("No existe el pedido #" + id + ".");
    }

    public void avanzarEstado(int id) throws PedidoInvalidoException, EstadoInvalidoException {
        buscar(id).avanzarEstado();
    }

    public void cancelar(int id) throws PedidoInvalidoException, EstadoInvalidoException {
        buscar(id).cancelar();
    }

    public List<Pedido> pedidosDelDia() {
        List<Pedido> resultado = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (Pedido p : pedidos) {
            if (p.getFecha().toLocalDate().equals(hoy)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private List<Pedido> vendidosDelDia() {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidosDelDia()) {
            if (p.getEstado() != EstadoPedido.CANCELADO) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public double ventasDelDia() {
        double total = 0;
        for (Pedido p : vendidosDelDia()) {
            total += p.getTotal();
        }
        return total;
    }

    public int cantidadPedidosVendidos() {
        return vendidosDelDia().size();
    }

    /** Unidades vendidas por tipo de producto (Pizza / Hamburguesa), de mayor a menor. */
    public Map<String, Integer> productosMasPedidos() {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Pedido p : vendidosDelDia()) {
            for (ItemPedido item : p.getItems()) {
                conteo.merge(item.getProducto().getNombre(), item.getCantidad(), Integer::sum);
            }
        }
        return ordenar(conteo);
    }

    /** Veces que se pidió cada extra (por unidad vendida), de mayor a menor. */
    public Map<String, Integer> extrasMasSolicitados() {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Pedido p : vendidosDelDia()) {
            for (ItemPedido item : p.getItems()) {
                for (String extra : item.getProducto().getExtras()) {
                    conteo.merge(extra, item.getCantidad(), Integer::sum);
                }
            }
        }
        return ordenar(conteo);
    }

    private Map<String, Integer> ordenar(Map<String, Integer> conteo) {
        Map<String, Integer> ordenado = new LinkedHashMap<>();
        conteo.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(e -> ordenado.put(e.getKey(), e.getValue()));
        return ordenado;
    }
}
