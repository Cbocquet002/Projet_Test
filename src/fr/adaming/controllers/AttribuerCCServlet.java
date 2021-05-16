package fr.adaming.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.adaming.entities.Client;
import fr.adaming.entities.CompteCourant;
import fr.adaming.service.ClientServiceImpl;
import fr.adaming.service.CompteCourantServiceImpl;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICompteCourantService;

public class AttribuerCCServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		IClientService cService = new ClientServiceImpl();
		ICompteCourantService ccService = new CompteCourantServiceImpl();
		
		// R�cup�rer les param�tres de la requette http
		int idCl = Integer.parseInt(req.getParameter("pIdClient"));
		Client client = cService.getClientByIdService(idCl);
		
		int idCo = Integer.parseInt(req.getParameter("pIdCompte"));
		CompteCourant compte = ccService.getCompteCourantByIdService(idCo);
		
		// Appel de la m�thode service
		int verif = ccService.attributeCompteCourantToClientService(compte, client);
		
		if (verif != 0) {

			// R�cup�rer la nouvelle liste
			List<CompteCourant> liste = ccService.getAllCompteCourantService();

			// Ajouter la liste comme attribut de la requete
			// Passer de la Servlet � la JSP
			req.setAttribute("ccs", liste);

			// Recuperer le support de d�l�gation

			RequestDispatcher rd = req.getRequestDispatcher("/listeCC.jsp");

			// Envoyer la requete
			rd.forward(req, resp);

		} else {
			req.setAttribute("erreur", verif);

			// Recuperer le support de d�l�gation
			RequestDispatcher rd = req.getRequestDispatcher("/ajoutCC.jsp");

			// Envoyer la requete
			rd.forward(req, resp);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		IClientService cService = new ClientServiceImpl();
		ICompteCourantService ccService = new CompteCourantServiceImpl();
		
		// Liste des clients et des comptes courants
		List<CompteCourant> list = ccService.getAllCompteCourantService();
		List<Client> listec = cService.getAllClientService();
		
		// R�cup�rer le support de la d�l�gation
		RequestDispatcher rd = req.getRequestDispatcher("/ajoutCC.jsp");

		req.setAttribute("ccs", list);
		req.setAttribute("cs", listec);

		// Deleguer la requette http
		rd.forward(req, resp);		
	}
	
}
