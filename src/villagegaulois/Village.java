package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	private class Marche {
		private Etal[] etals;
		
		private Marche(int nombreEtal) {
			this.etals = new Etal[nombreEtal];
			for (int i = 0; i < nombreEtal; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (etals[indiceEtal].isEtalOccupe()) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
				System.out.println("L'espace " + indiceEtal + " est désormais occupé par "
						+ vendeur.getNom() + ".");
				return;
			}
			System.out.println("Erreur cet espace est déjà occupé.");
			return;
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			} // ELLE VEUT QU4ON METTE ISETAL DANS FOR MAIS JSP COMENT FAIT
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
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
					text.append("\\n");
				} else {
					nbEtalVide++;
				}
			}
			
			return text + "\\nIl reste" + nbEtalVide + "étals non utilisés dans le marché.";
		}
	}
	
}