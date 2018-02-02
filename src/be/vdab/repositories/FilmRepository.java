package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

import be.vdab.entities.Film;

public class FilmRepository extends AbstractRepository {
	public static final String SELECT_FILM_VAN_GENRE = 
			"select id, genreid, titel, voorraad, gereserveerd, prijs from films where genreid = ? order by titel";
	public static final String SELECT_FILM_DETAIL =
			"select id, genreid, titel, voorraad, gereserveerd, prijs from films where id = ?";
	
	public List<Film> readFilmsGenre(long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SELECT_FILM_VAN_GENRE)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			List<Film> filmsGenre = new ArrayList<>();
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					filmsGenre.add(resultSetRijNaarFilms(resultSet));
				}
			}
			connection.commit();
			return filmsGenre;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public Film readFilmDetail(long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SELECT_FILM_DETAIL)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			Film film = null;
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					film = resultSetRijNaarFilms(resultSet);
				}
			}
			connection.commit();
			return film;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	private Film resultSetRijNaarFilms(ResultSet resultSet) throws SQLException {
		return new Film(resultSet.getLong("id"), 
							resultSet.getLong("genreid"),
							resultSet.getString("titel"), 
							resultSet.getLong("voorraad"), 
							resultSet.getLong("gereserveerd"), 
							resultSet.getBigDecimal("prijs"));
	}
}
