package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.vdab.entities.Klanten;

public class KlantenRepository extends AbstractRepository {
	public static final String SELECT_KLANTEN_FAMILIENAAM = 
			"select familienaam, voornaam, straatNummer, postcode, gemeente "
			+ "from klanten "
			+ "where familienaam like ? "
			+ "order by id";
	
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
	
	private Klanten resultSetRijNaarKlanten(ResultSet resultSet) throws SQLException {
		return new Klanten(resultSet.getString("familienaam"), 
				resultSet.getString("voornaam"), 
				resultSet.getString("straatNummer"), 
				resultSet.getString("postcode"), 
				resultSet.getString("gemeente"));
	}
}
