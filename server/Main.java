package server;



import utils.SerializationUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.file.Path;


public class Main {
    protected static final Path pathToDBFile = Path.of("C:\\Users\\tadan\\MyJars\\JSON Database (Java)\\JSON Database (Java)\\task\\src\\server\\data\\db.json");
    static Boolean isRunning = true;
    static Database database;
    static ServerSocket server;

    public static void main(String[] args) {
        System.out.println("Server started!");
        database = loadDatabase();
        String address = "127.0.0.1";
        int port = 23456;
        try {
            server = new ServerSocket(port, 50, InetAddress.getByName(address));
            while (isRunning) {
                Session session = new Session(server.accept());
                session.start();
            }
        } catch (SocketException ignored) {
            ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                saveDatabase(database);
                server.close();
            } catch (IOException ignored) {
                ;
            }
        }
    }

    public static void saveDatabase(Database database) {
        // SerializationUtils.serializeToJsonFile(database, pathToDBFile);
    }

    public static Database loadDatabase() {
        Database tempDatabase = SerializationUtils.deserializeDatabaseFromJson(pathToDBFile, Database.class);
        if (tempDatabase != null) {
            return tempDatabase;
        } else {
            return new Database();
        }
    }

}
