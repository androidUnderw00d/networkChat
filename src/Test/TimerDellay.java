package Test;

import java.util.Scanner;
import java.util.TimerTask;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerDellay {
    public static void main(String[] args) {

        Timer timer = new Timer();

        TimerTask task = new TimerTask(){
            public void run(){
                for (int i = 0; i <= 30; i++){
                    System.out.print(i + " ");
//                    System.out.println(i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 10000); //1000ms = 1sec




//        autentification();
//        timerDellay();
//        thread.interrupt();
    }

/*    private static void autentification() {
        String password = "1234";
        Scanner scanner = new Scanner(System.in);

        Thread thread = new Thread(() -> {

            System.out.println("Поток запущен, ожидаем авторизации");

            String inputPassword = scanner.nextLine();
            System.out.println("Введите пароль");
            while (!true) {
                inputPassword.equals(password); {
                    System.out.println("Пароль не верный, попробуйте снова");
                } else System.out.println("Пароль верный");
            }
        });
        thread.start();
    }*/


    private static void timerDellay() {
        long TIME = 2*60*1000;

        java.util.Timer timer = null;
        timer.schedule(new TimerTask() {
            private final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                System.out.println("Осталось" + (TIME -
                        System.currentTimeMillis() - startTime));
            }
        }, TIME);
    }


}


