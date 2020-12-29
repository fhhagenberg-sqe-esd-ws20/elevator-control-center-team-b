package at.fhhagenberg.sqe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;


/**
 * Sample Skeleton for 'ElevatorControl.fxml' Controller Class
 */
public class ElevatorControlController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="elevatorsListView"
    private ListView<Pane> elevatorsListView; // Value injected by FXMLLoader

    @FXML // fx:id="floorsListView"
    private ListView<Pane> floorsListView; // Value injected by FXMLLoader

    @FXML // fx:id="automaticModeToggleButton"
    private ToggleButton automaticModeToggleButton; // Value injected by FXMLLoader

    private boolean automaticModeActive;
    
    private ObservableList<FloorController> floorControllerList;
    
    private ObservableList<ElevatorController> elevatorControllerList;
    
    private int numberFloors;
    
    private int numberElevators;
    
    private BuildingModel buildingModel;
    
    private Timer timer;    
    private ElevatorScheduler elevatorScheduler;

    
    @FXML
    void handleAutomaticModeChange(ActionEvent event) {
    	automaticModeActive = automaticModeToggleButton.isSelected();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws ControlCenterException {
        assert elevatorsListView != null : "fx:id=\"elevatorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert floorsListView != null : "fx:id=\"floorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert automaticModeToggleButton != null : "fx:id=\"modeToggleButton\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        
        automaticModeActive = false;
        numberFloors = 5;
        numberElevators = 2;
        
        timer = new Timer();
        elevatorScheduler = new ElevatorScheduler();
        
        buildingModel = new BuildingModel(new BuildingMock(numberFloors, numberElevators, 2.0));
        
        // schedule run-task of the model
        elevatorScheduler.addAsyncModel(buildingModel);
        timer.scheduleAtFixedRate(elevatorScheduler, 0, 10);
        
        // get floor models + elevator models
        int numberOfFloors = buildingModel.getFloorNum();
        SetNumberFloors(numberOfFloors);

        int numberOfElevators = buildingModel.getElevatorNum();
        SetNumberElevators(numberOfElevators);
    }
    
    public void SetNumberFloors(int number) {
    	try {
    		floorControllerList = FXCollections.observableArrayList();
    		for (int i = 0; i < number; i++) {
    			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Floor.fxml"));
    			Pane listItem = fxmlLoader.load();
    			FloorController controller = fxmlLoader.getController();
    			// initial settings
    			controller.SetUpArrowActive(false);
    			controller.SetDownArrowActive(false);
    			controller.SetFloorNumber(i);
    			// attach model
    			controller.SetModel(buildingModel.getFloor(i));
    			floorControllerList.add(controller);
    			floorsListView.getItems().add(0, listItem);
    		}
    	} catch (IOException ex) {
        	
        }
    	
    	numberFloors = number;
    }
    
    public void SetNumberElevators(int number) {
    	try {
            elevatorControllerList = FXCollections.observableArrayList();
            for (int i = 1; i <= number; i++) {
            	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Elevator.fxml"));
            	Pane listItem = fxmlLoader.load();
            	ElevatorController controller = fxmlLoader.getController();
            	
            	controller.SetElevatorNumber(i);
            	// initial settings
            	controller.SetPayload(0);
            	controller.SetVelocity(0);
            	controller.SetDoorStatus(false);
            	controller.SetDestination(0);
            	controller.SetNumberFloors(numberFloors);
            	// attach model
            	controller.SetElevatorModel(buildingModel.getElevator(i-1));
            	elevatorControllerList.add(controller);
            	elevatorsListView.getItems().add(listItem);
            }
    	} catch (IOException ex) {
    	
    	}
    	
    	numberElevators = number;
    }
    
    public boolean GetAutomaticModeActive() {
    	return automaticModeActive;
    }
    
    public FloorController GetFloor(int number) {
    	return floorControllerList.get(number);
    }
    
    public ElevatorController GetElevator(int number) {
    	return elevatorControllerList.get(number);
    }
}
