package ui;
import proggeto.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminFunctions {
    private final String usersFile = "usuarios.json";
    private final String notesFile = "notes.json";
    private final JSONParser jsonParser = new JSONParser();

    // Obtener la lista de todos los usuarios registrados
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        File file = new File(usersFile);

        if (!file.exists()) return users;

        try (FileReader reader = new FileReader(file)) {
            JSONArray usersList = (JSONArray) jsonParser.parse(reader);
            for (Object obj : usersList) {
                JSONObject userJson = (JSONObject) obj;
                String email = (String) userJson.get("emailUser");
                users.add(email);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Obtener las notas de un usuario específico
    public List<Notes> getUserNotes(String email) {
        List<Notes> notes = new ArrayList<>();
        File file = new File(notesFile);

        if (!file.exists()) return notes;

        try (FileReader reader = new FileReader(file)) {
            JSONArray notesList = (JSONArray) jsonParser.parse(reader);
            for (Object noteObj : notesList) {
                JSONObject json = (JSONObject) noteObj;
                Notes note = Notes.fromJSON(json);
                if (note.getAuthor().equals(email)) {
                    notes.add(note);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return notes;
    }

    // Eliminar una nota de cualquier usuario
    public void deleteUserNote(String email, String title) {
        File file = new File(notesFile);

        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            JSONArray notes = (JSONArray) jsonParser.parse(reader);
            boolean noteDeleted = notes.removeIf(noteObj -> {
                JSONObject noteJson = (JSONObject) noteObj;
                return noteJson.get("author").equals(email) && noteJson.get("title").equals(title);
            });

            if (noteDeleted) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(notes.toJSONString());
                    System.out.println("Nota eliminada con éxito.");
                }
            } else {
                System.out.println("Nota no encontrada.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Verificar si un usuario es administrador
    public boolean isAdmin(String email) {
        return email.equals("admin@example.com");
    }
}

class UIAdmin extends JFrame {
    private AdminFunctions adminFunctions;

    public UIAdmin() {
        adminFunctions = new AdminFunctions();

        setTitle("Admin - Lista de Usuarios");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultListModel<String> userModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        panel.add(scrollPane, BorderLayout.CENTER);

        List<String> users = adminFunctions.getAllUsers();
        for (String user : users) {
            userModel.addElement(user);
        }

        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    new UIUserNotes(selectedUser);
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}

class UIUserNotes extends JFrame {
    public UIUserNotes(String email) {
        setTitle("Notas de " + email);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        AdminFunctions adminFunctions = new AdminFunctions();
        List<Notes> notes = adminFunctions.getUserNotes(email);

        DefaultListModel<String> notesModel = new DefaultListModel<>();
        JList<String> notesList = new JList<>(notesModel);
        JScrollPane scrollPane = new JScrollPane(notesList);
        add(scrollPane, BorderLayout.CENTER);

        for (Notes note : notes) {
            notesModel.addElement(note.getTitle());
        }

        setVisible(true);
    }
}
