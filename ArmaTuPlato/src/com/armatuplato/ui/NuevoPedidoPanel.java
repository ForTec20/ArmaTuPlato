package com.armatuplato.ui;

import com.armatuplato.builder.HamburguesaBuilder;
import com.armatuplato.builder.PizzaBuilder;
import com.armatuplato.builder.ProductoBuilder;
import com.armatuplato.builder.ProductoDirector;
import com.armatuplato.decorator.Decoradores;
import com.armatuplato.excepciones.ArmaTuPlatoException;
import com.armatuplato.modelo.Catalogo;
import com.armatuplato.modelo.Categoria;
import com.armatuplato.modelo.IProducto;
import com.armatuplato.modelo.Ingrediente;
import com.armatuplato.modelo.Producto;
import com.armatuplato.modelo.Tamano;
import com.armatuplato.servicio.Facturacion;
import com.armatuplato.servicio.GestorPedidos;
import com.armatuplato.servicio.ItemPedido;
import com.armatuplato.servicio.Pedido;
import com.armatuplato.util.Formato;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Pestaña "Nuevo pedido": arma el producto paso a paso (Builder), le agrega extras (Decorator)
 * y lo suma al pedido en curso.
 */
public class NuevoPedidoPanel extends JPanel {
    private final Catalogo catalogo;
    private final GestorPedidos gestor;
    private final Runnable alConfirmar;
    private final ProductoDirector director = new ProductoDirector();

    private Pedido borrador = new Pedido();
    private boolean cargando = false;

    private final JTextField txtCliente = new JTextField(16);
    private final JComboBox<String> cmbTipo = new JComboBox<>(new String[] {"Pizza", "Hamburguesa"});
    private final JComboBox<Tamano> cmbTamano = new JComboBox<>(Tamano.values());
    private final JComboBox<String> cmbBase = new JComboBox<>();
    private final JComboBox<String> cmbSalsa = new JComboBox<>();
    private final JComboBox<String> cmbQueso = new JComboBox<>();
    private final JComboBox<String> cmbProteina = new JComboBox<>();
    private final DefaultListModel<String> modeloVegetales = new DefaultListModel<>();
    private final JList<String> lstVegetales = new JList<>(modeloVegetales);
    private final Map<String, JCheckBox> chkExtras = new LinkedHashMap<>();
    private final JSpinner spCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
    private final JTextArea txtVista = new JTextArea(5, 30);
    private final DefaultTableModel modeloItems = new DefaultTableModel(
            new String[] {"Producto", "Cant.", "P. unitario", "Subtotal"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tablaItems = new JTable(modeloItems);
    private final JLabel lblTotales = new JLabel(" ");

    public NuevoPedidoPanel(Catalogo catalogo, GestorPedidos gestor, Runnable alConfirmar) {
        this.catalogo = catalogo;
        this.gestor = gestor;
        this.alConfirmar = alConfirmar;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.WEST);
        add(crearResumen(), BorderLayout.CENTER);

        cargando = true;
        cargarOpciones();
        cargando = false;
        actualizarVista();
    }

    // ---------------------------------------------------------------- construcción de la UI

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Arma tu producto"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 4, 3, 4);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        int fila = 0;

        fila = agregarFila(panel, c, fila, "Cliente:", txtCliente);
        fila = agregarFila(panel, c, fila, "Producto:", cmbTipo);
        fila = agregarFila(panel, c, fila, "Tamaño:", cmbTamano);
        fila = agregarFila(panel, c, fila, "Base:", cmbBase);
        fila = agregarFila(panel, c, fila, "Salsa:", cmbSalsa);
        fila = agregarFila(panel, c, fila, "Queso:", cmbQueso);
        fila = agregarFila(panel, c, fila, "Proteína:", cmbProteina);

        lstVegetales.setVisibleRowCount(4);
        lstVegetales.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        fila = agregarFila(panel, c, fila, "Vegetales:", new JScrollPane(lstVegetales));

        JPanel extras = new JPanel();
        extras.setLayout(new javax.swing.BoxLayout(extras, javax.swing.BoxLayout.Y_AXIS));
        for (Ingrediente e : catalogo.porCategoria(Categoria.EXTRA)) {
            JCheckBox chk = new JCheckBox(e.getNombre());
            chk.addActionListener(ev -> actualizarVista());
            chkExtras.put(e.getNombre(), chk);
            extras.add(chk);
        }
        fila = agregarFila(panel, c, fila, "Extras:", extras);
        fila = agregarFila(panel, c, fila, "Cantidad:", spCantidad);

        JButton btnSugerido = new JButton("Cargar sugerido");
        btnSugerido.addActionListener(e -> cargarSugerido());
        JButton btnAgregar = new JButton("Agregar al pedido");
        btnAgregar.addActionListener(e -> agregarAlPedido());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        botones.add(btnSugerido);
        botones.add(btnAgregar);
        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 2;
        panel.add(botones, c);

        // listeners
        cmbTipo.addActionListener(e -> {
            if (cargando) {
                return;
            }
            cargando = true;
            cargarBases();
            cargando = false;
            actualizarVista();
        });
        for (JComboBox<?> cmb : new JComboBox<?>[] {cmbTamano, cmbBase, cmbSalsa, cmbQueso, cmbProteina}) {
            cmb.addActionListener(e -> actualizarVista());
        }
        lstVegetales.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarVista();
            }
        });
        return panel;
    }

    private int agregarFila(JPanel panel, GridBagConstraints c, int fila, String texto, java.awt.Component comp) {
        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 1;
        c.weightx = 0;
        panel.add(new JLabel(texto), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(comp, c);
        return fila + 1;
    }

    private JPanel crearResumen() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        txtVista.setEditable(false);
        txtVista.setLineWrap(true);
        txtVista.setWrapStyleWord(true);
        JScrollPane vista = new JScrollPane(txtVista);
        vista.setBorder(BorderFactory.createTitledBorder("Producto actual (descripción y precio)"));
        vista.setPreferredSize(new Dimension(400, 130));
        panel.add(vista, BorderLayout.NORTH);

        tablaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaItems.getColumnModel().getColumn(0).setPreferredWidth(360);
        tablaItems.getColumnModel().getColumn(1).setPreferredWidth(45);
        tablaItems.getColumnModel().getColumn(2).setPreferredWidth(90);
        tablaItems.getColumnModel().getColumn(3).setPreferredWidth(90);
        JScrollPane scroll = new JScrollPane(tablaItems);
        scroll.setBorder(BorderFactory.createTitledBorder("Pedido en curso"));
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnQuitar = new JButton("Quitar seleccionado");
        btnQuitar.addActionListener(e -> quitarSeleccionado());
        JButton btnLimpiar = new JButton("Cancelar pedido en curso");
        btnLimpiar.addActionListener(e -> nuevoBorrador());
        JButton btnConfirmar = new JButton("Confirmar pedido");
        btnConfirmar.addActionListener(e -> confirmar());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        botones.add(btnQuitar);
        botones.add(btnLimpiar);
        botones.add(btnConfirmar);

        JPanel sur = new JPanel(new BorderLayout(4, 4));
        sur.add(lblTotales, BorderLayout.NORTH);
        sur.add(botones, BorderLayout.SOUTH);
        panel.add(sur, BorderLayout.SOUTH);
        return panel;
    }

    // ---------------------------------------------------------------- carga de opciones

    private void cargarOpciones() {
        cargarBases();
        llenar(cmbSalsa, Categoria.SALSA);
        llenar(cmbQueso, Categoria.QUESO);
        llenar(cmbProteina, Categoria.PROTEINA);
        modeloVegetales.clear();
        for (Ingrediente i : catalogo.porCategoria(Categoria.VEGETAL)) {
            modeloVegetales.addElement(i.getNombre());
        }
    }

    private void cargarBases() {
        boolean pizza = "Pizza".equals(cmbTipo.getSelectedItem());
        llenar(cmbBase, pizza ? Categoria.BASE_PIZZA : Categoria.BASE_HAMBURGUESA);
    }

    private void llenar(JComboBox<String> combo, Categoria categoria) {
        combo.removeAllItems();
        for (Ingrediente i : catalogo.porCategoria(categoria)) {
            combo.addItem(i.getNombre());
        }
    }

    // ---------------------------------------------------------------- lógica

    /** Arma el producto con el Builder y le aplica los extras con el Decorator. */
    private IProducto armarProducto() throws ArmaTuPlatoException {
        ProductoBuilder builder = "Pizza".equals(cmbTipo.getSelectedItem())
                ? new PizzaBuilder(catalogo)
                : new HamburguesaBuilder(catalogo);

        builder.tamano((Tamano) cmbTamano.getSelectedItem())
                .base((String) cmbBase.getSelectedItem())
                .salsa((String) cmbSalsa.getSelectedItem())
                .queso((String) cmbQueso.getSelectedItem())
                .proteina((String) cmbProteina.getSelectedItem());
        for (String vegetal : lstVegetales.getSelectedValuesList()) {
            builder.agregarVegetal(vegetal);
        }
        IProducto producto = builder.construir();

        for (Map.Entry<String, JCheckBox> e : chkExtras.entrySet()) {
            if (e.getValue().isSelected()) {
                producto = Decoradores.aplicar(producto, catalogo.buscar(Categoria.EXTRA, e.getKey()));
            }
        }
        return producto;
    }

    private void actualizarVista() {
        if (cargando) {
            return;
        }
        try {
            IProducto producto = armarProducto();
            txtVista.setText(producto.getDescripcion() + "\n\nPrecio por unidad: "
                    + Formato.moneda(producto.getPrecio()));
        } catch (ArmaTuPlatoException ex) {
            txtVista.setText("Producto incompleto: " + ex.getMessage());
        }
    }

    private void cargarSugerido() {
        ProductoBuilder builder = "Pizza".equals(cmbTipo.getSelectedItem())
                ? new PizzaBuilder(catalogo)
                : new HamburguesaBuilder(catalogo);
        try {
            Tamano tamano = (Tamano) cmbTamano.getSelectedItem();
            Producto p = "Pizza".equals(cmbTipo.getSelectedItem())
                    ? director.pizzaClasica(builder, tamano)
                    : director.hamburguesaClasica(builder, tamano);
            cargando = true;
            cmbBase.setSelectedItem(p.getBase().getNombre());
            cmbSalsa.setSelectedItem(p.getSalsa().getNombre());
            cmbQueso.setSelectedItem(p.getQueso().getNombre());
            cmbProteina.setSelectedItem(p.getProteina().getNombre());
            lstVegetales.clearSelection();
            for (Ingrediente v : p.getVegetales()) {
                int idx = modeloVegetales.indexOf(v.getNombre());
                if (idx >= 0) {
                    lstVegetales.addSelectionInterval(idx, idx);
                }
            }
            for (JCheckBox chk : chkExtras.values()) {
                chk.setSelected(false);
            }
            cargando = false;
            actualizarVista();
        } catch (ArmaTuPlatoException ex) {
            cargando = false;
            Dialogos.error(this, ex.getMessage());
        }
    }

    private void agregarAlPedido() {
        try {
            IProducto producto = armarProducto();
            borrador.agregarItem(producto, (Integer) spCantidad.getValue());
            refrescarItems();
        } catch (ArmaTuPlatoException ex) {
            Dialogos.error(this, ex.getMessage());
        }
    }

    private void quitarSeleccionado() {
        try {
            borrador.quitarItem(tablaItems.getSelectedRow());
            refrescarItems();
        } catch (ArmaTuPlatoException ex) {
            Dialogos.error(this, ex.getMessage());
        }
    }

    private void confirmar() {
        try {
            borrador.setCliente(txtCliente.getText());
            Pedido registrado = gestor.registrar(borrador);
            String comprobante = Facturacion.generarComprobante(registrado);
            String comanda = Facturacion.generarComanda(registrado);
            try {
                Path ruta = Facturacion.guardarComprobante(registrado);
                comprobante += "\n(Guardado en " + ruta + ")";
            } catch (ArmaTuPlatoException ex) {
                Dialogos.aviso(this, ex.getMessage());
            }
            Dialogos.texto(this, "Pedido #" + registrado.getId() + " confirmado",
                    comprobante + "\n\n==== COMANDA PARA COCINA ====\n" + comanda);
            nuevoBorrador();
            txtCliente.setText("");
            alConfirmar.run();
        } catch (ArmaTuPlatoException ex) {
            Dialogos.error(this, ex.getMessage());
        }
    }

    private void nuevoBorrador() {
        borrador = new Pedido();
        refrescarItems();
    }

    private void refrescarItems() {
        modeloItems.setRowCount(0);
        for (ItemPedido item : borrador.getItems()) {
            modeloItems.addRow(new Object[] {
                    item.getProducto().getDescripcion(),
                    item.getCantidad(),
                    Formato.moneda(item.getProducto().getPrecio()),
                    Formato.moneda(item.getSubtotal())});
        }
        lblTotales.setText("Subtotal: " + Formato.moneda(borrador.getSubtotal())
                + "   |   Impoconsumo (" + Math.round(Pedido.TASA_IMPUESTO * 100) + "%): "
                + Formato.moneda(borrador.getImpuesto())
                + "   |   TOTAL: " + Formato.moneda(borrador.getTotal()));
    }

    /** Recalcula la vista (por si cambiaron precios en el catálogo). */
    public void refrescar() {
        actualizarVista();
    }
}
