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
	ListProperty<Double> points;

	public static List<ShapeDetailsProperty<?>> detailsProperties;
	public static String icon = "/draw-polygon-solid.png";
	public static AddNewOption addNewOption = new AddNewOption(icon, "Polygon", ShapeType.POLYGON);
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Polygon>("Side", InputType.LIST_NUMBER, "points", Polygon.class));
	}
	
	public Polygon() {
		this((new Double[] {}));
		empty.set(true);
	}
	public Polygon(Double[] s) {
		super("Polygon-" + s.length);
		setupPoints(s);
	}

	public Polygon(Double[] s, double x, double y) {
		super("Polygon-" + s.length, x, y);
		setupPoints(s);
	}
	
	public void setupPoints(Double[] s) {
	    ObservableList<Double> observableList = FXCollections.observableArrayList(s);
		points = new SimpleListProperty<Double>(observableList);
		
	}
	public String toString() {
		return this.id.get();
	}

	@Override
	public Node getShape() {
		javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon();
		polygon.getPoints().addAll(points);
		points.addListener((arg, oldVal, newVal) -> {
			polygon.getPoints().clear();
			polygon.getPoints().addAll(newVal);
		});
		polygon.translateXProperty().bind(x);
		polygon.translateYProperty().bind(y);
		polygon.visibleProperty().bind(visible);
		return polygon;
	}

	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}

	@Override
	public String getIcon() {
		return icon;
	}
}
