import models.*;

import java.sql.*;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();
        //int IdPassager = ConnexionPassager();
        //System.out.println("l'Id du passager connecter est : "+ IdPassager);
        InscriptionPassager();



    }

    private static void InscriptionPassager() throws SQLException {
        // Workflow pour l'inscription
        Scanner entree = new Scanner(System.in);
        System.out.println("Veuillez entrer vos informations :\n");
        //Nom Passager
        System.out.println("Votre nom de Famille :");
        String nom = entree.nextLine();
        //Prenom Passager
        System.out.println("Votre Prenom :");
        String prenom = entree.nextLine();
        //Email
        boolean emailExiste = false;
        String email;
        do{
            System.out.println("Veuillez donner votre E-mail : ");
            email = entree.nextLine();
            String chercheEmail = "SELECT idPersonne FROM Personne WHERE email = ?";
            try (PreparedStatement statement = Connexion.con.prepareStatement(chercheEmail)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Ce passager est déjà inscrit. " +
                            "Veuillez donner un autre e-mail ou vous connecter à votre compte.");
                    emailExiste = true;
                } else {
                    emailExiste = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                emailExiste = true; // en cas d'erreur de base de données, continuez à demander
            }
        }while (emailExiste);
        //Numero de telephone
        System.out.println("Votre numero de telephone :");
        String numTelephone = entree.nextLine();
        //Date de Naissance
        System.out.println("Votre date de naissance");
        String DateNaissance = util.Date();
        //Mot de Passe
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Instanciation du passager
        Passager passager = new Passager(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        passager.inscription();
    }

    private static int ConnexionPassager() throws SQLException {
        // Connexion du passager à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter, veuillez donner votre email : ");
        String email = entree.nextLine();
        System.out.println("Donnez votre mot de passe : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Passager passager = new Passager(null,null,email,null,null,HashermotDePasseFourni);
        return passager.seConnecter(email, motDePasse);
    }

    private static void ajouterUnAdministrateur() throws SQLException {
        //Ajout d'un administrateur

        Scanner entree = new Scanner(System.in);
        System.out.println("Veuillez entrer les informations du nouveau administrateur :\n");
        //Nom Admin
        System.out.println("Votre nom de Famille :");
        String nom = entree.nextLine();
        //Prenom Admin
        System.out.println("Votre Prenom :");
        String prenom = entree.nextLine();
        //Email
        System.out.println("Votre Email :");
        String email = entree.nextLine();
        //Numero de telephone
        System.out.println("Votre numero de telephone :");
        String numTelephone = entree.nextLine();
        //Date de Naissance
        String DateNaissance = util.Date();
        //Mot de Passe
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Instanciation de l'admin
        Admin admin = new Admin(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        //Ajout dans la base de données
        admin.ajoutAdmin();
    }

    private static int ConnexionAdmin(){
        // Connexion de l'admin à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter en tant que Admin, veuillez donner votre E-mail : ");
        String email = entree.nextLine();
        System.out.println("Donnez votre mot de passe Admin : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Admin admin = new Admin(null,null,email,null,null,HashermotDePasseFourni);
        //admin.seConnecter(email, motDePasse);
        int connexionAdminReussieIdPersonne = admin.seConnecter(email, motDePasse);
        if (connexionAdminReussieIdPersonne!=-1){
            return connexionAdminReussieIdPersonne;
        }else return -1;

    }

    //Les details de reservation effectuer par un passager
    public static void detailReservation() throws SQLException {
        int IdPassager = ConnexionPassager();
        if (IdPassager != -1) {
            String InfoReservation = "SELECT \n" +
                    "    r.idReservation,\n" +
                    "    r.dateReservation,\n" +
                    "    r.nombreDePassager,\n" +
                    "    p.montant AS montantPaiement,\n" +
                    "    p.modePaiement,\n" +
                    "    p.datePaiement,\n" +
                    "    c.nom AS categorie,\n" +
                    "    v.immatriculation,\n" +
                    "    v.villeDeDepart,\n" +
                    "    v.villeDArrive,\n" +
                    "    v.dateDeDepart,\n" +
                    "    v.dateDArrive,\n" +
                    "    v.nombreDEscale,\n" +
                    "    v.tarif,\n" +
                    "    ip.nomPassagerEtranger AS nomPassager,\n" +
                    "    ip.prenomPassagerEtranger AS prenomPassager,\n" +
                    "    ip.numeroPasseport\n" +
                    "FROM \n" +
                    "    reservation r\n" +
                    "LEFT JOIN \n" +
                    "    paiement p ON r.idReservation = p.idReservation\n" +
                    "LEFT JOIN \n" +
                    "    infopassager ip ON r.idReservation = ip.idReservation\n" +
                    "LEFT JOIN \n" +
                    "    vol v ON ip.idVol = v.idVol\n" +
                    "LEFT JOIN \n" +
                    "    categorie c ON ip.idCategorie = c.idCategorie\n" +
                    "WHERE \n" +
                    "    r.idPassager = ?\n" +
                    "ORDER BY\n" +
                    "    r.dateReservation DESC;\n";

            try (PreparedStatement statementInfoReservation = Connexion.con.prepareStatement(InfoReservation)) {
                statementInfoReservation.setInt(1, IdPassager);

                try (ResultSet resultSetInfoReservation = statementInfoReservation.executeQuery()) {
                    if (resultSetInfoReservation.next()) {
                        do {
                            int idReservation = resultSetInfoReservation.getInt("idReservation");
                            String dateReservation = resultSetInfoReservation.getString("dateReservation");
                            int nombreDePassager = resultSetInfoReservation.getInt("nombreDePassager");
                            int montantPaiement = resultSetInfoReservation.getInt("montantPaiement");
                            String modePaiement = resultSetInfoReservation.getString("modePaiement");
                            String datePaiement = resultSetInfoReservation.getString("datePaiement");
                            String categorie = resultSetInfoReservation.getString("categorie");
                            String immatriculation = resultSetInfoReservation.getString("immatriculation");
                            String villeDeDepart = resultSetInfoReservation.getString("villeDeDepart");
                            String villeDArrive = resultSetInfoReservation.getString("villeDArrive");
                            String dateDeDepart = resultSetInfoReservation.getString("dateDeDepart");
                            String dateDArrive = resultSetInfoReservation.getString("dateDArrive");
                            int nombreDEscale = resultSetInfoReservation.getInt("nombreDEscale");
                            int tarif = resultSetInfoReservation.getInt("tarif");
                            String nomPassager = resultSetInfoReservation.getString("nomPassager");
                            String prenomPassager = resultSetInfoReservation.getString("prenomPassager");
                            String numeroPasseport = resultSetInfoReservation.getString("numeroPasseport");

                            System.out.println("Les détails de réservation du passager " + IdPassager + " sont :");
                            System.out.println("ID Réservation : " + idReservation);
                            System.out.println("Date Réservation : " + dateReservation);
                            System.out.println("Nombre de Passagers : " + nombreDePassager);
                            System.out.println("Montant Paiement : " + montantPaiement);
                            System.out.println("Mode Paiement : " + modePaiement);
                            System.out.println("Date Paiement : " + datePaiement);
                            System.out.println("Catégorie : " + categorie);
                            System.out.println("Immatriculation : " + immatriculation);
                            System.out.println("Ville de Départ : " + villeDeDepart);
                            System.out.println("Ville d'Arrivée : " + villeDArrive);
                            System.out.println("Date de Départ : " + dateDeDepart);
                            System.out.println("Date d'Arrivée : " + dateDArrive);
                            System.out.println("Nombre d'Escales : " + nombreDEscale);
                            System.out.println("Tarif : " + tarif);
                            System.out.println("Nom Passager : " + nomPassager);
                            System.out.println("Prénom Passager : " + prenomPassager);
                            System.out.println("Numéro Passeport : " + numeroPasseport);
                            System.out.println("------------------------------------------");
                        } while (resultSetInfoReservation.next());
                    } else {
                        System.out.println("Pas de réservation disponible pour l'ID : " + IdPassager);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la connexion : " + e.getMessage());
            }
        }
    }



}
