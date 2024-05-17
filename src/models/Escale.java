package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Escale {

	private int idVille;
	private int idVol;
	private String immatriculation;
	private String dateHeure;

	public Escale(int idVille, int idVol, String immatriculation, String dateHeure) {
		this.idVille = idVille;
		this.idVol = idVol;
		this.immatriculation = immatriculation;
		this.dateHeure = dateHeure;
	}

	public int getIdVille() {
		return idVille;
	}

	public void setIdVille(int idVille) {
		this.idVille = idVille;
	}

	public int getIdVol() {
		return idVol;
	}

	public void setIdVol(int idVol) {
		this.idVol = idVol;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getDateHeure() {
		return dateHeure;
	}

	public void setDateHeure(String dateHeure) {
		this.dateHeure = dateHeure;
	}

	public static void ajouteEscale(Connection connection, Scanner scanner){
		System.out.println("Les informations sur les categories : ");

		 int villeEscale  = 0;
		 boolean isInt = false;
			do {
				System.out.println("Id Ville : ");
			 if (scanner.hasNextInt()) {
				 villeEscale = scanner.nextInt();
				 isInt = true;
			 } else {
				 System.out.println("Veuillez saisir un entier.");
				 scanner.next(); // Pour vider le scanner et éviter une boucle infinie
			 }
			} while (!isInt);

		int idVol  = 0;
		do {
			System.out.println("Id Vol : ");
			if (scanner.hasNextInt()) {
				idVol = scanner.nextInt();
				isInt = true;
			} else {
				System.out.println("Veuillez saisir un entier.");
				scanner.next(); // Pour vider le scanner et éviter une boucle infinie
			}
		} while (!isInt);

		System.out.println("Immatriculation Avion : ");
		String imma = scanner.next();

		System.out.println("Date et Heure : ");
		String dateHeure = util.Date();

		String sql = "INSERT INTO escale (idVille, idVol, immatriculation, dateEtHeure) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1,villeEscale);
			statement.setInt(2,idVol);
			statement.setString(3,imma);
			statement.setString(4,dateHeure);

			int lignesModifiees = statement.executeUpdate();
			if (lignesModifiees > 0) {
				System.out.println("Les données d'escale ont été ajoutées avec succès !");
			} else {
				System.out.println("Erreur lors de l'ajout des données.");
			}
		}catch (SQLException throwables){
			throwables.printStackTrace();
		}
	}

	public static void modifierCategorie(Connection connection, Scanner scanner) {
		System.out.println("Modification categorie : ");

		int villeEscale  = 0;
		boolean isInt = false;
		do {
			System.out.println("Id Ville : ");
			if (scanner.hasNextInt()) {
				villeEscale = scanner.nextInt();
				isInt = true;
			} else {
				System.out.println("Veuillez saisir un entier.");
				scanner.next(); // Pour vider le scanner et éviter une boucle infinie
			}
		} while (!isInt);

		int idVol  = 0;
		do {
			System.out.println("Id Vol : ");
			if (scanner.hasNextInt()) {
				idVol = scanner.nextInt();
				isInt = true;
			} else {
				System.out.println("Veuillez saisir un entier.");
				scanner.next(); // Pour vider le scanner et éviter une boucle infinie
			}
		} while (!isInt);

		System.out.println("Immatriculation Avion : ");
		String imma = scanner.next();

		System.out.println("Date et Heure : ");
		String dateHeure = util.Date();

		String sql = "UPDATE escale SET idVille = ?, idVol = ?, immatriculation = ?, dateEtHeure = ? WHERE idVille = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, villeEscale);
			statement.setInt(2, idVol);
			statement.setString(2, imma);
			statement.setString(2, dateHeure);

			int lignesModifiees = statement.executeUpdate();
			if (lignesModifiees > 0) {
				System.out.println("Le nom du categorie a été modifié avec succès !");
			} else {
				System.out.println("Erreur lors de la modification du nom du categorie.");
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

}
