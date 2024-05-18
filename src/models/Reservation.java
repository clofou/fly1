package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import models.Connexion;
import utils.Color;
import utils.NameValidator;

import static utils.Date.lireDateValide;

public class Reservation {
	private int id_passager;
	private String date_reservation;
	private int nbre_de_passager;


	Scanner c=new Scanner(System.in);

	public int getId_passager() {
		return id_passager;
	}

	public void setId_passager(int id_passager) {
		this.id_passager = id_passager;
	}



	public String getDate_reservation() {
		return date_reservation;
	}

	public void setDate_reservation(String string) {
		this.date_reservation = string;
	}
	public int getNbre_de_passager() {
		return nbre_de_passager;
	}

	public void setNbre_de_passager(int nbre_de_passager) {
		this.nbre_de_passager = nbre_de_passager;
	}

	public void EffecuterReservation(int id_passager) throws SQLException {
		String sql="INSERT INTO reservation(idPassager,dateReservation,nombreDePassager) values(?,?,?)";

		Reservation r=new Reservation();
		System.out.println(Color.ANSI_BLUE+"----- Renseignez les informations concernant la reservation. -----\n"+Color.ANSI_RESET);
		r.setId_passager(id_passager);
		String villeDepa;
		while (true){
			System.out.print("Choisissez la ville de Depart : ");
			ArrayList<String> listeDeVille = Ville.listeDeVille(Connexion.con);
			villeDepa = c.next();
			if (listeDeVille.contains(villeDepa)){
				break;
			} else {
				System.out.println(Color.ANSI_RED+"La Ville que vous avez entrer ne figure pas dans la liste des villes enregistree\n"+Color.ANSI_RESET);
			}
		}


		System.out.print("Choisissez la ville D'arrivee : ");
		String villeDArrive;
		while (true){
			System.out.print("Choisissez la ville d'arrive : ");
			ArrayList<String> listeDeVille = Ville.listeDeVille(Connexion.con);
			villeDArrive = c.next();
			if (listeDeVille.contains(villeDArrive)){
				break;
			} else {
				System.out.println(Color.ANSI_RED+"La Ville que vous avez entrer ne figure pas dans la liste des villes enregistree\n"+Color.ANSI_RESET);
			}
		}
		System.out.print("Choisissez la date ");
		r.setDate_reservation(new utils.Date(lireDateValide()).formatAnglais());

		System.out.print("Vous voulez reserver pour combien de place ? ");
		while(true) {
			if(c.hasNextInt()) {
				r.setNbre_de_passager(c.nextInt());
				if(r.getNbre_de_passager() >= 1 && r.getNbre_de_passager() <= 9){
					break;
				}
			}
			else {
				System.out.println(Color.ANSI_RED+" ⚠️ Entrer un entier Compris entre 1 et 9 : "+Color.ANSI_RESET);
                c.next();
			}
		}
		int a=r.getNbre_de_passager();


		// Affiche La liste des Vols Correspondants
		boolean isVolDispo = Vol.volDispoAUneDate(Connexion.con, r.getDate_reservation(),a,villeDepa, villeDArrive);

		if (isVolDispo){
			Infopassager i=new Infopassager();
			ArrayList<Integer> listIdVol = Vol.listeDesIds(Connexion.con);
			while (true){
				System.out.print("Choisissez votre numero vol : ");
				i.setIdVol(c.nextInt());
				if(listIdVol.contains(i.getIdVol())){
					break;
				} else {
					System.out.println(Color.ANSI_RED+" ⚠️ Le Vol Selectionne n'existe pas"+Color.ANSI_RESET);
				}
			}


			if(a>0) {
				try {
					Connexion.seConecter();
					PreparedStatement ps=Connexion.con.prepareStatement(sql);
					ps.setInt(1, r.getId_passager());ps.setString(2, r.getDate_reservation());
					ps.setInt(3, r.getNbre_de_passager());ps.execute();
					System.out.println(Color.ANSI_GREEN+ "Reservation Effectuee !!! \n"+Color.ANSI_RESET);
					System.out.println("--- Maintenant renseigner les infos des voyageurs ---");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				String rq="INSERT INTO infopassager(idReservation,idVol,idCategorie,nomPassagerEtranger,prenomPassagerEtranger,numeroPasseport) "
						+ "values(?,?,?,?,?,?)";
				for(int j=1;j<= a;j++) {

					System.out.println(Color.ANSI_PURPLE+"			Enregistrer la personne "+j +"\n"+Color.ANSI_RESET);

					i.setIdReservation(recupererIdReservation(Connexion.con));

					System.out.println("Choisissez la categorie de reservation :");
					Categorie.listeDeCategorie(Connexion.con);
					System.out.print("Categorie: ");
					i.setIdCategorie(c.nextInt());


					//Nom Passager

					while (true) {
						System.out.print("nom de Famille : ");
						i.setNomPassagerEtranger(c.next());
						if (NameValidator.isValidName(i.getNomPassagerEtranger())) {
							break;
						} else {
							System.out.println(Color.ANSI_RED+"⚠️ Nom Invalide !!!"+Color.ANSI_RESET);
						}
					}
					//Prenom Passager
					String prenom;

					while (true) {
						System.out.print("Votre Prenom : ");
						i.setPrenomPassagerEtranger(c.next());
						if (NameValidator.isValidName(i.getPrenomPassagerEtranger())) {
							break;
						} else {
							System.out.println(Color.ANSI_RED+"⚠️ prenom Invalide !!!"+Color.ANSI_RESET);
						}
					}

					System.out.print("Entrer le numero du passe-port : ");
					i.setNumeroPasseport(c.next());


					try {
						PreparedStatement ps=Connexion.con.prepareStatement(rq);
						ps.setInt(1, i.getIdReservation());
						ps.setInt(2, i.getIdVol());
						ps.setInt(3, i.getIdCategorie());
						ps.setString(4, i.getNomPassagerEtranger());
						ps.setString(5, i.getPrenomPassagerEtranger());
						ps.setString(6, i.getNumeroPasseport());
						ps.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Voulez-vous payer ???? : ");
				String b =c.next();

				if(b.equals("oui")) {

				}


			}
		} else {
			System.out.println("Aucun vol Disponible !!!!");
		}
	}



	public void modifierReservation() {
		System.out.println("Renseigner le champ à modifier : ");
		String champString=c.next();
		System.out.println("Renseigner la modifier à effectuer : ");
		String val=c.next();
		System.out.println("Quel l'identifiant du champ à modifier : ");
		int id=c.nextInt();
		String sql="UPDATE infopassager SET "+champString+" = ? Where id = ?";
		Connexion.seConecter();

		try {
			PreparedStatement ps=Connexion.con.prepareStatement(sql);
			ps.setString(1, val);
			ps.setInt(2, id);
			ps.execute();
			System.out.println("Modification reusse !!! : ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void supprimerResevation() {
		System.out.println("Entrez l'identifiant de la ligne à supprimer : ");
		int id=c.nextInt();
		String sq="DELETE  FROM infopassager where id=?";
		Connexion.seConecter();
		try {
			PreparedStatement ps=Connexion.con.prepareStatement(sq);
			ps.setInt(1, id);
			ps.execute();
			System.out.println("Suppression reussi !!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	private static int recupererIdReservation(Connection connection) throws SQLException {
		// Préparer la requête SQL
		String sql = "SELECT idReservation FROM Reservation ORDER BY idReservation DESC LIMIT 1";
		int idReservation = 0;
		try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(sql)) {

			// Vérifier si le ResultSet contient des données
			if (resultSet.next()) {
				// Récupérer la valeur unique
				idReservation = resultSet.getInt("idReservation");
				System.out.println("Id de la Reservation : " + idReservation);

				// Utiliser la valeur récupérée
				// ... votre code ici pour utiliser la valeur 'nomUtilisateur'
			} else {
				System.out.println("Aucune donnée trouvée.");
			}
		}
		return idReservation;
	}


}
