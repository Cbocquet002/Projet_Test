package fr.adaming.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.adaming.entities.Client;
import fr.adaming.service.ClientServiceImpl;
import fr.adaming.service.IClientService;

public class SupClientServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IClientService cService = new ClientServiceImpl();

		// R�cup�rer les param�tres de la requete
		int id = Integer.parseInt(req.getParameter("pId"));
		
		// Appel de la m�thode service
		cService.getClientByIdService(id);
		
		int verif = cService.deleteClientService(id);
		
		if (verif != 0) {

			// R�cup�rer la liste modifi�e
			List<Client> liste = cService.getAllClientService();

			// Ajouter la liste comme attribut de la requete
			// Passer de la Servlet � la JSP
			req.setAttribute("clients", liste);

			// Recuperer le support de d�l�gation

			RequestDispatcher rd = req.getRequestDispatcher("/listeClient.jsp");

			// Envoyer la requete
			rd.forward(req, resp);

		} 

	}
}
