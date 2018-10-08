package de.georf.pdf2table;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

class PageProcessorTest {
	PageProcessor processor;
	Line area;

	@BeforeEach
	void setUp() {
		Main.setLogger(true, true);
		processor = new PageProcessor(new PDPage());
	}

	@Test
	void testExtractTableFor2018InselpokalPage1() throws Exception {
		checkPdfPages("2018-inselpokal", 0, 55, 9);
	}

	@Test
	void testExtractTableFor2018InselpokalPage2() throws Exception {
		checkPdfPages("2018-inselpokal", 1, 55, 9);
	}

	@Test
	void testExtractTableFor2018InselpokalPage3() throws Exception {
		checkPdfPages("2018-inselpokal", 2, 55, 9);
	}

	@Test
	void testExtractTableFor2018InselpokalPage4() throws Exception {
		checkPdfPages("2018-inselpokal", 3, 18, 9);
	}

	@Test
	void testExtractTableForFreieRahmen() throws Exception {
		checkPdfPages("freie-rahmen", 0, 3, 1);
	}

	@Test
	void testAddLine() {
		Line line = new Line(1, 1, 1, 1);
		processor.addLine(line);
		List<Line> list = new ArrayList<Line>();
		list.add(line);
		assertArrayEquals(list.toArray(), processor.getLines().toArray());
		processor.addLine(new Line(-1, -1, -1, -1));
		assertArrayEquals(list.toArray(), processor.getLines().toArray());
	}

	@Test
	void testGetLines() {
		Line line = new Line(1, 1, 1, 1);
		processor.addLine(line);
		List<Line> list = new ArrayList<Line>();
		list.add(line);
		assertArrayEquals(list.toArray(), processor.getLines().toArray());
	}

	private void checkPdfPages(String baseName, int page, int rows, int columns) throws Exception {
		InputStream pdfIs = this.getClass().getResourceAsStream("/pdfs/" + baseName + ".pdf");
		assertNotNull(pdfIs, "PDF " + baseName + " not found");
		InputStream odsIs = this.getClass().getResourceAsStream("/odss/" + baseName + ".ods");
		assertNotNull(odsIs, "ODS " + baseName + " not found");

		PDDocument doc = PDDocument.load(pdfIs);
		processor = new PageProcessor(doc.getPage(page));
		String[][] expectedTable = readOdsTable(odsIs, "Seite " + (page + 1), rows, columns);
		compareTable(expectedTable, processor.extractTable());
		doc.close();
	}

	private void compareTable(String[][] expectedTable, String[][] extractedTable) {
		for (int row = 0; row < expectedTable.length; row++) {
			for (int column = 0; column < expectedTable[row].length; column++) {
				assertEquals(expectedTable[row][column], extractedTable[row][column]);
			}
		}
	}

	private String[][] readOdsTable(InputStream is, String tableName, int rows, int columns) throws Exception {
		SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(is);
		Table table = doc.getTableByName(tableName);
		String[][] output = new String[rows][columns];
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				output[row][column] = table.getRowByIndex(row).getCellByIndex(column).getStringValue();
			}

		}
		return output;
	}
}
