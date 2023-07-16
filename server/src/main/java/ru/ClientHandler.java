package ru;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler extends Thread {
    private final Socket client;
    private final OutputStream writer;
    private final InputStream reader;
    private final Server server;

    public ClientHandler(Socket client, Server server) throws IOException {
        this.client = client;
        this.server = server;
        writer = client.getOutputStream();
        reader = client.getInputStream();
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] buffer = new byte[1024];
                int bytesRead = reader.read(buffer);
                if (bytesRead != -1) {
                    String message = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                    System.out.println(message);
                    server.sendMessageAll(message);
                }
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

    public void send(String message) {
        try {
            writer.write(message.getBytes(StandardCharsets.UTF_8));
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
