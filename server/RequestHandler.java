package server;

import com.google.gson.JsonElement;
import commands.Command;
import commands.CommandType;
import responses.ErrorResponse;
import responses.OKResponse;
import responses.Response;
import responses.ValueResponse;

import java.io.IOException;

public class RequestHandler {
    Command command;


    public RequestHandler(Command command) {
        this.command = command;
    }

    public Response getResponse() {
        CommandType commandType = command.getType();
        JsonElement key = command.getKey();
        JsonElement value = command.getValue();
        Response response;
        switch (commandType) {
            case GET -> {
                JsonElement databaseValue = Main.database.getValue(key);
                if (databaseValue != null) {
                    response = new ValueResponse(databaseValue);
                } else {
                    response = new ErrorResponse("No such key");
                }
            }
            case SET -> {
                Main.database.addEntry(key, value);
                response = new OKResponse();
            }
            case DELETE -> {
                if (Main.database.removeEntry(key)) {
                    response = new OKResponse();
                } else {
                    response = new ErrorResponse("No such key");
                }
            }
            case EXIT -> {
                Main.isRunning = false;
                response = new OKResponse();
                try {
                    Main.server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            default -> response = new ErrorResponse("Invalid");
        }
        return response;
    }

}
