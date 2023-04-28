package com.epf.rentmanager.exception;

public class VehicleException extends Exception{

    public VehicleException(){
        super("Le nombre de places est incorrect");
    }

}
