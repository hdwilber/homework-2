package com.shapesmanager;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

public class Board implements Callback<ListView<Shape>, ListCell<Shape>>{
	ObservableList<Shape> shapes;

	public Board() {
		shapes = FXCollections.observableArrayList();
		shapes.add(new Square(100));
		shapes.add(new Circle(200, 500, 500));
		shapes.add(new Circle(250));
		shapes.add(new Circle(250));
		shapes.add(new Square(50));
		shapes.add(new Image("Image"));
		shapes.add(new Polygon(new Double[] {
				500.0, 333.0, 600.0, 1000.0, 23.0, 120.0
		}));
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
        	AddNewDialog modal = new AddNewDialog();
			Optional<Shape> result = modal.showAndWait();
			if (result.isPresent()) {
				result.ifPresent(response -> {
//					this.commitEdit(response);
					System.out.println("THE RESULT");
					System.out.println(response);
					ShapeDetails newModal = new ShapeDetails(response);
					Optional<Shape> newResult = newModal.showAndWait();
					if (result.isPresent()) {
						result.ifPresent(newShape -> {
							System.out.println("THIS IS THE FINAL RESPONSE");
							System.out.println(newShape);
						});
					} else {
							System.out.println("DISCARDING THE NEW INSTANCE");
					}




				});
			}
        });
        HBox buttonBar = new HBox(20, addButton);
        root.setBottom(buttonBar);

        return root;
	}

	@Override
	public ListCell<Shape> call(ListView<Shape> arg0) {
		ShapeCell cell = new ShapeCell();
		return cell;
	}
}
