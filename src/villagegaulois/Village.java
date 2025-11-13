package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private Marche marche;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum, int nombreEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nombreEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			String nom = gaulois.getNom();
			if (nom != null && nom.equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
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
				chaine.append("- ").append(villageois[i].getNom()).append("\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		int indiceEtal = marche.trouverEtalLibre();
		StringBuilder text = new StringBuilder();
		text.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		if (indiceEtal == -1) {
			text.append("Il n'y a plus de place au marché.\n");
		} else {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			text.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n*" + indiceEtal + ".\n");
		}
		return text.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder text = new StringBuilder();
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if (etalsProduit.length == 0) {
			text.append("Il n'y a aucun vendeur qui vend des " + produit + ".");
			return text.toString();
		} else if (etalsProduit.length == 1) {
			text.append("Seul le vendeur ");
		} else {
			text.append("Les vendeur qui proposent des " + produit + " sont : ");
		}
		for(int i = 0; i < etalsProduit.length; i++) {
			if (etalsProduit.length > 1) {
				text.append("\n- ");
			}
			text.append(etalsProduit[i].getVendeur().getNom());
		}
		if (etalsProduit.length == 1) {
			text.append(" propose des " + produit + "au marché. \n");
		}
		return text.toString(); 
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder text = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal!=null) {
			text.append(etal.libererEtal());		
		}
		return text.toString();
	}
	
	public String afficherMarche() {
		StringBuilder text = new StringBuilder();
		text.append("Le marché du village \""+this.nom+"\" possède plusieurs étals :\n");
		text.append(marche.afficherMarche());
		return text.toString();
	}
	
	//TODO visibilité, mot clef classe interne
	private class Marche {
		private Etal[] etals;
		
		private Marche(int nombreEtal) {
			this.etals = new Etal[nombreEtal];
			for (int i = 0; i < nombreEtal; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (!etals[indiceEtal].isEtalOccupe()) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
				System.out.println("L'espace " + indiceEtal + " est désormais occupé par "
						+ vendeur.getNom() + ".\n");
				return;
			}
			System.out.println("Erreur cet espace est déjà occupé. \n");
			return;
		}
		
		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			} // Mettre isEtal dans for mais jsp comment ont fait pcq le i++ disparait :(
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			Etal[] etalsProduit;
			int nombreEtal = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nombreEtal++;
				}
			}
			etalsProduit = new Etal[nombreEtal];
			int added = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsProduit[added] = etals[i];
					added++;
				}
			}
			return etalsProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder text = new StringBuilder();
			for (int i = 0; i<etals.length ; i++) {
				if (etals[i].isEtalOccupe()) {
					text.append(etals[i].afficherEtal());
					text.append("\n");
				} else {
					nbEtalVide++;
				}
			}
			
			return text + "\nIl reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
		}
	}
	

}
