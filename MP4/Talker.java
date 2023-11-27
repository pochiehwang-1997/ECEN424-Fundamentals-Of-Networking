package MP4;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Talker {
    public static void main(String args[]) throws Exception {
        // Read user input 
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input the port to create socket");
        int port = Integer.parseInt(inFromUser.readLine());
        String userInput;
        while (true) {
            System.out.println("Input the message");
            userInput = inFromUser.readLine();
            if (userInput.length() > 50) {
                System.out.println("The message is longer than 50 characters, Please input another message");
            } else {
                break;
            }
        }

        // Divide user input message to 10 charaacter messages
        int messagesNum = (int) Math.ceil((double) userInput.length() / 10);
        String[] messageArr = new String[messagesNum + 1];
        messageArr[0] = ("0" + String.valueOf(messagesNum));
        for (int i = 1; i < messagesNum + 1; i++) {
            if (i == (messagesNum)) {
                messageArr[i] = String.valueOf(i) + userInput.substring((i - 1) * 10);
            } else {
                messageArr[i] = String.valueOf(i) + userInput.substring((i - 1) * 10, i * 10);
            }
        }

        // Create Socket to listen request from listener
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        byte[] sendData;

        while (true) {
            System.out.println("Ready to receive connection from the listener!");
            socket.setSoTimeout(0);
            socket.receive(receivePacket);
            String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            int listenerPort = receivePacket.getPort();
            System.out.println(receivePacket.getLength());

            // Receive connection from listener
            if (Integer.parseInt(receiveMsg) == 0) {
                System.out.println("Receive Connection");
                int packetNum = 0;
                while (packetNum < (messagesNum + 1)) {

                    sendData = messageArr[packetNum].getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, listenerPort);
                    socket.send(sendPacket);

                    // Wait for 2 seconds
                    socket.setSoTimeout(2000);
                    long startTime = System.currentTimeMillis();
                    try { 
                        socket.receive(receivePacket);
                        receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        if (Integer.parseInt(receiveMsg) == (packetNum + 1)
                                && IPAddress.getHostAddress().equals(receivePacket.getAddress().getHostAddress())
                                && listenerPort == receivePacket.getPort()) {
                            // If receive correct packet
                            System.out.println("Received ACK packet " + packetNum);
                            packetNum += 1;
                        } else {
                            // Receive wrong packet wait until 2 seconds is finished
                            System.out.println("Received wrong ACK packet");
                            long waitTime = System.currentTimeMillis() - startTime;
                            Thread.sleep(2000 - waitTime);
                        }
                    } catch (Exception e) { // If not receive packet in 2 seconds
                        System.out.println("No ACK packet received within 2 seconds, resend packet");
                    }
                }
            }
        }
    }
}
