package chat.handler;

import chat.MyServer;
import chat.auth.AuthService;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private static final String AUTH_CMD_PREFIX = "/auth";
    private static final String AUTHOK_CMD_PREFIX = "/authok";
    private static final String AUTHERR_CMD_PREFIX = "/autherr";
    // для домашней работы
    private static final String PRIVATE_MSG_PREFIX = "/w";
    private static final String CLIENT_MSG_PREFIX = "/clientMsg";
    private static final String SERVER_MSG_PREFIX = "/serverMsg";
    private static final String END_CMD = "/end";


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
                readMessage();    // ожидание и чтение сообщения
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }).start();

    }

    private void authentification() throws IOException {
        String message = in.readUTF();

        while (true) {
            if (message.startsWith(AUTH_CMD_PREFIX)) {
                String[] parts = message.split("\\s+", 3);
                String login = parts[1];
                String password = parts[2];

                //процесс аутентификации
                AuthService authService = myServer.getAuthService();
                username = authService.getUsernameByLoginAndPassword(login, password);
                if (username != null) {
                    // проверить ли не занят ли ник нейм
                    if (myServer.isUsernameBusy(username)) {
                        out.writeUTF(String.format("%s %s", AUTHERR_CMD_PREFIX, "Логин уже используется"));
                    }

                    //отправить на клиент никнейм
                    out.writeUTF(String.format("%s %s", AUTHOK_CMD_PREFIX, username));

                    //оповестить о подключении новичка никнейм
                    myServer.broadcastMessage(String.format(">>> %s подключился к чату", username), this, true);

                    //зарегестрировать клиента
                    myServer.subscribe(this);
                    break;
                } else {
                    out.writeUTF(String.format("%s %s", AUTHERR_CMD_PREFIX, "Логин или пароль не верны"));
                }
            } else {
                out.writeUTF(String.format("%s %s", AUTHERR_CMD_PREFIX, "Ошибка авторизации"));

            }
        }
    }

    private void readMessage() throws IOException {
        while (true){
            String message = in.readUTF();
            System.out.println("message | " + username + ": " + message);
            if (message.startsWith(END_CMD)){
                return;
            }
            else if(message.startsWith(PRIVATE_MSG_PREFIX)){
                //TODO что-то сделать в случае приватного сообщения
                String[] parts = message.split("\\s+", 4);
                String username = parts[2];
                message = parts[3];
                myServer.privateMessage(this, username, message);
            }
            else { //во всех остальных случаях
                myServer.broadcastMessage(message,this, false); //this - данный текущий хендлер
            }
        }
    }

    public String getUsername() {
        return username;
    }

    //доработать в ДЗ логику реагирования на отосланый с клиента запрос о том что нужно отправить личное сообщение конкретному Хендлеру
    public void sendMessage(String sender, String message) throws IOException {
        if (sender == null) {
            out.writeUTF(String.format("%s %s", SERVER_MSG_PREFIX, message));
        } else {
            out.writeUTF(String.format("%s %s %s", CLIENT_MSG_PREFIX, sender, message)); //если отправляется личное сообщение клииента префикс никнейм само сообщение

        }
    }
}
