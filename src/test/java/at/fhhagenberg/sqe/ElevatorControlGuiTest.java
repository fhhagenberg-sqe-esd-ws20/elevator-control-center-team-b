package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.ListViewMatchers;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ElevatorControlGuiTest {
    private ElevatorControlController controller;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        var app = new ElevatorControl();
        app.start(stage);
        controller = app.GetController();
        try {
			controller.SetBuildingModel(new BuildingModel(new BuildingMock(5, 4, 2.0)));
		} catch (ControlCenterException e) {
			e.printStackTrace();
		}  
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testElevatorNumber(FxRobot robot) {
        FxAssert.verifyThat("#elevatorNumberLabel", LabeledMatchers.hasText("1"));
        FxAssert.verifyThat("#elevatorsListView", ListViewMatchers.hasItems(4));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testPayload(FxRobot robot) {
        FxAssert.verifyThat("#payloadLabel", LabeledMatchers.hasText("123.5 lbs"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testVelocity(FxRobot robot) {
        FxAssert.verifyThat("#velocityLabel", LabeledMatchers.hasText("0.0 ft/s"));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testDoorStatus(FxRobot robot) {
        FxAssert.verifyThat("#doorStatusLabel", LabeledMatchers.hasText("closed"));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testDestination(FxRobot robot) {
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("0"));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testDirection(FxRobot robot) {
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("--"));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testFloorNumber(FxRobot robot) {
    	FxAssert.verifyThat("#floorNumberLabel", LabeledMatchers.hasText("4"));
        FxAssert.verifyThat("#floorsListView", ListViewMatchers.hasItems(5));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testElevatorFloorNumber(FxRobot robot) {
        FxAssert.verifyThat("#floorButtonsListView", ListViewMatchers.hasItems(5));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testAutomaticMode(FxRobot robot) {
        robot.clickOn("#elevatorFloorHBox");
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("0"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("--"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testManualMode(FxRobot robot) {
        robot.clickOn("#automaticModeCheckBox");
        robot.clickOn("#elevatorFloorHBox");
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("4"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("--"));
    }
}