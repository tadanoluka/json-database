package commands;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CommandDeserializer implements JsonDeserializer<Command> {
    @Override
    public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement type = jsonObject.get("type");
        JsonElement key = jsonObject.get("key");
        JsonElement value = jsonObject.get("value");

        CommandType commandType = CommandType.getCommandTypeFromString(type.getAsString());

        switch (commandType) {
            case GET -> {
                return new GetCommand(commandType, key);
            }
            case SET -> {
                return new SetCommand(commandType, key, value);
            }
            case DELETE -> {
                return new DeleteCommand(commandType, key);
            }
            case EXIT -> {
                return new ExitCommand(commandType);
            }
            default -> {
                return null;
            }
        }
    }
}
