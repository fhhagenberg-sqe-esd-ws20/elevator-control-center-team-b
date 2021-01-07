package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ElevatorControlEndToEndTest {
    private ElevatorControlController controller;

    /**
     * Will be called before start method.
     */
    @Init
    public void init() throws Exception {
    	FxToolkit.registerStage(() -> new Stage());
    }
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
    	Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
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
     * Will be called after each test.
     */
    @Stop
    public void stop() throws Exception {
    	FxToolkit.hideStage();
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
    
    
    // 1. Aenderung in GUI, Check im Model (Mock) oder evtl im IElevator (Mock)
    // 2. Aenderung in Model, Check im GUI
}