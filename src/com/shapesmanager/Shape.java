package com.shapesmanager;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class Shape {
	SimpleFloatProperty x;
	SimpleFloatProperty y;
	Color stroke;
	Color fill;
	SimpleBooleanProperty visible;
	SimpleStringProperty id;

	public Shape(String i) {
		id = new SimpleStringProperty(i);
		visible = new SimpleBooleanProperty(true);
		x = new SimpleFloatProperty(100);
		y = new SimpleFloatProperty(100);
	}
	public Shape(String i, float x, float y ) {
		id = new SimpleStringProperty(i);
		visible = new SimpleBooleanProperty(true);
		this.x = new SimpleFloatProperty(x);
		this.y = new SimpleFloatProperty(y);
	}

	public ObservableValue<Boolean> getVisibleProp() {
		return visible;
	}
	public ObservableValue<Number> getXProp() {
		return x;
	}
	public ObservableValue<Number> getYProp() {
		return y;
	}
	
	public void updateDetailsForm(Pane pane) {
		Node[] idNodes = ShapeDetailsInput.getLabelInput("ID", ShapeDetailsInput.InputTypes.TEXT, id);
		Node[] xNodes = ShapeDetailsInput.getLabelInput("X", ShapeDetailsInput.InputTypes.NUMBER, x);
		Node[] yNodes = ShapeDetailsInput.getLabelInput("Y", ShapeDetailsInput.InputTypes.NUMBER, y);
		
		TilePane idTile = new TilePane();
		TilePane xTile = new TilePane();
		TilePane yTile = new TilePane();

		TilePane.setAlignment(idNodes[0], Pos.CENTER_LEFT);
		idTile.getChildren().addAll(idNodes[0], idNodes[1]);
		TilePane.setAlignment(xNodes[0], Pos.CENTER_LEFT);
		xTile.getChildren().addAll(xNodes[0], xNodes[1]);
		TilePane.setAlignment(yNodes[0], Pos.CENTER_LEFT);
		yTile.getChildren().addAll(yNodes[0], yNodes[1]);

		pane.getChildren().add(idTile);
		pane.getChildren().add(xTile);
		pane.getChildren().add(yTile);
	}
	
	public abstract javafx.scene.shape.Shape getShape();
}
