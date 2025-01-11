package ui;

import proggeto.App;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public UI() {



        // Configurar la ventana (JFrame)
        setTitle("Blocco Note - Benvenuto"); // Título en italiano
        setSize(400, 300); // Ancho x Alto
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes
        JLabel welcomelabel = new JLabel("Benvenuto al Blocco Note"); // Mensaje de bienvenida en italiano
        welcomelabel.setFont(new Font("Arial", Font.BOLD, 16)); // Cambiar estilo de fuente
        welcomelabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto

        // Crear botones
        JButton loginButton = new JButton("Accedi"); // Botón para iniciar sesión en italiano
        JButton registerButton = new JButton("Registrati"); // Botón para registrarse en italiano

        // Añadir ActionListeners para los botones
        loginButton.addActionListener(e -> showLoginFields());
        registerButton.addActionListener(e -> showRegisterFields());

        // Configurar layout
        setLayout(new BorderLayout());

        // Añadir componentes al JFrame
        add(welcomelabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.CENTER);



        // Hacer visible la ventana
        setVisible(true);
    }

    //PRUEBA DE FUNCIONAMINETO DE LOS BOTONES
    // Método para mostrar los campos de login
    public void showLoginFields() {

        // Crear un nuevo panel para los campos de login
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Crear etiquetas y campos de texto
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Botón para confirmar el login
        JButton confirmButton = new JButton("Conferma");

        // Añadir ActionListener al botón de confirmar login
        confirmButton.addActionListener(e -> processLogin());

        // Añadir los componentes al panel
        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Espacio vacío
        loginPanel.add(confirmButton);

        // Mostrar el panel en un cuadro de diálogo modal
        JOptionPane.showMessageDialog(this, loginPanel, "Accedi", JOptionPane.PLAIN_MESSAGE);
    }

    // Procesar el LOgin
    public void processLogin() {
        String email = emailField.getText(); // Obtener el correo ingresado
        String password = new String(passwordField.getPassword()); // Obtener la contraseña ingresada

        // Crear una instancia de la clase App para validar el login
        App app = new App();

        if (app.loginScreen(email, password)) {
            JOptionPane.showMessageDialog(this, "Accesso effettuato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            new UINotes(email);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali non valide.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar registro

    public void showRegisterFields() {
        // Crear un nuevo panel para los campos de registro
        JPanel registerPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Crear etiquetas y campos de texto
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Botón para confirmar el registro
        JButton confirmButton = new JButton("Conferma");

        // Añadir ActionListener al botón de confirmar registro
        confirmButton.addActionListener(e -> processRegister());

        // Añadir los componentes al panel
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel()); // Espacio vacío
        registerPanel.add(confirmButton);

        // Mostrar el panel en un cuadro de diálogo modal
        JOptionPane.showMessageDialog(this, registerPanel, "Registrati", JOptionPane.PLAIN_MESSAGE);
    }

    // Método para procesar el registro
    public void processRegister() {
        String email = emailField.getText(); // Obtener el correo ingresado
        String password = new String(passwordField.getPassword()); // Obtener la contraseña ingresada

        // Crear una instancia de la clase App para realizar el registro
        App app = new App();

        if (email.isEmpty() || password.isEmpty()) {
            // Validar que los campos no estén vacíos
            JOptionPane.showMessageDialog(this, "Per favore, riempi tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
        } else {
            app.registerScreen(email, password); // Llamar al método para registrar el usuario
            JOptionPane.showMessageDialog(this, "Registrati", "Successo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
