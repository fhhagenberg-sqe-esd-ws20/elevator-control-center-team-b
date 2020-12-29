package at.fhhagenberg.sqe;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Sample Skeleton for 'ElevatorFloor.fxml' Controller Class
 */
public class ElevatorFloorController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="elevatorImageView"
    private ImageView elevatorImageView; // Value injected by FXMLLoader
    
    @FXML // fx:id="elevatorFloorAnchorPane"
    private AnchorPane elevatorFloorAnchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="stopImageView"
    private ImageView stopImageView; // Value injected by FXMLLoader
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert elevatorImageView != null : "fx:id=\"elevatorImageView\" was not injected: check your FXML file 'ElevatorFloor.fxml'.";
        assert stopImageView != null : "fx:id=\"stopImageView\" was not injected: check your FXML file 'ElevatorFloor.fxml'.";
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
}
