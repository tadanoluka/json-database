package client;

import com.google.gson.Gson;
import commands.Command;
import commands.CommandSerializer;
import utils.SerializationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Session {

    public void send(Command request) {
        String address = "127.0.0.1";
        int port = 23456;
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            String jsonText = SerializationUtils.serializeCommandToJsonString(request);
            output.writeUTF(jsonText);
            System.out.printf("Sent: %s\n".formatted(jsonText));
            String answer = input.readUTF();
            System.out.printf("Received: %s\n".formatted(answer));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String jsonRequest) {
        String address = "127.0.0.1";
        int port = 23456;
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            output.writeUTF(jsonRequest);
            System.out.printf("Sent: %s\n".formatted(jsonRequest));
            String answer = input.readUTF();
            System.out.printf("Received: %s\n".formatted(answer));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
