package at.fhhagenberg.sqe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.DoorStatus;
import at.fhhagenberg.sqe.model.ElevatorModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
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
    protected ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    protected URL location;

    @FXML // fx:id="destinationLabel"
    protected Label destinationLabel; // Value injected by FXMLLoader

    @FXML // fx:id="payloadLabel"
    protected Label payloadLabel; // Value injected by FXMLLoader

    @FXML // fx:id="floorButtonsListView"
    protected ListView<Pane> floorButtonsListView; // Value injected by FXMLLoader

    @FXML // fx:id="doorStatusLabel"
    protected Label doorStatusLabel; // Value injected by FXMLLoader

    @FXML // fx:id="directionLabel"
    protected Label directionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="automaticModeCheckBox"
    protected CheckBox automaticModeCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="velocityLabel"
    protected Label velocityLabel; // Value injected by FXMLLoader

    @FXML // fx:id="elevatorNumberLabel"
    protected Label elevatorNumberLabel; // Value injected by FXMLLoader
    
    private ObservableList<ElevatorFloorController> floorControllerList;
    
    private int numberFloors = 0;
    
    private ElevatorModel mElevatorModel;
    
    private int currentTarget = -1;

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
    }
    
    public void SetElevatorModel(ElevatorModel elevatorModel)
    {
    	mElevatorModel = elevatorModel;
    	
    	mElevatorModel.addListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName() == "FloorNum") {
					// maximum number of floors
					SetNumberFloors((int)evt.getNewValue());
				}
				else if(evt.getPropertyName() == "Direction") {
					SetDirection((Direction)evt.getNewValue());
				}
				else if(evt.getPropertyName() == "DoorStatus") {
					SetDoorStatus((DoorStatus)evt.getNewValue());
				}
				else if(evt.getPropertyName() == "PressedFloorButtons") {
					List<Integer> pressedButtons = mElevatorModel.getPressedFloorButtons();
					// reset all floors previously
					for(ElevatorFloorController floor : floorControllerList) {
						floor.SetStopActive(false);
					}
					// get new list + update
					for(int i : pressedButtons) {
						floorControllerList.get(i).SetStopActive(true);
					}
				}
				else if(evt.getPropertyName() == "ServicedFloors") {
					List<Integer> activeFloors = mElevatorModel.getServicedFloors();
					// reset all floors previously
					for(ElevatorFloorController floor : floorControllerList) {
						floor.SetFloorActive(false);
					}
					// get new list + update
					for(int i : activeFloors) {
						floorControllerList.get(i).SetFloorActive(true);
					}
				}
				else if(evt.getPropertyName() == "CurrentFloor") {
					int currentFloor = mElevatorModel.getCurrentFloor();
					// reset all floors
					for(ElevatorFloorController floor : floorControllerList) {
						floor.SetElevatorActive(false);
					}
					// set only current floor
					floorControllerList.get(currentFloor).SetElevatorActive(true);
					try {
						if (currentFloor < currentTarget) {
							mElevatorModel.setDirection(Direction.Up);
						} 
						else if (currentFloor > currentTarget) {
							mElevatorModel.setDirection(Direction.Down);
						}
						else {
							mElevatorModel.setDirection(Direction.Uncommited);
						}
					}
	    			catch (ControlCenterException e) {
						e.printStackTrace();
					}
				}
				else if(evt.getPropertyName() == "Speed") {
					SetVelocity((double)evt.getNewValue());
				}
				else if(evt.getPropertyName() == "Acceleration") {
					// not needed
				}
				else if(evt.getPropertyName() == "Weight") {
					SetPayload((double)evt.getNewValue());
				}
				else if(evt.getPropertyName() == "Target") {
					SetDestination((int)evt.getNewValue());					
				}
			}
		});
    	
    	// set initial value
    	SetDestination(0);
    	floorControllerList.get(0).SetElevatorActive(true);
    }
    
    public void SetElevatorNumber(int number) {
    	Platform.runLater(() -> elevatorNumberLabel.setText(Integer.toString(number)));
    }
    
    public void SetPayload(double payload) {
    	Platform.runLater(() -> payloadLabel.setText(String.format("%.1f",  payload) + " lbs"));
    }
    
    public void SetVelocity(double velocity) {
    	Platform.runLater(() -> velocityLabel.setText(String.format("%.1f",  velocity) + " ft/s"));
    }
    
    public void SetDoorStatus(DoorStatus status) {
    	if (status == DoorStatus.Open) {
    		Platform.runLater(() -> doorStatusLabel.setText("open"));
    	} else if(status == DoorStatus.Opening) {
    		Platform.runLater(() -> doorStatusLabel.setText("opening"));
    	} else if(status == DoorStatus.Closing) {
    		Platform.runLater(() -> doorStatusLabel.setText("closing"));
    	} else {
    		Platform.runLater(() -> doorStatusLabel.setText("closed"));
    	}
    }
    
    public void SetDestination(int floor) {
    	Platform.runLater(() -> destinationLabel.setText(Integer.toString(floor)));
    }
    
    public void SetDirection(Direction dir) {
    	if (dir == Direction.Up) {
    		Platform.runLater(() -> directionLabel.setText("up"));
    	} else if (dir == Direction.Down) {
    		Platform.runLater(() -> directionLabel.setText("down"));
    	} else {
    		Platform.runLater(() -> directionLabel.setText("--"));
    	}
    }
    
    public void SetNumberFloors(int number) {
    	if(numberFloors != number)
    	{
    		try {
    			floorControllerList = FXCollections.observableArrayList();
    			floorButtonsListView.getItems().clear();
    			for (int i = 0; i < number; i++) {
    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ElevatorFloor.fxml"));
    				Pane listItem = fxmlLoader.load();
    				ElevatorFloorController controller = fxmlLoader.getController();
    				controller.AddMouseClickEventHandler(new EventHandler() {
    					@Override
    					public void handle(Event event) {
    						try {
    							if(currentTarget != GetDestination() && !GetAutomaticModeActive())
    							{
    								mElevatorModel.setTarget(GetDestination());
    								currentTarget = GetDestination();
    							}
    						} catch (ControlCenterException e) {
    							e.printStackTrace();
    						}
    					}
    				});
    				controller.SetElevatorActive(false);
    				controller.SetStopActive(false);
    				controller.SetFloorActive(true);
    				floorControllerList.add(controller);
    				floorButtonsListView.getItems().add(0, listItem);
    			}
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		} 
    		numberFloors = number;
    	}
    }
    
    public boolean GetAutomaticModeActive() {
    	return automaticModeCheckBox.isSelected();
    }
    
    public int GetDestination() {
    	// if no floor has been selected, the index will be -1
    	int selectedIndex = floorButtonsListView.getSelectionModel().getSelectedIndex();
    	if(selectedIndex == -1)
    		selectedIndex = 0;
    	
    	return numberFloors - 1 - selectedIndex;
    }
    
    // floors from 0 to n
    public ElevatorFloorController GetFloor(int number) {
    	return floorControllerList.get(number);
    }
}
