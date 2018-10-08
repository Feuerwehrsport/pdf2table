package de.georf.pdf2table;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.hp.hpl.jena.util.FileUtils;

import de.georf.pdf2table.output.DebugPdf;
import de.georf.pdf2table.output.OdsFile;

public class Pdf2Table {
	private OdsFile ods;
	private DebugPdf debugPdf;
	private String sourcePath;
	private String outputPath;
	private boolean forceOverride;

	public Pdf2Table(String sourcePath, String outputPath, boolean forceOverride) throws IOException {
		this.sourcePath = sourcePath;
		this.outputPath = outputPath;
		this.forceOverride = forceOverride;
		startOutput();
		processPages();
		closeOutput();
	}

	private void closeOutput() throws IOException {
		ods.close();
		if (Main.logger.isDebugEnabled())
			this.debugPdf.close();

	}

	private void startOutput() throws IOException {
		if (FileUtils.isFile(outputPath) && !forceOverride)
			throw new IOException("File exists: " + outputPath);
		this.ods = new OdsFile(outputPath);
		if (Main.logger.isDebugEnabled())
			this.debugPdf = new DebugPdf(sourcePath.replaceAll("\\.pdf$", "-debug.pdf"), forceOverride);

	}

	private void processPages() throws IOException {
		PDDocument doc = PDDocument.load(new File(sourcePath));
		for (PDPage page : doc.getDocumentCatalog().getPages()) {
			processPage(page);
		}
		doc.close();
	}

	private void processPage(PDPage page) throws IOException {
		PageProcessor processor = new PageProcessor(page);
		String[][] tableData = processor.extractTable();
		ods.addTable(tableData);
		if (Main.logger.isDebugEnabled())
			debugPdf.addPage(page, processor, tableData);
	}
}
