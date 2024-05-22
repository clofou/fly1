package models;

import utils.Color;
import utils.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import static models.util.*;
import static utils.Date.lireDateValide;

public class Paiement {
    private int idPaiement;
    private int montant;
    private String datePaiement;
    private int idReservation;
    private String idmodePaiement;
    private String numeroTelephone;
    private String numeroCarte;
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

    private String getIdmodePaiement() {
        return idmodePaiement;
    }
    private void setIdmodePaiement(String idmodePaiement) {
        this.idmodePaiement = idmodePaiement;
    }

    private String getNumeroTelephone() {
        return numeroTelephone;
    }
    private void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    private String getNumeroCarte() {
        return numeroCarte;
    }
    private void setNumeroCarte(String numeroCarte) {
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

    public static void ajouterPaiement(int montant, int idReservation, Scanner c){
        Paiement p = new Paiement();



            System.out.println(Color.ANSI_PURPLE+"Veuillez saisir les informations de paiement :"+Color.ANSI_RESET);

            while (true){
                System.out.println("Mode de paiement : ");
                System.out.println("1:OM , 2:Carte Bancaire : ");
                p.setIdmodePaiement(c.next());

                if (Objects.equals(p.getIdmodePaiement(), "1") || Objects.equals(p.getIdmodePaiement(), "2")){
                    break;
                } else {
                    System.out.println(Color.ANSI_RED+"⚠️ Choisissez une option valide !!"+Color.ANSI_RESET);
                }
            }


            System.out.println(Color.ANSI_YELLOW+"Le montant " + montant + "$ Sera retire de votre compte."+Color.ANSI_RESET);
            p.setMontant(montant);

            p.setIdReservation(idReservation);



            if (Objects.equals(p.getIdmodePaiement(), "1")) {
                // Traitement pour le mode de paiement OM
                //Numero de telephone
                String numTelephone;

                while (true) {
                    System.out.print("Votre numero de telephone OM (Format International, Ex: +223 ... ) : ");
                    p.setNumeroTelephone(c.next());
                    if (isValidInternationalNumber(p.getNumeroTelephone())) {
                        break;
                    } else {
                        System.out.println(Color.ANSI_RED+"⚠️Numero Invalide !!!"+Color.ANSI_RESET);
                    }
                }

            }

            if (Objects.equals(p.getIdmodePaiement(), "2")) {
                // Traitement pour le mode de paiement par carte bancaire
                String numeroCarte;
                while (true) {
                    System.out.print("Entrez votre numéro de carte bancaire (14 chiffres) : ");
                    numeroCarte = c.next();
                    if (hasFourteenOrAnyDigits(numeroCarte, 14)) {

                        break;
                    } else {
                        System.out.println(Color.ANSI_RED+"Erreur : Le numéro de carte bancaire doit comporter 14 chiffres."+Color.ANSI_RESET);
                    }
                }
                p.setNumeroCarte(numeroCarte);

                while (true) {
                    System.out.print("Entre le numero CVV de votre carte (3 chiffres) :");
                    String numeroCvv = c.next();
                    if (hasFourteenOrAnyDigits(numeroCvv, 3)) {
                        p.setNumeroCvv(Integer.parseInt(numeroCvv));
                        break;
                    } else {
                        System.out.println(Color.ANSI_RED+"Erreur : Le numéro CVV doit comporter 3 chiffres."+Color.ANSI_RESET);
                    }
                }

                System.out.print("Entre la date d'expiration de votre carte ");
                p.setDateExpiration(new Date(lireDateValide()).formatAnglais());
            }


        String sql = "INSERT INTO paiement (montant, datePaiement, idReservation, modePaiement, numeroTelephone, numeroCarte, numeroCvv, dateExpiration) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?)";
        Connexion.seConecter();
        try {
            PreparedStatement ps = Connexion.con.prepareStatement(sql);
            ps.setInt(1, p.getMontant());
            ps.setInt(2, p.getIdReservation());
            ps.setInt(3, Integer.parseInt(p.getIdmodePaiement()));
            if (Objects.equals(p.getIdmodePaiement(), "1")) {
                ps.setString(4, p.getNumeroTelephone());
                ps.setNull(5, java.sql.Types.BIGINT);
                ps.setNull(6, java.sql.Types.INTEGER);
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else {
                ps.setNull(4, java.sql.Types.BIGINT);
                ps.setString(5, p.getNumeroCarte());
                ps.setInt(6, p.getNumeroCvv());
                ps.setString(7, p.getDateExpiration());
            }

            ps.executeUpdate();
            System.out.println("Paiement en cours ...");
            Thread.sleep(2000);
            System.out.println(Color.ANSI_GREEN+"Paiement effectué avec succès !!!"+Color.ANSI_RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
