package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private Map<String, String> database;
    private JsonObject jsonDatabaseRoot;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();

    public Database () {
        this.database = new HashMap<>();
        this.jsonDatabaseRoot = new JsonObject();
    }

    public boolean addEntry(JsonElement key, JsonElement value) {
        writeLock.lock();
        jsonDatabaseRoot.add(String.valueOf(key), value);
        writeLock.unlock();
        return true;
    }

    public boolean removeEntry(JsonElement key) {
        writeLock.lock();
        JsonElement prev = jsonDatabaseRoot.remove(String.valueOf(key));
        writeLock.unlock();
        return prev != null;

    }

    public JsonElement getValue(JsonElement key) {
        readLock.lock();
        JsonElement value = jsonDatabaseRoot.get(String.valueOf(key));
        readLock.unlock();
        return value;
    }
}
