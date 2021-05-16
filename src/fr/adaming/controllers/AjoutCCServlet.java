package fr.adaming.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.adaming.entities.CompteCourant;
import fr.adaming.service.CompteCourantServiceImpl;
import fr.adaming.service.ICompteCourantService;

public class AjoutCCServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Transformation de l'aasociation UML en Java
				ICompteCourantService ccService = new CompteCourantServiceImpl();
				
				// R�cup�ration des parametres de la requete http
				String numeroCompte = req.getParameter("pNumeroCompte");
				double solde = Double.parseDouble(req.getParameter("pSolde"));
				double decouvert = Double.parseDouble(req.getParameter("pDecouvert"));
				
				// Instancier un compte courant avec ces valeurs
				CompteCourant ccNew = new CompteCourant(numeroCompte, solde, decouvert);
				
				// Appel de la m�thode service
				int verif = ccService.addCompteCourantService(ccNew);
				
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

}
