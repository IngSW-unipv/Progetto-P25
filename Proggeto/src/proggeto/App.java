package proggeto;


import ui.UI;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.util.ArrayList;

public class App {
    //launching the app
    public void run(){
        SwingUtilities.invokeLater(UI::new);
        addAdmin();
    }



    public void addAdmin() {
        JSONParser jsonParser = new JSONParser();

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


}
