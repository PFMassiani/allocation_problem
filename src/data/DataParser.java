package data;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public abstract class DataParser<T> {
	protected String file;
	protected List<T> data;
	protected HSSFSheet sheet;
	
	public DataParser(String file) {
		this.file = file;
		try {
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    sheet = wb.getSheetAt(0);
		    data = new LinkedList<T>();
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
	}
	public List<T> data() {return data;}
	protected int getNumberOfRows() { return sheet.getPhysicalNumberOfRows();}
	protected int getNumberOfColumns() {
		HSSFRow row;
		int rows = getNumberOfRows(),
				tmp = 0,
				cols = 0;
		for(int i = 0; i < 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if(row != null) {
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if(tmp > cols) cols = tmp;
			}
		}
		return cols;
	}
	
	abstract public List<T> parse() throws Exception;
}
