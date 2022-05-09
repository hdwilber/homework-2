package com.shapesmanager;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Group extends Shape {
	ListProperty<Shape> shapes;
	public static List<ShapeDetailsProperty<?>> detailsProperties;
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Group>("Items", InputType.LIST_SHAPE, "shapes", Group.class));
	}
	public Group() {
		super("Group");
		shapes = new SimpleListProperty<Shape>(FXCollections.observableArrayList(new Shape[]{}));
	}
	public Group(List<Shape> s) {
		super("Group");
		shapes = new SimpleListProperty<Shape>(FXCollections.observableArrayList(s));
	}

	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}

	@Override
	public Node getShape() {
		Pane content = new Pane();
		content.layoutXProperty().bind(x);
		content.layoutYProperty().bind(y);
		content.visibleProperty().bind(visible);
		
		ObservableList<Node> children = content.getChildren();
		shapes.forEach(shape -> {
			children.add(shape.getShape());
		});
		return content;
	}

}
