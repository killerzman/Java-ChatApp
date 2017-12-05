/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server.exceptions;

/**
 *
 * @author vladt
 */
public class MissingKeyException extends Exception {
    public MissingKeyException(String message){
        super(message);
    }
}
