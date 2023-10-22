import java.io.*;
import java.net.*;
import java.util.Scanner;

public class NaiveClient {
    public static void main(String argv[]) throws Exception {
        
        // Input host and port
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input host");
        String host =scanner.nextLine();
        System.out.println("Input port");
        int port = Integer.parseInt(scanner.nextLine());

        // Create socket
        Socket clientSocket = new Socket(host, port);

        // Create buffer reader
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Print server's output
        System.out.println("From server:");
        char character;
        while(true){
            character = (char) inFromServer.read();
            System.out.print(character);
            if(character == '\n'){
                break;
            }
        }        

        // Close socket and scanner
        clientSocket.close();
        scanner.close();
    }
}
