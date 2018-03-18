package assigner;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import data.DataParser;
import data.Etudiant;
import data.ParserEtudiant;
import evaluateur.EvaluateurEtudiant;

public class AssignEtudiant extends Assign<Etudiant> {
	
	public AssignEtudiant(ParserEtudiant parserFrancais, ParserEtudiant parserEtrangers) {
		super(parserFrancais,parserEtrangers);
	}
	
	@Override
	public void traiterResultats(Map<Etudiant, List<Etudiant>> resultats) {
		Workbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("Résultats d'attribution");

	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row = sheet.createRow((short)0);
	    // Create a cell and put a value in it.
	    row.createCell(0).setCellValue(
	    		createHelper.createRichTextString("Mail français"));
	    row.createCell(1).setCellValue(
	    		createHelper.createRichTextString("Mails étrangers"));
	    row.createCell(2).setCellValue(
	    		createHelper.createRichTextString("Corps du mail"));
	    
	    String mail = "Hello,\n" + 
	    		"\n" + 
	    		"My name is Pierre-François Massiani, and I am responsible for the pairing up during your ATHENS week in Paris. You filled a Google form a few days ago and said that you would like to be put in touch with other ATHENS students.\n" + 
	    		"You can find in the \"Addressee\" field the mail addresses of the persons you were paired up with. The main addressee is the French student, and the ones in copy are the foreign students. Due to participation reasons, we could not make a one-to-one matching : it will only be funnier for you ! I hope you will get along well. You should get in touch and plan to get drink as soon as possible, in order to meet new people !\n" + 
	    		"\n" + 
	    		"Enjoy your stay in Paris !\n" + 
	    		"\n" + 
	    		"Pierre-François Massiani\n" + 
	    		"Mines Paristech's Student's office\n" + 
	    		"Volunteer for the welcoming of foreign students during the ATHENS Week";
	    
	    int i = 1;
	    String mailsEtrangers;
	    Iterator<Etudiant> it;
	    for(Map.Entry<Etudiant, List<Etudiant>> res : resultats.entrySet()) {
	    	mailsEtrangers= "";
	    	row = sheet.createRow((short) i);
	    	row.createCell(0).setCellValue(
		    		createHelper.createRichTextString(res.getKey().mail()));
	    	it = res.getValue().iterator();
	    	while(it.hasNext()) {
	    		mailsEtrangers += it.next().mail();
	    		if (it.hasNext())
	    			mailsEtrangers += ",";
	    	}
	    	
		    row.createCell(1).setCellValue(
		    		createHelper.createRichTextString(mailsEtrangers));
		    row.createCell(2).setCellValue(
		    		createHelper.createRichTextString(mail));
		    i++;
	    }

	    // Write the output to a file
	    try {
	    	FileOutputStream fileOut = new FileOutputStream("jumelage.xls");
	    	wb.write(fileOut);
	    	fileOut.close();
		    wb.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		String francais = "./files/francais.xls",
				etrangers = "./files/etrangers.xls";
		ParserEtudiant parserFrancais = new ParserEtudiant(francais,2,3,1,4,6),
				parserEtrangers = new ParserEtudiant(etrangers);
		AssignEtudiant assign = new AssignEtudiant(parserFrancais,parserEtrangers);
		assign.assigner(new EvaluateurEtudiant());
	}
}
