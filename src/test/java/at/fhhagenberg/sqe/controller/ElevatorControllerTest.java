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

public class ElevatorControllerTest extends ElevatorController {	
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
	public void testSetElevatorModel() {
		controller.SetElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));
		assertFalse(controller.floorButtonsListView.getItems().isEmpty());
	}
	
	@Test
    public void testSetElevatorNumber() {
		controller.SetElevatorNumber(1);
		assertEquals("1", controller.elevatorNumberLabel.getText());
    }
	
	@Test
    public void testSetPayload() {
		controller.SetPayload(1.1);
		assertEquals("1.1 lbs", controller.payloadLabel.getText());
    }
	
	@Test
    public void testSetVelocity() {
		controller.SetVelocity(1.1);
		assertEquals("1.1 ft/s", controller.velocityLabel.getText());
    }
	
	@Test
    public void testSetDoorStatus() {
		controller.SetDoorStatus(DoorStatus.Open);
		assertEquals("open", controller.doorStatusLabel.getText());
		controller.SetDoorStatus(DoorStatus.Closed);
		assertEquals("closed", controller.doorStatusLabel.getText());
		controller.SetDoorStatus(DoorStatus.Opening);
		assertEquals("opening", controller.doorStatusLabel.getText());
		controller.SetDoorStatus(DoorStatus.Closing);
		assertEquals("closing", controller.doorStatusLabel.getText());
    }
	
	@Test
    public void testSetDirection() {
		controller.SetDirection(Direction.Uncommited);
		assertEquals("--", controller.directionLabel.getText());
		controller.SetDirection(Direction.Up);
		assertEquals("up", controller.directionLabel.getText());
		controller.SetDirection(Direction.Down);
		assertEquals("down", controller.directionLabel.getText());
    }
	
	@Test
    public void testGetAutomaticModeActive() {
		controller.automaticModeCheckBox.setSelected(true);
		assertTrue(controller.GetAutomaticModeActive());
    }
	
	@Test
    public void testGetDestination() throws ControlCenterException, InterruptedException {
		controller.SetElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));
		controller.floorButtonsListView.getSelectionModel().select(1);
		assertEquals(1, controller.GetDestination());
    }
	
	@Test
	public void testGetDestinationUnselected() throws ControlCenterException {
		controller.SetElevatorModel(new ElevatorModel(new ElevatorControlMock(3))); 
		assertEquals(-1, controller.GetDestination());
	}
	
	@Test
    public void testGetFloor() throws ControlCenterException {
		controller.SetElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));   
		assertEquals(0.25, controller.GetFloor(0).stopImageView.getOpacity());
		assertEquals(0.25, controller.GetFloor(1).stopImageView.getOpacity());
		assertEquals(0.25, controller.GetFloor(2).stopImageView.getOpacity());
    }
	
	@Test
	public void testGetFloorException() throws ControlCenterException {
		controller.SetElevatorModel(new ElevatorModel(new ElevatorControlMock(3)));   
		assertThrows(IndexOutOfBoundsException.class, () -> controller.GetFloor(-1));
	}
}
