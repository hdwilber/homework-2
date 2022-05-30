package com.shapesmanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public abstract class Shape {
	private String internalId;
	StringProperty id;
	DoubleProperty x;
	DoubleProperty y;
	BooleanProperty visible;
	BooleanProperty empty;

	enum ShapeType {
		NONE,
		SQUARE,
		CIRCLE,
		POLYGON,
		IMAGE,
		GROUP,
	}
	static List<ShapeDetailsProperty<?>> detailsProperties;
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.add(new ShapeDetailsProperty<Shape>("Id", InputType.TEXT, "id", Shape.class));
		detailsProperties.add(new ShapeDetailsProperty<Shape>("X", InputType.NUMBER, "x", Shape.class));
		detailsProperties.add(new ShapeDetailsProperty<Shape>("Y", InputType.NUMBER, "y", Shape.class));
	}
	
	public Shape(String i) {
		this(i, 0, 0);
		setRandomPosition();
	}
	
	public void setRandomPosition() {
		x.set(Math.random() * 1000);
		y.set(Math.random() * 1000);
	}

	public Shape(String i, double x, double y ) {
		internalId = Utils.getUUID();
		id = new SimpleStringProperty(i);
		visible = new SimpleBooleanProperty(true);
		empty = new SimpleBooleanProperty(true);
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
	}
	public String internalId() {
		return internalId;
	}

	public StringProperty idProperty() {
		return id;
	}

	public BooleanProperty visibleProperty() {
		return visible;
	}

	public DoubleProperty xProperty() {
		return x;
	}

	public DoubleProperty yProperty() {
		return y;
	}
	
	public abstract Pane getDetailsForm();

	public abstract Node getShape();

	public Pane getDetailsForm(List<ShapeDetailsProperty<?>> inputs) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(8, 8, 8, 8));
		grid.setHgap(16);
		grid.setVgap(16);
		ColumnConstraints constraints = new ColumnConstraints();
		constraints.setHgrow(Priority.SOMETIMES);
		grid.getColumnConstraints().addAll(new ColumnConstraints(), constraints);
		int rows = 0;
		Iterator<ShapeDetailsProperty<?>> it = inputs.iterator();
		while(it.hasNext()) {
			List<Node> nodes = it.next().getLabelInputFor(this);
			if (!nodes.isEmpty()) {
				if (nodes.size() == 2) {
					grid.add(nodes.get(0), 0, rows);
					grid.add(nodes.get(1), 1, rows);
					rows++;
				} else {
					System.out.println("more nodes " + nodes.toString());
					for(int j = 0; j < nodes.size(); j++) {
						Node node = nodes.get(j);
						grid.add(node, j % 2, rows);
						if (j % 2 == 1) {
							rows++;
						}
					}
				}
			}
		}
		return grid;
	}

	public String toString() {
		return this.id.get();
	}

	public abstract String getIcon();
	public abstract String getName();
}
