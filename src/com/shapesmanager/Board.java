package com.shapesmanager;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Board {
	ListProperty<Shape> shapes;
	ShapeListView shapeListView;

	public Board(ListProperty<Shape> s) {
		shapes = s;
		shapeListView = new ShapeListView(s);
	}

	public Shape getNewShapePerType(Shape.ShapeType type) {
		switch(type) {
		case CIRCLE:  return new Circle();
		case SQUARE: return new Square();
		case POLYGON: return new Polygon();
		case IMAGE: return new Image();
		default:
			break;
		}
		return null;
	}

	public Pane getPane() {
        VBox pane = new VBox(16);
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
        	AddNewDialog modal = new AddNewDialog();
			Optional<Shape.ShapeType> result = modal.showAndWait();
			System.out.println("THE RESTL: " + result.toString());
			if (result.isPresent() && result.get() != Shape.ShapeType.NONE) {
				result.ifPresent(shapeType -> {
					Shape emptyShape = getNewShapePerType(shapeType);
					ShapeDetails newModal = new ShapeDetails(emptyShape);
					Optional<Shape> newResult = newModal.showAndWait();
					if (newResult.isPresent()) {
						newResult.ifPresent(newShape -> {
							System.out.println(newShape);
						});
					} else {
							System.out.println("Discard");
					}
				});
			}
        });
        
        Button groupButton = new Button("Group");
        Button ungroupButton = new Button("Ungroup");
        ungroupButton.setDisable(true);
        groupButton.setDisable(true);
        Button deleteButton = new Button("Delete");
        deleteButton.setDisable(true);

		shapeListView.getSelectionModel().selectedIndexProperty().addListener((arg, oldVal, newVal) -> {
			ObservableList<Integer> selected = shapeListView.getSelectionModel().getSelectedIndices();
			groupButton.setDisable(selected.size() <= 1);
			deleteButton.setDisable(selected.size() < 1);
			
			if (selected.size() == 1) {
				Shape shape = shapes.get(selected.get(0));
				System.out.println(shape);
				if (shape != null && shape instanceof Group) {
					ungroupButton.setDisable(false);
				} else {
					ungroupButton.setDisable(true);
				}
			} else {
				ungroupButton.setDisable(true);
			}
		});

		groupButton.setOnAction(e -> {
			List<Shape> selectedShapes = shapeListView.getSelectionModel().getSelectedItems();
			this.shapes.add(new Group(selectedShapes));
			selectedShapes.forEach(shape -> {
				shapes.remove(shape);
			});
		});

		deleteButton.setOnAction(e -> {
			List<Shape> selectedShapes = shapeListView.getSelectionModel().getSelectedItems();
			selectedShapes.forEach(shape -> {
				shapes.remove(shape);
			});
			shapeListView.getSelectionModel().clearSelection();
		});
		
		ungroupButton.setOnAction(e -> {
			List<Shape> selectedShapes = shapeListView.getSelectionModel().getSelectedItems();
			if (selectedShapes.size() == 1) {
				Group group = (Group)selectedShapes.get(0);
				shapes.remove(group);
				shapes.addAll(group.shapes);
			}
		});

        HBox buttonBox = new HBox(20, addButton, groupButton, ungroupButton, deleteButton);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        pane.getChildren().add(shapeListView);
        pane.getChildren().add(buttonBox);
        return pane;
	}
}