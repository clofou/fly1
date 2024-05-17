package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Ville {
    private int idVille;
    private String nom;

    public Ville(int idVille, String nom) {
        this.idVille = idVille;
        this.nom = nom;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static void ajouterUneVille() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Entrez le nom de la ville: ");
            String nom = scanner.nextLine();

            System.out.println("Liste des pays: ");
            try {
                Pays.listeDePays(Connexion.con);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            boolean idPaysValide = false;
            String idPays = null;
            while (!idPaysValide) {
                System.out.print("Entrez l'ID du pays (chiffres uniquement) : ");
                idPays = scanner.nextLine();

                // Vérifier si l'entrée ne contient que des chiffres à l'aide d'une expression régulière
                if (idPays.matches("\\d+")) {
                    try {
                        int idPaysInt = Integer.parseInt(idPays);

                        // Vérifier si l'ID existe dans la liste des ID de pays disponibles
                        boolean idTrouve = false;
                        List<Integer> idPaysDisponibles = Pays.afficherTousLesIds(Connexion.con);
                        for (Integer idDisponible : idPaysDisponibles) {
                            if (idDisponible == idPaysInt) {
                                idTrouve = true;
                                break;
                            }
                        }

                        if (idTrouve) {
                            idPaysValide = true;
                        } else {
                            System.out.println("Erreur: L'ID du pays n'est pas valide. Veuillez choisir parmi les ID disponibles:");
                            for (Integer idDisponible : idPaysDisponibles) {
                                System.out.print(idDisponible + " ");
                            }
                            System.out.println();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur: L'ID du pays doit être un nombre.");
                    }
                } else {
                    System.out.println("Erreur: L'ID du pays ne peut contenir que des chiffres.");
                }
            }

            String sql = "INSERT INTO ville(idPays, nom) VALUES(?, ?)";

            try {
                Connection con = Connexion.con;
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idPays));
                ps.setString(2, nom);
                ps.executeUpdate();
                System.out.println("Ville ajoutée avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void modifierVilleParId() {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Entrez l'ID de la ville à modifier: ");
			int idVille = scanner.nextInt();
			scanner.nextLine(); // Consume newline character

			System.out.print("Entrez le nouveau nom de la ville: ");
			String nouveauNom = scanner.nextLine();

			String sql = "UPDATE ville SET nom = ? WHERE idVille = ?";

			try {
			    Connection con = Connexion.con;
			    PreparedStatement ps = con.prepareStatement(sql);
			    ps.setString(1, nouveauNom);
			    ps.setInt(2, idVille);
			    int rowsAffected = ps.executeUpdate();
			    if (rowsAffected > 0) {
			        System.out.println("Ville modifiée avec succès !");
			    } else {
			        System.out.println("Aucune ville trouvée avec cet identifiant.");
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			}
		}
    }



}
