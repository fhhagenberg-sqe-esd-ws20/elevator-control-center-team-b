package at.fhhagenberg.sqe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import at.fhhagenberg.sqe.controlcenter.IElevatorControl;
import at.fhhagenberg.sqe.model.ElevatorModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * Sample Skeleton for 'Elevator.fxml' Controller Class
 */
public class ElevatorController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="destinationLabel"
    private Label destinationLabel; // Value injected by FXMLLoader

    @FXML // fx:id="payloadLabel"
    private Label payloadLabel; // Value injected by FXMLLoader

    @FXML // fx:id="floorButtonsListView"
    private ListView<Pane> floorButtonsListView; // Value injected by FXMLLoader

    @FXML // fx:id="doorStatusLabel"
    private Label doorStatusLabel; // Value injected by FXMLLoader

    @FXML // fx:id="directionLabel"
    private Label directionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="automaticModeCheckBox"
    private CheckBox automaticModeCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="velocityLabel"
    private Label velocityLabel; // Value injected by FXMLLoader

    @FXML // fx:id="elevatorNumberLabel"
    private Label elevatorNumberLabel; // Value injected by FXMLLoader
    
    private ObservableList<ElevatorFloorController> floorControllerList;
    
    private int numberFloors;
    
    private ElevatorModel mElevatorModel;
    
    private int elevatorNumber;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert destinationLabel != null : "fx:id=\"destinationLabel\" was not injected: check your FXML file 'Elevator.fxml'.";
        assert payloadLabel != null : "fx:id=\"payloadLabel\" was not injected: check your FXML file 'Elevator.fxml'.";
        assert floorButtonsListView != null : "fx:id=\"floorButtonsListView\" was not injected: check your FXML file 'Elevator.fxml'.";
        assert doorStatusLabel != null : "fx:id=\"doorStatusLabel\" was not injected: check your FXML file 'Elevator.fxml'.";
        assert directionLabel != null : "fx:id=\"directionLabel\" was not injected: check your FXML file 'Elevator.fxml'.";
        assert automaticModeCheckBox != null : "fx:id=\"automaticModeCheckBox\" was not injected: check your FXML file 'Elevator.fxml'.";
        assert velocityLabel != null : "fx:id=\"velocityLabel\" was not injected: check your FXML file 'Elevator.fxml'.";
        assert elevatorNumberLabel != null : "fx:id=\"elevatorNumberLabel\" was not injected: check your FXML file 'Elevator.fxml'.";
    
    
        // bei Init oder Set Funktion das Elevator Model mit hinzufügen
        // + Listener implementieren
        // ID über setter bzw. übers. model
    }
    
    public void SetElevatorModel(ElevatorModel elevatorModel)
    {
    	mElevatorModel = elevatorModel;
    	
    	mElevatorModel.addListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName() == "FloorNum") {
					//int value = (int)evt.getNewValue(); // cast to suspected type and adapt view
				}
				else if(evt.getPropertyName() == "Direction") {
					
				}
				else if(evt.getPropertyName() == "DoorStatus") {
				}
				else if(evt.getPropertyName() == "PressedFloorButtons") {
					
				}
				else if(evt.getPropertyName() == "ServicedFloors") {
					
				}
				else if(evt.getPropertyName() == "CurrentFloor") {
					
				}
				else if(evt.getPropertyName() == "Speed") {
					SetVelocity((int)evt.getNewValue());
				}
				else if(evt.getPropertyName() == "Acceleration") {
				}
				else if(evt.getPropertyName() == "Weight") {
					SetPayload((int)evt.getNewValue());
				}
				else if(evt.getPropertyName() == "Target") {
					SetDestination((int)evt.getNewValue());					
				}
			}
		});
    	
    }
    
    public void SetElevatorNumber(int number) {
    	elevatorNumber = number;
    	elevatorNumberLabel.setText(Integer.toString(number));
    }
    
    public void SetPayload(int payload) {
    	payloadLabel.setText(Integer.toString(payload) + " kg");
    }
    
    public void SetVelocity(int velocity) {
    	velocityLabel.setText(Integer.toString(velocity) + " m/s");
    }
    
    public void SetDoorStatus(boolean open) {
    	if (open) {
    		doorStatusLabel.setText("open");
    	} else {
    		doorStatusLabel.setText("closed");
    	}
    }
    
    public void SetDestination(int floor) {
    	destinationLabel.setText(Integer.toString(floor));
    }
    
    public void SetDirection(boolean up) {
    	if (up) {
    		directionLabel.setText("up");
    	} else {
    		directionLabel.setText("down");
    	}
    }
    
    public void SetNumberFloors(int number) {
    	try {
    		floorControllerList = FXCollections.observableArrayList();
    		for (int i = 0; i < number; i++) {
    			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ElevatorFloor.fxml"));
    			Pane listItem = fxmlLoader.load();
    			ElevatorFloorController controller = fxmlLoader.getController();
    			controller.SetElevatorActive(false);
    			controller.SetStopActive(false);
    			floorControllerList.add(controller);
    			floorButtonsListView.getItems().add(0, listItem);
    		}
    	} catch (IOException ex) {
        	
        }
    	
    	numberFloors = number;
    }
    
    public boolean GetAutomaticModeActive() {
    	return automaticModeCheckBox.isSelected();
    }
    
    public int GetDestination() {
    	return numberFloors - 1 - floorButtonsListView.getSelectionModel().getSelectedIndex();
    }
    
    public ElevatorFloorController GetFloor(int number) {
    	return floorControllerList.get(number);
    }
}
