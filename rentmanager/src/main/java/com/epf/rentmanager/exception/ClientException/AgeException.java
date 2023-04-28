package com.epf.rentmanager.exception.ClientException;

public class AgeException extends Exception{

    public AgeException(){
        super("Le client doit être majeur");
    }
}
