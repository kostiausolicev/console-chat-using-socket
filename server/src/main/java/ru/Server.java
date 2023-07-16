package ru;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private final ServerSocket socket;
    private final List<ClientHandler> connections = new ArrayList<>();

    public Server() throws IOException {
        int port = 8000;
        socket = new ServerSocket(port);
        System.out.println("Сервер запущен");
    }

    @Override
    public void run() {
        try {
            while (true) {
                var clientSocket = socket.accept();
                System.out.println("New connect from " + clientSocket.getInetAddress().getHostName());
                ClientHandler handler = new ClientHandler(clientSocket, this);
                connections.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        start();
    }

    public void sendMessageAll(String msg) {
        for (var client : connections) {
            client.send(msg);
        }
    }
}
