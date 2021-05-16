package fr.adaming.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.adaming.entities.Client;
import fr.adaming.entities.Personne;

public class PersonneDaoImpl implements IPersonneDao {

	// Les constantes de la connexion
	public final static String URL = "jdbc:mysql://localhost:3306/db_projet_jdbc";
	public final static String USER = "root";
	public final static String MDP = "root";

	// Les objets de la connexion
	private Connection cx = null;
	private PreparedStatement ps = null;

	@Override
	public Personne seConnecter(Personne pIn) {

		try {
			// Etape1 : chargement du pilote
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape2 : ouvrir une connexion � la BD 
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape3 : ecrire la requete SQL
			String req = "SELECT * FROM personnes WHERE mail_p=? AND mdp_p=?";

			// Etape4a : creation ps
			ps = cx.prepareStatement(req);

			// Etape4b : passage des parametres de la requete
			ps.setString(1, pIn.getMail());
			ps.setString(2, pIn.getMdp());

			// Etape5 : envoyer requete sql et recuperer le resultat
			ResultSet rs = ps.executeQuery();

			// Etape6 : exploiter le r�sultat
			rs.next();

			Personne pOut = new Personne(rs.getInt("id_p"), rs.getString("mail_p"), rs.getString("mdp_p"));
			
			return pOut;

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermer les flux
			try {
				if (ps != null) {
					ps.close();
				}
				if (cx != null) {
					cx.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public int addPersonne(Personne pIn) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Etape 2 : Ouvrir une connexion � la BD
			cx = DriverManager.getConnection(URL, USER, MDP);
			
			// Etape 3 : Construire la requete SQL
			String req = "INSERT INTO personnes (mail_p, mdp_p) VALUES (?,?)";
			
			// Etape 4-a) Creation d'un objet Prepared Statement
			ps = cx.prepareStatement(req);
			
			// Etape 4-b) Passage des param�tres de la requete
			ps.setString(1, pIn.getMail());
			ps.setString(2, pIn.getMdp());
			
			// Etape 4-c) et 5 : Envoi de la requete et r�cuperation du resultat
			int verif = ps.executeUpdate();
			
			// Etape 6 : Exploitation du r�sultat
			return verif;
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			// Fermer les flux
			try {
				// Fermer le statement
				if (ps != null) {
					ps.close();
				}

				// Fermer la connexion
				if (cx != null) {
					cx.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return 0;
	}

	@Override
	public int updatePersonne(Personne pIn) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Etape 2 : Ouvrir une connexion � la BD
			cx = DriverManager.getConnection(URL, USER, MDP);
			
			// Etape 3 : Construction de la requete SQL
			String req = "UPDATE personnes SET mail_p=?, mdp_p=? WHERE id_p=?";
			
			// Etape 4-a) : Construction d'un objet de type prepared statement
			ps = cx.prepareStatement(req);
			
			// Etape 4-b) : Passage des param�tres de la requete
			ps.setString(1, pIn.getMail());
			ps.setString(2, pIn.getMdp());
			ps.setInt(3, pIn.getId());
			
			// Etape 4-c) et 5 : Envoi de la requete et r�cuperation du resultat
			int verif = ps.executeUpdate();
			
			return verif;
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			// Fermer les flux
			try {
				// Fermer le statement
				if (ps != null) {
					ps.close();
				}

				// Fermer la connexion
				if (cx != null) {
					cx.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int deletePersonne(int id) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL pour modifier �tudiant avec id
			String req = "DELETE FROM personnes WHERE id_p=?"; // Index � chaque point d'interrogation qui commence par 1

			// Etape 4 et 5 : Envoyer la requete SQL � la BD et r�cup�rer le r�sultat
			// 4-a : Recuperer un objet de type prepareStatement afin d'envoyer la requete
			ps = cx.prepareStatement(req);

			// 4-b : Passage des param�tres de la requete SQL
			ps.setInt(1, id);
			
			// 4-c et 5 : Envoyer la requete concretement et r�cup�rer le r�sultat
			int verif = ps.executeUpdate(); // Pas d'argument en entr�e car la requete est d�j� pass�e

			// Etape 6 : Exploitation du r�sultat
			return verif;
			
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {

			// Fermer les flux
			try {
				// Fermer le statement
				if (ps != null) {
					ps.close();
				}

				// Fermer la connexion
				if (cx != null) {
					cx.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public Personne getPersonneById(int id) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL pour modifier client avec id
			String req = "SELECT * FROM clients WHERE id_p=?"; // Index � chaque point d'interrogation qui commence par 1

			// Etape 4 et 5 : Envoyer la requete SQL � la BD et r�cup�rer le r�sultat
			// 4-a : Cr�ation d'un objet de type prepared Statement 
			ps = cx.prepareStatement(req);
			
			// 4-b : Assignation des param�tres s'il y en a 
			ps.setInt(1, id);
			
			// 4-c et 5 : Envoyer la requete concretement et r�cup�rer le r�sultat
			ResultSet resultat = ps.executeQuery();

			// Etape 6 : Exploitation du r�sultat
			// D�placer le curseur
			resultat.next(); // A ne pas oublier !!! Une seule ligne donc pas de while
			
			// Lire les valeurs de la ligne
			Personne pOut = new Personne(resultat.getInt("id_p"), resultat.getString("mail_p"), resultat.getString("mdp_p"));
			
			// Retourner cet �tudiant
			return pOut;            
					
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {

			// Fermer les flux
			try {
				// Fermer le statement
				if (ps != null) {
					ps.close();
				}

				// Fermer la connexion
				if (cx != null) {
					cx.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
