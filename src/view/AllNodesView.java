package view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AllNodesView {
	
	Scene scene;
	Stage stage;
	
	public AllNodesView()
	{
		 try {
	     	FXMLLoader fxmlLoader = new FXMLLoader();
	        fxmlLoader.setLocation(getClass().getResource("/fxml/allNodesView.fxml"));
	        scene = new Scene(fxmlLoader.load());
	        stage = new Stage();
	        stage.setTitle("My Productivity Tool");
	        stage.setScene(scene);
		 } catch (IOException e) {
	        e.printStackTrace();
		 }
	}
	
	public void show() {
		try {
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
