package com.shapesmanager;

import javafx.scene.paint.Color;

public class Circle extends Shape {
	float radius;

	public Circle(float r) {
		super("Circle-" + r);
		radius = r;
	}
	public Circle(float r, float x, float y) {
		super("Circle-" + r, x, y);
		radius = r;
	}

	@Override
	public String toString() {
		return this.id.get();
	}

	@Override
	public javafx.scene.shape.Shape getShape() {
		// TODO Auto-generated method stub
		javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(radius,Color.BLUE);
		circle.setCenterX(x.get());
		circle.setCenterY(y.get());
		circle.visibleProperty().bind(visible);
		return circle;
	}
}
