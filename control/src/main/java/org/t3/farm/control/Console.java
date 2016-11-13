package org.t3.farm.control;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Console extends Application {
	class TitleProperty extends SimpleStringProperty {	}
	Scene scene;
	Pane root;
	StringProperty title = new TitleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new StackPane();
		scene = new Scene(root, 800, 400);
		scene.setFill(Color.CYAN);
		scene.setCursor(Cursor.DEFAULT);
		// scene.setOnMouseClicked(this);
		primaryStage.setScene(scene);
		primaryStage.show();
		Label label = new Label();
		label.textProperty().bind(title);
		root.getChildren().add(label);
		title.setValue("Idle");
		TabPane tabPane = new TabPane();
		Tab states = new Tab();
		states.setText("States");
		TableView<String> stateTable = new TableView<String>();
		ObservableList<String> stateList = null;
		stateTable.setItems(stateList);
//		stateTable.setCo
		states.setContent(stateTable);
		tabPane.getTabs().add(states);
		Tab events = new Tab();
		events.setText("Events");
		events.setContent(new Rectangle(200, 200, Color.CYAN));
		tabPane.getTabs().add(events);
		Tab history = new Tab();
		history.setText("History");
		history.setContent(new Rectangle(200, 200, Color.LIGHTGREEN));
		tabPane.getTabs().add(history);
		root.getChildren().add(tabPane);
	}

}
