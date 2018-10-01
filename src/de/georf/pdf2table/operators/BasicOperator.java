package de.georf.pdf2table.operators;

import java.util.List;

import org.apache.pdfbox.contentstream.operator.OperatorProcessor;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSInteger;

import de.georf.pdf2table.LineBuilder;

public abstract class BasicOperator extends OperatorProcessor {
	protected LineBuilder lineBuilder;

	public BasicOperator(LineBuilder lineBuilder) {
		this.lineBuilder = lineBuilder;
	}

	protected static float[] floatListTo(List<COSBase> arguments) {
		float[] returnList = new float[arguments.size()];
		for (int i = 0; i < arguments.size(); i++) {
			if (arguments.get(i) instanceof COSFloat) {
				returnList[i] = ((COSFloat) arguments.get(i)).floatValue();
			} else {
				returnList[i] = ((COSInteger) arguments.get(i)).floatValue();
			}
		}
		return returnList;
	}
}
