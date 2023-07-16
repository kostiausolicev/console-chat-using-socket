package ru;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            final Socket client = new Socket("localhost", 8000);
            final PrintWriter writer = new PrintWriter(client.getOutputStream(), true);;
            final BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Thread readThread = new Thread(() -> {
                while (true) {
                    try {
                        var message = reader.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread writeThread = new Thread(() -> {
                final Scanner scanner = new Scanner(System.in);
                while (true) {
                    var message = scanner.nextLine();
                    writer.println(message);
                    writer.flush();
                }
            });

            readThread.start();
            writeThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
