package data;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

public enum Ecole {
	MINES,TELECOM,ESPCI,ENSAM,NA;
	
	public static Ecole parserEcole(String cours) throws IndexOutOfBoundsException{
		if (cours.substring(0,2).compareTo("MP") == 0 || cours.compareTo("Mines Paristech") == 0)
			return MINES;
		else if (cours.substring(0,3).compareTo("TPT") == 0 || cours.compareTo("Télécom Paristech") == 0)
			return TELECOM;
		else if (cours.substring(0,5).compareTo("ESPCI") == 0 || cours.compareTo("ESPCI") == 0) 
			return ESPCI;
		else if (cours.substring(0,5).compareTo("ENSAM") == 0 || cours.compareTo("ENSAM") == 0)
			return ENSAM;
		else 
			return NA;
	}
	
	public static boolean sontProches(Ecole e, Ecole f) {
		if (e == NA || f == NA) return false;
		
		Set<Set<Ecole>> ecolesConnexes = new HashSet<>();
		
		Ecole[][] cnx = {
				{MINES,TELECOM,ESPCI},
				{ENSAM}
				};
		for(int i = 0; i < cnx.length; i++)
			ecolesConnexes.add(new HashSet<>(Arrays.asList(cnx[i])));
		
		for (Set<Ecole> compConnexe : ecolesConnexes)
			if (compConnexe.contains(e) && compConnexe.contains(f))
				return true;
		return false;
	}
}
