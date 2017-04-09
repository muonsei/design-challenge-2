package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainAppWindowView extends Application {
	
	@Override
    public void start(Stage primaryStage) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("/fxml/appView.fxml"));
    		Scene scene = new Scene(root);
    		scene.getStylesheets().add(getClass().getResource("/fxml/application.css").toExternalForm());
    		primaryStage.setScene(scene);
    		primaryStage.setTitle("My Productivity Tool");
    		primaryStage.show();
    		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
    		});
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
    	MainAppWindowView.pStage = pStage;
    }
    
    private static Stage pStage;
}
