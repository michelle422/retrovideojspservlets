package be.vdab.servlets;

import java.io.IOException;
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
import be.vdab.repositories.KlantenRepository;
import be.vdab.utils.StringUtils;

/**
 * Servlet implementation class BevestigenServlet
 */
@WebServlet("/bevestigen.htm")
public class BevestigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/bevestigen.jsp";
	private static final String MANDJE = "mandje";
	private final transient KlantenRepository klantenRepository = new KlantenRepository();
    
	@Resource(name = KlantenRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantenRepository.setDataSource(dataSource);
	}
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String klantId = request.getParameter("id");
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
