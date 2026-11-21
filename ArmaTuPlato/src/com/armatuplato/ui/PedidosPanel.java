package com.armatuplato.ui;

import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.servicio.Facturacion;
import com.armatuplato.servicio.GestorPedidos;
import com.armatuplato.servicio.Pedido;
import com.armatuplato.util.Formato;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/** Pestaña "Pedidos del día": consulta, cambio de estado, cancelación, comprobante y comanda. */
public class PedidosPanel extends JPanel {
    private final GestorPedidos gestor;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[] {"ID", "Cliente", "Hora", "Estado", "Total"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public PedidosPanel(GestorPedidos gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnAvanzar = new JButton("Avanzar estado");
        btnAvanzar.addActionListener(e -> accion(() -> gestor.avanzarEstado(idSeleccionado())));
        JButton btnCancelar = new JButton("Cancelar pedido");
        btnCancelar.addActionListener(e -> accion(() -> gestor.cancelar(idSeleccionado())));
        JButton btnComprobante = new JButton("Ver comprobante");
        btnComprobante.addActionListener(e -> ver(false));
        JButton btnComanda = new JButton("Ver comanda");
        btnComanda.addActionListener(e -> ver(true));
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> refrescar());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        botones.add(btnAvanzar);
        botones.add(btnCancelar);
        botones.add(btnComprobante);
        botones.add(btnComanda);
        botones.add(btnActualizar);
        add(botones, BorderLayout.SOUTH);
    }

    @FunctionalInterface
    private interface Accion {
        void ejecutar() throws ArmaTuPlatoException;
    }

    private void accion(Accion accion) {
        try {
            accion.ejecutar();
            refrescar();
        } catch (ArmaTuPlatoException ex) {
            Dialogos.error(this, ex.getMessage());
        }
    }

    private int idSeleccionado() throws ArmaTuPlatoException {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            throw new ArmaTuPlatoException("Seleccione un pedido de la lista.");
        }
        return (Integer) modelo.getValueAt(fila, 0);
    }

    private void ver(boolean comanda) {
        try {
            Pedido p = gestor.buscar(idSeleccionado());
            Dialogos.texto(this, (comanda ? "Comanda" : "Comprobante") + " - pedido #" + p.getId(),
                    comanda ? Facturacion.generarComanda(p) : Facturacion.generarComprobante(p));
        } catch (ArmaTuPlatoException ex) {
            Dialogos.error(this, ex.getMessage());
        }
    }

    public void refrescar() {
        modelo.setRowCount(0);
        for (Pedido p : gestor.pedidosDelDia()) {
            modelo.addRow(new Object[] {p.getId(), p.getCliente(), Formato.hora(p.getFecha()),
                    p.getEstado(), Formato.moneda(p.getTotal())});
        }
    }
}
