package com.epf.rentmanager.validateur;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import java.util.List;

public class ValidateurClient {

    public static ClientDao clientDao;
    public static boolean isNotLegal(Client client) {
        return client.getAge() <= 18;
    }

    public static boolean isNomValide(Client client ){
        return(client.getNom().length() > 3 & client.getPrenom().length() > 3);
    }

    public static boolean isMailValide(Client c  , ClientService clientService){
        try {
            for(Client client : clientService.findAll() ){
                if(c.getEmail().equals(client.getEmail())){
                    return true;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return false;
    }

}
