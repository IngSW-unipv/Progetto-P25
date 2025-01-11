package ui;

import proggeto.App;
import proggeto.Notes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UINotes extends JFrame {

    public UINotes(String email) {
        App app = new App();

        setTitle("Le Tue Note");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Obtener las notas del usuario
        List<Notes> userNotes = app.getUserNotes(email);

        // Panel para mostrar las notas
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS));

        for (Notes note : userNotes) {
            JTextArea noteArea = new JTextArea();
            noteArea.setText("Titolo: " + note.getTitle() + "\nContenuto: " + note.getContent());
            noteArea.setEditable(false);
            notesPanel.add(noteArea);
        }

        // Botón para añadir una nueva nota
        JButton addNoteButton = new JButton("Aggiungi Nota");
        addNoteButton.addActionListener(e -> addNewNote(email, notesPanel));

        add(new JScrollPane(notesPanel), BorderLayout.CENTER);
        add(addNoteButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addNewNote(String email, JPanel notesPanel) {
        JTextField titleField = new JTextField(20);
        JTextArea contentArea = new JTextArea(5, 20);

        Object[] message = {
                "Titolo:", titleField,
                "Contenuto:", new JScrollPane(contentArea)
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Aggiungi Nota", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String content = contentArea.getText();

            if (title.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Riempire tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear la nueva nota
            Notes newNote = new Notes(title, content, email);

            // Añadir la nota al archivo JSON
            App app = new App();
            app.addNote(newNote);

            // Actualizar la lista de notas
            JTextArea noteArea = new JTextArea();
            noteArea.setText("Titolo: " + newNote.getTitle() + "\nContenuto: " + newNote.getContent());
            noteArea.setEditable(false);
            notesPanel.add(noteArea);
            notesPanel.revalidate();
        }
    }
}
