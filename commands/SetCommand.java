package commands;

import client.Session;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.Serial;
import java.io.Serializable;

public class SetCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private transient Session session;
    private CommandType type = CommandType.SET;
    private JsonElement key;
    private JsonElement value;

    public SetCommand(Session session, JsonElement key, JsonElement value) {
        this.session = session;
        this.key = key;
        this.value = value;
    }

    public SetCommand(Session session, String key, String value) {
        this.session = session;
        this.key = JsonParser.parseString(key);
        this.value = JsonParser.parseString(value);
    }


    public SetCommand(CommandType type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute() {
        session.send(this);
    }

    @Override
    public String toString() {
        return "%s %s %s".formatted(type.toString(), key, value);
    }

    @Override
    public CommandType getType() {
        return type;
    }

    @Override
    public JsonElement getKey() {
        return key;
    }

    @Override
    public JsonElement getValue() {
        return value;
    }
}
