package de.georf.pdf2table;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class Line {

	private float fromX;
	private float fromY;
	private float toX;
	private float toY;

	public Line(float fromX, float fromY, float toX, float toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}

	public boolean isVertical() {
		if (distanceX() == 0)
			return true;
		if (distanceX() == 0 && distanceY() == 0)
			return false;
		return distanceY() / distanceX() > 2;
	}

	public boolean isHorizontal() {
		if (distanceY() == 0)
			return true;
		if (distanceX() == 0 && distanceY() == 0)
			return false;
		return distanceX() / distanceY() > 2;
	}

	public float x() {
		return (fromX + toX) / 2;
	}

	public float y() {
		return (fromY + toY) / 2;
	}

	public float distanceX() {
		return Math.abs(fromX - toX);
	}

	public float distanceY() {
		return Math.abs(fromY - toY);
	}

	public boolean inArea(PDRectangle area) {
		return fromX > area.getLowerLeftX() && toX < area.getUpperRightX() && fromY > area.getLowerLeftY()
				&& toY < area.getUpperRightY();
	}

	public String toString() {
		return "" + fromX + "x" + fromY + " " + toX + "x" + toY;
	}

	public float getFromX() {
		return fromX;
	}

	public float getFromY() {
		return fromY;
	}

	public float getToX() {
		return toX;
	}

	public float getToY() {
		return toY;
	}
}
