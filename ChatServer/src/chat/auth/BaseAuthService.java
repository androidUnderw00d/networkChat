package chat.auth;

import chat.User;
import java.util.List;
import static com.sun.tools.javac.util.List.of;


public class BaseAuthService implements AuthService{

    //List.of

    private static final List<User> clients = of(
            new User("user1", "1111", "Чубакка"),
            new User("user2", "2222", "Оби-Ван Кеноби"),
            new User("user3", "3333", "Йода")
    );

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User client : clients) {
            if(client.getLogin().equals(login) && client.getPassword().equals(password)) {
                return client.getUsername();
            }
        }
        return null;
    }

    @Override
    public void close() {
        System.out.println("Сервис аутентификации завершен");
    }
}
