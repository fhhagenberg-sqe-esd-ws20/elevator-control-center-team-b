package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.controller.ElevatorControlController;
import at.fhhagenberg.sqe.model.BuildingModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

class ElevatorControlControllerTest extends ElevatorControlController {	
	private ElevatorControlController controller;
	
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
	protected void setUp() {
		controller = new ElevatorControlController();
		controller.elevatorsListView = new ListView<Pane>();
		controller.messageTextArea = new TextArea();
		controller.floorsListView = new ListView<Pane>();
		controller.initialize();
	}
	
	@Test
	void testSetBuildingModel() throws ControlCenterException {
		controller.setBuildingModel(new BuildingModel(new BuildingMock(3, 1, 1.0)));
		assertFalse(controller.floorsListView.getItems().isEmpty());
		assertFalse(controller.elevatorsListView.getItems().isEmpty());
	}
	
	@Test
    void testSetMessage() {
		controller.setMessage("abc");
		assertEquals("abc\n", controller.messageTextArea.getText());
    }
	
	@Test
    void testSetMessages() {
		controller.setMessage("abc");
		controller.setMessage("def");
		assertEquals("abc\ndef\n", controller.messageTextArea.getText());
    }
	
	@Test
    void testGetFloor() throws ControlCenterException {
		controller.setBuildingModel(new BuildingModel(new BuildingMock(3, 1, 1.0)));   
		assertEquals("0", controller.getFloor(0).floorNumberLabel.getText());
		assertEquals("1", controller.getFloor(1).floorNumberLabel.getText());
		assertEquals("2", controller.getFloor(2).floorNumberLabel.getText());
    }
    
	@Test
    void testSetNumberElevators() throws ControlCenterException {
		controller.setBuildingModel(new BuildingModel(new BuildingMock(3, 3, 1.0)));   
		assertEquals("1", controller.getElevator(0).elevatorNumberLabel.getText());
		assertEquals("2", controller.getElevator(1).elevatorNumberLabel.getText());
		assertEquals("3", controller.getElevator(2).elevatorNumberLabel.getText());
    }
	
	@Test
	void testGetFloorException() throws ControlCenterException {
		controller.setBuildingModel(new BuildingModel(new BuildingMock(3, 1, 1.0)));  
		assertThrows(IndexOutOfBoundsException.class, () -> controller.getFloor(-1));
	}
	
	@Test
	void testGetElevatorException() throws ControlCenterException {
		controller.setBuildingModel(new BuildingModel(new BuildingMock(3, 3, 1.0)));  
		assertThrows(IndexOutOfBoundsException.class, () -> controller.getElevator(-1));
	}
}
