package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}

	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?,nb_places= ? WHERE id=?;";
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur , nb_places FROM Vehicle;";
	private static final String COUNT_VEHICULES_QUERY = "SELECT COUNT(id) AS nbVehicule FROM Vehicle ";

	public long create(Vehicle vehicle) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNbPlaces());

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

	public long delete(Vehicle vehicle) throws DaoException {

		int nbreLigneModif = 0;

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);

			ps.setLong(1, vehicle.getId());
			nbreLigneModif = ps.executeUpdate();

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return nbreLigneModif;
	}

	//changer les connection => try with ressources
	//foreach plutot que for
	public Vehicle findById(long id) throws DaoException {

		Vehicle vehicle = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);
			ps.setLong(1, id);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				String constructeur = resultSet.getString("constructeur");
				int nbPlaces = resultSet.getInt("nb_places");
				vehicle = new Vehicle(id, constructeur, nbPlaces);
			}

			//ps.close();
			//connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return vehicle;
	}

	public List<Vehicle> findAll() throws DaoException {

		List<Vehicle> vehicles = new ArrayList<>();

		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while(rs.next()){
				int id = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				int nbPlaces = rs.getInt("nb_places");

				vehicles.add(new Vehicle(id,constructeur, nbPlaces));
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}

		return vehicles;
		
	}

	public int count() throws DaoException {

		int nbVehicules = 0;

		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_VEHICULES_QUERY);

			if(rs.next()){
				nbVehicules = rs.getInt("nbVehicule");
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}

		return nbVehicules;

	}

	public long update(Vehicle vehicle) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY);
			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNbPlaces());
			ps.setLong(3, vehicle.getId());

			ps.execute();

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}

		return id;
	}

}
