package com.shapesmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class Polygon extends Shape {
	ListProperty<Double> sides;

	public static List<ShapeDetailsProperty<?>> detailsProperties;
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Polygon>("Side", InputType.LIST_NUMBER, "sides", Polygon.class));
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
		List<Double> simple = new ArrayList();
		for(int i = 0; i < s.length; i++) {
			simple.add(s[i]);
		}
	    ObservableList<Double> observableList = FXCollections.observableArrayList(simple)	;
		sides = new SimpleListProperty<Double>(observableList);
		
	}
	public String toString() {
		return this.id.get();
	}

	@Override
	public javafx.scene.shape.Shape getShape() {
		// TODO Auto-generated method stub
		javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon();
//		polygon.getPoints().setAll(sides.getValue());
		System.out.println("VALUES: " + sides.getValue());
		polygon.getPoints().addAll(sides.getValue());

		polygon.visibleProperty().bind(visible);
		
		return polygon;
	}

	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}

}
