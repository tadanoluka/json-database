package server;


import com.google.gson.Gson;
import commands.Command;
import responses.Response;
import utils.SerializationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {
    Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
        ) {
            String jsonText = input.readUTF();
            Command request = SerializationUtils.deserializeJsonStringToCommand(jsonText);

            RequestHandler requestHandler = new RequestHandler(request);
            Response response = requestHandler.getResponse();
            String responseJson = new Gson().toJson(response);

            output.writeUTF(responseJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
