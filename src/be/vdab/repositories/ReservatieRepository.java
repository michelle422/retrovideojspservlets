package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import be.vdab.entities.Reservaties;

public class ReservatieRepository extends AbstractRepository {
	public static final String CREATE_RESERVATIE = 
			"insert into reservaties values (?, ?, ?)";
	public static final String UPDATE_FILM = 
			"update films "
			+ "set gereserveerd = gereserveerd + 1 "
			+ "where id in (";
	
	public void createReservatie(Reservaties reservatie) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CREATE_RESERVATIE, Statement.RETURN_GENERATED_KEYS)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, reservatie.getKlantId());
			statement.setLong(2, reservatie.getFilmId());
			statement.setTimestamp(3, Timestamp.valueOf(reservatie.getReservatieDatum()));
			statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public void updateGereserveerd(List<Long> filmIds) {
		
	}
}
