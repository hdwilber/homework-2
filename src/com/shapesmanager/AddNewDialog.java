package com.shapesmanager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.javafx.scene.control.skin.ButtonSkin;

class ImageButton extends Button {
	private ObjectProperty<javafx.scene.image.Image> image;
	public ImageButton(String i, String l) {
		image = new SimpleObjectProperty<javafx.scene.image.Image>(new javafx.scene.image.Image(i));
		ImageView view = new ImageView();
		view.imageProperty().bind(image);
		view.setFitWidth(64);
		view.setFitHeight(48);
		this.setContentDisplay(ContentDisplay.TOP);
		this.setText(l);
		setGraphic(view);
	}
}

class AddNewOption<Shape> {
	String image;
	String label;
	Class<Shape> className;

	public AddNewOption(String i, String l, Class<Shape> c) {
		image = i;
		label = l;
		className = c;
	}
	public ImageButton getImageButton() {
		return new ImageButton(image, label);
	}
	
	public Shape createNewInstance() throws InstantiationException, IllegalAccessException {
		return className.newInstance();
	}
}

public class AddNewDialog extends Dialog<Shape> {
	public AddNewDialog() {
		super();
		ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().add(cancelButtonType);
		setResizable(true);
		
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
		while(it.hasNext()) {
			AddNewOption option = it.next();
			ImageButton button = option.getImageButton();
			childrenPane.add(button);
			button.setOnAction(e -> {
				try {
					this.setResult((com.shapesmanager.Shape) option.createNewInstance());
					hide();
				} catch (InstantiationException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		getDialogPane().setHeaderText("Select the new shape to add");
		getDialogPane().setContent(pane);
	}
}