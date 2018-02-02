package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Film;
import be.vdab.repositories.FilmRepository;

/**
 * Servlet implementation class InMandjeServlet
 */
@WebServlet("/inmandje.htm")
public class InMandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/inmandje.jsp";
	private static final String MANDJE = "mandje";
	private final transient FilmRepository filmRepository = new FilmRepository();
	
	@Resource(name = FilmRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmRepository.setDataSource(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			Film film = (Film) session.getAttribute("film");
			if (mandje == null) {
				mandje = new LinkedHashSet<>();	
			}
			if (film != null) {
				mandje.add(film.getId());
				session.removeAttribute("film");
			}
			request.setAttribute("filmsInMandje", 
									mandje.stream().map(id -> filmRepository.readFilmDetail(id))
									.collect(Collectors.toList()));
			session.setAttribute(MANDJE, mandje);
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
		String[] checked = request.getParameterValues("id");  
		if (checked != null) {
			for(String id : checked) {
				Film film = filmRepository.readFilmDetail(Long.parseLong(id));
				mandje.remove(film.getId());
			}
			session.setAttribute(MANDJE, mandje);
		}
		response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
	}
}
