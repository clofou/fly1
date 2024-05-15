package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Reservation {
		private int id_passager;
		private String date_reservation;
		private int nbre_de_passager;


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

	public static void listeDeReservation(Connection connection) throws SQLException{
		System.out.println("\n");
        // Create a statement object
        try (Statement statement = connection.createStatement();
             // Create the SQL query to retrieve the entire column list
             ResultSet resultSet = statement.executeQuery("SELECT idPays, nom FROM Pays")) {

            // Iterate through the result set and print each value
            while (resultSet.next()) {
                String idPays = resultSet.getString("idPays");
                String nomPays = resultSet.getString("nom");

                System.out.print(idPays + "- ");
                System.out.println(nomPays);
            }
        }
		System.out.println("\n\n");
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

	public static void EffecuterReservation(Passager p, Scanner c) {
		String sql="INSERT INTO reservation(idPassager,dateReservation,nombreDePassager) values(?,?,?)";
		
		
		Reservation r=new Reservation();
		
		System.out.println("---- Informations concernant la reservation ----\n");
		r.setId_passager(p.getIdPassager());
		System.out.print("CHOISISEZ la ville Depart : ");
		String villeDeDepart = c.nextLine();
		System.out.print("CHOISISEZ la ville D'arrive : ");
		String villeDArrive = c.nextLine();
		System.out.print("CHOISISEZ la date : ");
		r.setDate_reservation(c.next());
		System.out.print("Vous voulez reserver pour combien de place ? ");
		r.setNbre_de_passager(c.nextInt());
		int a=r.getNbre_de_passager();
		
		System.out.println("\nVol disponible à cette date:");
		try {
			Vol.volDispoAUneDate(Connexion.con, r.date_reservation, r.getNbre_de_passager(), villeDeDepart, villeDArrive);
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}

		System.out.print("\nChoisissez l'immatriculation de l'avion : ");
		String idAvion = c.next();

		
		int canReserv = Avion.updateAvionCapacite(Connexion.con, a, idAvion);
		if (canReserv!=-1) {
			System.out.println("!!! Reservation Effectuee !!!\n\n");

			try {
					
				PreparedStatement ps=Connexion.con.prepareStatement(sql);
				ps.setInt(1, r.getId_passager());ps.setString(2, r.getDate_reservation());
				ps.setInt(3, r.getNbre_de_passager());ps.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
			if(a>0) {
				
				System.out.println("Maintenant renseigner les infos des voyageurs :");
				String rq="INSERT INTO infopassager(idReservation,idVol,idCategorie,nomPassagerEtranger,prenomPassagerEtranger,numeroPasseport) "
						+ "values(?,?,?,?,?,?)";

			
				for(int j=1;j<= a;j++) {
					Infopassager i=new Infopassager();
					try{
						i.setIdReservation(Reservation.recupererIdReservation(Connexion.con));
					} catch (SQLException e){
						e.printStackTrace();
					}
					System.out.println("!!! Enregistrer la personne "+j +" !!!");
					
					
					System.out.println("Liste Des Vols: \n");
					try {
						Vol.volDispoAUneDate(Connexion.con, r.date_reservation, r.getNbre_de_passager(), villeDeDepart, villeDArrive);
					} catch (SQLException e) {
						System.out.println(e.getStackTrace());
					}
					System.out.print("Entrer son numero de vol : ");
					i.setIdVol(c.nextInt());

					System.out.println("\nListe des Categories:");
					try {
						Categorie.listeDeCategorie(Connexion.con);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.print("Choisissez la categorie de sa reservation : ");
					i.setIdCategorie(c.nextInt());
					System.out.print("Saisissez son nom : ");
					i.setNomPassagerEtranger(c.next());
					System.out.print("Saisissez son prenom : ");
					i.setPrenomPassagerEtranger(c.next());
					System.out.print("son numero du passe-port : ");
					i.setNumeroPasseport(c.next());
					try {
						PreparedStatement ps=Connexion.con.prepareStatement(rq);
						ps.setInt(1, i.getIdReservation());ps.setInt(2, i.getIdVol());ps.setInt(3, i.getIdCategorie());
						ps.setString(4, i.getNomPassagerEtranger());ps.setString(5, i.getPrenomPassagerEtranger());
						ps.setString(6, i.getNumeroPasseport());
						ps.execute();

						System.out.println("\n\n  ??? Veillez Effectuer Le paiement ....");
						try{
							i.setIdReservation(Reservation.recupererIdReservation(Connexion.con));
						} catch (SQLException e){
							e.printStackTrace();
						}
						
						
						System.out.println("\n--- Reservation Terminé avec Succès --- \n");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Paiement.ajouterPaiement(Connexion.con, c);
			}
		}
	}
	
	public void modifierReservation(Scanner c) {
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
			System.out.println("Modification reussie !!! : ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void supprimerResevation(Scanner c) {
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
			e.printStackTrace();
		}
		
		
		}

}
