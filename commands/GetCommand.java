package commands;

import client.Session;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Serial;
import java.io.Serializable;

public class GetCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private transient Session session;
    private CommandType type = CommandType.GET;
    private JsonElement key;

    public GetCommand(Session session, JsonElement key) {
        this.session = session;
        this.key = key;
    }

    public GetCommand(Session session, String key) {
        this.session = session;
        this.key = JsonParser.parseString(key);
    }

    public GetCommand(CommandType type, JsonElement key) {
        this.type = type;
        this.key = key;
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
        return null;
    }

    @Override
    public void execute() {
        session.send(this);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(type.toString(), key);
    }
}
