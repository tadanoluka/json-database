package utils;

import com.google.gson.*;
import commands.*;
import server.Database;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class SerializationUtils {

    /**
     * Serialize the given object to the file
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static byte[] serializeToBytes(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos);
        ) {
            oos.writeObject(obj);
            oos.flush();
            byte[] bytes = bos.toByteArray();
            oos.close();
            return bytes;
        }
    }

    public static Object deserializeFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis);
        ) {
            Object obj = ois.readObject();
            ois.close();
            return obj;
        }
    }

    public static String serializeToJsonString(Object obj) {
        return new Gson().toJson(obj);
    }

    public static String serializeCommandToJsonString(Command command) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Command.class, new CommandSerializer())
                .create();
        return gson.toJson(command, Command.class);
    }

    public static Command deserializeJsonStringToCommand(String jsonText) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Command.class, new CommandDeserializer())
                .create();
        return gson.fromJson(jsonText, Command.class);
    }

    public static void serializeToJsonFile(Object obj, Path pathToJson) {
        String jsonText = serializeToJsonString(obj);
        byte[] bytes = jsonText.getBytes(StandardCharsets.UTF_8);
        try (FileOutputStream fos = new FileOutputStream(pathToJson.toFile());
             BufferedOutputStream bos = new BufferedOutputStream(fos);
        ) {
            bos.write(bytes);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Command deserializeCommandFromJsonString(String jsonText) {
        JsonObject jsonObject = new Gson().fromJson(jsonText, JsonObject.class);
        JsonElement type = jsonObject.get("type");
        CommandType commandType = CommandType.getCommandTypeFromString(type.getAsString());
        switch (commandType) {
            case GET -> {
                return new Gson().fromJson(jsonText, GetCommand.class);
            }
            case SET -> {
                return new Gson().fromJson(jsonText, SetCommand.class);
            }
            case DELETE -> {
                return new Gson().fromJson(jsonText, DeleteCommand.class);
            }
            case EXIT -> {
                return new Gson().fromJson(jsonText, ExitCommand.class);
            }
            default -> {
                return null;
            }
        }
    }

    public static Database deserializeDatabaseFromJson(Path pathToJson, Class<Database> databaseClass) {
        try (FileInputStream fileInputStream = new FileInputStream(pathToJson.toFile());
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ) {
            byte[] fileBytes = bufferedInputStream.readAllBytes();
            String textJson = new String(fileBytes, StandardCharsets.UTF_8);
            Database database = new Gson().fromJson(textJson, databaseClass);
            return database;
        } catch (IOException e) {
            return null;
        }
    }
}
