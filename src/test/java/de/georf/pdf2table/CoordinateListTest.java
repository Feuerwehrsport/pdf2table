package de.georf.pdf2table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordinateListTest {
	private CoordinateList list;

	@BeforeEach
	void setUp() {
		list = new CoordinateList();
	}

	@Test
	void testAddWithTolerance() {
		assertEquals(0, list.size());
		list.addWithTolerance(1);
		assertEquals(1, list.size());
		list.addWithTolerance(1.01f);
		assertEquals(1, list.size());
		list.addWithTolerance(1.4f);
		assertEquals(2, list.size());
	}

	@Test
	void testSize() {
		assertEquals(0, list.size());
		list.addWithTolerance(1);
		assertEquals(1, list.size());
	}

	@Test
	void testGet() {
		list.addWithTolerance(1);
		assertEquals(1, list.get(0));
	}
}
