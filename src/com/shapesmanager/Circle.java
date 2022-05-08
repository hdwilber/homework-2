package com.shapesmanager;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Circle extends Shape {
	DoubleProperty radius;

	public Circle() {
		this(0);
		empty.set(true);
	}
	public Circle(float r) {
		super("Circle-" + r);
		radius = new SimpleDoubleProperty(r);
	}
	public Circle(float r, float x, float y) {
		super("Circle-" + r, x, y);
		radius = new SimpleDoubleProperty(r);
	}

	public static List<ShapeDetailsProperty<?>> detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
	public static AddNewOption<Circle> addNewOption = new AddNewOption<Circle>("/circle-solid.svg", "Circle", Circle.class);
	static {
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Circle>("Radius", InputType.NUMBER, "radius", Circle.class));
	}

	@Override
	public String toString() {
		return this.id.get();
	}

	@Override
	public javafx.scene.shape.Shape getShape() {
		javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(radius.get(),Color.BLUE);
		circle.radiusProperty().bind(radius);
		circle.centerXProperty().bind(x);
		circle.centerYProperty().bind(y);
		circle.visibleProperty().bind(visible);
		return circle;
	}
	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}
}
