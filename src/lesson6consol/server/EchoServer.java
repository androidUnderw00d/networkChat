package lesson6consol.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    private static final int SERVER_PORT = 8089;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static Object closeConnection;


    public static void main(String[] args) {

        Socket socket = null;

        Thread thread1 = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("Сервер запущен, ожидаем подключения...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился");
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
                while (true) {
                    String str = in.readUTF();
                    System.out.println(str);
                    if (str.equals("/end")) {
                        break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {

            try {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String serverMessage = scanner.nextLine();
                    out.writeUTF(serverMessage);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        thread2.start();


    }
}
