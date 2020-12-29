package lesson6consol.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    private static final int SERVER_PORT = 8089;
    private static final String SERVER_HOST = "localhost";
    private static BufferedReader reader;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static Socket clientSocket;

    public EchoClient() {
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openConnection() throws IOException {

        clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());

        Thread thread1 = new Thread(() -> {
            try {
                while (true) {
                    String strFromServer = in.readUTF();
                    if (strFromServer.equalsIgnoreCase("/end")) {
                        break;
                    }
                    System.out.println(strFromServer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread1.start();
    }

    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    Как отправить сообщение в чат
    public static void sendMessage() throws IOException {

        Thread thread2 = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String message = scanner.nextLine();
                    out.writeUTF(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread2.start();

    }

    public static void main(String[] args) throws IOException {
        new EchoClient();
        sendMessage();
    }
}
