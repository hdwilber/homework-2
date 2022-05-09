package com.shapesmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

enum InputType {
       TEXT,
       NUMBER,
       LIST_NUMBER,
       LIST_SHAPE,
       IMAGE,
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

	public List<Node> getLabelInputListNumber(Property prop) {
		List<Node> result = new ArrayList<Node>();
		if (prop instanceof ListProperty) {
			List<Double> values = ((ListProperty)prop).getValue();
			Iterator<Double> it = values.iterator();
			int index = 1;
			while(it.hasNext()) {
				Label labelX = new Label(this.label + " X" + index);
				TextField inputX = new TextField();
				Double valueX = it.next();

				Label labelY = new Label(this.label + " Y" + index);
				TextField inputY = new TextField();
				Double valueY = it.next();

				inputX.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
				inputY.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
				inputX.textProperty().set(valueX.toString());
				inputY.textProperty().set(valueY.toString());
				result.add(labelX);
				result.add(inputX);
				result.add(labelY);
				result.add(inputY);
				index++;
			}
		}
		return result;
	}
	public List<Node> getLabelInputImageFor(Property<javafx.scene.image.Image> prop) {
		Label label = getLabel();
		Button button = new Button("Load");
		List<Node> result = new ArrayList<Node>();
		result.add(label);
		result.add(button);

		button.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
					);
			File selectedFile = fileChooser.showOpenDialog(button.getScene().getWindow());
			if (selectedFile != null) {
				try {
					javafx.scene.image.Image image;
					image = new javafx.scene.image.Image(new FileInputStream(selectedFile));
					prop.setValue(image);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		return result;

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
		List<Node> result = new ArrayList<Node>();
		input.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
		NumberStringConverter converter = new NumberStringConverter(new DecimalFormat());
		Bindings.bindBidirectional(input.textProperty(), (Property<Number>)prop, converter);

		Label label = getLabel();
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
			} else if (type == InputType.LIST_NUMBER) {
				return getLabelInputListNumber(prop);
			} else if (type == InputType.IMAGE) {
				return getLabelInputImageFor(prop);
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
		} else {
			setTitle("New Shape");
		}
		setResultConverter(btn -> btn.getButtonData() == ButtonData.OK_DONE ? shape : null);
	}
}
