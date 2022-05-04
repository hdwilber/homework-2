package com.shapesmanager;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.util.converter.NumberStringConverter;

enum InputType {
	TEXT,
	NUMBER,
}

class ShapeDetailsProperty<T> {
	String label;
	InputType type;
	String field;
	Class<T> className;
	public ShapeDetailsProperty(String l, InputType t, String f) {
		label = l;
		type = t;
		field = f;
	}
	public ShapeDetailsProperty(String l, InputType t, String f, Class<T> c) {
		this(l, t, f);
		className = c;
	}
	
	public List<Node> getLabelInputTextFor(Property prop) {
		TextField input = new TextField();
		if(prop instanceof StringProperty) {
			input.textProperty().bind(prop);
		}
		Label label = getLabel();
		List<Node> result = new ArrayList<Node>();
		result.add(label);
		result.add(input);
		return result;
	}
	
	public Label getLabel() {
		Label label = new Label(this.label + ":");
		label.setTextAlignment(TextAlignment.JUSTIFY);
		return label;
	}
	public List<Node> getLabelInputNumberFor(Property prop) {
		TextField input = new TextField();
		NumberStringConverter converter = new NumberStringConverter(new DecimalFormat());
		Bindings.bindBidirectional(input.textProperty(), (Property<Number>)prop, converter);

		Label label = getLabel();
		List<Node> result = new ArrayList<Node>();
		result.add(label);
		result.add(input);
		return result;
	}

	public List<Node> getLabelInputFor(Shape shape) {
		try {
			Field field = className.getDeclaredField(this.field);
			Property prop = (Property)field.get(shape);
			if (type == InputType.TEXT) {
				return getLabelInputTextFor(prop);
			} else if (type == InputType.NUMBER) {
				return getLabelInputNumberFor(prop);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return new ArrayList<Node>();
	}
}

public class ShapeDetails extends Dialog<Shape>{
	Shape shape;

	public ShapeDetails() {
		super();
		createForm();
	}
	public ShapeDetails(Shape s) {
		super();
		shape = s;
		createForm();
	}

	public void createForm() {
		ButtonType okButtonType = new ButtonType("Add", ButtonData.OK_DONE);
		ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);
		setResizable(true);

		if (shape != null) {
			Pane form = shape.getDetailsForm();
			setTitle(shape.idProperty().get());
			shape.id.addListener((arg, oldVal,newVal) -> setTitle(newVal));
			getDialogPane().setContent(form);
			setWidth(400);
			setHeight(400);
		} else {
			setTitle("New Shape");
		}
		setResultConverter(btn -> btn.getButtonData() == ButtonData.OK_DONE ? shape : null);
	}
}
