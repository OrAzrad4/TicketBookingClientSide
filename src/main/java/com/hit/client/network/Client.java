package com.hit.client.network;

import com.google.gson.Gson;
import com.hit.client.model.Request;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    // הפורט חייב להיות זהה למה שהוגדר ב-ServerDriver
    private static final int PORT = 34567;
    private Gson gson = new Gson();

    public String sendRequest(Request request) {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner reader = new Scanner(socket.getInputStream())) {

            // המרה ל-JSON ושליחה לשרת
            String jsonRequest = gson.toJson(request);
            writer.println(jsonRequest);

            // קבלת תשובה
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