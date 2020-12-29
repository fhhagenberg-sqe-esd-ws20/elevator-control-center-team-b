package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
//			controller.SetNumberFloors(10);
//			controller.SetNumberElevators(5);
//			controller.GetElevator(0).GetFloor(5).SetElevatorActive(true);
//			controller.GetElevator(0).GetFloor(3).SetStopActive(true);
//			controller.GetFloor(7).SetUpArrowActive(true);
			
			var scene = new Scene(root);
			stage.setTitle("Elevator Control");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public ElevatorControlController GetController() {
    	return controller;
    }
    
    public static void main(String[] args) {
        launch();
    }
}