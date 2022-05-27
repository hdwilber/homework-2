package com.shapesmanager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ImageButton extends Button {
	private ObjectProperty<javafx.scene.image.Image> image;
	public ImageButton(String i, String l) {
		image = new SimpleObjectProperty<javafx.scene.image.Image>(new javafx.scene.image.Image(i));
		ImageView view = new ImageView();
		view.imageProperty().bind(image);
		view.setFitWidth(32);
		view.setFitHeight(32);
		setPadding(new Insets(16, 16, 16, 16));
		this.setContentDisplay(ContentDisplay.TOP);
		this.setText(l);
		setGraphic(view);
	}
}

class AddNewOption {
	String image;
	String label;
	Shape.ShapeType type;

	public AddNewOption(String i, String l, Shape.ShapeType t) {
		image = i;
		label = l;
		type = t;
	}
	public ImageButton getImageButton() {
		return new ImageButton(image, label);
	}
}

public class AddNewDialog extends Dialog<Shape.ShapeType> {
	public AddNewDialog() {
		super();
		ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().add(cancelButtonType);
		setResizable(true);
		setTitle("New Shape to Add");
		addOptions();
		
	}
	public void addOptions() {
		List<AddNewOption> newButtons = new ArrayList<AddNewOption>();
		newButtons.add(Square.addNewOption);
		newButtons.add(Circle.addNewOption);
		newButtons.add(Polygon.addNewOption);
		newButtons.add(Image.addNewOption);
		
		Iterator<AddNewOption> it = newButtons.iterator();
		FlowPane pane = new FlowPane();
		ObservableList<Node> childrenPane = pane.getChildren();
		setResult(Shape.ShapeType.NONE);
		while(it.hasNext()) {
			AddNewOption option = it.next();
			ImageButton button = option.getImageButton();
			childrenPane.add(button);
			button.setOnAction(e -> {
				setResult(option.type);
				hide();
			});
		}
		setResultConverter(btn -> btn.getButtonData() == ButtonData.CANCEL_CLOSE ? Shape.ShapeType.NONE : null);
		getDialogPane().setHeaderText("Select the new shape to add:");
		getDialogPane().setContent(pane);
	}

}