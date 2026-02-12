import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Client {

    private static final String SERVER_IP = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        try {
            Socket socket = new Socket(SERVER_IP, PORT);
            System.out.println("Connected to server.");

            BufferedReader input =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedWriter output =
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader keyboard =
                    new BufferedReader(new InputStreamReader(System.in));

            // Receive server request for username
            input.readLine();
            System.out.print("Enter your username: ");
            String username = keyboard.readLine();
            output.write(username + "\n");
            output.flush();

            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = input.readLine()) != null) {

                    if (msg.equals("SERVER_EXIT")) {
                        System.out.println("Server disconnected.");
                        break;
                    }

                    System.out.println(getTime() + " " + msg);
                    }

                } catch (Exception e) {
                    System.out.println("Connection closed.");
                }
            });

            Thread sendThread = new Thread(() -> {
                try {
                    String msg;
                    while (true) {
                        msg = keyboard.readLine();
                        output.write(username + ": " + msg + "\n");
                        output.flush();

                        System.out.println(getTime() + " " + username + ": " + msg);

                        if (msg.equalsIgnoreCase("exit")) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error sending.");
                }
            });

            receiveThread.start();
            sendThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getTime() {
            DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + LocalTime.now().format(formatter) + "]";
    }
}
