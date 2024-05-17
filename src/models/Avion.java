package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Avion {
	private String immatriculation ;
	private int capacite;
	private String modele;
	private int idCompagnie;
	
	private String getImmatriculation() {
		return immatriculation;
	}
	private void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	private int getCapacite() {
		return capacite;
	}
	private void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	private String getModele() {
		return modele;
	}
	private void setModele(String modele) {
		this.modele = modele;
	}
	private int getIdCompagnie() {
		return idCompagnie;
	}
	private void setIdCompagnie(int idCompagnie) {
		this.idCompagnie = idCompagnie;
	}


	public static void AjouterAvions() {
		Avion a=new Avion();
		try (Scanner c = new Scanner(System.in)) {
			System.out.println("Entrez l'immatriculation de l'avion : ");
			a.setImmatriculation(c.next());
			System.out.println("Entrez le model de l'avion : ");
			a.setModele(c.next());

			System.out.println("Quel est le nombre de places dans l'avion : ");

			boolean entreeValide = false;
			while (!entreeValide) {
				if (c.hasNextInt()) {
					int nombreDePlaces = c.nextInt();
					if (nombreDePlaces > 0) {
						a.setCapacite(nombreDePlaces);
						System.out.println("Nombre de places dans l'avion: " + nombreDePlaces);
						entreeValide = true;
					} else {
						System.out.println("Veuillez entrer un nombre supérieur à 0.");
					}
				} else {
					System.out.println("Veuillez entrer un nombre entier.");
					c.next(); // Pour vider le buffer
				}
			}

			/* while (!entreeValide) {
				if (c.hasNextInt()) {
					 a.setCapacite(c.nextInt());
					 entreeValide = true;
				} else {
					System.out.println("Veuillez entrer un nombre entier.");
					c.next(); // Pour vider le buffer
				}
			} */


			System.out.println("Entrez l'identifiant de la compagnie : ");
			a.setIdCompagnie(c.nextInt());
		}

		String sql="INSERT INTO Avion VALUES(?,?,?,?)";
		Connexion.seConecter();
		try {
			PreparedStatement ps=Connexion.con.prepareStatement(sql);
			ps.setString(1, a.getImmatriculation());
			ps.setInt(2, a.getCapacite());
			ps.setString(3, a.getModele());
			ps.setInt(4, a.getIdCompagnie());
			ps.execute();
			System.out.println("Enregistrement effectuée avec succès !!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void ModifierAvions(){
		Scanner c = new Scanner(System.in);
		System.out.println("Veuille donnee le champt a modifier :");
		String val = c.next();
		System.out.println("Entre Votre modification :");
		String mod = c.next();
		System.out.println("Donnee l'immatriculation du champt a modifier :");
		String immatriculation = c.next();

		String sql = "UPDATE Avion SET "+val+" = ? WHERE immatriculation = ?";
		Connexion.seConecter();
		try {
			PreparedStatement p = Connexion.con.prepareStatement(sql);
			p.setString(1, mod);
			p.setString(2, immatriculation);
			p.executeUpdate();
			System.out.println("Modification effectuée avec succès");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void SupprimerAvion(){
		Scanner c = new Scanner(System.in);
		System.out.println("Entre l'immatriculation du champt a supprimer :");
		String immatriculation = c.next();

		Connexion.seConecter();
		String sql = "DELETE FROM Avion WHERE immatriculation = ?";
		try {
			PreparedStatement p = Connexion.con.prepareStatement(sql);
			p.setString(1, immatriculation);
			p.execute();
			System.out.println("Suppresion effectuée avec succès !!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int recupererCapaciteAvion(Connection connection, String idAvion) throws SQLException{
		// Préparer la requête SQL
        String sql = "SELECT capacite FROM Avion WHERE immatriculation='"+idAvion+"'";
		int capacit = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Vérifier si le ResultSet contient des données
            if (resultSet.next()) {
                // Récupérer la valeur unique
                capacit = resultSet.getInt("capacite");
                System.out.println("Capacite : " + capacit);

                // Utiliser la valeur récupérée
                // ... votre code ici pour utiliser la valeur 'nomUtilisateur'
            } else {
                System.out.println("Aucune donnée trouvée.");
            }
        }
		return capacit;
	}

	public static int updateAvionCapacite(Connection connection, int nombreDePlace, String idAvion){
		int nouvelleCapacite = -1;
		try {
			int cap = recupererCapaciteAvion(Connexion.con, idAvion);
			nouvelleCapacite = cap - nombreDePlace;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (nouvelleCapacite>=0) {
			// Mise a jour de la capacite de l'avion
			String sql1 = "UPDATE Avion SET capacite = ? WHERE immatriculation = ?";
			try (PreparedStatement statement1 = connection.prepareStatement(sql1)) {
				statement1.setInt(1, nouvelleCapacite);
				statement1.setString(2, idAvion);
				int lignesModifiees = statement1.executeUpdate();
				if (lignesModifiees > 0) {
					System.out.println("Avion a été modifié avec succès !");
					return 1;
				} else {
					System.out.println("Erreur lors de la modification de l'avion.");
				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		} else {
			System.out.println("L'avion est plein");
		}
		return nouvelleCapacite;
	}

	public static void listeDAvion(Connection connection) throws SQLException{
        // Create a statement object
		System.out.println("\n");
        try (Statement statement = connection.createStatement();
             // Create the SQL query to retrieve the entire column list
             ResultSet resultSet = statement.executeQuery("SELECT immatriculation, modele FROM Avion")) {

            // Iterate through the result set and print each value
            while (resultSet.next()) {
				
                String imm = resultSet.getString("immatriculation");
                String model = resultSet.getString("modele");

                System.out.print(imm + "- ");
                System.out.println(model);
            }
        }
		System.out.println("\n\n");
    }
}
