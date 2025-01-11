package proggeto;

import org.json.simple.*;

public class Notes {
    String title;
    String content;
    String author;

    public Notes(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    //Convierte a JSON
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("content", content);
        json.put("author", author);
        return json;
    }

    // Crear una nota a partir de un objeto JSON
    public static Notes fromJSON(JSONObject json) {
        String title = (String) json.get("title");
        String content = (String) json.get("content");
        String author = (String) json.get("author");
        return new Notes(title, content, author);
    }
}
