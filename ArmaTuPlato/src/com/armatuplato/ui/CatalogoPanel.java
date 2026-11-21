package com.armatuplato.ui;

import com.armatuplato.excepciones.CatalogoException;
import com.armatuplato.modelo.Catalogo;
import com.armatuplato.modelo.Ingrediente;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.util.List;

/** Pestaña "Catálogo": el administrador edita los precios (doble clic en la columna Precio). */
public class CatalogoPanel extends JPanel {
    private final List<Ingrediente> filas;
    private final Runnable alCambiar;

    public CatalogoPanel(Catalogo catalogo, Runnable alCambiar) {
        this.filas = catalogo.todos();
        this.alCambiar = alCambiar;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JLabel("Doble clic sobre un precio para modificarlo (en pesos, sin puntos)."), BorderLayout.NORTH);
        add(new JScrollPane(new JTable(new ModeloCatalogo())), BorderLayout.CENTER);
    }

    private class ModeloCatalogo extends AbstractTableModel {
        private final String[] columnas = {"Categoría", "Nombre", "Precio"};

        @Override
        public int getRowCount() {
            return filas.size();
        }

        @Override
        public int getColumnCount() {
            return columnas.length;
        }

        @Override
        public String getColumnName(int columna) {
            return columnas[columna];
        }

        @Override
        public Object getValueAt(int fila, int columna) {
            Ingrediente i = filas.get(fila);
            switch (columna) {
                case 0:
                    return i.getCategoria();
                case 1:
                    return i.getNombre();
                default:
                    return String.valueOf(Math.round(i.getPrecio()));
            }
        }

        @Override
        public boolean isCellEditable(int fila, int columna) {
            return columna == 2;
        }

        @Override
        public void setValueAt(Object valor, int fila, int columna) {
            try {
                String texto = String.valueOf(valor).replace("$", "").replace(".", "").replace(",", "").trim();
                filas.get(fila).setPrecio(Double.parseDouble(texto));
                fireTableCellUpdated(fila, columna);
                alCambiar.run();
            } catch (NumberFormatException ex) {
                Dialogos.error(CatalogoPanel.this, "Escriba un número válido para el precio.");
            } catch (CatalogoException ex) {
                Dialogos.error(CatalogoPanel.this, ex.getMessage());
            }
        }
    }
}
