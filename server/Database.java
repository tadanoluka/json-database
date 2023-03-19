package server;

import com.google.gson.*;

import java.util.HashMap;
import java.util.List;
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
        if (key.isJsonPrimitive()) {
            addEntryByKey(key.getAsJsonPrimitive(), value);
        } else if (key.isJsonArray()) {
            addEntryByKeys(key.getAsJsonArray(), value);
        }
        writeLock.unlock();
        return true;
    }

    private void addEntryByKey(JsonPrimitive key, JsonElement value) {
        String keyString = key.getAsString();
        jsonDatabaseRoot.add(keyString, value);
    }

    private void addEntryByKeys(JsonArray keys, JsonElement value) {
        JsonElement prevElement = jsonDatabaseRoot;
        for (int i = 0; i < keys.size(); i++) {
            if (prevElement.isJsonObject()) {
                JsonObject jsonObject = prevElement.getAsJsonObject();
                if (i != keys.size() - 1) {
                    prevElement = jsonObject.get(keys.get(i).getAsString());
                } else if (prevElement.isJsonObject()) {
                    String key = keys.get(keys.size() - 1).getAsString();
                    prevElement.getAsJsonObject().add(key, value);
                }
            }
        }
    }

    public boolean removeEntry(JsonElement key) {
        JsonElement prev = null;
        writeLock.lock();
        if (key.isJsonPrimitive()) {
            prev = removeEntryByKey(key.getAsJsonPrimitive());
        } else if (key.isJsonArray()) {
            prev = removeEntryByKeys(key.getAsJsonArray());
        }
        writeLock.unlock();
        return prev != null;

    }

    private JsonElement removeEntryByKey(JsonPrimitive key) {
        String keyString = key.getAsString();
        return jsonDatabaseRoot.remove(keyString);
    }

    private JsonElement removeEntryByKeys(JsonArray keys) {
        JsonElement prevElement = jsonDatabaseRoot;
        for (int i = 0; i < keys.size(); i++) {
            if (prevElement.isJsonObject()) {
                JsonObject jsonObject = prevElement.getAsJsonObject();
                if (i != keys.size() - 1) {
                    prevElement = jsonObject.get(keys.get(i).getAsString());
                } else if (prevElement.isJsonObject()) {
                    String key = keys.get(keys.size() - 1).getAsString();
                    return prevElement.getAsJsonObject().remove(key);
                }
            }
        }
        return null;
    }


    public JsonElement getValue(JsonElement key) {
        readLock.lock();
        JsonElement value = null;
        if (key.isJsonPrimitive()) {
            value = getValueByKey(key.getAsJsonPrimitive());
        } else if (key.isJsonArray()) {
            value = getValueByKeys(key.getAsJsonArray());
        }
        readLock.unlock();
        return value;
    }

    private JsonElement getValueByKey(JsonPrimitive key) {
        String keyString = key.getAsString();
        return jsonDatabaseRoot.get(keyString);
    }

    private JsonElement getValueByKeys(JsonArray keys) {
        JsonElement prevElement = jsonDatabaseRoot;
        for (JsonElement key : keys) {
            if (prevElement.isJsonObject()) {
                JsonObject jsonObject = prevElement.getAsJsonObject();
                prevElement = jsonObject.get(key.getAsString());
                if (prevElement == null) {
                    return null;
                }
            }
        }
        return prevElement;
    }

    public void printDb() {
        String jsonDBString = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(jsonDatabaseRoot);
        System.out.println(jsonDBString);
    }
}
