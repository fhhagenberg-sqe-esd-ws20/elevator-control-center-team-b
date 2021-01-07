package at.fhhagenberg.sqe;

import java.util.Locale;

import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class ElevatorControl extends Application {

	private ElevatorControlController controller;
	
    @Override
    public void start(Stage stage) {
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ElevatorControl.fxml"));
        	Parent root = fxmlLoader.load();
        	controller = fxmlLoader.getController();
        	controller.SetBuildingModel(new BuildingModel(new BuildingMock(5, 2, 2.0)));       
			var scene = new Scene(root);
			stage.setTitle("Elevator Control");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void stop(){
        controller.stop();
    }
    
    public ElevatorControlController GetController() {
    	return controller;
    }
    
    public static void main(String[] args) {
        launch();
    }
}