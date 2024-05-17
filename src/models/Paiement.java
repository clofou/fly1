package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Paiement {
    private int idPaiement;
    private int montant;
    private String datePaiement;
    private int idReservation;
    private int idmodePaiement;
    private int numeroTelephone;
    private int numeroCarte;
    private int numeroCvv;
    private String dateExpiration;

    // getter et setter

    private int getIdPaiement() {
        return idPaiement;
    }
    private void setIdPaiement(int idPaiement) {
        this.idPaiement = idPaiement;
    }
    private int getMontant() {
        return montant;
    }
    private void setMontant(int montant) {
        this.montant = montant;
    }

    private String getdatePaiement() {
        return datePaiement;
    }
    private void setdatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }
    private int getIdReservation() {
        return idReservation;
    }
    private void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    private int getIdmodePaiement() {
        return idmodePaiement;
    }
    private void setIdmodePaiement(int idmodePaiement) {
        this.idmodePaiement = idmodePaiement;
    }

    private int getNumeroTelephone() {
        return numeroTelephone;
    }
    private void setNumeroTelephone(int numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    private int getNumeroCarte() {
        return numeroCarte;
    }
    private void setNumeroCarte(int numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    private int getNumeroCvv() {
        return numeroCvv;
    }
    private void setNumeroCvv(int numeroCvv) {
        this.numeroCvv = numeroCvv;
    }

    private String getDateExpiration() {
        return dateExpiration;
    }
    private void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public static void ajouterPaiement(){
        Paiement p = new Paiement();

        try (Scanner c = new Scanner(System.in)) {

            System.out.println("Veuillez saisir les informations de paiement :");
            System.out.println("Entre le montant a payer :");
            p.setMontant(c.nextInt());

            System.out.println("Entre l'identifient de la reservation");
            p.setIdReservation(c.nextInt());

            System.out.println("mode de paiement : ");
            System.out.println("1:OM , 2:Carte Bancaire : ");
            p.setIdmodePaiement(c.nextInt());

            if (p.getIdmodePaiement() == 1) {
                // Traitement pour le mode de paiement OM
                System.out.println("numéro OM  : ");
                p.setNumeroTelephone(c.nextInt());
            } else if (p.getIdmodePaiement() == 2) {
                // Traitement pour le mode de paiement par carte bancaire
                while (true) {
                    System.out.print("Entrez votre numéro de carte bancaire (14 chiffres) : ");
                    int numeroCarte = c.nextInt();
                    if (String.valueOf(numeroCarte).length() == 14) {
                        p.setNumeroCarte(numeroCarte);
                        break;
                    } else {
                        System.out.println("Erreur : Le numéro de carte bancaire doit comporter 14 chiffres.");
                    }
                }

                while (true) {
                    System.out.println("Entre le numero CVV de votre carte (3 chiffres) :");
                    int numeroCvv = c.nextInt();
                    if (String.valueOf(numeroCvv).length() == 3) {
                        p.setNumeroCvv(numeroCvv);
                        break;
                    } else {
                        System.out.println("Erreur : Le numéro CVV doit comporter 3 chiffres.");
                    }
                }
            } else {
                System.out.println("Mode de paiement invalide");
                return;
            }

            if (p.getIdmodePaiement() == 2) {
                System.out.println("Entre la date d'expiration de votre carte (au format YYYY-MM-DD): ");
                p.setDateExpiration(c.next());
            }
        }

        String sql = "INSERT INTO paiement (montant, datePaiement, idReservation, idmodePaiement, numeroTelephone, numeroCarte, numeroCvv, dateExpiration) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?)";
        Connexion.seConecter();
        try {
            //(Connection connection = Connexion.seConnecter();
            PreparedStatement ps = Connexion.con.prepareStatement(sql);
            ps.setInt(1, p.getMontant());
            ps.setInt(2, p.getIdReservation());
            ps.setInt(3, p.getIdmodePaiement());
            if (p.getIdmodePaiement() == 1) {
                ps.setLong(4, p.getNumeroTelephone());
                ps.setNull(5, java.sql.Types.BIGINT);
                ps.setNull(6, java.sql.Types.INTEGER);
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else {
                ps.setNull(4, java.sql.Types.BIGINT);
                ps.setLong(5, p.getNumeroCarte());
                ps.setInt(6, p.getNumeroCvv());
                ps.setString(7, p.getDateExpiration());
            }

            ps.executeUpdate();
            System.out.println("Enregistrement effectué avec succès !!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

       /* try(Scanner c = new Scanner(System.in)){

            System.out.println("Veuillez saisir les informations de paiement :");
            System.out.println("Entre le montant a payer :");
            p.setMontant(c.nextInt());

            System.out.println("Entre l'identifient de la reservation");
            p.setIdReservation(c.nextInt());

            System.out.println("mode de paiement : ");
            System.out.println("1:OM , 2:CArte Bancaire : ");
            p.setIdmodePaiement(c.nextInt());


            if (p.getIdmodePaiement() == 1) {
                // Traitement pour le mode de paiement OM

                System.out.println("numéro OM  : ");
                p.setNumeroTelephone(c.nextInt());
            }
            else if (p.getIdmodePaiement() == 2) {

                // String dateExpiration;
                do {
                    System.out.print("Entrez votre numéro de carte bancaire : ");
                    p.setNumeroCarte(c.nextInt());

                    System.out.println("Entre le numero cvv de votre carte :");
                    p.setNumeroCvv(c.nextInt());

                    if ((p.getNumeroCarte() != 14) || (p.numeroCvv != 3)){
                        System.out.println("Erreur : Le numéro de carte bancaire doit comporter 14 chiffres.");
                    }
                } while ((p.getNumeroCarte() != 14) || (p.numeroCvv != 3));

                System.out.println("Numéro de carte bancaire valide : " + p.getNumeroCarte());
                System.out.println("Numéro de carte CVV valide : " + p.getNumeroCvv());

                // Traitement pour le mode de paiement par carte bancaire
            }
            else {
                System.out.println("Mode de paiement invalide");
            }

            System.out.println("Entre la date d'expiration de votre carte (au format YYYY-MM-DD): ");
            p.setDateExpiration(c.next());
        }

        String sql = "INSERT INTO paiement VALUES (?,?,?,?,?,?,?,?,?)";
        Connexion.seConecter();
        try {
            PreparedStatement ps = Connexion.con.prepareStatement(sql);
            ps.setInt(1, p.getIdPaiement());
            ps.setInt(2, p.getMontant());
            ps.setString(3, p.getdatePaiement());
            ps.setInt(4, p.getIdReservation());
            ps.setInt(5, p.getIdmodePaiement());
            ps.setInt(6, p.getNumeroTelephone());
            ps.setInt(7, p.getNumeroCarte());
            ps.setInt(8, p.getNumeroCvv());
            ps.setString(9, p.getDateExpiration());
            ps.execute();

            System.out.println("Enregistrement effectuee avec succes !!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
}
