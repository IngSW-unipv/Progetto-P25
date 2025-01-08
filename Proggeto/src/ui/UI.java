package ui;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {

    public UI() {
        // Configurar la ventana (JFrame)
        setTitle("Blocco Note - Benvenuto"); // Título en italiano
        setSize(400, 300); // Ancho x Alto
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes
        JLabel label = new JLabel("Benvenuto al Blocco Note"); // Mensaje de bienvenida en italiano
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Cambiar estilo de fuente
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto

        // Crear botones
        JButton loginButton = new JButton("Accedi"); // Botón para iniciar sesión en italiano
        JButton registerButton = new JButton("Registrati"); // Botón para registrarse en italiano

        // Añadir ActionListeners para los botones
        loginButton.addActionListener(e -> showLoginScreen());
        registerButton.addActionListener(e -> showRegisterScreen());

        // Configurar layout
        setLayout(new BorderLayout());

        // Añadir componentes al JFrame
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }

    //PRUEBA DE FUNCIONAMINETO DE LOS BOTONES
    private void showLoginScreen() {
        JOptionPane.showMessageDialog(this, "Qui verrà visualizzata la schermata di Login", "Login", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRegisterScreen() {
        JOptionPane.showMessageDialog(this, "Qui verrà visualizzata la schermata di Registrazione", "Registrazione", JOptionPane.INFORMATION_MESSAGE);
    }
}
