package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class ElevatorControlControllerTest extends ElevatorControlController {	
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
	
	@BeforeEach
	protected void setUp() {
		controller = new ElevatorControlController();
		controller.elevatorsListView = new ListView<Pane>();
		controller.messageTextArea = new TextArea();
		controller.floorsListView = new ListView<Pane>();
	}
	
	@Test
    public void testSetMessage() {
		controller.SetMessage("abc");
		assertEquals("abc\n", controller.messageTextArea.getText());
    }
	
	@Test
    public void testSetMessages() {
		controller.SetMessage("abc");
		controller.SetMessage("def");
		assertEquals("abc\ndef\n", controller.messageTextArea.getText());
    }
	
	@Test
    public void testGetFloor() throws ControlCenterException {
		controller.SetBuildingModel(new BuildingModel(new BuildingMock(3, 1, 1.0)));   
		assertEquals("0", controller.GetFloor(0).floorNumberLabel.getText());
		assertEquals("1", controller.GetFloor(1).floorNumberLabel.getText());
		assertEquals("2", controller.GetFloor(2).floorNumberLabel.getText());
    }
    
	@Test
    public void testSetNumberElevators() throws ControlCenterException {
		controller.SetBuildingModel(new BuildingModel(new BuildingMock(1, 3, 1.0)));   
		assertEquals("1", controller.GetElevator(1).elevatorNumberLabel.getText());
		assertEquals("2", controller.GetElevator(2).elevatorNumberLabel.getText());
		assertEquals("3", controller.GetElevator(3).elevatorNumberLabel.getText());
    }
	
	// TODO: test out of bound exceptions
}
