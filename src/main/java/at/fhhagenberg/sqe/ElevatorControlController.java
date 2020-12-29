package at.fhhagenberg.sqe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="elevatorsListView"
    private ListView<Pane> elevatorsListView; // Value injected by FXMLLoader

    @FXML // fx:id="messageTextArea"
    private TextArea messageTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="floorsListView"
    private ListView<Pane> floorsListView; // Value injected by FXMLLoader

    private ObservableList<FloorController> floorControllerList;
    
    private ObservableList<ElevatorController> elevatorControllerList;
    
    private int numberFloors;
    
    private int numberElevators;
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert elevatorsListView != null : "fx:id=\"elevatorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert messageTextArea != null : "fx:id=\"messageTextArea\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert floorsListView != null : "fx:id=\"floorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";

        numberFloors = 0;
        numberElevators = 0;
    }
    
    public void SetNumberFloors(int number) {
    	try {
    		floorControllerList = FXCollections.observableArrayList();
    		for (int i = 0; i < number; i++) {
    			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Floor.fxml"));
    			Pane listItem = fxmlLoader.load();
    			FloorController controller = fxmlLoader.getController();
    			controller.SetUpArrowActive(false);
    			controller.SetDownArrowActive(false);
    			controller.SetFloorNumber(i);
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
            	controller.SetPayload(0);
            	controller.SetVelocity(0);
            	controller.SetDoorStatus(false);
            	controller.SetDestination(0);
            	controller.SetDirection(false);
            	controller.SetNumberFloors(numberFloors);
            	elevatorControllerList.add(controller);
            	elevatorsListView.getItems().add(listItem);
            }
    	} catch (IOException ex) {
    	
    	}
    	
    	numberElevators = number;
    }
    
    public void SetMessage(String message) {
    	messageTextArea.setText(messageTextArea.getText() + message + "\n");
    }
    
    public FloorController GetFloor(int number) {
    	return floorControllerList.get(number);
    }
    
    public ElevatorController GetElevator(int number) {
    	return elevatorControllerList.get(number);
    }
}
