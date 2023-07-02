package com.restassuredapi.util;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestUtil {
	
	static String baseDirectoryProject = System.getProperty("user.dir");
	static String filePath = baseDirectoryProject + "\\src\\test\\resources\\data.xlsx";

	public static Object[][] readTestData(String sheetName) {
		
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook workbook =  WorkbookFactory.create(fileInputStream);
			Sheet sheet =  workbook.getSheet(sheetName);
			
			int columnSize = sheet.getRow(0).getLastCellNum();
			int rowSize = sheet.getLastRowNum();
			
			
			Object[][] data = new Object[rowSize][columnSize];
			
			for(int i=1; i<= rowSize;i++) {
				for(int j=0;j<columnSize;j++) {
					data[i-1][j] = sheet.getRow(i).getCell(j).toString();
				}
			}
			
			return data;
			
		}catch(Exception e) {
			
		}
		
		return null;
		
	}
	
}
