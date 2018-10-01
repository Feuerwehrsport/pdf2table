package de.georf.pdf2table;

public class LineBuilder {
	private float[] lastPoint = new float[] { 0, 0 };
	private PageProcessor processor;
	private float[] scale = new float[] { 1, 1 };

	public LineBuilder(PageProcessor processor) {
		this.processor = processor;
	}

	public void moveTo(float x, float y) {
		lastPoint[0] = x * scale[0];
		lastPoint[1] = y * scale[0];
	}

	public void lineTo(float x, float y) {
		processor.addLine(new Line(lastPoint[0], lastPoint[1], x * scale[0], y * scale[1]));
	}

	public void changeScale(float x, float y) {
		scale[0] = x;
		scale[1] = y;
	}
}