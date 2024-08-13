package application;
	
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.fxml.FXMLLoader;

/**
 * メインクラス
 */
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
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * メインメソッド
	 * @param args	起動引数
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
