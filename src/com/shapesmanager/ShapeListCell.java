package com.shapesmanager;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class ShapeListCell extends ListCell<Shape> {
	HBox row;
	public ShapeListCell() {
		row = new HBox(16);
		row.setAlignment(Pos.CENTER_LEFT);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		ShapeDetails modal = new ShapeDetails(getItem());
		Optional<Shape> result = modal.showAndWait();
		if (result.isPresent()) {
			result.ifPresent(response -> {
				this.commitEdit(response);
			});
		} else {
			this.cancelEdit();
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
			row.getChildren().clear();
			row.getChildren().addAll(checkbox, label);
			checkbox.setSelected(item.visibleProperty().getValue());
			checkbox.setOnAction(e -> {
				item.visibleProperty().set(checkbox.isSelected());
			});
			label.setText(item.toString());
			setGraphic(row);
		}
	}
}
