/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author vladt
 */
public class TextClient{

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9000;
    
    public static void main(String[] args) throws ClassNotFoundException{
        Scanner keyboardScanner = new Scanner(System.in);

        try {
            Socket communicationSocket = new Socket(HOST, PORT);
            System.out.println("");
            String sender = keyboardScanner.nextLine();
            ClientPeer cpeer = new ClientPeer(sender, communicationSocket); 
            cpeer.start();
            while (true) {
                String command = keyboardScanner.nextLine().trim();
                if (command.equals("/q")) {
                    communicationSocket.close();
                    break;
                } else if (command.matches("/w\\s+\\w+\\s+.+")) {
                    String[] messageParts = command.split("\\s+", 3);
                    cpeer.sendMessage(messageParts[1], messageParts[2]);
                } else {
                    cpeer.sendMessage(command);
                }  
            }
            
        } catch (IOException ex) {

        }
        
    } 
}

