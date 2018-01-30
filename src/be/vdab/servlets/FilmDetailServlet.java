package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.repositories.FilmsRepository;
import be.vdab.utils.StringUtils;

/**
 * Servlet implementation class FilmDetailServlet
 */
@WebServlet("/filmdetail.htm")
public class FilmDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/filmdetail.jsp";
	private static final String REDIRECT_URL = "/inmandje.htm";
	private final static String MANDJE = "mandje";
	private final transient FilmsRepository filmsRepository = new FilmsRepository();
       
	@Resource(name = FilmsRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmsRepository.setDataSource(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (StringUtils.isLong(id)) {
			filmsRepository.readFilmDetail(Long.parseLong(id)).ifPresent(film -> request.setAttribute("film", film));
		} else {
			request.setAttribute("fout", "id niet correct");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String inMandje = request.getParameter("beschikbaar");
//		System.out.println(inMandje);
		String id = request.getParameter("id");
		if (id != null) {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			if (mandje == null) {
				mandje = new LinkedHashSet<>();
			}
			mandje.add(Long.parseLong(id));
			session.setAttribute(MANDJE, mandje);
		}
		response.sendRedirect(request.getContextPath() + REDIRECT_URL);
	}

}
