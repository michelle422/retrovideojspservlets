package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import be.vdab.entities.Klanten;

public class KlantenRepository extends AbstractRepository {
	public static final String SELECT_KLANTEN_FAMILIENAAM = 
			"select id, familienaam, voornaam, straatNummer, postcode, gemeente "
			+ "from klanten "
			+ "where familienaam like ? "
			+ "order by id";
	public static final String SELECT_KLANT_ID = 
			"select id, familienaam, voornaam, straatNummer, postcode, gemeente "
			+ "from klanten where id = ?";
	
	public List<Klanten> readKlanten(String familienaam) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SELECT_KLANTEN_FAMILIENAAM)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, "%" + familienaam + "%");
			List<Klanten> klanten = new ArrayList<>();
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					klanten.add(resultSetRijNaarKlanten(resultSet));
				}
			}
			connection.commit();
			return klanten;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public Optional<Klanten> readKlant(long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SELECT_KLANT_ID)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			Optional<Klanten> klant;
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					klant = Optional.of(resultSetRijNaarKlanten(resultSet));
				} else {
					klant = Optional.empty();
				}
			}
			connection.commit();
			return klant;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	private Klanten resultSetRijNaarKlanten(ResultSet resultSet) throws SQLException {
		return new Klanten(resultSet.getLong("id"),
				resultSet.getString("familienaam"), 
				resultSet.getString("voornaam"), 
				resultSet.getString("straatNummer"), 
				resultSet.getString("postcode"), 
				resultSet.getString("gemeente"));
	}
}
