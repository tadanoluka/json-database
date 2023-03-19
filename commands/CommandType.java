package commands;

import java.util.Objects;

public enum CommandType {
    GET, SET, DELETE, EXIT, NO_TYPE, READ_FROM_FILE;


    public static CommandType getCommandTypeFromString(String string) {
        for (CommandType commandType : CommandType.values()) {
            if (Objects.equals(string, commandType.toString())) {
                return commandType;
            }
        }
        return NO_TYPE;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
