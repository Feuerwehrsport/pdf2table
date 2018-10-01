package de.georf.pdf2table.output;

import java.io.IOException;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

public class OdsFile {

	private SpreadsheetDocument document;
	private String filePath;

	public OdsFile(String filePath) throws IOException {
		this.filePath = filePath;
		try {
			document = SpreadsheetDocument.newSpreadsheetDocument();
			document.removeSheet(0);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	public void addTable(String[][] tableData) {
		Table currentSheet = document.appendSheet("Seite " + (document.getSheetCount() + 1));
		for (int row = 0; row < tableData.length; row++) {
			for (int column = 0; column < tableData[row].length; column++) {
				currentSheet.getRowByIndex(row).getCellByIndex(column).setStringValue(tableData[row][column]);
			}
		}

	}

	public void close() throws IOException {
		try {
			document.save(filePath);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}
}