package com.armatuplato;

import com.armatuplato.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Punto de entrada de ArmaTuPlato. */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignorada) {
            // si falla, se usa el aspecto por defecto de Swing
        }
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
