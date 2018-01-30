package be.vdab.servlets;

import java.io.IOException;
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
//		String id = request.getParameter("id");
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			if (mandje != null) {
				request.setAttribute("filmsInMandje", 
						mandje.stream()
							.map(id -> filmsRepository.readFilmDetail(id))
							.filter(optionalPizza -> optionalPizza.isPresent())
							.map(optionalFilm -> optionalFilm.get())
							.collect(Collectors.toList()));
			}
//			if (StringUtils.isLong(id)) {
//				filmsRepository.readFilmDetail(Long.parseLong(id)).ifPresent(filmInMandje -> request.setAttribute("filmInMandje", filmInMandje));
//			} else {
//				request.setAttribute("fout", "id niet correct");
//			}
//			mandje.add(filmInMandje);
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
