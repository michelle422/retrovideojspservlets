package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import be.vdab.entities.Reservatie;

public class ReservatieRepository extends AbstractRepository {
	public static final String CREATE_RESERVATIE = 
			"insert into reservaties values (?, ?, ?)";
	public static final String UPDATE_FILM = 
			"update films "
			+ "set gereserveerd = gereserveerd + 1 "
			+ "where id = ?";
	
	public void createReservatie(Reservatie reservatie) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statementInsert = connection.prepareStatement(CREATE_RESERVATIE);
			 PreparedStatement statementUpdate = connection.prepareStatement(UPDATE_FILM)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			statementInsert.setLong(1, reservatie.getKlantId());
			statementInsert.setLong(2, reservatie.getFilmId());
			statementInsert.setTimestamp(3, Timestamp.valueOf(reservatie.getReservatieDatum()));
			statementInsert.executeUpdate();
			statementUpdate.setLong(1, reservatie.getFilmId());
			statementUpdate.executeUpdate();
			connection.commit();
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
