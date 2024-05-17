package models;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Vol {

    private int idVol;
    private String immatriculation;
    private String villeDeDepart;
    private String villeDArrive;
    private Date dateDeDepart;
    private Date dateDArrive;
    private int nombreDEscale;
    private int tarif;

    public Vol(int idVol, String villeDeDepart, String villeDArrive, Date dateDeDepart, Date dateDArrive){
        this.immatriculation = immatriculation;
        this.idVol = idVol;
        this.villeDeDepart = villeDeDepart;
        this.villeDArrive = villeDArrive;
        this.dateDeDepart = dateDeDepart;
        this.dateDArrive = dateDArrive;
    }


    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public int getIdVol() {
        return idVol;
    }

    public void setIdVol(int idVol) {
        this.idVol = idVol;
    }

    public String getVilleDeDepart() {
        return villeDeDepart;
    }

    public void setVilleDeDepart(String villeDeDepart) {
        this.villeDeDepart = villeDeDepart;
    }

    public String getVilleDArrive() {
        return villeDArrive;
    }

    public void setVilleDArrive(String villeDArrive) {
        this.villeDArrive = villeDArrive;
    }

    public Date getDateDeDepart() {
        return dateDeDepart;
    }

    public void setDateDeDepart(Date dateDeDepart) {
        this.dateDeDepart = dateDeDepart;
    }

    public Date getDateDArrive() {
        return dateDArrive;
    }

    public void setDateDArrive(Date dateDArrive) {
        this.dateDArrive = dateDArrive;
    }

    public int getNombreDEscale() {
        return nombreDEscale;
    }

    public void setNombreDEscale(int nombreDEscale) {
        this.nombreDEscale = nombreDEscale;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    public static void volDispoAUneDate(Connection connection, String date, int nbreDePlace, String villeDeDepar, String villeDArriv) throws SQLException{
        
        // Create a statement object
        try (Statement statement = connection.createStatement();
             // Create the SQL query to retrieve the entire column list
             ResultSet resultSet = statement.executeQuery("SELECT idVol, villeDeDepart, villeDArrive, dateDeDepart, nombreDEscale, modele, tarif, capacite, immatriculation, COUNT(*) i FROM Vol NATURAL JOIN Avion NATURAL JOIN infopassager i WHERE dateDeDepart='"+date+"'AND villeDeDepart='"+villeDeDepar+"' AND villeDArrive='"+villeDArriv+"'")) {

            // Iterate through the result set and print each value
            
            

            while (resultSet.next()) {
                
                int idVol = resultSet.getInt("idVol");
                String villeDeDepart = resultSet.getString("villeDeDepart");
                String villeDArrive = resultSet.getString("villeDArrive");
                String dateDeDepart = resultSet.getString("dateDeDepart");
                int nombreDEscale = resultSet.getInt("nombreDEscale");
                int tarif = resultSet.getInt("tarif");
                String immatriculation = resultSet.getString("immatriculation");
                int placeDispo=(resultSet.getInt("capacite")-resultSet.getInt("i"));
                
                System.out.print("	");
                System.out.print("matricule: "+immatriculation + "  ");
                System.out.print("numeroVol: "+idVol + "  ");
                System.out.print(villeDeDepart + "-");
                System.out.print(villeDArrive + "  ");
                System.out.print(dateDeDepart + " ");
                System.out.print("nbreEscale:"+nombreDEscale + " ");
                System.out.print("tarif:"+tarif+"cfa  ");
                System.out.println(placeDispo+" places disponible");

            }
        }
    }

    public static void ajouterVol(Connection connection, Scanner scanner){
        System.out.println("Veuillez saisir les informations de vol :");

        try {
            System.out.println("Les avions disponible : ");
            Avion.listeDAvion(Connexion.con);
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.print("Immatriculation de l'avion : ");
        String imma = scanner.nextLine();

        System.out.print("Ville de départ : ");
        String villeDepart = scanner.nextLine();

        System.out.print("Ville d'arrivée : ");
        String villeArrivee = scanner.nextLine();

        System.out.print("Entrez la date de depart au format (dd-MM-yyyy) : ");
        String dateDepart = util.Date();

        System.out.print("Entrez la date d'arrivée au format (dd-MM-yyyy) : ");
        String dateArrivee = util.Date();


        int nbr_de_escale = 0;
        boolean isInt = false;
        do {
            System.out.print("Nombre d'escale : ");
            if (scanner.hasNextInt()) {
                nbr_de_escale = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        System.out.print("Tarif : ");
        int tarif = scanner.nextInt();

        String sql = "INSERT INTO vol (immatriculation, villeDeDepart, villeDArrive, dateDeDepart, dateDArrive, nombreDEscale, tarif ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, imma);
            statement.setString(2, villeDepart);
            statement.setString(3, villeArrivee);
            statement.setString(4, dateDepart);
            statement.setString(5, dateArrivee);
            statement.setInt(6, nbr_de_escale);
            statement.setInt(7, tarif);

            // Exécutez la requête
            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Les données ont été ajoutées avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout des données.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void modifierVol(Connection connection, Scanner scanner) {
        System.out.println("Modification vol : ");

        System.out.print("Immatriculation de l'avion : ");
        String imma = scanner.nextLine();


        System.out.print("Modifier ville de départ : ");
        String villeDepart = scanner.nextLine();

        System.out.print("Modifier ville d'arrivée : ");
        String villeArrivee = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dateDepart = null;
        boolean isDateValid = false;
        do {
            System.out.print("Entrez la date de départ au format (dd-MM-yyyy) : ");
            String inputDate = scanner.nextLine();

            try {
                dateDepart = new java.sql.Date(dateFormat.parse(inputDate).getTime());
                isDateValid = true;
            } catch (ParseException e) {
                System.out.println("Format de date incorrect. Veuillez réessayer.");
            }
        } while (!isDateValid);

        Date dateArrivee = null;
        isDateValid = false;
        do {
            System.out.print("Entrez la date d'arrivée au format (dd-MM-yyyy) : ");
            String inputDate = scanner.nextLine();

            try {
                dateArrivee = new java.sql.Date(dateFormat.parse(inputDate).getTime());
                isDateValid = true;
            } catch (ParseException e) {
                System.out.println("Format de date incorrect. Veuillez réessayer.");
            }
        } while (!isDateValid);


        int nbr_de_escale = 0;
        boolean isInt = false;
        do {
            System.out.print("Nombre d'escale : ");
            if (scanner.hasNextInt()) {
                nbr_de_escale = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        System.out.print("Tarif : ");
        int tarif = scanner.nextInt();


        int idVol = 0;
        do {
            System.out.println("ID Categorie : ");
            if (scanner.hasNextInt()) {
                idVol = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        String sql = "UPDATE vol SET immatriculation = ? villeDeDepart = ?, villeDArrive = ?, dateDeDepart = ?, dateDArrive = ?, nombreDEscale = ?, tarif = ? WHERE idVol = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, imma);
            statement.setString(2, villeDepart);
            statement.setString(3, villeArrivee);
            statement.setDate(4, dateDepart);
            statement.setDate(5, dateArrivee);
            statement.setInt(6, nbr_de_escale);
            statement.setInt(7, tarif);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Vol a été modifié avec succès !");
            } else {
                System.out.println("Erreur lors de la modification du vol.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void supprimerVol(Connection connection, Scanner scanner) {
        System.out.println("Suppression vol : ");
        int idVol = 0;
        boolean isInt = false;
        do {
            System.out.println("ID vol à supprimer : ");
            if (scanner.hasNextInt()) {
                idVol = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        String sql = "DELETE FROM vol WHERE idVol = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVol);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Vol a été supprimé avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression du vol.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void chercherEtAfficherVols(Connection connection, Scanner scanner) {
        List<Vol> vols = chercherVol(connection, scanner);

        if (!vols.isEmpty()) {
            System.out.println("Résultats de la recherche :");
            for (Vol vol : vols) {
                System.out.println("ID : " + vol.getIdVol());
                System.out.println("Ville de départ : " + vol.getVilleDeDepart());
                System.out.println("Ville d'arrivée : " + vol.getVilleDArrive());
                System.out.println("Date de départ : " + vol.getDateDeDepart());
                System.out.println("Date d'arrivée : " + vol.getDateDArrive());
                System.out.println(); // Ligne vide pour séparer les résultats
            }
        } else {
            System.out.println("Aucun résultat trouvé.");
        }
    }


    public static List<Vol> chercherVol(Connection connection, Scanner scanner) {
        List<Vol> vols = new ArrayList<>();

        System.out.println("Entrez la ville de départ (ou appuyez sur Entrée pour ignorer) : ");
        String villeDeDepart = scanner.nextLine();

        System.out.println("Entrez la ville d'arrivée (ou appuyez sur Entrée pour ignorer) : ");
        String villeDArrive = scanner.nextLine();


        String query = "SELECT * FROM vol WHERE villeDeDepart LIKE ? AND villeDArrive LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Utiliser des paramètres pour spécifier les critères de recherche
            statement.setString(1, "%" + villeDeDepart + "%");
            statement.setString(2, "%" + villeDArrive + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idVol = resultSet.getInt("idVol");
                String villeDepartResult = resultSet.getString("villeDeDepart");
                String villeArriveeResult = resultSet.getString("villeDArrive");
                Date dateDepartResult = resultSet.getDate("dateDeDepart");
                Date dateArriveeResult = resultSet.getDate("dateDArrive");


                Vol vol = new Vol(idVol, villeDepartResult, villeArriveeResult,dateDepartResult,dateArriveeResult);
                // Ajouter le vol à la liste des résultats
                vols.add(vol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vols;
    }

}
