package at.fhhagenberg.sqe.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import at.fhhagenberg.sqe.*;
import at.fhhagenberg.sqe.AutomaticMode;
import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
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
    private AutomaticMode mAutomaticMode;
    
    private int currentTarget = 0;
    private int currentFloor = 0;
    private Direction currentDirection = Direction.UNCOMMITTED;
    private DoorStatus doorStatus = DoorStatus.CLOSED;
    
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final int timerInterval_ms = 1000;
    
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
    
    public void setElevatorModel(ElevatorModel elevatorModel)
    {
    	mElevatorModel = elevatorModel;
    	setNumberFloors(mElevatorModel.getFloorNum());
    	mAutomaticMode = new AutomaticMode(mElevatorModel.getFloorNum());
    	mElevatorModel.addListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Platform.runLater(() -> {
					if(evt.getPropertyName().equals("FloorNum")) {
						// maximum number of floors
						setNumberFloors((int)evt.getNewValue());
					}
					else if(evt.getPropertyName().equals("Direction")) {
						setDirection((Direction)evt.getNewValue());
					}
					else if(evt.getPropertyName().equals("DoorStatus")) {
						doorStatus = (DoorStatus)evt.getNewValue();
						setDoorStatus(doorStatus);
					}
					else if(evt.getPropertyName().equals("PressedFloorButtons")) {
						List<Integer> pressedButtons = mElevatorModel.getPressedFloorButtons();
						// reset all floors previously
						for(ElevatorFloorController floor : floorControllerList) {
							floor.setStopActive(false);
						}
						// get new list + update
						for(int i : pressedButtons) {
							floorControllerList.get(i).setStopActive(true);
						}
					}
					else if(evt.getPropertyName().equals("ServicedFloors")) {
						List<Integer> activeFloors = mElevatorModel.getServicedFloors();
						// reset all floors previously
						for(ElevatorFloorController floor : floorControllerList) {
							floor.setFloorActive(false);
						}
						// get new list + update
						for(int i : activeFloors) {
							floorControllerList.get(i).setFloorActive(true);
						}
					}
					else if(evt.getPropertyName().equals("CurrentFloor")) {
						currentFloor = mElevatorModel.getCurrentFloor();
						// reset all floors
						for(ElevatorFloorController floor : floorControllerList) {
							floor.setElevatorActive(false);
						}
						// set only current floor
						floorControllerList.get(currentFloor).setElevatorActive(true);
					}
					else if(evt.getPropertyName().equals("Speed")) {
						setVelocity((double)evt.getNewValue());
					}
					else if(evt.getPropertyName().equals("Acceleration")) {
						// not needed
					}
					else if(evt.getPropertyName().equals("Weight")) {
						setPayload((double)evt.getNewValue());
					}
					else if(evt.getPropertyName().equals("Target")) {
						setDestination((int)evt.getNewValue());					
					}
				});
			}
		});
    	// Automatic mode
		scheduledExecutorService.scheduleAtFixedRate(()-> {
			if (getAutomaticModeActive() && doorStatus == DoorStatus.OPEN) {
				mAutomaticMode.setCurrentDirection(currentDirection);
				mAutomaticMode.setCurrentFloor(currentFloor);
				mAutomaticMode.calculateNextTargetAndDirection();
				currentTarget = mAutomaticMode.getNextTarget();
				currentDirection = mAutomaticMode.getNextDirection();
				try {
					mElevatorModel.setTarget(currentTarget);
					mElevatorModel.setDirection(currentDirection);
				} catch (ControlCenterException e) {
					// reconnect is handled by another controller
				}
			}
		},timerInterval_ms,timerInterval_ms,TimeUnit.MILLISECONDS);
    }
    
    public void setElevatorNumber(int number) {
    	elevatorNumberLabel.setText(Integer.toString(number));
    }
    
    public void setPayload(double payload) {
    	payloadLabel.setText(String.format("%.1f",  payload) + " lbs");
    }
    
    public void setVelocity(double velocity) {
    	velocityLabel.setText(String.format("%.1f",  velocity) + " ft/s");
    }
    
    public void setDoorStatus(DoorStatus status) {
    	if (status == DoorStatus.OPEN) {
    		doorStatusLabel.setText("open");
    	} else if(status == DoorStatus.OPENING) {
    		doorStatusLabel.setText("opening");
    	} else if(status == DoorStatus.CLOSING) {
    		doorStatusLabel.setText("closing");
    	} else {
    		doorStatusLabel.setText("closed");
    	}
    }
    
    public void setDestination(int floor) {
    	destinationLabel.setText(Integer.toString(floor));
    }
    
    public void setDirection(Direction dir) {
    	if (dir == Direction.UP) {
    		directionLabel.setText("up");
    	} else if (dir == Direction.DOWN) {
    		directionLabel.setText("down");
    	} else {
    		directionLabel.setText("--");
    	}
    }
    
    public void setNumberFloors(int number) {
    	if(number > 0 && numberFloors != number)
    	{
    		try {
    			floorControllerList = FXCollections.observableArrayList();;
    			floorButtonsListView.getItems().clear();
    			for (int i = 0; i < number; i++) {
    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ElevatorFloor.fxml"));
    				Pane listItem = fxmlLoader.load();
    				ElevatorFloorController controller = fxmlLoader.getController();
    				controller.addMouseClickEventHandler(new EventHandler() {
    					@Override
    					public void handle(Event event) {
    						Platform.runLater(() -> {
    							try {
    								if(currentTarget != getDestination() && !getAutomaticModeActive() && doorStatus == DoorStatus.OPEN)
    								{
    									currentTarget = getDestination();
    									mElevatorModel.setTarget(currentTarget);
    									if (currentFloor < currentTarget) {
    										currentDirection = Direction.UP;
    									} 
    									else if (currentFloor > currentTarget) {
    										currentDirection = Direction.DOWN;
    									}
    									else {
    										currentDirection = Direction.UNCOMMITTED;
    									}
    									mElevatorModel.setDirection(currentDirection);
    								}	
    							} catch (ControlCenterException e) {
    								// reconnect is handled by another controller
    							}
    						});
    					}
    				});
    				controller.setElevatorActive(false);
    				controller.setStopActive(false);
    				controller.setFloorActive(true);
    				floorControllerList.add(controller);
    				floorButtonsListView.getItems().add(0, listItem);
    			}
    			floorControllerList.get(0).setElevatorActive(true);
    		} catch (IOException ex) {
    			// what should be done here?
    		} 
    		numberFloors = number;
    	}
    }
    
    public boolean getAutomaticModeActive() {
    	return automaticModeCheckBox.isSelected();
    }
    
    public int getDestination() {
    	// if no floor has been selected, the index will be -1
    	int ret = -1;
    	int selectedIndex = floorButtonsListView.getSelectionModel().getSelectedIndex();
    	if(selectedIndex > -1) {
    		ret = numberFloors - 1 - selectedIndex;
    	}
    	
    	return ret;
    }
    
    // floors from 0 to n
    public ElevatorFloorController getFloor(int number) {
    	return floorControllerList.get(number);
    }
    
    // cancel the timer so all created threads will stop at termination
    public void stop() {
    	scheduledExecutorService.shutdown();
    }
}
