package be.vdab.servlets;

import java.io.IOException;

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
			request.setAttribute("film", filmsRepository.readFilmDetail(Long.parseLong(id)));
//			filmsRepository.readFilmDetail(Long.parseLong(id)).ifPresent(film -> request.setAttribute("film", film));
		} else {
			request.setAttribute("fout", "id niet correct");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		if (StringUtils.isLong(id)) {
			session.setAttribute("film", filmsRepository.readFilmDetail(Long.parseLong(id)));
		} else {
			request.setAttribute("fout", "id niet correct");
		}

		response.sendRedirect(request.getContextPath() + REDIRECT_URL);
	}

}
