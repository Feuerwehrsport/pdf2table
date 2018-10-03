package de.georf.pdf2table.output;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import de.georf.pdf2table.Line;
import de.georf.pdf2table.PageProcessor;

public class DebugPdf {
	private PDDocument pdf;
	private String filePath;

	public DebugPdf(String filePath) throws IOException {
		pdf = new PDDocument();
		this.filePath = filePath;
	}

	public void addPage(PDPage originalPage, PageProcessor processor, String[][] tableData) throws IOException {
		PDPage page = new PDPage(originalPage.getMediaBox());
		pdf.addPage(page);
		PDPageContentStream content = new PDPageContentStream(pdf, page);

		content.setStrokingColor(200, 0, 0);
		for (Line line : processor.getLines()) {
			content.moveTo(line.getFromX(), line.getFromY());
			content.lineTo(line.getToX(), line.getToY());
		}
		content.close();
	}

	public void close() throws IOException {
		pdf.save(filePath);
		pdf.close();
	}

}
