package MP4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Listener {
    public static void main(String args[]) throws Exception {

        // Read user input
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input the port to create socket");
        int port = Integer.parseInt(inFromUser.readLine());
        System.out.println("Input the talker IP address");
        String host = inFromUser.readLine();
        System.out.println("Input the talker socket port");
        int talkerPort = Integer.parseInt(inFromUser.readLine());
        
        // Create socket
        DatagramSocket socket = new DatagramSocket(port);
        InetAddress IPAddress = InetAddress.getByName(host);
        byte[] sendData;

        // Request connection to the Talker 
        sendData = "0".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, talkerPort);
        socket.send(sendPacket);

        // Wait for the talker's packet 0
        int messagesNum;
        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        while (true) {
            socket.receive(receivePacket);
            String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            if (Integer.parseInt(receiveMsg.substring(0, 1)) == 0
                    && IPAddress.getHostAddress().equals(receivePacket.getAddress().getHostAddress())
                    && talkerPort == receivePacket.getPort()) {
                System.out.println("Receive packet 0");
                messagesNum = Integer.parseInt(receiveMsg.substring(1));
                sendData = String.valueOf(1).getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, talkerPort);
                socket.send(sendPacket);
                break;
            }
        }

        // Receive packets and send ACK packets 
        String sentence = "";
        int packetNum = 1;
        while (packetNum < messagesNum + 1) {
            socket.receive(receivePacket);
            String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            if (Integer.parseInt(receiveMsg.substring(0, 1)) == packetNum
                    && IPAddress.getHostAddress().equals(receivePacket.getAddress().getHostAddress())
                    && talkerPort == receivePacket.getPort()) {
                double randomNum = Math.random();
                if (randomNum > 0.5) {
                    System.out.println("Receive packet " + packetNum);
                    sentence += receiveMsg.substring(1);
                    packetNum += 1;
                }
                else{
                    continue;
                }
            }
            sendData = String.valueOf(packetNum).getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, talkerPort);
            socket.send(sendPacket);
        }

        // Print out message and close socket
        System.out.println(sentence);
        socket.close();
    }
}
