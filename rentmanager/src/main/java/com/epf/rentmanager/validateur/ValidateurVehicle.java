package com.epf.rentmanager.validateur;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

public class ValidateurVehicle {

    public boolean nombrePlacesValide(Vehicle vehicle ){

        return(vehicle.getNbPlaces()>2 && vehicle.getNbPlaces()<10);
    }
}
