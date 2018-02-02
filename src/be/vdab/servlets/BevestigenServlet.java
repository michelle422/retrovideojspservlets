package be.vdab.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Film;
import be.vdab.entities.Reservatie;
import be.vdab.repositories.FilmRepository;
import be.vdab.repositories.KlantRepository;
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
	private final transient KlantRepository klantRepository = new KlantRepository();
	private final transient ReservatieRepository reservatieRepository = new ReservatieRepository();
	private final transient FilmRepository filmRepository = new FilmRepository();
    
	@Resource(name = ReservatieRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
		reservatieRepository.setDataSource(dataSource);
		filmRepository.setDataSource(dataSource);
	}
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String klantId = request.getParameter("id");
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			int aantalFilms = mandje.size();
			request.setAttribute("aantalFilms", aantalFilms);
			if (StringUtils.isLong(klantId)) {
				klantRepository.readKlant(Long.parseLong(klantId)).ifPresent(klant -> request.setAttribute("klant", klant));
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
		Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
		List<Long> filmIds = new ArrayList<>();
		List<String> mislukt = new ArrayList<>();
		int aantalFilms = mandje.size();
		request.setAttribute("aantalFilms", aantalFilms);		
		for (Long id : mandje) {
			Film filmRec = filmRepository.readFilmDetail(id);
			if (filmRec.getVoorraad() > filmRec.getGereserveerd()) {
				filmIds.add(id);
			} else {
				String titel = filmRepository.readFilmDetail(id).getTitel();
				mislukt.add("Reservatie mislukt at ".concat(titel));
			}	
		}
		if (mislukt.isEmpty()) {
			for (long filmId : filmIds) {
				Reservatie reservatie = new Reservatie(Long.parseLong(klantId), filmId, LocalDateTime.now());
				reservatieRepository.createReservatie(reservatie);
			}
			session.invalidate();
			response.sendRedirect(request.getContextPath() + REDIRECT_URL);
		} else {
			request.setAttribute("mislukt", mislukt);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
