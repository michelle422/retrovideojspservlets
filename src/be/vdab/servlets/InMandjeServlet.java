package be.vdab.servlets;

import java.io.IOException;
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
import be.vdab.repositories.FilmsRepository;

/**
 * Servlet implementation class InMandjeServlet
 */
@WebServlet("/inmandje.htm")
public class InMandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/inmandje.jsp";
	private static final String MANDJE = "mandje";
	private final transient FilmsRepository filmsRepository = new FilmsRepository();
	
	@Resource(name = FilmsRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmsRepository.setDataSource(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
//			Map<String, BigDecimal> mandje = (Map<String, BigDecimal>) session.getAttribute(MANDJE);
			List<Films> mandje = (List<Films>) session.getAttribute(MANDJE);
			Films film = (Films) session.getAttribute("film");
			if (mandje == null) {
//				mandje = new HashMap<>();
				mandje = new ArrayList<>();
			}
			if (film != null) {
//				mandje.put(film.getTitel(), film.getPrijs());
				mandje.add(film);
				session.removeAttribute("film");
			}
			request.setAttribute("filmsInMandje", mandje);
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
//		Map<Long, String> mandje = (Map<Long, String>) session.getAttribute(MANDJE);
		List<Films> mandje = (List<Films>) session.getAttribute(MANDJE);
		String[] checked = request.getParameterValues("id");  
		if (checked != null) {
			for(String id : checked) {
//				Films film = filmsRepository.readFilmDetail(Long.parseLong(id));
//				int index = mandje.indexOf(film);
				mandje.remove(Integer.parseInt(id));
//				if (!id.isEmpty()) {
//					mandje.remove(Long.parseLong(id));  // ipv Integer.parseInt(id)
//				}
			}
			session.setAttribute(MANDJE, mandje);
		}
		response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
	}
}
