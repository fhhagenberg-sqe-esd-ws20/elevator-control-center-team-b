package at.fhhagenberg.sqe;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Sample Skeleton for 'ElevatorFloor.fxml' Controller Class
 */
public class ElevatorFloorController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    protected ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    protected URL location;

    @FXML // fx:id="elevatorImageView"
    protected ImageView elevatorImageView; // Value injected by FXMLLoader
    
    @FXML // fx:id="elevatorFloorHBox"
    protected HBox elevatorFloorHBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="elevatorFloorAnchorPane"
    protected AnchorPane elevatorFloorAnchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="stopImageView"
    protected ImageView stopImageView; // Value injected by FXMLLoader
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert elevatorImageView != null : "fx:id=\"elevatorImageView\" was not injected: check your FXML file 'ElevatorFloor.fxml'.";
        assert stopImageView != null : "fx:id=\"stopImageView\" was not injected: check your FXML file 'ElevatorFloor.fxml'.";
    }
    
    public void AddMouseClickEventHandler(EventHandler handler) {
    	elevatorFloorHBox.setOnMouseClicked(handler);
    }
    
    public void SetElevatorActive(boolean active) {
    	if (active) {
    		elevatorImageView.setOpacity(1);
    	} else {
    		elevatorImageView.setOpacity(0);
    	}
    }
    
    public void SetStopActive(boolean active) {
    	if (active) {
    		stopImageView.setOpacity(1);
    	} else {
    		stopImageView.setOpacity(0.25);
    	}
    }
    
    public void SetFloorActive(boolean active) {
    	if (active) {
    		elevatorFloorHBox.setOpacity(1);
    	} else {
    		elevatorFloorHBox.setOpacity(0.25);
    	}
    }
}
