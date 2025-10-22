package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		Etal etalVide = new Etal();
			
		Gaulois gaulois = new Gaulois("Pedrux",1);
		etal.occuperEtal(gaulois, "fleurs", 20);
			
		etal.acheterProduit(1, null);
			
		System.out.println(etal.afficherEtal());
		try {
			etal.acheterProduit(-10, gaulois);
		} catch (IllegalArgumentException e) {
			etal.acheterProduit(10, gaulois);
		}
		System.out.println(etal.afficherEtal());
			
		try {
			etalVide.acheterProduit(10, gaulois);
		} catch (IllegalStateException e) {
			System.out.println("Etal non occupé");
		}
		
		System.out.println("Fin du test");
	}
	
}
