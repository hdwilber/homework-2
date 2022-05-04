package com.shapesmanager;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

public class Image extends Shape {
	DoubleProperty width;
	DoubleProperty height;
	StringProperty url;


	public Image(String u) {
		super("Image " + u);
		url = new SimpleStringProperty(u);
		width = new SimpleDoubleProperty(Math.random()* 1000);
		height = new SimpleDoubleProperty(Math.random() * 1000);
	}
	
	public static List<ShapeDetailsProperty<?>> detailsProperties;
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Image>("Url", InputType.TEXT, "url", Image.class));
		detailsProperties.add(new ShapeDetailsProperty<Image>("Width", InputType.NUMBER, "width", Image.class));
		detailsProperties.add(new ShapeDetailsProperty<Image>("Height", InputType.NUMBER, "height", Image.class));
	}

	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}


	@Override
	public javafx.scene.shape.Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
