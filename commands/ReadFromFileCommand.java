package commands;

import client.Session;
import com.google.gson.JsonElement;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ReadFromFileCommand implements Command {
    String pathToFile = "C:\\Users\\tadan\\MyJars\\JSON Database (Java)\\JSON Database (Java)\\task\\src\\client\\data";

    Session session;
    String filename;

    public ReadFromFileCommand(Session session, String filename) {
        this.session = session;
        this.filename = filename;
    }

    @Override
    public CommandType getType() {
        return null;
    }

    @Override
    public JsonElement getKey() {
        return null;
    }

    @Override
    public JsonElement getValue() {
        return null;
    }

    @Override
    public void execute() {
        Path path = Path.of(pathToFile, filename);
        File file = new File(path.toUri());
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            byte[] bytes = bis.readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            session.send(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
