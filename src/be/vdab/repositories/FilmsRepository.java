package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

import be.vdab.entities.Films;
import be.vdab.entities.Genres;

public class FilmsRepository extends AbstractRepository {
	public static final String SELECT_FILM_VAN_GENRE = 
			"select id, genreid, titel, voorraad, gereserveerd, prijs from films where genreid = ? order by titel";
	public static final String SELECT_FILM_DETAIL =
			"select id, genreid, titel, voorraad, gereserveerd, prijs from films where id = ?";
	private static final String FIND_ALL_GENRES = 
			"select id, naam from genres order by naam";
	public static final String UPDATE_FILM = 
			"update films "
			+ "set gereserveerd = gereserveerd + 1 "
			+ "where id in (";
	
	public List<Films> readFilmsGenre(long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SELECT_FILM_VAN_GENRE)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			List<Films> filmsGenre = new ArrayList<>();
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
	
//	public Optional<Films> readFilmDetail(long id) {
	public Films readFilmDetail(long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SELECT_FILM_DETAIL)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
//			Optional<Films> film;
			Films film = null;
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					film = resultSetRijNaarFilms(resultSet);
//					film = Optional.of(resultSetRijNaarFilms(resultSet));
//				} else {
//					film = Optional.empty();
				}
			}
			connection.commit();
			return film;
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
	
	public void updateGereserveerd(List<Long> filmIds) {
		StringBuilder update = new StringBuilder(UPDATE_FILM);
		filmIds.stream().forEach(id -> update.append("?,"));
		update.deleteCharAt(update.length()-1);
		update.append(')');
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(update.toString())) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			int index = 1;
			for (long id : filmIds) {
				statement.setLong(index++, id);
			}
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}

	private Genres resultSetRijNaarGenres(ResultSet resultSet) throws SQLException {
		return new Genres(resultSet.getLong("id"), resultSet.getString("naam"));
	}
	
	private Films resultSetRijNaarFilms(ResultSet resultSet) throws SQLException {
		return new Films(resultSet.getLong("id"), 
							resultSet.getLong("genreid"),
							resultSet.getString("titel"), 
							resultSet.getLong("voorraad"), 
							resultSet.getLong("gereserveerd"), 
							resultSet.getBigDecimal("prijs"));
	}
}
