package chat.handler;

import chat.MyServer;
import chat.auth.AuthService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private static final String AUTH_CND_PREFIX = "/auth";
    private static final String AUTHOK_CND_PREFIX = "/authok";
    private static final String AUTHERR_CND_PREFIX = "/autherr";
    // для домашней работы
    private static final String PRIVATE_MSG_PREFIX = "/w";
    private static final String END_CND = "/end";
    private static final String CLIENT_MSG_PREFIX = "/clientMsg";
    private static final String SERVER_MSG_PREFIX = "/serverMsg";


    private final MyServer myServer;
    private final Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;
        
    }

    public void handle() throws IOException {
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            //аутентификация
            try {
                authentification();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            // ожидание и чтение сообщение
        }).start();

    }

    private void authentification() throws IOException {
        String message = in.readUTF();

        while (true){
            if (message.startsWith(AUTH_CND_PREFIX)){
                String[] parts = message.split("\\s+", 3);
                String login = parts[1];
                String password = parts[2];

                //процесс аутентификации
                AuthService authService = myServer.getAuthService();
                username = authService.getUsernameByLoginAndPassword(login, password);
                if (username != null) {
                    // проверить ли не занят ли ник нейм
                    if(myServer.isUsernameBusy(username)){

                    }

                    //отправить на клиент никнейм
                    out.writeUTF(String.format("%s %s", AUTH_CND_PREFIX, username));

                    //оповестить о подключении никнейм


                    //зарегестрировать клиента
                    myServer.subscribe(this);
                }
            }
        }
    }

    public String getUsername() {
        return username;
    }
}
