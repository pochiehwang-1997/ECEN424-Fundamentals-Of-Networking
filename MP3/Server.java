import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Server extends Thread {

    private Socket connectionSocket;
    private int socketNum;
    private String sentenceToClient;
    private int times;
    public boolean isRunning;

    public Server(Socket connectionSocket, int times, String sentenceToClient, int socketNum) {
        this.isRunning = false;
        this.socketNum = socketNum;
        this.times = times;
        this.sentenceToClient = sentenceToClient;
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        try {
            this.isRunning = true;
            DataOutputStream outToClient = new DataOutputStream(this.connectionSocket.getOutputStream());
            System.out.println("Socket " + this.socketNum + " created");
            for (int i = 0; i < this.times - 1; i++) {
                outToClient.writeBytes(this.sentenceToClient);
                try {
                    TimeUnit.SECONDS.sleep(1); // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            outToClient.writeBytes(this.sentenceToClient + "\n");
            this.isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) throws Exception {

        // Read user inputs
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which port do you want to create socket");
        int port = Integer.parseInt(scanner.nextLine());
        System.out.println("Set maximum clients");
        int maxClients = Integer.parseInt(scanner.nextLine());
        System.out.println("Set message to send");
        String sentenceToClient = scanner.nextLine();
        System.out.println("Set number of times");
        int times = Integer.parseInt(scanner.nextLine());
        scanner.close();

        // Listen on port
        ServerSocket welcomeSocket = new ServerSocket(port);
        System.out.println("Listening on port " + port);

        // Create array of sockets with maximum clients number
        Server[] servers = new Server[maxClients];
        
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            boolean isAvailable = false;
            int socketNum = 0;
            while(!isAvailable){
                for(int i = 0; i < maxClients; i++){
                    if(servers[i] == null || servers[i].isRunning==false){
                        socketNum = i;
                        isAvailable = true;
                        break;
                    }
                }
            }
            servers[socketNum] = new Server(connectionSocket, times, sentenceToClient, socketNum);
            servers[socketNum].start();

        }

    }
}
