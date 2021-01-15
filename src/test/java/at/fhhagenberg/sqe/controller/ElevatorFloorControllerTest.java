package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controller.ElevatorFloorController;

import org.junit.jupiter.api.BeforeEach;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ElevatorFloorControllerTest extends ElevatorFloorController {	
	private ElevatorFloorController controller;
	
	/**
	 * Start Toolkit.
	 */
	@BeforeAll
	static protected void init() {
		try {
			Platform.startup(() -> {});
		} catch (IllegalStateException e) {
			// toolkit is already running
		}
	}
	
	/**
	 * Init FXML variables.
	 */
	@BeforeEach
	protected void setUp() throws Exception {
		controller = new ElevatorFloorController();
		controller.elevatorImageView = new ImageView();
		controller.stopImageView = new ImageView();
		controller.elevatorFloorHBox = new HBox();
		controller.initialize();
	}
	
	@Test
    public void testSetElevatorActive() {
		controller.SetElevatorActive(true);
		assertEquals(1.0, controller.elevatorImageView.getOpacity());
		
		controller.SetElevatorActive(false);
		assertEquals(0.0, controller.elevatorImageView.getOpacity());
    }
    
	@Test
    public void testSetStopActive() {
		controller.SetStopActive(true);
		assertEquals(1.0, controller.stopImageView.getOpacity());
		
		controller.SetStopActive(false);
		assertEquals(0.25, controller.stopImageView.getOpacity());
    }
    
	@Test
    public void testSetFloorActive() {
		controller.SetFloorActive(true);
		assertEquals(1.0, controller.elevatorFloorHBox.getOpacity());
		
		controller.SetFloorActive(false);
		assertEquals(0.25, controller.elevatorFloorHBox.getOpacity());
    }
}
