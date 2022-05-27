package com.shapesmanager;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ShapeListCell extends ListCell<Shape> {
	HBox row;
	ImageView editIcon;
	ShapeDetailsForm form;
	public ShapeListCell() {
		row = new HBox(16);
		row.setAlignment(Pos.CENTER_LEFT);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setEditable(true);
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		if (editIcon == null) {
			editIcon = new ImageView("/icon-pen.png");
			editIcon.setFitWidth(16);
			editIcon.setFitHeight(16);
			row.getChildren().add(editIcon);
		}
		ButtonBar buttonBar = new ButtonBar();
		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(arg -> cancelEdit());
		saveButton.setOnAction(arg -> {
			commitEdit(getItem());
			cancelEdit();
		});
		ButtonBar.setButtonData(saveButton, ButtonData.OK_DONE);
		ButtonBar.setButtonData(cancelButton, ButtonData.CANCEL_CLOSE);
		buttonBar.getButtons().addAll(saveButton, cancelButton);

		form = new ShapeDetailsForm(getItem());
		VBox root = new VBox(16);
		root.getChildren().add(form);
		root.getChildren().add(buttonBar);
		((ShapeListView)getListView()).form.getChildren().add(root);

	}
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		if (editIcon != null) {
			row.getChildren().remove(editIcon);
			editIcon = null;
		}
		if (form != null) {
			((ShapeListView)getListView()).form.getChildren().clear();
		}
	}

	protected void updateItem(Shape item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			CheckBox checkbox;
			Label label;
			checkbox = new CheckBox();
			label = new Label(item.toString());

			String icon = item.getIcon();

			ObservableList<Node> children = row.getChildren();
			children.clear();
			children.add(checkbox);
			if (icon != null) {
				ImageView image = new ImageView(item.getIcon());
				image.setFitWidth(16);
				image.setFitHeight(16);
				children.add(image);
			}
			children.add(label);
			checkbox.setSelected(item.visibleProperty().getValue());
			checkbox.setOnAction(e -> {
				item.visibleProperty().set(checkbox.isSelected());
			});
			label.setText(item.toString());
			setGraphic(row);
		}
	}
}
