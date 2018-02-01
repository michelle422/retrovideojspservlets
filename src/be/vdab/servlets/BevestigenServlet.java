package be.vdab.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Films;
import be.vdab.entities.Reservaties;
import be.vdab.repositories.FilmsRepository;
import be.vdab.repositories.KlantenRepository;
import be.vdab.repositories.ReservatieRepository;
import be.vdab.utils.StringUtils;

/**
 * Servlet implementation class BevestigenServlet
 */
@WebServlet("/bevestigen.htm")
public class BevestigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/bevestigen.jsp";
	private static final String MANDJE = "mandje";
	private static final String REDIRECT_URL = "/rapport.htm";
	private final transient KlantenRepository klantenRepository = new KlantenRepository();
	private final transient ReservatieRepository reservatieRepository = new ReservatieRepository();
	private final transient FilmsRepository filmsRepository = new FilmsRepository();
    
	@Resource(name = ReservatieRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantenRepository.setDataSource(dataSource);
		reservatieRepository.setDataSource(dataSource);
		filmsRepository.setDataSource(dataSource);
	}
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String klantId = request.getParameter("id");
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
//			Map<Long, String> mandje = (Map<Long, String>) session.getAttribute(MANDJE);
			List<Films> mandje = (List<Films>) session.getAttribute(MANDJE);
			int aantalFilms = mandje.size();
			request.setAttribute("aantalFilms", aantalFilms);
			if (StringUtils.isLong(klantId)) {
				klantenRepository.readKlant(Long.parseLong(klantId)).ifPresent(klant -> request.setAttribute("klant", klant));
			} else {
				request.setAttribute("fout", "id niet correct");
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String klantId = request.getParameter("id");
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		List<Films> mandje = (List<Films>) session.getAttribute(MANDJE);
		List<Long> filmIds = new ArrayList<>();
		List<String> mislukt = new ArrayList<>();
		int aantalFilms = mandje.size();
		request.setAttribute("aantalFilms", aantalFilms);		
		for (Films film : mandje) {
			Films filmRec = filmsRepository.readFilmDetail(film.getId());
			if (filmRec.getVoorraad() > filmRec.getGereserveerd()) {
				filmIds.add(film.getId());
			} else {
				mislukt.add("Reservatie mislukt at ".concat(film.getTitel()));
			}	
		}
		if (mislukt.isEmpty()) {
			for (long filmId : filmIds) {
				Reservaties reservatie = new Reservaties(Long.parseLong(klantId), filmId, LocalDateTime.now());
				reservatieRepository.createReservatie(reservatie);
			}
			filmsRepository.updateGereserveerd(filmIds);
			session.invalidate();
			response.sendRedirect(request.getContextPath() + REDIRECT_URL);
		} else {
			request.setAttribute("mislukt", mislukt);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
