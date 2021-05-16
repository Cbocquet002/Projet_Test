package fr.adaming.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.adaming.entities.CompteEpargne;
import fr.adaming.service.CompteEpargneServiceImpl;
import fr.adaming.service.ICompteEpargneService;

public class ModifCEServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ICompteEpargneService ceService = new CompteEpargneServiceImpl();
		
		// R�cup�rer param req
		int idCE = Integer.parseInt(req.getParameter("pIdCE"));

		CompteEpargne ce = ceService.getCompteEpargneByIdService(idCE);

		req.setAttribute("ceModif", ce);

		// Recuperer le support de d�l�gation
		RequestDispatcher rd = req.getRequestDispatcher("/modifCE.jsp");

		// Envoyer la requete
		rd.forward(req, resp);

	}
	
	private Email envoyerMail;
	
	@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Traduction association UML en Java
				ICompteEpargneService ceService = new CompteEpargneServiceImpl();
				
				// R�cup�rer les param�tres de la requete http
				int id = Integer.parseInt(req.getParameter("pId"));
				String numeroCompte = req.getParameter("pNumeroCompte");
				double solde = Double.parseDouble(req.getParameter("pSolde"));
				double taux = Double.parseDouble(req.getParameter("pTaux"));
				
				// Envoyer un mail de modification
				String destinataire = req.getParameter("pMail");

				String sujet = "modification d'un compte �pargne";
				String message = "la modification du compte �pargne a bien �t� r�alis�e !!";
				String expediteur = "morgan.ledortz@gmail.com";
				String username = "morgan.ledortz";
				String password = "motdepasse"; // Pour des raisons de s�curit� je ne mets pas mon mot de passe, je pourrai faire une d�mo avec lundi

				// Instancier un client avec ces valeurs
				CompteEpargne ce1 = new CompteEpargne(id, numeroCompte, solde, taux);
				
				// Appel de la m�thode service
				int verif = ceService.updateCompteEpargneService(ce1);
				
				if (verif != 0) {
					
					if (req.getParameter("bouton") != null)
						try {
							envoyerMail.sendEmail(expediteur, username, password, destinataire, sujet, message);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					// R�cup�rer la liste modifi�e
					List<CompteEpargne> liste = ceService.getAllCompteEpargneService();

					// Ajouter la liste comme attribut de la requete
					// Passer de la Servlet � la JSP
					req.setAttribute("ces", liste);

					// Recuperer le support de d�l�gation

					RequestDispatcher rd = req.getRequestDispatcher("/listeCE.jsp");

					// Envoyer la requete
					rd.forward(req, resp);

				} else {
					req.setAttribute("erreur", verif);

					// Recuperer le support de d�l�gation
					RequestDispatcher rd = req.getRequestDispatcher("/modifCE.jsp");

					// Envoyer la requete
					rd.forward(req, resp);
				}

			}
}
