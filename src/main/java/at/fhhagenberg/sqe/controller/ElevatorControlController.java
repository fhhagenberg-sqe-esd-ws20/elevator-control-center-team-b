package at.fhhagenberg.sqe.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import at.fhhagenberg.sqe.*;
import at.fhhagenberg.sqe.ElevatorScheduler;
import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.DoorStatus;
import at.fhhagenberg.sqe.exceptionhandler.ElevatorExceptionHandler;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
    private ElevatorExceptionHandler mHandler;
    private ScheduledFuture<?> mHandlerFuture;
    
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
     
    private ElevatorScheduler elevatorScheduler;
    private ScheduledFuture<?> mSchedulerFuture;
    
    static private final int mTimerIntervalMs = 1000;
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert elevatorsListView != null : "fx:id=\"elevatorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert messageTextArea != null : "fx:id=\"messageTextArea\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        assert floorsListView != null : "fx:id=\"floorsListView\" was not injected: check your FXML file 'ElevatorControl.fxml'.";
        elevatorScheduler = new ElevatorScheduler();
    }
    
    public void setError(String str) {
    	setMessage(str);
    }
    
    public void updateModel(IBuilding building) throws ControlCenterException {
    	// in a safe state again -> GUI can change into normal state again
    	if(mBuildingModel == null) {
    		var model = new BuildingModel(building);
    		mHandlerFuture.cancel(false);
    		Platform.runLater(() -> setBuildingModel(model));
    	}
    	else {
    		mBuildingModel.updateBuilding(building);
    		mHandlerFuture.cancel(false);
    		mSchedulerFuture = scheduledExecutorService.scheduleAtFixedRate(()->elevatorScheduler.run(),mTimerIntervalMs,mTimerIntervalMs,TimeUnit.MILLISECONDS);
    	}
    	
    	
    	
    }
    
    private boolean isInErrorMode() {
    	return mSchedulerFuture == null || mSchedulerFuture.isCancelled();
    }
    
    private void setReconnectionMode() {
    	if(elevatorControllerList != null)
    	{
    		// reset all called floors and stops
    		for(ElevatorController elevator : elevatorControllerList) {
				elevator.setPayload(0);
				elevator.setVelocity(0);
				elevator.setDoorStatus(DoorStatus.CLOSED);
				elevator.setDirection(Direction.UNCOMMITTED);
    		}
    	}

    	if(floorControllerList != null)
    	{
    		for(FloorController floor : floorControllerList) {
    			floor.setDownArrowActive(false);
    			floor.setUpArrowActive(false);
    		}
    	}
    }
    
    private void setErrorMode() {
    	if(mSchedulerFuture != null) {
    		mSchedulerFuture.cancel(false);
    	}
    	mHandlerFuture = scheduledExecutorService.scheduleAtFixedRate(()->mHandler.run(),mTimerIntervalMs,mTimerIntervalMs,TimeUnit.MILLISECONDS);
    	Platform.runLater(() ->  setReconnectionMode());
    }
    
    public void setExceptionHandler(ElevatorExceptionHandler handler) {
    	mHandler = handler;
    }
    
    public void setBuildingModel(BuildingModel buildingModel) {
    	var me = this;
    	if(buildingModel == null) {
    		setErrorMode();
    		return;
    	}
    	
        mBuildingModel = buildingModel;
        mBuildingModel.addListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("Exception")) {
					if(evt.getNewValue().getClass() == ControlCenterException.class) {
						if(!me.isInErrorMode()) {
							var strError = ((Exception)evt.getNewValue()).getLocalizedMessage();
							
							me.setError(strError.substring(strError.indexOf(':')+2));
							me.setErrorMode();
						}
					}
					else {
						System.out.print("Unhandled Exception occured: ");
						System.out.println(evt.getNewValue().toString());
					}
				}
			}
        });
        
        // schedule run-task of the model
        elevatorScheduler.addAsyncModel(mBuildingModel);

        // get floor models + elevator models
        setNumberFloors(mBuildingModel.getFloorNum());
        setNumberElevators(mBuildingModel.getElevatorNum());

        mSchedulerFuture = scheduledExecutorService.scheduleAtFixedRate(()->elevatorScheduler.run(),mTimerIntervalMs,mTimerIntervalMs,TimeUnit.MILLISECONDS);
    }
    
    public void setNumberFloors(int number) {
    	if(number > 0 && numberFloors != number) {
    		try {
    			floorControllerList = FXCollections.observableArrayList();
    			floorsListView.getItems().clear();
    			for (int i = 0; i < number; i++) {
    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Floor.fxml"));
    				Pane listItem = fxmlLoader.load();
    				FloorController controller = fxmlLoader.getController();
    				// initial settings
    				controller.setUpArrowActive(false);
    				controller.setDownArrowActive(false);
    				controller.setFloorNumber(i);
    				// attach model
    				controller.setFloorModel(mBuildingModel.getFloor(i));
    				floorControllerList.add(controller);
    				floorsListView.getItems().add(0, listItem);
    			}
    		} catch (IOException ex) {
    			//ex.printStackTrace();
    		}
    		numberFloors = number;
    	}
    }
    
    public void setNumberElevators(int number) {
    	if(number > 0 && numberElevators != number) {
    		try {
    			elevatorControllerList = FXCollections.observableArrayList();
    			elevatorsListView.getItems().clear();
    			for (int i = 1; i <= number; i++) {
    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Elevator.fxml"));
    				Pane listItem = fxmlLoader.load();
    				ElevatorController controller = fxmlLoader.getController();
    				// initial settings
    				controller.setElevatorNumber(i);
    				controller.setPayload(0);
    				controller.setVelocity(0);
    				controller.setDoorStatus(DoorStatus.CLOSED);
    				controller.setDestination(0);
    				controller.setDirection(Direction.UNCOMMITTED);
    				controller.setNumberFloors(numberFloors);
    				// attach model
    				controller.setElevatorModel(mBuildingModel.getElevator(i-1));
    				elevatorControllerList.add(controller);
    				elevatorsListView.getItems().add(listItem);
    			}
    		} catch (IOException ex) {
    			//ex.printStackTrace();
    		}
    		numberElevators = number;
    	}
    }
    
    public void setMessage(String message) {
    	messageTextArea.setText(messageTextArea.getText() + message + "\n");
    	messageTextArea.setScrollTop(Double.MAX_VALUE);
    }
    
    // floors from 0 to n
    public FloorController getFloor(int number) {
    	return floorControllerList.get(number);
    }
    
    // elevators from 0 to n
    public ElevatorController getElevator(int number) {
    	return elevatorControllerList.get(number);
    }
    
    // cancel the timer so all created threads will stop at termination
    public void stop() {
    	if (elevatorControllerList != null) {
    		for(ElevatorController elevator : elevatorControllerList) {
    			elevator.stop();
    		}
    	}
    	if(mSchedulerFuture != null) {
    		mSchedulerFuture.cancel(false);
    	}
    	if(mHandlerFuture != null) {
    		mHandlerFuture.cancel(false);
    	}
    	scheduledExecutorService.shutdown();
    }
}
