package de.georf.pdf2table;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import de.georf.pdf2table.output.DebugPdf;
import de.georf.pdf2table.output.OdsFile;

public class Pdf2Table {
	private OdsFile ods;
	private DebugPdf debugPdf;
	private String sourcePath;
	private boolean debug;
	private Line area;

	public static void main(String[] args) {
		try {
			new Pdf2Table("/home/georf/Zeiten-alle-Dosen.pdf", true);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Pdf2Table(String sourcePath, boolean debug) throws IOException {
		this.sourcePath = sourcePath;
		this.debug = debug;
		this.area = new Line(0, 0, 590, 840);
		startOutput();
		processPages();
		closeOutput();
	}

	private void closeOutput() throws IOException {
		ods.close();
		if (debug)
			this.debugPdf.close();
		
	}

	private void startOutput() throws IOException {
		this.ods = new OdsFile(sourcePath.replaceAll("\\.pdf$", ".ods"));
		if (debug)
			this.debugPdf = new DebugPdf(sourcePath.replaceAll("\\.pdf$", "-debug.pdf"));
		
	}

	private void processPages() throws IOException {
		PDDocument doc = PDDocument.load(new File(sourcePath));
		for (PDPage page : doc.getDocumentCatalog().getPages()) {
			processPage(page);
		}
	}

	private void processPage(PDPage page) throws IOException {
		PageProcessor processor = new PageProcessor(page, area);
		String[][] tableData = processor.extractTable();
		ods.addTable(tableData);
		if (debug)
			debugPdf.addPage(page, processor, tableData);
	}
}
