package fr.adaming.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.adaming.entities.CompteEpargne;

public class CompteEpargneDaoImpl implements ICompteEpargneDao{

	// D�claration des infos de la connexion � la BD
	public static final String URL = "jdbc:mysql://localhost:3306/db_projet_jdbc";
	public static final String USER = "root";
	public static final String MDP = "root";
	
	private Connection cx = null;
	private PreparedStatement ps = null;
	
	@Override
	public int addCompteEpargneDao(CompteEpargne ce) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Etape 2 : Ouvrir une connexion � la BD
			cx = DriverManager.getConnection(URL, USER, MDP);
			
			// Etape 3 : Construire la requete SQL
			String req = "INSERT INTO compte_epargnes (taux_ce, num_compte_ce, solde_ce) VALUES (?,?,?)";
			
			// Etape 4-a) Creation d'un objet Prepared Statement
			ps = cx.prepareStatement(req);
			
			// Etape 4-b) Passage des param�tres de la requete
			ps.setDouble(1, ce.getTaux());
			ps.setString(2, ce.getNumeroCompte());
			ps.setDouble(3, ce.getSolde());
			
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
	public int updateCompteEpargneDao(CompteEpargne ce) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Etape 2 : Ouvrir une connexion � la BD
			cx = DriverManager.getConnection(URL, USER, MDP);
			
			// Etape 3 : Construction de la requete SQL
			String req = "UPDATE compte_epargnes SET taux_ce=?, num_compte_ce=?, solde_ce=? WHERE id_ce=?";
			
			// Etape 4-a) : Construction d'un objet de type prepared statement
			ps = cx.prepareStatement(req);
			
			// Etape 4-b) : Passage des param�tres de la requete
			ps.setDouble(1, ce.getTaux());
			ps.setString(2, ce.getNumeroCompte());
			ps.setDouble(3, ce.getSolde());
			ps.setInt(4, ce.getIdCo());
			
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
	public int deleteCompteEpargneDao(int id) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL pour modifier �tudiant avec id
			String req = "DELETE FROM compte_epargnes WHERE id_ce=?"; // Index � chaque point d'interrogation qui commence par 1

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
	public List<CompteEpargne> getAllCompteEpargneDao() {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL 
			String req = "SELECT id_ce, taux_ce, num_compte_ce, solde_ce FROM compte_epargnes"; // Index � chaque point d'interrogation qui commence par 1

			// Etape 4 et 5 : Envoyer la requete SQL � la BD et r�cup�rer le r�sultat
			// 4-a : Recup�ration d'un objet de type Prepared Statement
			ps = cx.prepareStatement(req);
			
			// 4-b et 5 : Envoyer la requete concretement et r�cup�rer le r�sultat
			ResultSet rs = ps.executeQuery();

			// Etape 6 : Exploitation du r�sultat
			List<CompteEpargne> listeOut = new ArrayList<>();
			
			// Transformer le ResultSet en list d'�tudiant
			while (rs.next()) // next() Moves the cursor forward one row from its current position et retourne un boolean
			{
				// R�cup�rer les valeurs de l'enregistrment de l'itt�ration actuelle
				int idOut = rs.getInt("id_ce");
				double tauxOut = rs.getDouble("taux_ce");
				String numCompteOut = rs.getString("num_compte_ce");
				double soldeOut = rs.getDouble("solde_ce");
				
				// Instanciation etudiant � partir de ces valeurs
				CompteEpargne cOut = new CompteEpargne (idOut, numCompteOut, soldeOut, tauxOut);
				
				// Ajouter cet �tudiant dans la liste pour ne pas l'�craser pendant l'itt�ration suivante
				listeOut.add(cOut);
				
				// listeOut.add(new Etudiant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			return listeOut;            
					
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

	@Override
	public CompteEpargne getCompteEpargneByIdDao(int id) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL pour modifier compte courant avec id
			String req = "SELECT * FROM compte_epargnes WHERE id_ce=?"; // Index � chaque point d'interrogation qui commence par 1

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
			CompteEpargne ccOut = new CompteEpargne(resultat.getInt("id_ce"), resultat.getString("num_compte_ce"), resultat.getDouble("solde_ce"), resultat.getDouble("taux_ce"));
			
			// Retourner cet �tudiant
			return ccOut;            
					
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

	@Override
	public CompteEpargne getCompteEpargneByProprietaireIdDao(int id) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL pour modifier compte courant avec id
			String req = "SELECT * FROM compte_epargnes WHERE cl_id=?"; // Index � chaque point d'interrogation qui commence par 1

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
			CompteEpargne ccOut = new CompteEpargne(resultat.getInt("id_ce"), resultat.getString("num_compte_ce"), resultat.getDouble("solde_ce"), resultat.getDouble("taux_ce"));
			
			// Retourner cet �tudiant
			return ccOut;            
					
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

	@Override
	public int attributeCompteEpargneToClientDao(CompteEpargne ce) {
		try {
			// Etape 1 : Chargement du pilote
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Etape 2 : Ouvrir une connexion
			cx = DriverManager.getConnection(URL, USER, MDP);
			
			// Etape 3 : Ecrire la requete
			String req = "UPDATE compte_epargnes SET cl_id=? WHERE id_ce=?";
			
			// Etape 4-a) : Construction objet de type prepared statement
			ps = cx.prepareStatement(req);
			
			// Etape 4-b) : Passage des param�tres
			ps.setInt(1, ce.getCl().getIdCl());
			ps.setInt(2, ce.getIdCo());
			
			// Etape 4-c) et 5 : Envoi de la requete et r�cuperation du resultat
			int verif = ps.executeUpdate();
						
			return verif;
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {

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
	public int withdrawMoneyDao(CompteEpargne ce, double montant) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL pour modifier compte courant avec id
			String req = "UPDATE compte_epargnes SET solde_ce=? WHERE id_ce=?"; // Index � chaque point d'interrogation qui commence par 1

			// Etape 4 et 5 : Envoyer la requete SQL � la BD et r�cup�rer le r�sultat
			// 4-a : Cr�ation d'un objet de type prepared Statement 
			ps = cx.prepareStatement(req);
			
			// 4-b : Assignation des param�tres s'il y en a 
			ps.setDouble(1, ce.getSolde()-montant);
			ps.setInt(2, ce.getIdCo());
			
			// 4-c et 5 : Envoyer la requete concretement et r�cup�rer le r�sultat
			int verif = ps.executeUpdate();

			// Etape 6 : Exploitation du r�sultat
			// D�placer le curseur
			
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
	public int addMoneyDao(CompteEpargne ce, double montant) {
		try {
			// Etape 1 : Chargement du pilote dans la m�moire
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : Ouvrir une connection � la bd
			cx = DriverManager.getConnection(URL, USER, MDP);

			// Etape 3 : Construire la SQL pour modifier compte courant avec id
			String req = "UPDATE compte_epargnes SET solde_ce=? WHERE id_ce=?"; // Index � chaque point d'interrogation qui commence par 1

			// Etape 4 et 5 : Envoyer la requete SQL � la BD et r�cup�rer le r�sultat
			// 4-a : Cr�ation d'un objet de type prepared Statement 
			ps = cx.prepareStatement(req);
			
			// 4-b : Assignation des param�tres s'il y en a 
			ps.setDouble(1, ce.getSolde()+montant);
			ps.setInt(2, ce.getIdCo());
			
			// 4-c et 5 : Envoyer la requete concretement et r�cup�rer le r�sultat
			int verif = ps.executeUpdate();

			// Etape 6 : Exploitation du r�sultat
			// D�placer le curseur
			
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
}
