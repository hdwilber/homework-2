package com.shapesmanager;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Polygon extends Shape {
	ListProperty<Double> sides;

	public static List<ShapeDetailsProperty<?>> detailsProperties;
	public static AddNewOption addNewOption = new AddNewOption("/draw-polygon-solid.png", "Polygon", ShapeType.POLYGON);
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Polygon>("Side", InputType.LIST_NUMBER, "sides", Polygon.class));
	}
	
	public Polygon() {
		this((new Double[] {}));
		empty.set(true);
	}
	public Polygon(Double[] s) {
		super("Polygon-" + s.length);
		setupSides(s);
	}

	public Polygon(Double[] s, double x, double y) {
		super("Polygon-" + s.length, x, y);
		setupSides(s);
	}
	
	public void setupSides(Double[] s) {
	    ObservableList<Double> observableList = FXCollections.observableArrayList(s);
		sides = new SimpleListProperty<Double>(observableList);
		
	}
	public String toString() {
		return this.id.get();
	}

	@Override
	public Node getShape() {
		javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon();
		polygon.getPoints().addAll(sides.getValue());
		polygon.translateXProperty().bind(x);
		polygon.translateYProperty().bind(y);
		polygon.visibleProperty().bind(visible);
		return polygon;
	}

	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}

}
