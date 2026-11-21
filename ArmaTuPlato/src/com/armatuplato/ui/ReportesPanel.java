package com.armatuplato.ui;

import com.armatuplato.servicio.GestorPedidos;
import com.armatuplato.util.Formato;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Map;

/** Pestaña "Reportes": ventas del día, productos más pedidos y extras más solicitados. */
public class ReportesPanel extends JPanel {
    private final GestorPedidos gestor;
    private final JTextArea area = new JTextArea();

    public ReportesPanel(GestorPedidos gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton btn = new JButton("Actualizar");
        btn.addActionListener(e -> refrescar());
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sur.add(btn);
        add(sur, BorderLayout.SOUTH);
        refrescar();
    }

    public void refrescar() {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE VENTAS DEL DÍA\n=========================\n\n");
        sb.append("Pedidos vendidos (sin cancelados): ").append(gestor.cantidadPedidosVendidos()).append('\n');
        sb.append("Total vendido: ").append(Formato.moneda(gestor.ventasDelDia())).append("\n\n");
        tabla(sb, "PRODUCTOS MÁS PEDIDOS (unidades)", gestor.productosMasPedidos());
        tabla(sb, "EXTRAS MÁS SOLICITADOS (veces)", gestor.extrasMasSolicitados());
        area.setText(sb.toString());
        area.setCaretPosition(0);
    }

    private void tabla(StringBuilder sb, String titulo, Map<String, Integer> datos) {
        sb.append(titulo).append('\n');
        if (datos.isEmpty()) {
            sb.append("  (sin datos todavía)\n");
        }
        for (Map.Entry<String, Integer> e : datos.entrySet()) {
            sb.append(String.format("  %-18s %3d  %s%n", e.getKey(), e.getValue(), "#".repeat(e.getValue())));
        }
        sb.append('\n');
    }
}
