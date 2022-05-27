package com.shapesmanager;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Canvas extends ScrollPane {
	long width;
	long height;
	ListProperty<Shape> shapes;

	public Canvas(ListProperty<Shape> s, long w, long h) {
		width = w;
		height = h;
		shapes = s;
		renderShapes();
		shapes.addListener((arg, oldVal, newVal) -> {
			renderShapes();
		});
	}
	
	public void renderShapes() {
		Pane content = new Pane();
		content.setPrefSize(width, height);
		content.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		ObservableList<javafx.scene.Node> children = content.getChildren();
		children.clear();
		shapes.forEach(shape -> {
			if (shape.visibleProperty().getValue()) {
				Node o = shape.getShape();
				if (o != null) {
					children.add(o);
				}
			}
		});
		content.autosize();
		setContent(content);
	}
}
