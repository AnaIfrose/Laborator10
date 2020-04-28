/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private static final int PORT = 8102;
    private ServerSocket serverSocket;
    private boolean running = false;

    //initierw server si asteaptare cereri de la clienti
    private void init() {
        try {
            serverSocket = new ServerSocket(PORT);
            running = true;
            while (running) {
                System.out.println("Waiting for client... ");

                Socket socket = serverSocket.accept();
                new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        this.running = false;
        serverSocket.close();
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.init();

    }
}
