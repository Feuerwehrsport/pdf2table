package de.georf.pdf2table.operators;

import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import de.georf.pdf2table.LineBuilder;

public class LineOperator extends BasicOperator {

	public LineOperator(LineBuilder lineBuilder) {
		super(lineBuilder);
	}

	@Override
	public void process(Operator pdfo, List<COSBase> arguments) throws IOException {
		float[] floats = floatListTo(arguments);

		lineBuilder.lineTo(floats[0], floats[1]);
	}

	@Override
	public String getName() {
		return "l";
	}
}
