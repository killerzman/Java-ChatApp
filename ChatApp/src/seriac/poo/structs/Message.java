/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.structs;
import java.io.Serializable;

/**
 *
 * @author vladt
 */
public class Message implements Serializable{
    
    protected final String mNumeExpeditor, mContinut;
    
    public Message(String NumeExpeditor, String Continut){
        mNumeExpeditor = NumeExpeditor;
        mContinut = Continut;
    }
    
    @Override
    public String toString(){
        return mNumeExpeditor + ": " + mContinut;
    }
    
    public String getSender(){
        return this.mNumeExpeditor;
    }
}
