package ru;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            final Socket client = new Socket("localhost", 8000);
            final OutputStream writer = client.getOutputStream();
            final InputStream reader = client.getInputStream();

            Thread readThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead = reader.read(buffer);
                    if (bytesRead != -1) System.out.println(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread writeThread = new Thread(() -> {
                final Scanner scanner = new Scanner(System.in);
                while (true) {
                    try {
                        var message = scanner.nextLine();
                        writer.write(message.getBytes(StandardCharsets.UTF_8));
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            readThread.start();
            writeThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
