/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.structs;
import java.io.IOException;
import seriac.poo.server.config.ServerConfig;
import seriac.poo.server.exceptions.*;
/**
 *
 * @author vladt
 */
public class Main{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws seriac.poo.server.exceptions.InvalidFormatException
     * @throws seriac.poo.server.exceptions.MissingKeyException
     * @throws seriac.poo.server.exceptions.UnknownKeyException
     */
    public static void main(String[] args) throws IOException, InvalidFormatException, MissingKeyException, UnknownKeyException{
        Message m1 = new Message("Nelu", "lol");
        Message m2 = new Message("Coco", "da");
        Message m3 = new Message("Alina", "sal.");

        System.out.println(m1.toString());
        System.out.println(m2);
        System.out.printf("%s\n", m3);

        PrivateMessage pm1 = new PrivateMessage("Nelu", "Coco", "Buna seara!");
        PrivateMessage pm2 = new PrivateMessage("Coco", "Alina", "Buna dimineata!");
        PrivateMessage pm3 = new PrivateMessage("Alina", "Nelu", "Ce mai faceti?");

        System.out.println(pm1);
        System.out.println("To: " + pm1.getRecipient());
        System.out.println(pm2);
        System.out.println("To: " + pm2.getRecipient());
        System.out.println(pm3);
        System.out.println("To: " + pm3.getRecipient());

        try {
            ServerConfig config = new ServerConfig();
            System.out.println("Tcp port: " + config.getTcpPort());
            System.out.println("Max clients: " + config.getMaxClients());
        } catch (IOException ex) {
            System.err.println("Exceptie de tip IO: " + ex.getMessage());
        }
    }   
}
