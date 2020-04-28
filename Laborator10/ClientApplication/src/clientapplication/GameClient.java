/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class GameClient {

    private final static String serverAddress = "127.0.0.1";
    private final static int PORT = 8102;
    private static PrintWriter fout = null;
    private static BufferedReader fin = null;

    public static void main(String[] args) {
        Socket socket = null;
        GameClient client = new GameClient();
        try {
            socket = new Socket(serverAddress, PORT);
            fin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            fout = new PrintWriter(socket.getOutputStream());
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String cerere = scanner.nextLine();
                if (cerere.equals("exit")) {
                    fin.close();
                    fout.close();
                    break;
                } else {
                    client.sendRequestToServer(cerere);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequestToServer(String cerere) throws IOException {
        try {
            fout.println(cerere);
            fout.flush();
            cerere = fin.readLine();
            System.out.println(cerere);
        } catch (SocketException e) {
            System.err.println(e);
        }
    }

}
