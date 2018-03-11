package data;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public abstract class DataParser<T> {
	protected String file;
	protected T[] data;
	protected HSSFSheet sheet;
	
	public DataParser(String file) {
		this.file = file;
		try {
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    sheet = wb.getSheetAt(0);
		    
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
	}
	
	abstract public T[] parse() throws Exception;
}
