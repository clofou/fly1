import models.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();
        //Paiement.ajouterPaiement(Connexion.con, scanner);
       // System.out.println("Hello world!");

        // Paiement
        Paiement paiement = new Paiement();
        paiement.ajouterPaiement();
    }
}