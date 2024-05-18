import models.*;
import org.mindrot.jbcrypt.BCrypt;
import utils.Color;
import utils.Date;
import utils.NameValidator;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

import static models.util.*;
import static utils.Date.lireDateValide;

public class Main {
    public static void main(String[] args) throws SQLException {
        /* Chargement des fonctions Necessaire pour Executer le script,
        le scanner et la connexion a la base
         */
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();

//----------------------------------------------------------------------------

        // Le Passager Vient sur L'appli, 3 choix s'offre a lui

        while (true){
            String choice = "";
            while (true){
                System.out.println(Color.ANSI_BLUE +"--- Choisissez Une Option ---" + Color.ANSI_RESET);
                System.out.println("1- S'inscrire");
                System.out.println("2- Se Connecter");
                System.out.println("0- Quitter L'application ⚠️ !!!");
                choice = scanner.next();
                if(Objects.equals(choice, "1") || Objects.equals(choice, "2")){
                    break;
                } else if (choice.equals("0")) {
                    System.exit(0);
                } else {
                    System.out.println(Color.ANSI_RED+"⚠️ Choix Invalide!!!"+Color.ANSI_RESET);
                }
            }

            // Si l'utilisateur choisit Le choix 1, il s'inscrit
            if (choice.equals("1")) {
                System.out.println("Processus d'inscription declenche ...\n ");
                InscriptionPassager();
                continue; // Cette instruction donne la chance a l'utilisateur de se connecter Maintenant
            }

            // Ici On a le cas ou l'utilisation se connecte
            if(choice.equals("2")){
                String callback = "1203";
                System.out.println(Color.ANSI_BLUE + "\n-------------Page de Connexion------------" + Color.ANSI_RESET);
                // Ici L'utilisateur Essaie De se Connecter si il echoue, il reprend
                label:
                while (true){
                    int idPassager = ConnexionPassager();
                    if(idPassager != -1){

                        // --------------MAIN--------------------------

                        // Ce que Peut faire un utilisateur Connecte
                        // Message de Bienvenue a l'utilisateur Connecte

                        // Affiche une liste d'option de ce que l'utilisateur connecte peut faire
                        passagerConnecter:
                        while (true){
                            String choice1 = "";
                            while (true){
                                System.out.println(Color.ANSI_BLUE +"--- Choisissez Une Option ---" + Color.ANSI_RESET);
                                System.out.println("1- Reserver Un Vol");
                                System.out.println("2- Liste des reservations effectuees");
                                System.out.println("0- Se deconnecter ⚠️ !!!");
                                choice1 = scanner.next();
                                if(Objects.equals(choice1, "1") || Objects.equals(choice1, "2")){
                                    break;
                                } else if (choice1.equals("0")) {
                                    break;
                                } else {
                                    System.out.println(Color.ANSI_RED+"⚠️ Choix Invalide!!!"+Color.ANSI_RESET);
                                }
                            }
                            switch (choice1) {
                                case "0":
                                    break label;


                                // En fonction des choix effectuer Le Workflow Correspondant
                                case "1":
                                    Reservation r = new Reservation();
                                    r.EffecuterReservation(idPassager);

                                    System.out.println(" ");
                                    continue passagerConnecter; // Cette instruction donne la chance a l'utilisateur de se connecter Maintenant

                                case "2":
                                    detailReservation(idPassager);
                                    continue passagerConnecter; // Cette instruction donne la chance a l'utilisateur de se connecter Maintenant

                            }

                            break;
                        }
                    } else {
                        System.out.println("Voulez-vous revenir Au menu Inscription-Connexion ? (oui/non)");
                        System.out.println("Ou tapez 'quit' pour fermer l'application?");

                        callback = scanner.next();
                        if(callback.equalsIgnoreCase("oui") || callback.equalsIgnoreCase("o")){
                            break;
                        }
                        if (callback.equals("quit")){
                            System.exit(0);
                        }

                    }

                }
                // Ce Code permet de revenir en arriere tout au debut du script
                if(callback.equalsIgnoreCase("oui") || callback.equalsIgnoreCase("o") || callback.equals("1203")){
                    continue;
                }
            }
            break;
        }

    }

    private static void InscriptionPassager() throws SQLException {
        // Workflow pour l'inscription
        Scanner entree = new Scanner(System.in);
        System.out.println(Color.ANSI_BLUE + "Veuillez entrer vos informations :\n"+ Color.ANSI_RESET);
        //Nom Passager
        String nom;

        while (true) {
            System.out.print("Votre nom de Famille : ");
            nom = entree.nextLine();
            if (NameValidator.isValidName(nom)) {
                break;
            } else {
                System.out.println(Color.ANSI_RED+"⚠️ Nom Invalide !!!"+Color.ANSI_RESET);
            }
        }
        //Prenom Passager
        String prenom;

        while (true) {
            System.out.print("Votre Prenom : ");
            prenom = entree.nextLine();
            if (NameValidator.isValidName(prenom)) {
                break;
            } else {
                System.out.println(Color.ANSI_RED+"⚠️ prenom Invalide !!!"+Color.ANSI_RESET);
            }
        }
        //Email
        String email;

        while (true) {
            System.out.print("Veuillez donner votre E-mail : ");
            email = entree.nextLine();
            if (isValidEmail(email)) {
                if(Passager.isEmailExist(Connexion.con, email) ){
                    System.out.println(Color.ANSI_RED+"L'email Existe deja dans la base"+Color.ANSI_RESET);
                    continue;
                }
                break;
            }
            else {
                System.out.println(Color.ANSI_RED+"⚠️Email INVALIDE !!!"+Color.ANSI_RESET);
            }
        }
        //Numero de telephone
        String numTelephone;

        while (true) {
            System.out.print("Votre numero de telephone (Format International, Ex: +223 ... ) : ");
            numTelephone = entree.nextLine();
            if (isValidInternationalNumber(numTelephone)) {
                break;
            } else {
                System.out.println(Color.ANSI_RED+"⚠️Numero Invalide !!!"+Color.ANSI_RESET);
            }
        }
        //Date de Naissance
        System.out.print("Votre date de naissance ");
        String DateNaissance = new Date(lireDateValide()).formatAnglais();
        ;
        //Mot de Passe
        String motDePasse;
        while (true) {
            System.out.print("Donnez un mot de passe :");
            motDePasse = entree.nextLine();
            if (motDePasse.length() < 6) {
                System.out.println(Color.ANSI_RED+"La longueur du mot de passe doit etre superieur a 6caracteres"+Color.ANSI_RESET);
                continue;
            }
            break;
        }
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Instanciation du passager
        Passager passager = new Passager(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        passager.inscription();


    }

    private static int ConnexionPassager() {
        // Connexion du passager à la base de données
        Scanner entree = new Scanner(System.in);
        String email;

        while (true){
            System.out.print("\nVeuillez donner votre E-mail : ");
            email = entree.nextLine();
            if (isValidEmail(email)){
                break;
            } else {
                System.out.println(Color.ANSI_RED+ "⚠️Email INVALIDE !!!" + Color.ANSI_RESET);
            }
        }


        System.out.print("Donnez votre mot de passe : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Passager passager = new Passager(null, null, email, null, null, HashermotDePasseFourni);
        return passager.seConnecter(email, motDePasse);
    }

    //Les details de reservation effectuer par un passager
    private static void detailReservation(int IdPassager){
        if (IdPassager != -1) {
            String listeReservationIdPersonne = "SELECT r.idReservation, r.dateReservation, r.nombreDePassager, i.id, " +
                    "i.nomPassagerEtranger, i.prenomPassagerEtranger, i.numeroPasseport, v.idVol, v.immatriculation, " +
                    "v.villeDeDepart, v.villeDArrive, v.dateDeDepart, v.dateDArrive, v.nombreDEscale, v.tarif, " +
                    "c.idCategorie, c.nom AS nomCategorie FROM reservation r NATURAL JOIN infopassager i NATURAL JOIN " +
                    "vol v NATURAL JOIN categorie c WHERE r.idPassager ="+ IdPassager;


            System.out.println(Color.ANSI_PURPLE+"            \nDetails de resevation"+Color.ANSI_RESET);
            System.out.print("idReservation");
            System.out.print("      dateReservation");
            System.out.print("      nomPassagerEtranger");
            System.out.print("      prenomPassagerEtranger");
            System.out.print("      numeroPasseport");
            System.out.print("      immatriculation Avion");
            System.out.print("      villeDeDepart");
            System.out.print("      villeDArrive");
            System.out.print("      dateDeDepart");
            System.out.print("      nombreDEscale");
            System.out.print("      tarif");
            System.out.println("      nomCategorie");

            boolean isReser = false;

            try (PreparedStatement statement = Connexion.con.prepareStatement(listeReservationIdPersonne)) {

                // Exécution de la requête SELECT
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        isReser = true;

                        int idReservation = resultSet.getInt("r.idReservation");
                        String dateReservation = resultSet.getString("r.dateReservation");
                        String nomPassagerEtranger = resultSet.getString("i.nomPassagerEtranger");
                        String prenomPassagerEtranger = resultSet.getString("i.prenomPassagerEtranger");
                        String numeroPasseport = resultSet.getString("i.numeroPasseport");
                        String immatriculation = resultSet.getString("v.immatriculation");
                        String villeDeDepart = resultSet.getString("v.villeDeDepart");
                        String villeDArrive = resultSet.getString("v.villeDArrive");
                        String dateDeDepart = resultSet.getString("v.dateDeDepart");
                        int nombreDEscale = resultSet.getInt("v.nombreDEscale");
                        int tarif = resultSet.getInt("v.tarif");
                        String nomCategorie = resultSet.getString("nomCategorie");


                        System.out.print(idReservation);
                        System.out.print("                  "+dateReservation);
                        System.out.print("             "+ajoutEspace(nomPassagerEtranger));
                        System.out.print("               "+ajoutEspace(prenomPassagerEtranger));
                        System.out.print("                  "+ajoutEspace(numeroPasseport));
                        System.out.print("             "+ajoutEspace(immatriculation));
                        System.out.print("                  "+ajoutEspace(villeDeDepart));
                        System.out.print("             "+ajoutEspace(villeDArrive));
                        System.out.print("     "+dateDeDepart);
                        System.out.print("             "+nombreDEscale);
                        System.out.print("             "+tarif);
                        System.out.println("         "+ajoutEspace(nomCategorie));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (!isReser){
                System.out.println("                        ⚠️Pas de reservation Disponible .. ⚠️");
            }


        }
    }
}