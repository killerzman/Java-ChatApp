/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.client;

import seriac.poo.structs.PrivateMessage;
import seriac.poo.structs.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 *
 * @author vladt
 */
public class ClientPeer extends Thread{
    
    private final ObjectOutputStream mObjectStream;
    public JTextArea mJt;
    private String mSender;
    public Message mMessage;
    private final Socket mSocket;

    public ClientPeer(String sender, Socket communicationSocket) throws IOException {
        mSender = sender;
        mSocket = communicationSocket;
        mObjectStream = new ObjectOutputStream(communicationSocket.getOutputStream());
    }

    public void run(){
        try{
            ObjectInputStream stream = new ObjectInputStream(mSocket.getInputStream());
        while(true){
            Message message = (Message) stream.readObject();
            setOutputPane(mJt);
            mJt.append(message.toString()+"\n");
            }
        }catch(IOException | ClassNotFoundException e){
            
        }
    }
    
    public void setUsername(String nume) throws IOException{
        mSender = nume;
    }
    
    public void setOutputPane(JTextArea jt){
        mJt = jt; 
    }
    
    public void sendMessage(String message) throws IOException {
        mObjectStream.writeObject(new Message(mSender, message));
    }

    public void sendMessage(String recipient, String message) throws IOException {
        mObjectStream.writeObject(new PrivateMessage(recipient, mSender, message));
    }
}

