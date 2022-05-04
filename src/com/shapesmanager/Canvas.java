package com.shapesmanager;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class Canvas extends Pane {
	long width;
	long height;
	List<Shape> shapes;

	public Canvas(List<Shape> s, long w, long h) {
		width = w;
		height = h;
		shapes = s;
		setPrefSize(w, h);
		renderShapes();
	}
	
	public void renderShapes() {
		ObservableList<javafx.scene.Node> children = getChildren();
		shapes.forEach(shape -> {
			if (shape.getVisibleProp().getValue()) {
				children.add(shape.getShape());
			}
		});
	}
}
