package data;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import data.DataParser;
import data.Ecole;

public class ParserEtudiant extends DataParser<Etudiant> {
	public final int COL_NOM,COL_PRENOM,COL_MAIL,COL_JUMELAGE,COL_ECOLE;
	public ParserEtudiant(String file,int col_prenom,int col_nom,int col_mail,int col_jumelage,int col_ecole) {
		super(file);
		COL_NOM = col_nom;
		COL_PRENOM = col_prenom;
		COL_MAIL = col_mail;
		COL_JUMELAGE = col_jumelage;
		COL_ECOLE = col_ecole;
	}
	// Par défaut, formulaire des étrangers
	public ParserEtudiant(String file) {
		this(file,1,2,3,4,5);
	}
	
	@Override
	public Etudiant[] parse() throws Exception{
		Etudiant[] etu;
		HSSFRow row;
		HSSFCell cell;

		int rows; // No of rows
		rows = sheet.getPhysicalNumberOfRows();

		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it doesn't start from first few rows
		for(int i = 0; i < 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if(row != null) {
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if(tmp > cols) cols = tmp;
			}
		}
		
		etu = new Etudiant[rows-1];
		
		for(int r = 1; r < rows; r++) {
			row = sheet.getRow(r);
			if(row != null) {
				String nom=null,
						prenom=null,
						mail=null;
				Ecole ecole = null;
				boolean jumelage = false;
				for(int c = 1; c < cols; c++) {
					cell = row.getCell((short)c);
					if(cell != null) {
						if(c == COL_PRENOM)
							prenom = cell.getStringCellValue();
						else if(c == COL_NOM)
							nom = cell.getStringCellValue();
						else if(c == COL_MAIL)
							mail = cell.getStringCellValue();
						else if(c == COL_JUMELAGE) {
							String jum = cell.getStringCellValue();
							jumelage = jum.compareTo("Yes") == 0 || jum.compareTo("Oui") == 0;
						}
						else if(c == COL_ECOLE)
							ecole = Ecole.parserEcole(cell.getStringCellValue());
					}
				}
				if (prenom != null && nom != null && mail != null && ecole != Ecole.NA)
					etu[r-1] = new Etudiant(prenom,nom,mail,jumelage,ecole);
			}
		}
		int effectiveNumber = 0;
		for(Etudiant e : etu) 
			if(e != null)
				effectiveNumber++;
		Etudiant[] etudiants = new Etudiant[effectiveNumber];
		int index = 0;
		for(Etudiant e : etu) {
			if(e != null) {
				etudiants[index] = e;
				index++;
			}
		}
		return etudiants;
	}
}
