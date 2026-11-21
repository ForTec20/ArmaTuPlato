package com.armatuplato.ui;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

/** Ayudas para mostrar mensajes. */
final class Dialogos {
    private Dialogos() { }

    static void error(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "No se pudo completar la acción", JOptionPane.ERROR_MESSAGE);
    }

    static void aviso(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    static void texto(Component padre, String titulo, String contenido) {
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(640, 360));
        JOptionPane.showMessageDialog(padre, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}
