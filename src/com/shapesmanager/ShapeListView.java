package com.shapesmanager;

import javafx.beans.property.ListProperty;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

public class ShapeListView extends ListView<Shape> implements Callback<ListView<Shape>, ListCell<Shape>>{
	public ShapeListView(ListProperty<Shape> s) {
		super(s);
		setCellFactory(this);
		setEditable(true);
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	@Override
	public ListCell<Shape> call(ListView<Shape> arg0) {
		ShapeListCell cell = new ShapeListCell();
		return cell;
	}
}
