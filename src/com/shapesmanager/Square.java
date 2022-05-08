package com.shapesmanager;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Square extends Shape {
	DoubleProperty width;

	public static List<ShapeDetailsProperty<?>> detailsProperties;
	public static AddNewOption<Square> addNewOption = new AddNewOption<Square>("/square-solid.svg", "Square", Square.class);
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Square>("Width", InputType.NUMBER, "width", Square.class));

	}
	
	public Square() {
		this(0);
		empty.set(true);
	}
	public Square(double w) {
		super("Square-" + w);
		width = new SimpleDoubleProperty(w);
	}
	public Square(double w, double x, double y) {
		super("Square-" + w, x, y);
		width = new SimpleDoubleProperty(w);

	}
	
	public String toString() {
		return this.id.get();
	}

	@Override
	public Node getShape() {
		javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
		rectangle.setX(x.get());
		rectangle.setY(y.get());
		rectangle.setWidth(width.get());
		rectangle.setHeight(width.get());
		rectangle.visibleProperty().bind(visible);
		return rectangle;
	}

	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}

}
