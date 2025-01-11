package proggeto;


import ui.UI;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    //launching the app
    public void run(){
        SwingUtilities.invokeLater(UI::new);
        addAdmin();
    }

    //constructor
    public App(){
        File file = new File(notesFile);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // Crear un JSON vacío
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    JSONParser jsonParser = new JSONParser();
    private final String notesFile = "notes.json";

    public void addAdmin() {

        File file = new File("usuarios.json");

        // Crear el archivo si no existe
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // JSON vacío
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileReader reader = new FileReader(file)) {
            // Leer el archivo JSON
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;

            // Crear un objeto Administrator
            Administrator admin = new Administrator("Admin", "123456", "admin@example.com");

            // Verificar si ya existe un administrador con el mismo email
            boolean exists = usersList.stream()
                    .anyMatch(user -> ((JSONObject) user).get("emailUser").equals(admin.getEmailUser()));

            if (!exists) {
                // Convertir el administrador en un JSON
                JSONObject adminJson = new JSONObject();
                adminJson.put("nameUser", admin.getNameUser());
                adminJson.put("emailUser", admin.getEmailUser());
                adminJson.put("passwordUser", admin.getPasswordUser());

                // Añadir el administrador a la lista de usuarios
                usersList.add(adminJson);

                // Guardar la lista actualizada en el archivo JSON
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(usersList.toJSONString());
                    System.out.println("Administrador añadido correctamente.");
                }
            } else {
                // Salida de texto para anunciar que ya existe el Administrador
                System.out.println("El administrador ya existe. No se añadirá un duplicado.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public boolean loginScreen(String email, String password) {

        File file = new File("usuarios.json");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Non ci sono utenti registrati.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try(FileReader reader = new FileReader(file)){
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;

            //verificar si las credenciales coinciden
            for (Object user : usersList) {
                JSONObject userJson = (JSONObject) user;
                String storedEmail = (String) userJson.get("emailUser");
                String storedPassword = (String) userJson.get("passwordUser");

                if(storedEmail.equals(email)&& storedPassword.equals(password)){
                    return true;
                }
            }
        }catch(IOException  | ParseException e){
            e.printStackTrace();
        }

        return false;
    }

    public void registerScreen(String email, String password) {

        File file = new File("usuarios.json");

        // Probamos si existe el json, sino se crea
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // JSON vacío
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(FileReader reader = new FileReader(file)){
            // Leer el archivo JSON
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;

            // Verificar si el usuario ya está registrado
            boolean exists = usersList.stream()
                    .anyMatch(user -> ((JSONObject) user).get("emailUser").equals(email));

            if (exists) {
                // Mostrar un mensaje si el usuario ya está registrado
                System.out.println("L'utente è già registrato.");
                return; // Salir del metodo
            }

            // Si no está registrado, agregar el nuevo usuario
            JSONObject newUser = new JSONObject();
            newUser.put("emailUser", email);
            newUser.put("passwordUser", password);

            usersList.add(newUser); // Añadir el nuevo usuario al JSONArray

            // Guardar el archivo actualizado
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(usersList.toJSONString());
                System.out.println("Registrazione effettuata con successo.");
            }

        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public List<Notes> getUserNotes(String email){

        List<Notes> notes = new ArrayList<>();
        File file = new File(notesFile);

        // Crear el archivo si no existe
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // Crear un JSON vacío
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(FileReader reader = new FileReader(file)){
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



    public void addNote (Notes note) {

        File file = new File(notesFile);

        // Crear el archivo si no existe
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // Crear un JSON vacío
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileReader reader = new FileReader(file)){
            JSONArray notes = (JSONArray) jsonParser.parse(reader);


            notes.add(note.toJSON());

            //Guardar el actualizado
            try (FileWriter writer = new FileWriter(file)){
                writer.write(notes.toJSONString());
            }

        }catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


}
