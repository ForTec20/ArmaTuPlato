package com.armatuplato.ui;

import com.armatuplato.modelo.Catalogo;
import com.armatuplato.servicio.GestorPedidos;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

/** Ventana principal con las pestañas de la aplicación. */
public class MainFrame extends JFrame {

    public MainFrame() {
        super("ArmaTuPlato - Pedidos de pizzas y hamburguesas");
        Catalogo catalogo = new Catalogo();
        GestorPedidos gestor = new GestorPedidos();

        PedidosPanel pedidos = new PedidosPanel(gestor);
        ReportesPanel reportes = new ReportesPanel(gestor);
        NuevoPedidoPanel nuevo = new NuevoPedidoPanel(catalogo, gestor, () -> {
            pedidos.refrescar();
            reportes.refrescar();
        });
        CatalogoPanel panelCatalogo = new CatalogoPanel(catalogo, nuevo::refrescar);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Nuevo pedido", nuevo);
        tabs.addTab("Pedidos del día", pedidos);
        tabs.addTab("Catálogo y precios", panelCatalogo);
        tabs.addTab("Reportes", reportes);
        tabs.addChangeListener(e -> {
            pedidos.refrescar();
            reportes.refrescar();
        });

        setContentPane(tabs);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 560));
        pack();
        setLocationRelativeTo(null);
    }
}
