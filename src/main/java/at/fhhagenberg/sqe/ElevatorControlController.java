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
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.DoorStatus;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;
import at.fhhagenberg.sqe.model.ElevatorModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;


/**
 * Sample Skeleton for 'ElevatorControl.fxml' Controller Class
 */
public class ElevatorControlController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    protected ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    protected URL location;

    @FXML // fx:id="elevatorsListView"
    protected ListView<Pane> elevatorsListView; // Value injected by FXMLLoader

    @FXML // fx:id="messageTextArea"
    protected TextArea messageTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="floorsListView"
    protected ListView<Pane> floorsListView; // Value injected by FXMLLoader

    private ObservableList<FloorController> floorControllerList;
    
    private ObservableList<ElevatorController> elevatorControllerList;
    
    private int numberFloors;
    
    private int numberElevators;
    
    private BuildingModel mBuildingModel;
    
    private Timer timer;    
    private ElevatorScheduler elevatorScheduler;
    
    private final int timerInterval_ms = 100;
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert elevatorsListView != null : "fx:id=\"elevatorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert messageTextArea != null : "fx:id=\"messageTextArea\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert floorsListView != null : "fx:id=\"floorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
    }
    
    public void SetBuildingModel(BuildingModel buildingModel) {
    	if (timer != null) {
    		stop();
    	}
    	timer = new Timer();
        elevatorScheduler = new ElevatorScheduler();
        mBuildingModel = buildingModel;
        
        // schedule run-task of the model
        elevatorScheduler.addAsyncModel(mBuildingModel);

        // get floor models + elevator models
        SetNumberFloors(mBuildingModel.getFloorNum());
        SetNumberElevators(mBuildingModel.getElevatorNum());
        
        timer.scheduleAtFixedRate(elevatorScheduler, 0, timerInterval_ms);
    }
    
    public void SetNumberFloors(int number) {
    	if(numberFloors != number) {
    		try {
    			floorControllerList = FXCollections.observableArrayList();
    			floorsListView.getItems().clear();
    			for (int i = 0; i < number; i++) {
    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Floor.fxml"));
    				Pane listItem = fxmlLoader.load();
    				FloorController controller = fxmlLoader.getController();
    				// initial settings
    				controller.SetUpArrowActive(false);
    				controller.SetDownArrowActive(false);
    				controller.SetFloorNumber(i);
    				// attach model
    				controller.SetModel(mBuildingModel.getFloor(i));
    				floorControllerList.add(controller);
    				floorsListView.getItems().add(0, listItem);
    			}
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    		numberFloors = number;
    	}
    }
    
    public void SetNumberElevators(int number) {
    	if(numberElevators != number) {
    		try {
    			elevatorControllerList = FXCollections.observableArrayList();
    			elevatorsListView.getItems().clear();
    			for (int i = 1; i <= number; i++) {
    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Elevator.fxml"));
    				Pane listItem = fxmlLoader.load();
    				ElevatorController controller = fxmlLoader.getController();
    				// initial settings
    				controller.SetElevatorNumber(i);
    				controller.SetPayload(0);
    				controller.SetVelocity(0);
    				controller.SetDoorStatus(DoorStatus.Closed);
    				controller.SetDestination(0);
    				controller.SetDirection(Direction.Uncommited);
    				controller.SetNumberFloors(numberFloors);
    				// attach model
    				controller.SetElevatorModel(mBuildingModel.getElevator(i-1));
    				elevatorControllerList.add(controller);
    				elevatorsListView.getItems().add(listItem);
    			}
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    		numberElevators = number;
    	}
    }
    
    public void SetMessage(String message) {
    	messageTextArea.setText(messageTextArea.getText() + message + "\n");
    }
    
    // floors from 0 to n
    public FloorController GetFloor(int number) {
    	return floorControllerList.get(number);
    }
    
    // elevators from 1 to n
    public ElevatorController GetElevator(int number) {
    	return elevatorControllerList.get(number - 1);
    }
    
    // cancel the timer so all created threads will stop at termination
    public void stop() {
    	timer.cancel();
    }
}
