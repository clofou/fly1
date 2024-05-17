import models.*;
import org.mindrot.jbcrypt.BCrypt;
import utils.Date;
import java.sql.*;
import java.util.Scanner;

import static utils.Date.lireDateValide;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();

        InscriptionPassager();

        while (true){
            int idPassager = ConnexionPassager();
            if(idPassager != -1){
                Reservation r = new Reservation();
                r.EffecuterReservation(idPassager);
                break;
            }
        }

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
        System.out.println("Votre Email :");
        String email = entree.nextLine();
        //Numero de telephone
        System.out.println("Votre numero de telephone :");
        String numTelephone = entree.nextLine();
        //Date de Naissance
        System.out.println("Votre date de naissance");
        String DateNaissance = new Date(lireDateValide()).formatAnglais();;
        //Mot de Passe
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Instanciation du passager
        Passager passager = new Passager(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        passager.inscription();


    }

    private static int ConnexionPassager(){
        // Connexion du passager à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter Veuillez donner votre E-mail : ");
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
        String DateNaissance = new Date(lireDateValide()).formatAnglais();;
        //Mot de Passe
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

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
    public void detailReservation (){
        int IdPassager = ConnexionPassager();
        if (IdPassager != -1) {
            String listeReservationIdPersonne = "SELECT r.idReservation, r.dateReservation, r.nombreDePassager, p.idPaiement,\n" +
                    "                p.montant,\n" +
                    "                p.modePaiement,\n" +
                    "                p.datePaiement,\n" +
                    "                i.id,\n" +
                    "                i.nomPassagerEtranger,\n" +
                    "                i.prenomPassagerEtranger,\n" +
                    "                i.numeroPasseport,\n" +
                    "                v.idVol,\n" +
                    "                v.immatriculation,\n" +
                    "                v.villeDeDepart,\n" +
                    "                v.villeDArrive,\n" +
                    "                v.dateDeDepart,\n" +
                    "                v.dateDArrive,\n" +
                    "                v.nombreDEscale,\n" +
                    "                v.tarif,\n" +
                    "                c.idCategorie,\n" +
                    "                c.nom AS nomCategorie\n" +
                    "        FROM\n" +
                    "        reservation r\n" +
                    "        JOIN\n" +
                    "        infopassager i ON r.idReservation = i.idReservation\n" +
                    "        JOIN\n" +
                    "        vol v ON i.idVol = v.idVol\n" +
                    "        LEFT JOIN\n" +
                    "        paiement p ON r.idReservation = p.idReservation\n" +
                    "        LEFT JOIN\n" +
                    "        categorie c ON i.idCategorie = c.idCategorie\n" +
                    "        WHERE\n" +
                    "        r.idPassager = ?;";

            try (PreparedStatement statement = Connexion.con.prepareStatement(listeReservationIdPersonne)) {

                // Paramètre pour la requête SELECT
                statement.setInt(1, IdPassager);

                // Exécution de la requête SELECT
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int idReservation = resultSet.getInt("r.idReservation");
                        String dateReservation = resultSet.getString("r.dateReservation");
                        int nombreDePassager = resultSet.getInt("r.nombreDePassager");
                        int idPaiement = resultSet.getInt("p.idPaiement");
                        int montant = resultSet.getInt("p.montant");
                        String modePaiement = resultSet.getString("p.modePaiement");
                        String datePaiement = resultSet.getString("p.datePaiement");
                        int id = resultSet.getInt("i.id");
                        String nomPassagerEtranger = resultSet.getString("i.nomPassagerEtranger");
                        String prenomPassagerEtranger = resultSet.getString("i.prenomPassagerEtranger");
                        int numeroPasseport = resultSet.getInt("i.numeroPasseport");
                        int idVol = resultSet.getInt("v.idVol");
                        String immatriculation = resultSet.getString("v.immatriculation");
                        String villeDeDepart = resultSet.getString("v.villeDeDepart");
                        String villeDArrive = resultSet.getString("v.villeDArrive");
                        String dateDeDepart = resultSet.getString("v.dateDeDepart");
                        String dateDArrive = resultSet.getString("v.dateDArrive");
                        int nombreDEscale = resultSet.getInt("v.nombreDEscale");
                        int tarif = resultSet.getInt("v.tarif");
                        int idCategorie = resultSet.getInt("c.idCategorie");
                        String nom = resultSet.getString("c.nom");

                        System.out.println("Les details de resevation du passager "+IdPassager+"sont :");
                        System.out.printf("ID Reservation : "+idReservation+"\n" +
                                "Date Reservation : "+dateReservation+"\n" +
                                "");
                    } else {
                        System.out.println("Pas de reservation disponible pour l'ID :"+IdPassager);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la connexion : " + e.getMessage());
            }


        }
    }
}