package com.shapesmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Board extends ScrollPane {
	ListProperty<Shape> shapes;
	ShapeListView shapeListView;
	ShapeDetailsForm shapeDetailsForm;
	VBox container;
	Pane formContainer;

	public Board(ListProperty<Shape> s) {
		super();
		setPadding(new Insets(16, 16, 16, 16));
		shapes = s;
		formContainer = new Pane();
		shapeListView = new ShapeListView(s, formContainer);
		container = new VBox(16);
		prepare();
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

	public void prepare() {
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
        	AddNewDialog modal = new AddNewDialog();
			Optional<Shape.ShapeType> result = modal.showAndWait();
			if (result.isPresent() && result.get() != Shape.ShapeType.NONE) {
				result.ifPresent(shapeType -> {
					Shape emptyShape = getNewShapePerType(shapeType);
					ShapeDetailsDialog newModal = new ShapeDetailsDialog(emptyShape);
					Optional<Shape> newResult = newModal.showAndWait();
					if (newResult.isPresent()) {
						newResult.ifPresent(newShape -> {
							shapes.add(newShape);
						});
					} else {
						// Do nothing
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
				if (shape != null && shape instanceof Group) {
					ungroupButton.setDisable(false);
				} else {
					ungroupButton.setDisable(true);
				}
			} else {
				ungroupButton.setDisable(true);
			}
		});
		
		shapeListView.setOnEditCommit(new EventHandler<ListView.EditEvent<Shape>>() {
			@Override
			public void handle(EditEvent<Shape> event) {
				System.out.println("THE NEW VALUE");
				System.out.println(event.getNewValue().toString());
			}
		});

		groupButton.setOnAction(e -> {
			List<Shape> selectedShapes = new ArrayList<Shape>(shapeListView.getSelectionModel().getSelectedItems());
			shapes.add(new Group(selectedShapes));
			selectedShapes.forEach(shape -> {
				shapes.remove(shape);
			});
			shapeListView.getSelectionModel().clearSelection();
		});

		deleteButton.setOnAction(e -> {
			List<Shape> selectedShapes = new ArrayList<Shape>(shapeListView.getSelectionModel().getSelectedItems());
			selectedShapes.forEach(shape -> {
				shapes.remove(shape);
			});
			shapeListView.getSelectionModel().clearSelection();
		});
		
		ungroupButton.setOnAction(e -> {
			List<Shape> selectedShapes = new ArrayList<Shape>(shapeListView.getSelectionModel().getSelectedItems());
			if (selectedShapes.size() == 1) {
				Group group = (Group)selectedShapes.get(0);
				shapes.remove(group);
				shapes.addAll(group.shapes);
			}
			shapeListView.getSelectionModel().clearSelection();
		});

        HBox buttonBox = new HBox(20, addButton, groupButton, ungroupButton, deleteButton);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        container.getChildren().add(shapeListView);
        container.getChildren().add(buttonBox);
        container.getChildren().add(formContainer);
        setContent(container);
	}
}