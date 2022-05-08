package com.shapesmanager;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Image extends Shape {
	DoubleProperty width;
	DoubleProperty height;
	StringProperty url;
	ObjectProperty<javafx.scene.image.Image> image;


	public Image() {
		this(null);
		empty.set(true);
	}
	public Image(String u) {
		super("Image " + u);
		url = new SimpleStringProperty(u);
		image = new SimpleObjectProperty<javafx.scene.image.Image>();
		width = new SimpleDoubleProperty(0);
		height = new SimpleDoubleProperty(0);

		image.addListener((arg, oldVal, newVal) -> {
			if (newVal != null) {
				width.setValue(newVal.getWidth());
				height.setValue(newVal.getHeight());
			}
		});
	}

	public static List<ShapeDetailsProperty<?>> detailsProperties;
	public static AddNewOption<Image> addNewOption = new AddNewOption<Image>("/image-solid.svg", "Image", Image.class);
	static {
		detailsProperties = new ArrayList<ShapeDetailsProperty<?>>();
		detailsProperties.addAll(Shape.detailsProperties);
		detailsProperties.add(new ShapeDetailsProperty<Image>("Url", InputType.TEXT, "url", Image.class));
		detailsProperties.add(new ShapeDetailsProperty<Image>("Source", InputType.IMAGE, "image", Image.class));
		detailsProperties.add(new ShapeDetailsProperty<Image>("Width", InputType.NUMBER, "width", Image.class));
		detailsProperties.add(new ShapeDetailsProperty<Image>("Height", InputType.NUMBER, "height", Image.class));
	}

	@Override
	public Pane getDetailsForm() {
		return getDetailsForm(detailsProperties);
	}


	@Override
	public Node getShape() {
		javafx.scene.image.ImageView view = new javafx.scene.image.ImageView();
		view.imageProperty().bind(image);
		view.xProperty().bind(x);
		view.yProperty().bind(y);
		view.fitWidthProperty().bind(width);
		view.fitHeightProperty().bind(height);
		view.visibleProperty().bind(visible);
		return view;
	}
}
