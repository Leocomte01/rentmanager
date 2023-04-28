package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {
	private ClientDao clientDao;
	private VehicleDao vehicleDao;

	private static ReservationDao instance = null;
	private ReservationDao(ClientDao clientDao, VehicleDao vehicleDao) {
		this.clientDao = clientDao;
		this.vehicleDao = vehicleDao;
	}

	private static final String FIND_RESERVATION_QUERY = "SELECT id, vehicle_id, client_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATION_QUERY = "SELECT COUNT(id) AS nbReservations FROM Reservation";


	public long create(Reservation reservation) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			System.out.println();
			ps.setLong(1, reservation.getClient().getCle());
			ps.setLong(2, reservation.getVoiture().getId());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));

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
	
	public long delete(Reservation reservation) throws DaoException {

		int nbreLigneModif = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY);

			ps.setLong(1, reservation.getId());
			nbreLigneModif = ps.executeUpdate();

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return nbreLigneModif;
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ps.setLong(1, clientId);

			ResultSet resultSet = ps.executeQuery();


			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				long voiture_id = resultSet.getLong("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Vehicle voiture = vehicleDao.findById(voiture_id);
				reservations.add(new Reservation(id, voiture, debut, fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ps.setLong(1, vehicleId);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				long client_id = resultSet.getLong("client_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Client client = clientDao.findById(client_id);
				reservations.add(new Reservation(id, client, debut, fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}


	public List<Reservation> findAll() throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (rs.next()) {
				int id = rs.getInt("id");
				long voiture_id = rs.getLong("vehicle_id");
				long client_id = rs.getLong("client_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				Client client = clientDao.findById(client_id);
				Vehicle voiture = vehicleDao.findById(voiture_id);
				reservations.add(new Reservation(id, voiture, client, debut, fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	public Reservation findById(long id) throws DaoException {

		Reservation reservation = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_QUERY);
			ps.setLong(1, id);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Long vehicle_id = resultSet.getLong("vehicle_id");
				Long client_id = resultSet.getLong("client_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Client client = clientDao.findById(client_id);
				Vehicle voiture = vehicleDao.findById(vehicle_id);
				reservation = new Reservation(id, voiture, client, debut, fin);
			}

			//ps.close();
			//connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return reservation;
	}


	public int count() throws DaoException {

		int nbReservations = 0;

		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_RESERVATION_QUERY);

			if(rs.next()){
				nbReservations = rs.getInt("nbReservations");
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}

		return nbReservations;
	}
}
