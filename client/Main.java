package client;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import commands.*;
import com.beust.jcommander.JCommander;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public class Main {
    private static final String pathToFile = "C:\\Users\\tadan\\MyJars\\JSON Database (Java)\\JSON Database (Java)\\task\\src\\client\\data";

    public static void main(String[] args) {
        Args inputArgs = new Args();
        JCommander.newBuilder()
                .addObject(inputArgs)
                .build()
                .parse(args);
        System.out.println("Client started!");
        String jsonRequest;
        if (inputArgs.filename != null) {
            jsonRequest = readFile(inputArgs.filename);
        } else {
            jsonRequest = new Gson().toJson(inputArgs);
        }
        new Session().send(jsonRequest);
    }

    public static String readFile(String filename) {
        Path path = Path.of(pathToFile, filename);
        File file = new File(path.toUri());
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            byte[] bytes = bis.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
