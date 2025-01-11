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

        // Botón para borrar una nota
        JButton deleteNoteButton = new JButton("Elimina Nota");
        deleteNoteButton.addActionListener(e -> deleteNote(email, notesPanel));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addNoteButton);
        buttonPanel.add(deleteNoteButton);
        add(new JScrollPane(notesPanel), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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

    private void deleteNote(String email, JPanel notesPanel) {
        App app = new App();
        List<Notes> userNotes = app.getUserNotes(email);

        if(userNotes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Titolo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar un cuadro de diálogo para poder seleccionar la nota a eliminar
        String[] titles = userNotes.stream().map(Notes::getTitle).toArray(String[]::new);
        String titleToDelete = (String) JOptionPane.showInputDialog(
                this,
                "Seleziona la nota da eliminare:",
                "Elimina Nota",
                JOptionPane.PLAIN_MESSAGE,
                null,
                titles,
                titles[0]
        );

        if (titleToDelete != null) {
            // Eliminar la nota del archivo JSON
            app.deleteNotes(email, titleToDelete);

            // Actualizar la interfaz eliminando la nota del panel
            notesPanel.removeAll(); // Limpiar el panel

            // Volver a cargar las notas del usuario
            List<Notes> updatedNotes = app.getUserNotes(email);
            for (Notes note : updatedNotes) {
                JTextArea noteArea = new JTextArea();
                noteArea.setText("Titolo: " + note.getTitle() + "\nContenuto: " + note.getContent());
                noteArea.setEditable(false);
                notesPanel.add(noteArea);
            }

            notesPanel.revalidate();
            notesPanel.repaint();

            JOptionPane.showMessageDialog(this, "Nota eliminata con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
