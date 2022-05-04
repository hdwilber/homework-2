package com.shapesmanager;

import java.util.Optional;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.NumberStringConverter;

class ShapeDetailsInput {
	static enum InputTypes {
		TEXT,
		NUMBER,
	}
	public static <T> Node[] getLabelInput(String label, InputTypes t, Property<T> value) {
		Label labelNode = new Label(label);
		if (t == InputTypes.TEXT) {
			TextField input = new TextField();
			if(value instanceof StringProperty) {
				((StringProperty)value).bind(input.textProperty());
			}

			Node[] result = { labelNode, input };
			return result;
		} else if (t == InputTypes.NUMBER) {
			TextField input = new TextField();
			if(value instanceof FloatProperty) {
				NumberStringConverter converter = new NumberStringConverter(new DecimalFormat());
				Bindings.bindBidirectional(input.textProperty(), (Property<Number>)value, converter);
			}

			Node[] result = { labelNode, input };
			return result;
		}
		return null;
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

		VBox box = new VBox(8);
		box.setPadding(new Insets(25, 25, 25, 25));
		
		if (shape != null) {
			shape.updateDetailsForm(box);
			setTitle(shape.id.get());
			shape.id.addListener((arg, oldVal,newVal) -> setTitle(newVal));
		} else {
			setTitle("New Shape");
		}
		getDialogPane().setContent(box);
		setResultConverter(btn -> btn.getButtonData() == ButtonData.OK_DONE ? shape : null);
	}
}
