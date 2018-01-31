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
//import be.vdab.entities.Films;
import be.vdab.repositories.FilmsRepository;
//import be.vdab.utils.StringUtils;

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
			List<Films> mandje = (List<Films>) session.getAttribute(MANDJE);
			Films film = (Films) session.getAttribute("film");
			if (mandje == null) {
				mandje = new ArrayList<>();
			}
			if (film != null) {
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
		doGet(request, response);
	}

}
