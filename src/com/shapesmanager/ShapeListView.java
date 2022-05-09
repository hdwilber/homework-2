package com.shapesmanager;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class ShapeListView extends ListView<Shape> implements Callback<ListView<Shape>, ListCell<Shape>>{
	public ShapeListView(ListProperty<Shape> s) {
		super(s);
		setCellFactory(this);
		setEditable(true);
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
//	public Pane getBoard() {
//        BorderPane root = new BorderPane();
//        ListView<Shape> shapeListView = getListView();
//        root.setCenter(shapeListView);
//
//        Button addButton = new Button("Add");
//        addButton.setOnAction(e -> {
//        	AddNewDialog modal = new AddNewDialog();
//			Optional<Shape> result = modal.showAndWait();
//			if (result.isPresent()) {
//				result.ifPresent(response -> {
//					ShapeDetails newModal = new ShapeDetails(response);
//					Optional<Shape> newResult = newModal.showAndWait();
//					if (result.isPresent()) {
//						result.ifPresent(newShape -> {
//							System.out.println("THIS IS THE FINAL RESPONSE");
//							System.out.println(newShape);
//						});
//					} else {
//							System.out.println("DISCARDING THE NEW INSTANCE");
//					}
//				});
//			}
//        });
//        
//        Button groupButton = new Button("Group");
//        groupButton.setDisable(true);
//
//		shapeListView.getSelectionModel().selectedIndexProperty().addListener((arg, oldVal, newVal) -> {
//			ObservableList<Integer> selected = shapeListView.getSelectionModel().getSelectedIndices();
//			groupButton.setDisable(selected.size() <= 1);
//		});
//		groupButton.setOnAction(e -> {
//			List<Shape> selectedShapes = shapeListView.getSelectionModel().getSelectedItems();
//			this.shapes.add(new Group(selectedShapes));
//			selectedShapes.forEach(shape -> {
//				shapes.remove(shape);
//			});
//		});
//
//        HBox buttonBar = new HBox(20, addButton, groupButton);
//        root.setBottom(buttonBar);
//        
//
//        return root;
//	}

	@Override
	public ListCell<Shape> call(ListView<Shape> arg0) {
		ShapeListCell cell = new ShapeListCell();
		return cell;
	}
}
