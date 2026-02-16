package com.hit.client.network;

import com.google.gson.Gson;
import com.hit.client.model.Request;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    // The same port on server
    private static final int PORT = 34567;
    private Gson gson = new Gson();

    public String sendRequest(Request request) {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner reader = new Scanner(socket.getInputStream())) {

            // Convert to Json and send to server
            String jsonRequest = gson.toJson(request);
            writer.println(jsonRequest);

            // Wait to response
            if (reader.hasNextLine()) {
                return reader.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return null;
    }
}