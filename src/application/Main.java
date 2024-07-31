package application;
	
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	/**
	 * JavaFXスタート
	 * @param	primaryStage	ステージ
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("ウィンドウのタイトル");
			Accordion root = (Accordion)FXMLLoader.load(getClass().getResource("Main.fxml"));
			ObservableList<TitledPane> titiles = root.getPanes();
			root.setExpandedPane(titiles.get(0));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
