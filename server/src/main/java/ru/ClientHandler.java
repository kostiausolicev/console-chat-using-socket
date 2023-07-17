package ru;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket client;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final Server server;

    public ClientHandler(Socket client, Server server) throws IOException {
        this.client = client;
        this.server = server;
        writer = new PrintWriter(client.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = reader.readLine();
                System.out.println(message);
                server.sendMessageAll(message, client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean equals(Socket socket) {
        return client.equals(socket);
    }

    public void send(String message) {
        writer.println(message);
        writer.flush();
    }
}
