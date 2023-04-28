package com.epf.rentmanager.exception.ClientException;

public class MailException extends Exception{
    public MailException(){
        super("Le mail existe déjà");
    }
}
