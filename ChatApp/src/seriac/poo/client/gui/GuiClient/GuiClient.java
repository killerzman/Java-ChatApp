/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.client.gui.GuiClient;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import seriac.poo.client.ClientPeer;

/**
 *
 * @author vladt
 */
public class GuiClient {

    public String mUser;
    private static ClientPeer mCpeer;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9000;
    private final Socket mSocket;

    private javax.swing.JScrollPane mMessageAreaScrollPane;
    private javax.swing.JButton mChangeSenderButton;
    private static javax.swing.JTextArea mMessageDisplayTextArea;
    private javax.swing.JTextField mMessageTextField;
    private javax.swing.JButton mSendMessageButton;
    private javax.swing.JLabel mSenderNameLabel;
    private javax.swing.JTextField mSenderTextField;
    private final JFrame frame;
    
    public GuiClient(String username, Socket communicationSocket) throws IOException {
        frame = new JFrame("Java ChatApp");
        initGui(frame, frame.getContentPane());
        mUser = username;
        mSocket = communicationSocket;
        ClientPeer cpeer = new ClientPeer(mUser, mSocket);
        mCpeer = cpeer;
        cpeer.setOutputPane(mMessageDisplayTextArea);
    }

    public void display() {
        mCpeer.start();
        frame.setVisible(true);
    }
                         
    private void initGui(JFrame frame, Container pane) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        mSenderNameLabel = new javax.swing.JLabel();
        mSenderTextField = new javax.swing.JTextField();
        mChangeSenderButton = new javax.swing.JButton();
        mMessageAreaScrollPane = new javax.swing.JScrollPane();
        mMessageDisplayTextArea = new javax.swing.JTextArea();
        mMessageTextField = new javax.swing.JTextField();
        mSendMessageButton = new javax.swing.JButton();

        mSenderNameLabel.setText("Name:");

        mChangeSenderButton.setText("Change Name");
        mChangeSenderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    mChangeSenderButtonActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        mSendMessageButton.setText("Send");
        mSendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    mSendMessageButtonActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        mMessageDisplayTextArea.setEditable(false);
        mMessageDisplayTextArea.setColumns(20);
        mMessageDisplayTextArea.setRows(5);
        mMessageAreaScrollPane.setViewportView(mMessageDisplayTextArea);
        
        mMessageTextField.setColumns(20);
        mMessageTextField.setToolTipText("Write your message here");
        
        mSenderTextField.setColumns(10);
        mSenderTextField.setToolTipText("Change your name here");
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,10,10,0);
        pane.add(mSenderNameLabel,c);
        
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(mSenderTextField,c);
        
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(mChangeSenderButton,c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.ipady = 200;
        c.gridwidth = 3;
        c.insets = new Insets(10,0,0,0);
        pane.add(mMessageAreaScrollPane,c);
        
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.gridwidth = 2;
        pane.add(mMessageTextField,c);
        
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0;
        c.gridwidth = 1;
        pane.add(mSendMessageButton,c);
        
        frame.pack();
    }   

    private void mSendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        String msgout = "";
        msgout = mMessageTextField.getText();
        if (msgout.equals("/q")) {
                    mMessageDisplayTextArea.append("SERVER: Ati parasit chat-ul!");
                    mSocket.close();
                } else if (msgout.matches("/w\\s+\\w+\\s+.+")) {
                    String[] messageParts = msgout.split("\\s+", 3);
                    mCpeer.sendMessage(messageParts[1], messageParts[2]);
                } else {
                    mCpeer.sendMessage(msgout);
                }  
        mMessageTextField.setText("");
    }

    private void mChangeSenderButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        String temp = "";
        temp = mSenderTextField.getText();
        mCpeer.setUsername(temp);
        mMessageDisplayTextArea.append("Numele a fost schimbat in: " + temp + "\n");
    }

    public static void main(String args[]) throws IOException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket(HOST, PORT);
                    GuiClient guiClient = new GuiClient("user", socket);
                    guiClient.display();
                } catch (IOException ex) {}
            }
        });
    }

}
