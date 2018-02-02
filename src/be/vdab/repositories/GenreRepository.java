package be.vdab.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.vdab.entities.Genre;

public class GenreRepository extends AbstractRepository {
	private static final String FIND_ALL_GENRES = 
			"select id, naam from genres order by naam";
	
	public List<Genre> findAllGenres() {
		try (Connection connection = dataSource.getConnection(); 
			 Statement statement = connection.createStatement()) {
			List<Genre> genres = new ArrayList<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			try (ResultSet resultSet = statement.executeQuery(FIND_ALL_GENRES)) {
				while (resultSet.next()) {
					genres.add(resultSetRijNaarGenres(resultSet));
				}
			}
			connection.commit();
			return genres;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	private Genre resultSetRijNaarGenres(ResultSet resultSet) throws SQLException {
		return new Genre(resultSet.getLong("id"), resultSet.getString("naam"));
	}
}
