package de.georf.pdf2table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class LineBuilderTest {
	private PageProcessor processor;
	private LineBuilder builder;

	@BeforeEach
	void setUp() {
		this.processor = mock(PageProcessor.class);
		this.builder = new LineBuilder(processor);
	}

	@Test
	void testMoveTo() {
		builder.moveTo(12, 34);
	}

	@Test
	void testLineTo() {
		builder.lineTo(1, 2);
		ArgumentCaptor<Line> argument = ArgumentCaptor.forClass(Line.class);
		verify(processor).addLine(argument.capture());

		assertEquals("0.0x0.0 1.0x2.0", argument.getValue().toString());
	}

	@Test
	void testLineToWithMoveToAndScale() {
		builder.changeScale(2, 3);
		builder.moveTo(2, 5);
		builder.lineTo(1, 2);
		ArgumentCaptor<Line> argument = ArgumentCaptor.forClass(Line.class);
		verify(processor).addLine(argument.capture());

		assertEquals("4.0x10.0 2.0x6.0", argument.getValue().toString());
	}

	@Test
	void testChangeScale() {
		builder.changeScale(2, 3);
	}
}
