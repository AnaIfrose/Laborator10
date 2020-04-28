/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientThread extends Thread {

    private Socket socket = null;
    private GameServer server;

    ClientThread(Socket socket, GameServer server) throws SocketException {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        BufferedReader fin = null;
        String cerere = null;
        String raspuns = null;
        PrintWriter fout = null;
        try {
            fin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            fout = new PrintWriter(socket.getOutputStream());
            while (true) {
                cerere = fin.readLine();
                if (cerere == null) {
                    break;
                }
                if (cerere.equals("stop")) {
                    fout.println("Server stopped");
                    fout.flush();
                    socket.close();
                }
                raspuns = execute(cerere);

                fout.println(raspuns);
                fout.flush();
                if (raspuns.equals("Server stopped")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare IO \n" + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private String execute(String cerere) throws IOException {
        System.out.println(cerere);
        String raspuns;
        raspuns = "Server received the cerere " + cerere;
        return raspuns;
    }
}
