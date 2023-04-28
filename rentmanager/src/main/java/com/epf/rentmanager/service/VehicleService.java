package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.VehicleException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.validateur.ValidateurVehicle;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {
	private ValidateurVehicle validateurVehicle;
	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}


	public long create(Vehicle vehicle) throws ServiceException {
		try{
			if(validateurVehicle.nombrePlacesValide(vehicle) == false){
				throw new VehicleException();
			}
			return this.vehicleDao.create(vehicle);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}catch (VehicleException v){
			System.out.println(v);
			return 0;
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try{
			return this.vehicleDao.delete(vehicle);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try{
			return this.vehicleDao.findById(id);
		}catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return this.vehicleDao.findAll();
		}catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int count() throws ServiceException{
		try{
			return this.vehicleDao.count();
		}catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long modif(Vehicle vehicle) throws ServiceException {
		try{
			return this.vehicleDao.update(vehicle);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
}
