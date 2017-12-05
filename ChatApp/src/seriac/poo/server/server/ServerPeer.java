/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server.server;

import seriac.poo.structs.Message;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 *
 * @author vladt
 */
public class ServerPeer extends Thread{

    private final Socket mSocket;
    private final Server mServer;
    private String mUsername;
    private final ObjectOutputStream mObjectOutputStream;

    public ServerPeer(Server server, Socket communicationSocket) throws IOException {
        mServer = server;
        mSocket = communicationSocket;
        mObjectOutputStream = new ObjectOutputStream(communicationSocket.getOutputStream());
    }

    public String getUsername(){
        return mUsername;
    }
    
    public void run() {
        try {
            ObjectInputStream stream = new ObjectInputStream(mSocket.getInputStream());
            
            while (true) {
                Message message = (Message) stream.readObject();
                mUsername = message.getSender();
                mServer.dispatch(message);
            }
        } catch (EOFException ex) {
            // client disconnected gracefully so do nothing
        } catch (IOException ex) {
            System.err.println("Client connection reset: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("Unknown object received.");
        }
    }
    
    public void sendMessage(Message message) throws IOException{
        mObjectOutputStream.writeObject(message);
    }
}
