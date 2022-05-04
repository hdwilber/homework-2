package com.shapesmanager;

import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;

public class Square extends Shape {
	SimpleFloatProperty width;
	
	public Square(float w) {
		super("Square-" + w);
		width = new SimpleFloatProperty(w);
	}
	public Square(float w, float x, float y) {
		super("Square-" + w, x, y);
		width = new SimpleFloatProperty(w);

	}
	
	public String toString() {
		return this.id.get();
	}

	@Override
	public javafx.scene.shape.Shape getShape() {
		// TODO Auto-generated method stub
		javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
		rectangle.setX(x.get());
		rectangle.setY(y.get());
		rectangle.setWidth(width.get());
		rectangle.setHeight(width.get());
		return rectangle;
	}

	@Override
	public void updateDetailsForm(Pane pane) {
		super.updateDetailsForm(pane);
		Node[] widthNodes = ShapeDetailsInput.getLabelInput("Width", ShapeDetailsInput.InputTypes.NUMBER, width);
		TilePane box = new TilePane();
		box.setPrefColumns(2);
		box.getChildren().addAll(widthNodes[0], widthNodes[1]);
		pane.getChildren().add(box);

	}

}
