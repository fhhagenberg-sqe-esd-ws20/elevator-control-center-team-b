package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.mocks.FloorMock;
import at.fhhagenberg.sqe.controller.FloorController;
import at.fhhagenberg.sqe.model.FloorModel;

import org.junit.jupiter.api.BeforeEach;

import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class FloorControllerTest extends FloorController {	
	private FloorController controller;
	
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
		controller = new FloorController();
		controller.floorNumberLabel = new Label();
		controller.upArrowImageView = new ImageView();
		controller.downArrowImageView = new ImageView();
	}
	
	@Test
	public void testSetFloorModel() {
		controller.SetFloorModel(new FloorModel(new FloorMock(1)));
		assertEquals("1", controller.floorNumberLabel.getText());
	}
	
	@Test
	public void testSetFloorNumber() {
		controller.SetFloorNumber(1);
		assertEquals("1", controller.floorNumberLabel.getText());
	}
	
	@Test
	public void testSetUpArrowActive() {
		controller.SetUpArrowActive(true);
		assertEquals(1.0, controller.upArrowImageView.getOpacity());
		
		controller.SetUpArrowActive(false);
		assertEquals(0.25, controller.upArrowImageView.getOpacity());
	}

	@Test
	public void testSetDownArrowActive() {
		controller.SetDownArrowActive(true);
		assertEquals(1.0, controller.downArrowImageView.getOpacity());
		
		controller.SetDownArrowActive(false);
		assertEquals(0.25, controller.downArrowImageView.getOpacity());
	}
}
