package com.shapesmanager;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Canvas extends ScrollPane {
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
		Pane content = new Pane();
		ObservableList<javafx.scene.Node> children = content.getChildren();
		shapes.forEach(shape -> {
			if (shape.visibleProperty().getValue()) {
				Node o = shape.getShape();
				if (o != null) {
					children.add(o);
				}
			}
		});
		setContent(content);
	}
}
