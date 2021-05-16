package fr.adaming.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.adaming.entities.CompteEpargne;
import fr.adaming.service.CompteEpargneServiceImpl;
import fr.adaming.service.ICompteEpargneService;

public class SupCEServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ICompteEpargneService ceService = new CompteEpargneServiceImpl();

		// R�cup�rer les param�tres de la requete
		int id = Integer.parseInt(req.getParameter("pId"));
		
		// Appel de la m�thode service
		ceService.getCompteEpargneByIdService(id);
		
		int verif = ceService.deleteCompteEpargneService(id);
		
		if (verif != 0) {

			// R�cup�rer la liste modifi�e
			List<CompteEpargne> liste = ceService.getAllCompteEpargneService();

			// Ajouter la liste comme attribut de la requete
			// Passer de la Servlet � la JSP
			req.setAttribute("ces", liste);

			// Recuperer le support de d�l�gation

			RequestDispatcher rd = req.getRequestDispatcher("/listeCE.jsp");

			// Envoyer la requete
			rd.forward(req, resp);

		} 

	}
}
