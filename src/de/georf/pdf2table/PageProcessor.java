package de.georf.pdf2table;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import de.georf.pdf2table.operators.ChangeMatrixOperator;
import de.georf.pdf2table.operators.LineOperator;
import de.georf.pdf2table.operators.MoveOperator;
import de.georf.pdf2table.operators.RectangleOperator;

public class PageProcessor extends PDFStreamEngine {
	private PDPage page;
	private Line area;
	private CoordinateList xs = new CoordinateList();
	private CoordinateList ys = new CoordinateList();
	private List<Line> lines = new ArrayList<Line>();

	public PageProcessor(PDPage page, Line area) {
		this.page = page;
		this.area = area;
	}

	public String[][] extractTable() throws IOException {

		LineBuilder lineBuilder = new LineBuilder(this);
		addOperator(new RectangleOperator(lineBuilder));
		addOperator(new ChangeMatrixOperator(lineBuilder));
		addOperator(new MoveOperator(lineBuilder));
		addOperator(new LineOperator(lineBuilder));
		processPage(page);

		if (xs.size() == 0 || ys.size() == 0)
			return new String[0][0];

		String[][] output = new String[ys.size() - 1][xs.size() - 1];
		for (int xi = 1; xi < xs.size(); xi++) {
			for (int yi = 1; yi < ys.size(); yi++) {
				Rectangle2D rect = new Rectangle2D.Float(xs.get(xi - 1), ys.get(yi - 1),
						Math.abs(xs.get(xi - 1) - xs.get(xi)), Math.abs(ys.get(yi - 1) - ys.get(yi)));
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.addRegion("region", rect);
				stripper.extractRegions(page);
				output[ys.size() - yi - 1][xi - 1] = stripper.getTextForRegion("region").trim();
			}
		}

		return output;
	}

	public void addLine(Line line) {
		if (line.inArea(area)) {
			lines.add(line);
			if (line.isVertical())
				xs.addWithTolerance(line.x());
			if (line.isHorizontal())
				ys.addWithTolerance(line.y());
		}
	}

	public List<Line> getLines() {
		return lines;
	}
}
