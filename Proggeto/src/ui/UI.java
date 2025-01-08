package ui;

import javax.swing.*;

public class UI extends JFrame {
    public UI() {
        // Configurar la ventana (JFrame)
        setTitle("BUSTOS EN MARRUECOS");
        setSize(400, 300); // Ancho x Alto
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes
        JLabel label = new JLabel("¡HOLA CARLOS!");
        JButton button = new JButton("Haz clic aquí si Bustos estuvo en Marruecos");

        // Añadir componentes al JFrame
        add(label);
        add(button);

        // Configurar layout
        setLayout(new java.awt.FlowLayout());

        // Hacer visible la ventana
        setVisible(true);
    }



}
