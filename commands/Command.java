package commands;

import com.google.gson.JsonElement;

public interface Command {

    CommandType getType();
    JsonElement getKey();
    JsonElement getValue();

    void execute();

}
