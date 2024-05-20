package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import models.Connexion;
import utils.Color;
import utils.NameValidator;

import static models.util.isValidPassport;
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
		int montantTotal = 0;
		ArrayList<Integer> listeIdPassager = new ArrayList<Integer>();

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
		ArrayList<String> listIdVol = Vol.volDispoAUneDate(Connexion.con, r.getDate_reservation(),a,villeDepa, villeDArrive);
		boolean isVolDispo = !listIdVol.isEmpty();

        if (isVolDispo){
			Infopassager i=new Infopassager();
			String stIdVol;
			while (true){
				System.out.print("Choisissez votre numero vol : ");
				stIdVol = c.next();

				if(listIdVol.contains(stIdVol)){
					break;
				} else {
					System.out.println(Color.ANSI_RED+" ⚠️ Le Vol Selectionne n'existe pas"+Color.ANSI_RESET);
				}
			}
			i.setIdVol(Integer.parseInt(stIdVol));


			if(a>0) {
				try {
					PreparedStatement ps=Connexion.con.prepareStatement(sql);
					ps.setInt(1, r.getId_passager());ps.setString(2, r.getDate_reservation());
					ps.setInt(3, r.getNbre_de_passager());ps.execute();
					System.out.println(Color.ANSI_GREEN+ "Reservation Effectuee !!! \n"+Color.ANSI_RESET);
					System.out.println("--- Maintenant renseigner les infos des voyageurs ---");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				String rq="INSERT INTO infopassager(idReservation,idVol,idCategorie,nomPassagerEtranger,prenomPassagerEtranger,numeroPasseport, statut, tarif) "
						+ "values(?,?,?,?,?,?,?,?)";
				for(int j=1;j<= a;j++) {

					System.out.println(Color.ANSI_PURPLE+"			Enregistrer la personne "+j +"\n"+Color.ANSI_RESET);

					i.setIdReservation(recupererIdReservation(Connexion.con));

					// Choix De la Categorie
					System.out.println("Choisissez la categorie de reservation :");
					ArrayList<String> lisIdCat = Categorie.listeDeCategorie(Connexion.con);

					String stIdCat;
					while (true){
						System.out.print("Categorie: ");
						stIdCat = c.next();

						if(lisIdCat.contains(stIdCat)){
							break;
						} else {
							System.out.println(Color.ANSI_RED+" ⚠️ La Categorie Selectionne n'existe pas"+Color.ANSI_RESET);
						}
					}
					i.setIdCategorie(Integer.parseInt(stIdCat));

					int montant = 0;
					if (i.getIdCategorie()==1){
						montant = Vol.recupererVolTarif(Connexion.con, i.getIdVol()) + 100;
					} else if (i.getIdCategorie()==2) {
						montant = Vol.recupererVolTarif(Connexion.con, i.getIdVol()) + 50;
					} else {
						montant = Vol.recupererVolTarif(Connexion.con, i.getIdVol());
					}
					montantTotal = montantTotal + montant;


					//Nom Passager

					while (true) {
						System.out.print("Nom de Famille : ");
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

					String passeport;

					while (true) {
						System.out.print("Numero Passeport : ");
						i.setNumeroPasseport(c.next());
						if (isValidPassport(i.getNumeroPasseport())) {
							break;
						} else {
							System.out.println(Color.ANSI_RED+"⚠️ Passeport Invalide (9 Caractere alphanumerique)!!!"+Color.ANSI_RESET);
						}
					}


					try {
						PreparedStatement ps=Connexion.con.prepareStatement(rq);
						ps.setInt(1, i.getIdReservation());
						ps.setInt(2, i.getIdVol());
						ps.setInt(3, i.getIdCategorie());
						ps.setString(4, i.getNomPassagerEtranger());
						ps.setString(5, i.getPrenomPassagerEtranger());
						ps.setString(6, i.getNumeroPasseport());
						ps.setString(7, "non paye");
						ps.setInt(8, montant);
						ps.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listeIdPassager.add(Infopassager.recupererIdInfoPassager(Connexion.con));
				}
				System.out.println("Voulez-vous payer ???? : ");
				String b = c.next();

				if(b.equalsIgnoreCase("oui") || b.equalsIgnoreCase("o")) {
					Paiement p = new Paiement();
					Paiement.ajouterPaiement(montantTotal, i.getIdReservation(), c);
					for (int count=0; count<listeIdPassager.size();count++){
						statutPaye(listeIdPassager.get(count));
					}
				}


			}
		} else {
			System.out.println("Aucun vol Disponible !!!!\n");
		}
	}



	public void modifierReservation(ArrayList<String> lisIdModifiable, boolean annulation) {
		System.out.println(Color.ANSI_PURPLE+"-------- Modification en cours..\n"+Color.ANSI_RESET);
		if (!annulation){
			// Id du Champ a modifier
			String id;
			while (true){
				System.out.print("Choisissez l'identifiant de la reservation à modifier : ");
				id = c.next();

				if (lisIdModifiable.contains(id)){
					break;
				} else {
					System.out.println(Color.ANSI_RED + " ⚠️ L'identifiant entre n'existe pas" + Color.ANSI_RESET);
				}
			}


			ArrayList<String> listeDesChampsModifiables = new ArrayList<>();
			listeDesChampsModifiables.add("nom");
			listeDesChampsModifiables.add("prenom");
			listeDesChampsModifiables.add("passeport");
			System.out.println("Voici la liste des champs modifiables: ");
			System.out.println("[ nom, prenom, passeport ]");

			String champString;
			while (true) {
				System.out.print("Renseigner le champ à modifier : ");
				champString = c.next();
				if (listeDesChampsModifiables.contains(champString)) {
					break;
				} else {
					System.out.println(Color.ANSI_RED + " ⚠️ Entrer correctement le nom du champ" + Color.ANSI_RESET);
				}

			}


			String val;
			if (Objects.equals(champString, "nom")){
				champString = "nomPassagerEtranger";
				//Nom Passager
				while (true) {
					System.out.print("Renseigner la nouvelle valeur : ");
					val = c.next();
					if (NameValidator.isValidName(val)) {
						break;
					} else {
						System.out.println(Color.ANSI_RED+"⚠️ Nom Invalide !!!"+Color.ANSI_RESET);
					}
				}

			} else if (Objects.equals(champString, "prenom")) {
				champString = "prenomPassagerEtranger";

				// Prenon
				while (true) {
					System.out.print("Renseigner la nouvelle valeur : ");
					val = c.next();
					if (NameValidator.isValidName(val)) {
						break;
					} else {
						System.out.println(Color.ANSI_RED+"⚠️ Prenom Invalide !!!"+Color.ANSI_RESET);
					}
				}
			} else {
				champString = "numeroPasseport";

				// PassePort
				while (true) {
					System.out.print("Renseigner la nouvelle valeur : ");
					val = c.next();
					if (NameValidator.isValidName(val)) {
						break;
					} else {
						System.out.println(Color.ANSI_RED+"⚠️ Passeport Invalide (Le Passeport est compose de 9 Caracteres) !!!"+Color.ANSI_RESET);
					}
				}
			}

			String sql = "UPDATE infopassager SET " + champString + " = ? Where id = ?";

			try {
				PreparedStatement ps = Connexion.con.prepareStatement(sql);
				ps.setString(1, val);
				ps.setInt(2, Integer.parseInt(id));
				ps.execute();
				System.out.println(Color.ANSI_GREEN + "Modification reussie !!!\n"+Color.ANSI_RESET);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Id du Champ a modifier
			String id;
			while (true){
				System.out.print("Choisissez l'identifiant du champ à modifier : ");
				id = c.next();

				if (lisIdModifiable.contains(id)){
					break;
				}
			}

			String sql = "UPDATE infopassager SET statut = 'annule' Where id = ?";

			try {
				PreparedStatement ps = Connexion.con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(id));
				ps.execute();
				System.out.println("Annulation en cours...");
				Thread.sleep(1000);
				System.out.println("Remboursement en cours...");
				Thread.sleep(2000);
				System.out.println("Remboursement effectue");
				Thread.sleep(2000);
				System.out.println("Annulation Effectue");
				Thread.sleep(2000);
				System.out.println(Color.ANSI_GREEN + "Modification reussie !!!\n"+Color.ANSI_RESET);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

	private static void statutPaye(int id){
		String sql = "UPDATE infopassager SET statut='paye' WHERE id="+id;

		try {
			PreparedStatement ps = Connexion.con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
