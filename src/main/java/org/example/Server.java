package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static final int portNumber = 1234;

    static Boolean readLoop(BufferedReader in, PrintWriter out) {
        // waits for data and reads it in until connection dies
        // readLine() blocks until the server receives a new line from client
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
                if (s.equals("all") || s.equals("ALL"))
                    out.println(Cars.getInstance().toJSON(s));
                else if (s.equals("all_sorted_on_brand") || s.equals("ALL_SORTED_ON_BRAND"))
                    out.println(Cars.getInstance().toJSON(s));
                else if (s.equals("all_sorted_on_price") || s.equals("ALL_SORTED_ON_PRICE"))
                    out.println(Cars.getInstance().toJSON(s));
                else if (s.equals("more_expensive") || s.equals("MORE_EXPENSIVE"))
                    out.println(Cars.getInstance().toJSON(s));
                else if (s.equals(""))
                    out.println("Comando errato");
                else
                    out.println("Comando errato");
                out.flush();
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static void main(String[] args) {
        System.out.println("Server started!");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket clientSocket = null;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = null; // allocate to write answer to client.
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                readLoop(in, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert clientSocket != null;
            Client clientHandler = new Client(clientSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
            System.out.println("Server done!");

        }
    }
}
