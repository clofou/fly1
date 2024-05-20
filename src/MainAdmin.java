import models.*;
import org.mindrot.jbcrypt.BCrypt;
import utils.Color;
import utils.Date;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import static models.util.isValidEmail;
import static models.util.isValidInternationalNumber;
import static utils.Date.lireDateValide;

public class MainAdmin {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();

        //ajouterUnAdministrateur();

        while (true){
            String choice = "";
            while (true){
                System.out.println(Color.ANSI_BLUE +"--- Choisissez Une Option ---" + Color.ANSI_RESET);
                System.out.println("1- Se Connecter");
                System.out.println("0- Quitter L'application ⚠️ !!!");
                choice = scanner.next();
                if(Objects.equals(choice, "1")){
                    break;
                } else if (choice.equals("0")) {
                    System.exit(0);
                } else {
                    System.out.println(Color.ANSI_RED+"⚠️ Choix Invalide!!!"+Color.ANSI_RESET);
                }
            }

            // Ici On a le cas ou l'utilisation se connecte
            if(choice.equals("1")){
                String callback = "1203";
                System.out.println(Color.ANSI_BLUE + "\n-------------Page de Connexion------------" + Color.ANSI_RESET);
                // Ici L'utilisateur Essaie De se Connecter si il echoue, il reprend
                label:
                while (true){
                    int idAdmin = ConnexionAdmin();
                    if(idAdmin != -1){

                        // --------------MAIN--------------------------

                        // Ce que Peut faire un utilisateur Connecte
                        // Message de Bienvenue a l'utilisateur Connecte

                        // Affiche une liste d'option de ce que l'utilisateur connecte peut faire
                        adminConnecter:
                        while (true){
                            String choice1 = "";
                            while (true){
                                System.out.println(Color.ANSI_BLUE +"--- Choisissez Une Option ---" + Color.ANSI_RESET);
                                System.out.println("1- Ajouter une compagnie");
                                System.out.println("2- Modifier une compagnie");
                                System.out.println("3- Supprimer une Compagnie");
                                System.out.println("4- Liste des compagnies");
                                System.out.println("5- Ajouter Pays");
                                System.out.println("6- Ajouter Ville");
                                System.out.println("7- Ajouter Un Vol");
                                System.out.println("8- Ajouter Avion");
                                System.out.println("0- Se deconnecter ⚠️ !!!");
                                choice1 = scanner.next();
                                if(Objects.equals(choice1, "1") || Objects.equals(choice1, "2") || Objects.equals(choice1, "3")|| Objects.equals(choice1, "4")|| Objects.equals(choice1, "5")|| Objects.equals(choice1, "6")|| Objects.equals(choice1, "7")|| Objects.equals(choice1, "8")){
                                    break;
                                } else if (choice1.equals("0")) {
                                    break;
                                } else {
                                    System.out.println(Color.ANSI_RED+"⚠️ Choix Invalide!!!"+Color.ANSI_RESET);
                                }
                            }
                            CompagnieAerienne a = new CompagnieAerienne();
                            switch (choice1) {
                                case "0":
                                    break label;


                                // En fonction des choix effectuer Le Workflow Correspondant
                                case "1":
                                    a.AjouterCompagnie();

                                    System.out.println(" ");
                                    continue; // Cette instruction donne la chance a l'utilisateur de se connecter Maintenant

                                case "2":
                                case "4":
                                case "3":
                                    continue ;
                                case "5":
                                    Pays.ajouterPays(Connexion.con, scanner);
                                    continue ;
                                case "6":
                                    Ville.ajouterUneVille();
                                    continue ;
                                case "7":
                                    Vol.ajouterVol(Connexion.con, scanner);
                                    continue ;
                                case "8":
                                    Avion d = new Avion();
                                    d.AjouterAvions();
                                    continue ;


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
        String email;

        while (true){
            System.out.print("Veuillez donner votre E-mail : ");
            email = entree.nextLine();
            if (isValidEmail(email)){
                break;
            } else {
                System.out.println("⚠️Email INVALIDE !!!");
            }
        }
        //Numero de telephone
        String numTelephone;

        while (true){
            System.out.print("Votre numero de telephone (Format International, Ex: +223 ... ) : ");
            numTelephone = entree.nextLine();
            if (isValidInternationalNumber(numTelephone)){
                break;
            } else {
                System.out.println("⚠️Numero Invalide !!!");
            }
        }
        //Date de Naissance
        String DateNaissance = new Date(lireDateValide()).formatAnglais();;
        //Mot de Passe
        while(true){
            System.out.print("Donnez un mot de passe :");
            String motDePasse = entree.nextLine();
            if (motDePasse.length() < 6){
                System.out.println("La longueur du mot de passe doit etre superieur a 6caracteres");
                continue;
            }
            break;
        }
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        Admin admin = new Admin(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        //admin.inscription();
    }

    private static int ConnexionAdmin(){
        // Connexion de l'admin à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter en tant que Admin...");
        //Email
        String email;

        while (true){
            System.out.print("Veuillez donner votre E-mail : ");
            email = entree.nextLine();
            if (isValidEmail(email)){
                break;
            } else {
                System.out.println("⚠️Email INVALIDE !!!");
            }
        }
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

}
