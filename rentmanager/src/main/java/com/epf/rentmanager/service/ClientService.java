package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.ClientException.AgeException;
import com.epf.rentmanager.exception.ClientException.MailException;
import com.epf.rentmanager.exception.ClientException.TailleException;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.validateur.ValidateurClient;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

	private ValidateurClient validateurClient;
	private ClientDao clientDao;
	public static ClientService instance;

	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}


	public long create(Client client) throws ServiceException {
		try{
		if( validateurClient.isNotLegal(client) ) {
			throw new AgeException();
		} else if (validateurClient.isNomValide(client)==false) {
			throw new TailleException();

		} else if(validateurClient.isMailValide(client,this)){
			throw new MailException();
		}
			return this.clientDao.create(client);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
		catch (AgeException a){
			System.out.println(a);
			return 0;
		}
		catch (TailleException t){
			System.out.println(t);
			return 0;
		}
		catch (MailException m){
			System.out.println(m);
			return 0;
		}
	}

	public long delete(Client client) throws ServiceException {

		try{
			return this.clientDao.delete(client);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Client findById(long id) throws ServiceException {
		try{
			return this.clientDao.findById(id);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return this.clientDao.findAll();
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int count() throws ServiceException{
		try{
			return this.clientDao.count();
		}catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
