/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server.server;

import seriac.poo.structs.PrivateMessage;
import seriac.poo.structs.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import seriac.poo.server.config.*;
import seriac.poo.server.exceptions.*;

/**
 *
 * @author vladt
 */
public class Server {

    private final List<ServerPeer> mPeers;
    
    public Server() throws IOException, InvalidFormatException, MissingKeyException, UnknownKeyException{
        mPeers = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.listen();
            
        } catch (Throwable t) {
            System.err.println("Exception in thread main: " + t.getMessage());
        }
    }
    
    public synchronized void dispatch(Message message) throws IOException{
        if(message instanceof PrivateMessage){
        PrivateMessage privatemessage = (PrivateMessage) message;
            for(ServerPeer peer : mPeers){
                if(privatemessage.getRecipient().equals(peer.getUsername())
                        || privatemessage.getSender().equals(peer.getUsername()))
                    peer.sendMessage(message);
            }
        }else{
            for(ServerPeer peer : mPeers){
                    peer.sendMessage(message);
            }
        }
    }
    
    public synchronized void removeClient(ServerPeer peer) throws IOException{
        mPeers.remove(peer);
        dispatch(new Message("SERVER","Client" + peer.getUsername() + "has left the server."));
    }
    
    public void listen() throws IOException, InvalidFormatException, MissingKeyException, UnknownKeyException{
        ServerConfig config = new ServerConfig();
        ServerSocket serverSocket = new ServerSocket(config.getTcpPort());
        
        while(true){
        Socket socket = serverSocket.accept();
        synchronized(this){
        if(mPeers.size() >= config.getMaxClients()){
                socket.close();
                continue;
            }
        ServerPeer peer = new ServerPeer(this,socket);
        mPeers.add(peer);
        peer.start(); 
        dispatch(new Message("SERVER","Client " + peer.getUsername() + " just connected!"));
      }
    }
  }
}
