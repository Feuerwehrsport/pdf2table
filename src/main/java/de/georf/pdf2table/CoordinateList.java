package de.georf.pdf2table;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoordinateList {
	private List<Float> list = new ArrayList<Float>();
	private final double tolerance = 0.3;

	public void addWithTolerance(float item) {
		for (float otherItem : list) {
			if (otherItem + tolerance > item && otherItem - tolerance < item)
				return;
		}
		list.add(item);
		Collections.sort(list);
	}

	public int size() {
		return list.size();
	}
	
	public float get(int index) {
		return list.get(index);
	}
}
