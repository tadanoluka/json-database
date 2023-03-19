package commands;

import client.Session;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Serial;
import java.io.Serializable;

public class DeleteCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private transient Session session;
    private CommandType type = CommandType.DELETE;
    private JsonElement key;

    public DeleteCommand(Session session, JsonElement key) {
        this.session = session;
        this.key = key;
    }

    public DeleteCommand(Session session, String key) {
        this.session = session;
        this.key = JsonParser.parseString(key);
    }

    public DeleteCommand(CommandType type, JsonElement key) {
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
