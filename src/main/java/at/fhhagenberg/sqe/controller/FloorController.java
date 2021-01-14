package at.fhhagenberg.sqe.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import at.fhhagenberg.sqe.model.FloorModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Sample Skeleton for 'Floor.fxml' Controller Class
 */
public class FloorController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    protected ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    protected URL location;

    @FXML // fx:id="upArrowImageView"
    protected ImageView upArrowImageView; // Value injected by FXMLLoader

    @FXML // fx:id="floorNumberLabel"
    protected Label floorNumberLabel; // Value injected by FXMLLoader

    @FXML // fx:id="downArrowImageView"
    protected ImageView downArrowImageView; // Value injected by FXMLLoader
    
    private FloorModel mFloorModel;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert upArrowImageView != null : "fx:id=\"upArrowImageView\" was not injected: check your FXML file 'Floor.fxml'.";
        assert floorNumberLabel != null : "fx:id=\"floorNumberLabel\" was not injected: check your FXML file 'Floor.fxml'.";
        assert downArrowImageView != null : "fx:id=\"downArrowImageView\" was not injected: check your FXML file 'Floor.fxml'.";
    }
    
    public void SetFloorModel(FloorModel floorModel) {
    	mFloorModel = floorModel;
    	SetFloorNumber(mFloorModel.getFloorId());
    	mFloorModel.addListener(new PropertyChangeListener() {	
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Platform.runLater(() -> {
					if(evt.getPropertyName() == "Id") {
						SetFloorNumber((int)evt.getNewValue());
					}
					else if(evt.getPropertyName() == "ButtonUp") {
						SetUpArrowActive((boolean)evt.getNewValue());
					}
					else if(evt.getPropertyName() == "ButtonDown") {
						SetDownArrowActive((boolean)evt.getNewValue());
					}
				});
			}
    	});
    }
    
    public void SetFloorNumber(int number) {
    	floorNumberLabel.setText(Integer.toString(number));
    }
    
    public void SetUpArrowActive(boolean active) {
    	if (active) {
    		upArrowImageView.setOpacity(1);
    	} else {
    		upArrowImageView.setOpacity(0.25);
    	}
    }
    
    public void SetDownArrowActive(boolean active) {
    	if (active) {
    		downArrowImageView.setOpacity(1);
    	} else {
    		downArrowImageView.setOpacity(0.25);
    	}
    }
}
