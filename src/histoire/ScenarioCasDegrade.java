package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Village village = new Village(null, 0, 0);
		Gaulois obelix = new Gaulois("Obélix", 25);
		
		try {
			etal.libererEtal();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		try {
			etal.acheterProduit(0, null);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
		
		try {
			village.afficherVillageois();
		}catch (VillageSansChefException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");
	}
}