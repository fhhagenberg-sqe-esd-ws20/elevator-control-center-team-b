package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.DoorStatus;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.controlcenter.mocks.ElevatorControlMock;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.BuildingModel;
import at.fhhagenberg.sqe.model.ElevatorModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

class ElevatorControllerTest extends ElevatorController {	
	private ElevatorController controller;
	
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
		Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
	}
	
	/**
	 * Init FXML variables.
	 */
	@BeforeEach
	protected void setUp() {
		controller = new ElevatorController();
		controller.destinationLabel = new Label();
		controller.payloadLabel = new Label();
		controller.floorButtonsListView = new ListView<Pane>();
		controller.doorStatusLabel = new Label();
		controller.directionLabel = new Label();
		controller.automaticModeCheckBox = new CheckBox();
		controller.velocityLabel = new Label();
		controller.elevatorNumberLabel = new Label();
		controller.initialize();
	}
	
	@Test
	void testSetElevatorModel() {
		controller.setElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));
		assertFalse(controller.floorButtonsListView.getItems().isEmpty());
	}
	
	@Test
    void testSetElevatorNumber() {
		controller.setElevatorNumber(1);
		assertEquals("1", controller.elevatorNumberLabel.getText());
    }
	
	@Test
    void testSetPayload() {
		controller.setPayload(1.1);
		assertEquals("1.1 lbs", controller.payloadLabel.getText());
    }
	
	@Test
    void testSetVelocity() {
		controller.setVelocity(1.1);
		assertEquals("1.1 ft/s", controller.velocityLabel.getText());
    }
	
	@Test
    void testSetDoorStatus() {
		controller.setDoorStatus(DoorStatus.OPEN);
		assertEquals("open", controller.doorStatusLabel.getText());
		controller.setDoorStatus(DoorStatus.CLOSED);
		assertEquals("closed", controller.doorStatusLabel.getText());
		controller.setDoorStatus(DoorStatus.OPENING);
		assertEquals("opening", controller.doorStatusLabel.getText());
		controller.setDoorStatus(DoorStatus.CLOSING);
		assertEquals("closing", controller.doorStatusLabel.getText());
    }
	
	@Test
    void testSetDirection() {
		controller.setDirection(Direction.UNCOMMITTED);
		assertEquals("--", controller.directionLabel.getText());
		controller.setDirection(Direction.UP);
		assertEquals("up", controller.directionLabel.getText());
		controller.setDirection(Direction.DOWN);
		assertEquals("down", controller.directionLabel.getText());
    }
	
	@Test
    void testGetAutomaticModeActive() {
		controller.automaticModeCheckBox.setSelected(true);
		assertTrue(controller.getAutomaticModeActive());
    }
	
	@Test
    void testGetDestination() throws ControlCenterException, InterruptedException {
		controller.setElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));
		controller.floorButtonsListView.getSelectionModel().select(1);
		assertEquals(1, controller.getDestination());
    }
	
	@Test
	void testGetDestinationUnselected() throws ControlCenterException {
		controller.setElevatorModel(new ElevatorModel(new ElevatorControlMock(3))); 
		assertEquals(-1, controller.getDestination());
	}
	
	@Test
    void testGetFloor() throws ControlCenterException {
		controller.setElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));   
		assertEquals(0.25, controller.getFloor(0).stopImageView.getOpacity());
		assertEquals(0.25, controller.getFloor(1).stopImageView.getOpacity());
		assertEquals(0.25, controller.getFloor(2).stopImageView.getOpacity());
    }
	
	@Test
	void testGetFloorException() throws ControlCenterException {
		controller.setElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));   
		assertThrows(IndexOutOfBoundsException.class, () -> controller.getFloor(-1));
	}
}
