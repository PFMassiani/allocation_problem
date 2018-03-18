package data;

import java.util.LinkedList;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

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
	public List<Etudiant> parse() throws Exception{
		HSSFRow row;
		HSSFCell cell;

		int rows = getNumberOfRows(); // No of rows
		int cols = getNumberOfColumns(); // No of columns
		
		String nom = null, prenom = null, mail = null;
		Ecole ecole = null;
		boolean jumelage = false;
		
		for(int r = 1; r < rows; r++) {
			row = sheet.getRow(r);
			if(row != null) {
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
					data.add(new Etudiant(prenom,nom,mail,jumelage,ecole));
			}
		}
		return data;
	}
}
