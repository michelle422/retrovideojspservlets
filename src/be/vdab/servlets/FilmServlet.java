package be.vdab.servlets;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Genres;
import be.vdab.repositories.FilmsRepository;
import be.vdab.utils.StringUtils;

/**
 * Servlet implementation class KinderfilmServlet
 */
@WebServlet("*.htm")
public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/film.jsp";
	private final transient FilmsRepository filmsRepository = new FilmsRepository();

	@Resource(name = FilmsRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmsRepository.setDataSource(dataSource);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Genres> genres = filmsRepository.findAllGenres();
		request.setAttribute("genres", genres);
		String genreId = request.getParameter("id"); 
		if (StringUtils.isLong(genreId)) {
			request.setAttribute("films", filmsRepository.readFilmsGenre(Long.parseLong(genreId)));
		} else {
			request.setAttribute("fout", "id niet correct");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}