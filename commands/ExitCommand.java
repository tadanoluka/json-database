package commands;

import client.Session;
import com.google.gson.JsonElement;

import java.io.Serial;
import java.io.Serializable;

public class ExitCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private transient Session session;
    private CommandType type = CommandType.EXIT;

    public ExitCommand(Session session) {
        this.session = session;
    }

    public ExitCommand(CommandType type) {
        this.type = type;
    }

    @Override
    public CommandType getType() {
        return type;
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
        session.send(this);
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
