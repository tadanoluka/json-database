package commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CommandSerializer implements JsonSerializer<Command> {

    @Override
    public JsonElement serialize(Command src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject commandObj = new JsonObject();

        commandObj.addProperty("type", src.getType().toString());
        String key = String.valueOf(src.getKey());
        if (key != null) {
            commandObj.addProperty("key", key);
        }
        String value = String.valueOf(src.getKey());
        if (value != null) {
            commandObj.addProperty("value", value);
        }

        return commandObj;
    }
}
