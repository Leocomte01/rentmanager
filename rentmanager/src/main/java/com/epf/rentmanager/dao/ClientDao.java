package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	private static ClientDao instance = null;
	private ClientDao() {
	}


	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS nbClients FROM Client ";

	public long create(Client client) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getDateNaissance()));

			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}

		return id;
	}

	public long delete(Client client) throws DaoException {

		int nbreLigneModif = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);

			ps.setLong(1, client.getCle());
			nbreLigneModif = ps.executeUpdate();

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return nbreLigneModif;
	}

	public Client findById(long id) throws DaoException {

		Client client = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);
			ps.setLong(1, id);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate date = resultSet.getDate("naissance").toLocalDate();
				client = new Client(id, nom, prenom, email, date);
			}

			//ps.close();
			//connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return client;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate date = rs.getDate("naissance").toLocalDate();

				clients.add(new Client(id, nom, prenom, email, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clients;

	}

	public int count() throws DaoException {

		int nbClients = 0;

		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_CLIENTS_QUERY);

			if(rs.next()){
				nbClients = rs.getInt("nbClients");
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}

		return nbClients;

	}




}
