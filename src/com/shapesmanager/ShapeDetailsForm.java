package com.shapesmanager;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

interface ShapeDetailsEditListener {
	public void onEditComplete(Shape data);
	public void onEditCancel();
}
public class ShapeDetailsForm extends ScrollPane {
	Shape shape;
	ShapeDetailsEditListener listener;

	public ShapeDetailsForm() {
		this(null);
	}

	public ShapeDetailsForm(Shape s) {
		super();
		shape = s;
		createForm();
		setup();
	}

	public void setup() {
		setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.DEFAULT_WIDTHS)));
	}

	public void createForm() {
		VBox box = new VBox(16);
		box.setPadding(new Insets(8, 8, 8, 8 ));
		box.getChildren().addAll(shape.getDetailsForm());
		setContent(box);
	}
}
