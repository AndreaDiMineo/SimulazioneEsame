package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Client implements Runnable{

    private Socket clientSocket;

    private PrintWriter out = null;

    private List<Client> clientList;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public Client(Socket clientSocket){
        this.clientSocket = clientSocket;
        InetAddress address = clientSocket.getInetAddress();
        int port = clientSocket.getPort();
        System.out.println("connected " + address + "on port " + port);
    }

    void handle(){
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InetAddress address = clientSocket.getInetAddress();
        clientList.remove(this);
        System.out.println("done on " + address + ", now we have " + clientList.size() + " clients");
    }

    void write(String s){
        out.println(s);
        out.flush();
    }

    @Override
    public void run() {
        handle();
    }
}
