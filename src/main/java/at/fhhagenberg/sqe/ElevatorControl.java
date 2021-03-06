package at.fhhagenberg.sqe;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controller.ElevatorControlController;
import at.fhhagenberg.sqe.exceptionhandler.ElevatorExceptionHandler;
import at.fhhagenberg.sqe.exceptionhandler.RemoteElevatorExceptionHandler;
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
	
	private BuildingModel mModel;
	private ElevatorExceptionHandler mHandler;
	
	public ElevatorControl() throws RemoteException, MalformedURLException, ControlCenterException, NotBoundException {
		mModel = null;
		mHandler = new RemoteElevatorExceptionHandler("rmi://localhost/ElevatorSim", new ElevatorConnector());
	}
	
	public ElevatorControl(BuildingModel model, ElevatorExceptionHandler handler) {
		mModel = model;
		mHandler = handler;
	}
	
    @Override
    public void start(Stage stage) {
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ElevatorControl.fxml"));
        	Parent root = fxmlLoader.load();
        	controller = fxmlLoader.getController();
        	mHandler.setController(controller);
        	controller.setExceptionHandler(mHandler);
        	controller.setBuildingModel(mModel);
			var scene = new Scene(root);
			stage.setTitle("Elevator Control");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			//what should be done here??
		}
    }
    
    @Override
    public void stop(){
        controller.stop();
    }
    
    public ElevatorControlController getController() {
    	return controller;
    }
    
    public static void main(String[] args) {
        launch();
    }
}