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

class FloorControllerTest extends FloorController {	
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
		controller.initialize();
	}
	
	@Test
	void testSetFloorNumber() {
		controller.setFloorNumber(1);
		assertEquals("1", controller.floorNumberLabel.getText());
	}
	
	@Test
	void testSetUpArrowActive() {
		controller.setUpArrowActive(true);
		assertEquals(1.0, controller.upArrowImageView.getOpacity());
		
		controller.setUpArrowActive(false);
		assertEquals(0.25, controller.upArrowImageView.getOpacity());
	}

	@Test
	void testSetDownArrowActive() {
		controller.setDownArrowActive(true);
		assertEquals(1.0, controller.downArrowImageView.getOpacity());
		
		controller.setDownArrowActive(false);
		assertEquals(0.25, controller.downArrowImageView.getOpacity());
	}
}
