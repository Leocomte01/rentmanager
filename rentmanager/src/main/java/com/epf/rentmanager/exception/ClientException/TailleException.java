package com.epf.rentmanager.exception.ClientException;

public class TailleException extends Exception{

    public TailleException(){
        super("Le nom ou le prenom est trop court");
    }
}
