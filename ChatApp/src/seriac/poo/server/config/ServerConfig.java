/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server.config;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import seriac.poo.server.exceptions.*;

/**
 *
 * @author vladt
 */
public final class ServerConfig{

    private final String mDefaultFile = "server.conf";
    private final String mToReplace;
    private final File mFile;
    private int mTcpPort;
    private int mMaxClients;

   
    public ServerConfig(String file) throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException{
        if(ServerConfig.class.getResource(file) == null){
            throw new IOException("File not found!");
        }
        else{
            switch (checkOS()){
                case "Linux":
                    mToReplace = "file:";
                    break;
                default:
                    mToReplace = "file:/";
                    break;
            }
        mFile = new File(ServerConfig.class.getResource(file).toString().replace(mToReplace,""));
        }
        read();
    }

    public ServerConfig() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException{
        if(ServerConfig.class.getResource(mDefaultFile) == null){
            throw new IOException("File not found!");
        }
        else{
            switch (checkOS()){
                case "Linux":
                    mToReplace = "file:";
                    break;
                    
                default:
                    mToReplace = "file:/";
                    break;
            }
        mFile = new File(ServerConfig.class.getResource(mDefaultFile).toString().replace(mToReplace,""));
        }
        read();
    }

    public String getFile(){
        return mFile.getAbsolutePath();
    }

    public int getTcpPort(){
        return mTcpPort;
    }

    public int getMaxClients(){
        return mMaxClients;
    }

    private String checkOS(){
        return System.getProperty("os.name");
    }
    
    private void read() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException{
        mTcpPort = -1;
        mMaxClients = -1;

        Scanner scanner = new Scanner(mFile);
        while (scanner.hasNextLine()) {
            processLine(scanner.nextLine().trim());
        }
        
        if (mTcpPort == -1 || mMaxClients == -1){
            throw new MissingKeyException("Missing keys!");
        }
    }

    private void processLine(String line) throws IOException, InvalidFormatException, UnknownKeyException{
        if (line.length() < 1 || line.charAt(0) == '#'){
            return;
        }

        Scanner scanner = new Scanner(line);
        scanner.useDelimiter("=");

        switch (scanner.next().trim()){
            case "MAX_CLIENTS":
                mMaxClients = getValue(scanner.next().trim());
                break;

            case "TCP_PORT":
                mTcpPort = getValue(scanner.next().trim());
                break;

            default:
                throw new UnknownKeyException("Unknown key!");
            }
    }

    private int getValue(String s) throws InvalidFormatException{
        if (!s.matches("[0-9]+")){
            throw new InvalidFormatException("Invalid format!");
        }
        return Integer.parseInt(s);
    }
}
