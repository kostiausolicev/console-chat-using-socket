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
                        var resp = reader.readLine();
                        var message = MessageUtil.deser(resp);
                        System.out.println((message.getName() != null ? message.getName() + ": " : "") + message.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread writeThread = new Thread(() -> {
                final Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your name: ");
                final String name = scanner.nextLine();
                final MessageUtil mes = new MessageUtil();
                mes.setName(name);
                while (true) {
                    var message = scanner.nextLine();
                    mes.setMessage(message);
                    writer.println(MessageUtil.ser(mes));
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
