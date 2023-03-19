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
        System.out.println(jsonRequest);
        Invoker invoker = new Invoker();
        Session session = new Session();
        CommandType commandType = CommandType.getCommandTypeFromString(inputArgs.type);
        if (inputArgs.filename != null) {
            commandType = CommandType.READ_FROM_FILE;
        }
        switch (commandType) {
            case GET -> {
                Command getCommand = new GetCommand(session, inputArgs.key);
                invoker.setCommand(getCommand);
                invoker.executeCommand();
            }
            case SET -> {
                Command setCommand = new SetCommand(session, inputArgs.key, inputArgs.value);
                invoker.setCommand(setCommand);
                invoker.executeCommand();
            }
            case DELETE -> {
                Command deleteCommand = new DeleteCommand(session, inputArgs.key);
                invoker.setCommand(deleteCommand);
                invoker.executeCommand();
            }
            case READ_FROM_FILE -> {
                Command readFromFile = new ReadFromFileCommand(session, inputArgs.filename);
                invoker.setCommand(readFromFile);
                invoker.executeCommand();
            }
            case EXIT -> {
                Command exitCommand = new ExitCommand(session);
                invoker.setCommand(exitCommand);
                invoker.executeCommand();
            }
        }
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
