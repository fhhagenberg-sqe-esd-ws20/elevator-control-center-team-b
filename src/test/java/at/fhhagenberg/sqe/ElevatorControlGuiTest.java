package at.fhhagenberg.sqe;

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
import at.fhhagenberg.sqe.controller.ElevatorControlController;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ElevatorControlGuiTest {
    private ElevatorControlController controller;
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws ControlCenterException 
     * @throws InterruptedException 
     */
    @Start
    public void start(Stage stage) throws ControlCenterException, InterruptedException {
    	Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
    	var buildingmock = new BuildingMock(5,2,2.0);
        var app = new ElevatorControl(new BuildingModel(buildingmock),new ElevatorExceptionHandlerMock(buildingmock));
        app.start(stage);
        controller = app.GetController();
        try {
			controller.SetBuildingModel(new BuildingModel(new BuildingMock(3, 4, 2.0)));
		} catch (ControlCenterException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws InterruptedException 
     */
    @Test
    public void testDefaultValues(FxRobot robot) throws InterruptedException {
    	Thread.sleep(1000);
    	
        FxAssert.verifyThat("#elevatorNumberLabel", LabeledMatchers.hasText("1"));
        FxAssert.verifyThat("#elevatorsListView", ListViewMatchers.hasItems(4));
        
        FxAssert.verifyThat("#payloadLabel", LabeledMatchers.hasText("123.5 lbs"));
        
        FxAssert.verifyThat("#velocityLabel", LabeledMatchers.hasText("0.0 ft/s"));
        
        FxAssert.verifyThat("#doorStatusLabel", LabeledMatchers.hasText("open"));
        
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("1"));
        
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("up"));
        
        FxAssert.verifyThat("#floorNumberLabel", LabeledMatchers.hasText("2"));
        FxAssert.verifyThat("#floorsListView", ListViewMatchers.hasItems(3));
        
        FxAssert.verifyThat("#floorButtonsListView", ListViewMatchers.hasItems(3));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws InterruptedException 
     */
    @Test
    public void testAutomaticMode(FxRobot robot) throws InterruptedException {
    	Thread.sleep(750);
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("1"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("up"));
        Thread.sleep(1000);
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("2"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("up"));
        Thread.sleep(1000);
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("1"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("down"));
        Thread.sleep(1000);
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("0"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("down"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     * @throws InterruptedException 
     */
    @Test
    public void testManualMode(FxRobot robot) throws InterruptedException {
        robot.clickOn("#automaticModeCheckBox");
        robot.clickOn("#elevatorFloorHBox");
        Thread.sleep(1000);
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("2"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("up"));
    }
}