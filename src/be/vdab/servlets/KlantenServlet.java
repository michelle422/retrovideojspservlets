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
import javax.sql.DataSource;

import be.vdab.repositories.KlantenRepository;

/**
 * Servlet implementation class KlantenServlet
 */
@WebServlet("/klanten.htm")
public class KlantenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/klanten.jsp";
	private final transient KlantenRepository klantenRepository = new KlantenRepository();

	@Resource(name = KlantenRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantenRepository.setDataSource(dataSource);
	}
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getQueryString() != null) {
			List<String> fouten = new ArrayList<>();
			String familienaam = request.getParameter("familienaam");
			if (familienaam.isEmpty()) {
				fouten.add("verplicht");
			}
			if (fouten.isEmpty()) {
				request.setAttribute("klanten", klantenRepository.readKlanten(familienaam));
			} else {
				request.setAttribute("fouten", fouten);
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
