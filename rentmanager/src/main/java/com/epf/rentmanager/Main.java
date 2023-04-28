package com.epf.rentmanager;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] arg){
        Client client = new Client(1,"m","l","ll", LocalDate.now());
        System.out.println(client);
        ApplicationContext context = new
                AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);

        try{
            List<Vehicle> vehicleList = vehicleService.findAll();
            System.out.println(vehicleList);
        }catch (ServiceException e){
            e.printStackTrace();
        }

        /*
        try{
            System.out.println(cs.create(client));
        }catch (ServiceException e){
            e.printStackTrace();
        }*/
        try{
            Client client2 = clientService.findById(5);
            System.out.println(client2);
        }catch (ServiceException e){
            e.printStackTrace();
        }

        try{
            Vehicle vehicle = vehicleService.findById(2);
            System.out.println(vehicle);
        }catch (ServiceException e){
            e.printStackTrace();
        }


    }
}
