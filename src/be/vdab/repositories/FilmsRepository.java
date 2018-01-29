package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import be.vdab.entities.Films;
import be.vdab.entities.Genres;

public class FilmsRepository extends AbstractRepository {
	public static final String SELECT_FILM_VAN_GENRE = 
			"select id, titel from films where genreid = ? order by titel";
	private static final String FIND_ALL_GENRES = 
			"select id, naam from genres order by naam";
	
	public Optional<Films> readFilms(long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SELECT_FILM_VAN_GENRE)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			Optional<Films> filmsGenre;
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					filmsGenre = Optional.of(resultSetRijNaarFilms(resultSet));
				} else {
					filmsGenre = Optional.empty();
				}
			}
			connection.commit();
			return filmsGenre;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public List<Genres> findAllGenres() {
		try (Connection connection = dataSource.getConnection(); 
			 Statement statement = connection.createStatement()) {
			List<Genres> genres = new ArrayList<>();
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
	
	private Genres resultSetRijNaarGenres(ResultSet resultSet) throws SQLException {
		return new Genres(resultSet.getLong("id"), resultSet.getString("naam"));
	}
	
	private Films resultSetRijNaarFilms(ResultSet resultSet) throws SQLException {
		return new Films(resultSet.getLong("id"), 
							resultSet.getLong("genreId"), 
							resultSet.getString("titel"), 
							resultSet.getLong("voorraad"), 
							resultSet.getLong("gereserveerd"), 
							resultSet.getBigDecimal("prijs"));
	}
}
