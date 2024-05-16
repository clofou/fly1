import models.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();
        //Pays.ajouterPays(Connexion.con, scanner);
        Ville.ajouterUneVille();
        System.out.println("Hello Amadou!");
    }
}