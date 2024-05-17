package models;

import java.sql.*;
import java.util.Scanner;

import models.Connexion;

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
		System.out.println("renseignez les information concernant la reservation. \n");
		r.setId_passager(id_passager);
		System.out.println("CHOISISEZ la ville de Depart :");
		String villeDepa = c.next();
		System.out.println("CHOISISEZ la ville D'arrive :");
		String villeDArrive = c.next();
		System.out.println("CHOISISEZ la date :");
		r.setDate_reservation(util.Date());
		System.out.println("Vous voulez reserver pour combien de place ?");



		boolean bool=false;
		while(!bool) {
			if(c.hasNextInt()) {
				r.setNbre_de_passager(c.nextInt());
				bool= true;
			}


			else {
				System.out.println("Entrer une un entier : ");
				bool =false;
				c.next();
			} }

		int a=r.getNbre_de_passager();
		Vol.volDispoAUneDate(Connexion.con, r.getDate_reservation(),a,villeDepa, villeDArrive);

		if(a>0) {
			try {

				Connexion.seConecter();
				PreparedStatement ps=Connexion.con.prepareStatement(sql);
				ps.setInt(1, r.getId_passager());ps.setString(2, r.getDate_reservation());
				ps.setInt(3, r.getNbre_de_passager());ps.execute();
				System.out.println("Reservation Effectuee Maintenant renseigner les infos des autre voyageurs \n :");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			String rq="INSERT INTO infopassager(idReservation,idVol,idCategorie,nomPassagerEtranger,prenomPassagerEtranger,numeroPasseport) "
					+ "values(?,?,?,?,?,?)";
			for(int j=1;j<= a;j++) {
				Infopassager i=new Infopassager();
				System.out.println("Enregistrer la personne "+j +"\n");
				System.out.println("Choisissez votre numero reservation :");
				i.setIdReservation(c.nextInt());
				System.out.println("Choisissez votre numero numero vol :");
				i.setIdVol(c.nextInt());
				System.out.println("Choisissez la categorie de reservation :");
				i.setIdCategorie(c.nextInt());
				System.out.println("Saiisissez la nom : ");
				i.setNomPassagerEtranger(c.next());
				System.out.println("Saisissez le prenom : ");
				i.setPrenomPassagerEtranger(c.next());
				System.out.println("Entrer le numero du passe-port : ");
				i.setNumeroPasseport(c.next());
				try {
					Connexion.seConecter();
					PreparedStatement ps=Connexion.con.prepareStatement(rq);
					ps.setInt(1, i.getIdReservation());ps.setInt(2, i.getIdVol());ps.setInt(3, i.getIdCategorie());
					ps.setString(4, i.getNomPassagerEtranger());ps.setString(5, i.getPrenomPassagerEtranger());
					ps.setString(6, i.getNumeroPasseport());
					ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Voulez-vous payer ???? : ");
				String b =c.next();

				if(b.equals("oui")) {

				}
			}



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
