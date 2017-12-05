/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.structs;

/**
 *
 * @author vladt
 */
public class PrivateMessage extends Message{

    private final String mNumeDestinatar;

    public PrivateMessage(String NumeDestinatar, String NumeExpeditor, String Continut){
        super(NumeExpeditor, Continut);
        mNumeDestinatar = NumeDestinatar;
    }
    
    @Override
    public String toString(){
        return "(priv) " + mNumeExpeditor + ": " + mContinut;
    }
    
    public String getRecipient(){
        return this.mNumeDestinatar;
    }
}
