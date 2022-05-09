package com.shapesmanager;
import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShapesManager extends Application {
	ListProperty<Shape> shapes;
	Board board;
	Canvas canvas;
	
	public ShapesManager() {
		shapes = new SimpleListProperty<Shape>(FXCollections.observableArrayList());
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
	public Parent setup() {
		board = new Board(shapes);
        canvas = new Canvas(shapes, 200, 200);
        BorderPane root = new BorderPane();
        root.setRight(board.getPane());
        root.setCenter(canvas);
        return root;
	}

	public static void main(String args[]) {
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
        ShapesManager manager = new ShapesManager();
        Scene scene = new Scene(manager.setup(), 450, 200);
        stage.setScene(scene);
        stage.setTitle("JavaFX Test");
        stage.sizeToScene();
        stage.show();
	}
}
