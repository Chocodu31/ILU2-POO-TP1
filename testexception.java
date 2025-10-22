public String libererEtal() {
    if (!etalOccupe) {
        throw new IllegalStateException("L'étal n'est pas occupé");
    }
    etalOccupe = false;
    StringBuilder chaine = new StringBuilder(
            "Le vendeur " + vendeur.getNom() + " quitte son étal, ");
    int produitVendu = quantiteDebutMarche - quantite;
    if (produitVendu > 0) {
        chaine.append(
                "il a vendu " + produitVendu + " parmi " + produit + ".\n");
    } else {
        chaine.append("il n'a malheureusement rien vendu.\n");
    }
    return chaine.toString();
}

public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
    if (!etalOccupe) {
        throw new IllegalStateException("L'étal n'est pas occupé");
    }
    

    if (quantiteAcheter < 1) {
        throw new IllegalArgumentException("La quantité doit être positive");
    }

    try {
        StringBuilder chaine = new StringBuilder();
        chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
                + " " + produit + " à " + vendeur.getNom());
        if (quantite == 0) {
            chaine.append(", malheureusement il n'y en a plus !");
            quantiteAcheter = 0;
        }
        if (quantiteAcheter > quantite) {
            chaine.append(", comme il n'y en a plus que " + quantite + ", "
                    + acheteur.getNom() + " vide l'étal de "
                    + vendeur.getNom() + ".\n");
            quantiteAcheter = quantite;
            quantite = 0;
        }
        if (quantite != 0) {
            quantite -= quantiteAcheter;
            chaine.append(". " + acheteur.getNom()
                    + ", est ravi de tout trouver sur l'étal de "
                    + vendeur.getNom() + "\n");
        }
        return chaine.toString();
    } catch (NullPointerException e) {
        e.printStackTrace();
        return "";
    }
}
// VILLAGE

public String afficherVillageois() throws VillageSansChefException {
    if (chef == null) {
        throw new VillageSansChefException();
    }
    
    StringBuilder chaine = new StringBuilder();
    if (nbVillageois < 1) {
        chaine.append("Il n'y a encore aucun habitant au village du chef "
                + chef.getNom() + ".\n");
    } else {
        chaine.append("Au village du chef " + chef.getNom()
                + " vivent les légendaires gaulois :\n");
        for (int i = 0; i < nbVillageois; i++) {
            chaine.append("- " + villageois[i].getNom() + "\n");
        }
    }
    return chaine.toString();
}

package villagegaulois;

public class VillageSansChefException extends Exception {
    public VillageSansChefException() {
        super("Le village n'a pas de chef");
    }
    
    public VillageSansChefException(String message) {
        super(message);
    }
}


// DANS SCENARIO CAS DEGENE
try {
    System.out.println(etalFleur.acheterProduit(10, abraracourcix));
} catch (IllegalStateException | IllegalArgumentException e) {
    System.out.println("Erreur : " + e.getMessage());
}

try {
    System.out.println(village.afficherVillageois());
} catch (VillageSansChefException e) {
    System.out.println("Erreur : " + e.getMessage());
}

