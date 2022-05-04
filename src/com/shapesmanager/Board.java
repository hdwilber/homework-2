package com.shapesmanager;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;


class ShapeCell extends ListCell<Shape> {
	HBox row;
	public ShapeCell() {
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
				System.out.println("This is the result: " + response.toString());
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
			SimpleBooleanProperty visibleProp = (SimpleBooleanProperty)item.getVisibleProp();
			CheckBox checkbox;
			Label label;
			Button button;
			checkbox = new CheckBox();
			label = new Label(item.toString());
			button = new Button("...");
			row.getChildren().clear();
			row.getChildren().addAll(checkbox, label, button);
			checkbox.setSelected(visibleProp.getValue());
			checkbox.setOnAction(e -> {
				item.visible.set(checkbox.isSelected());
			});
			label.setText(item.toString());
			setGraphic(row);
		}
	}
	
}

public class Board implements Callback<ListView<Shape>, ListCell<Shape>>{
	ObservableList<Shape> shapes;
	SimpleObjectProperty<Shape> selectedShape;

	public Board() {
		shapes = FXCollections.observableArrayList();
		shapes.add(new Square(100, 500, 500));
		shapes.add(new Circle(200, 500, 500));
		shapes.add(new Circle(250, 125, 450));
		shapes.add(new Circle(250));
		shapes.add(new Square(50, 400, 300));

		
		selectedShape = new SimpleObjectProperty<Shape>();
	}
	public List<Shape> getShapes() {
		return shapes;
	}
	
	public ListView<Shape> getListView() {
		ListView<Shape> listView = new ListView<Shape>(shapes);
		listView.setCellFactory(this);
		listView.setEditable(true);
		return listView;
	}
	
	public Pane getBoard() {
        BorderPane root = new BorderPane();
        root.setCenter(getListView());

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
        	ShapeDetails modal = new ShapeDetails();
        	modal.show();
        });
        HBox buttonBar = new HBox( 20, addButton);
        root.setBottom(buttonBar);

        return root;
	}

	@Override
	public ListCell<Shape> call(ListView<Shape> arg0) {
		ShapeCell cell = new ShapeCell();
		return cell;
	}
}