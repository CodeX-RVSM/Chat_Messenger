import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Server {

    private static final int PORT = 5000;
    private static BufferedWriter logWriter;

    public static void main(String[] args) {


        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started...");
            System.out.println("Waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            logWriter = new BufferedWriter(new FileWriter("chatlog.txt", true));

            BufferedReader input =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedWriter output =
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader keyboard =
                    new BufferedReader(new InputStreamReader(System.in));

            // Get server username
            System.out.print("Enter your username: ");
            String serverName = keyboard.readLine();

            // Get client username
            output.write("Enter your username:\n");
            output.flush();
            String clientName = input.readLine();

            System.out.println("Connected with " + clientName);

            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = input.readLine()) != null) {
                        if (msg.equalsIgnoreCase("exit")) {
                            System.out.println("Client disconnected.");
                            logMessage(clientName, "Disconnected");
                            break;
                        }
                        System.out.println(getTime() + " " + msg);
                        logMessage("client", msg);
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
                        output.write(serverName + ": " + msg + "\n");
                        output.flush();

                        if (msg.equalsIgnoreCase("exit")) {

                            output.write("SERVER_EXIT\n");
                            output.flush();

                            logMessage(serverName, "Disconnected");
                            break;
                        }

                        if (msg.equalsIgnoreCase("exit")) {
                            logMessage(serverName, "Disconnected");
                            break;
                        }
                        System.out.println(getTime() + " " + serverName + ": " + msg);
                        logMessage(serverName, msg);

                    }
                } catch (Exception e) {
                    System.out.println("Error sending message.");
                }
            });

            receiveThread.start();
            sendThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void logMessage(String user, String msg) {
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date());
            logWriter.write(time + " | " + user + " : " + msg);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println("Logging error.");
        }
    }

    private static String getTime() {
            DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + LocalTime.now().format(formatter) + "]";
    }
}
