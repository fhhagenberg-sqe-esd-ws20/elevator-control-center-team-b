package at.fhhagenberg.sqe;

import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;
import at.fhhagenberg.sqe.controlcenter.IFloor;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.DoorStatus;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.controller.ElevatorControlController;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class ElevatorControlAutomaticModeGuiTest {
	private ElevatorControlController controller;
	
	@Start
    public void start(Stage stage) throws ControlCenterException, InterruptedException, ControlCenterException {
    	Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
    	
    	IBuilding buildingmock = new BuildingMock(3,4,2.0);
    	
        var app = new ElevatorControl(null,new ElevatorExceptionHandlerMock(buildingmock));
        app.start(stage);
        controller = app.getController();
        controller.setTimerInterval(1000);
    }
	
	/**
     * @param robot - Will be injected by the test runner.
     * @throws InterruptedException 
     * @throws TimeoutException 
     * @throws ControlCenterException 
     */
    @Test
    void testAutomaticMode(FxRobot robot) throws InterruptedException, TimeoutException, ControlCenterException {
    	
    	
    	WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#destinationLabel").queryAs(Label.class).getText().equals("1");
            }
        });
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("1"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("up"));
        WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#destinationLabel").queryAs(Label.class).getText().equals("2");
            }
        });
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("2"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("up"));
        WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#destinationLabel").queryAs(Label.class).getText().equals("1");
            }
        });
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("1"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("down"));
        WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#destinationLabel").queryAs(Label.class).getText().equals("0");
            }
        });
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("0"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("down"));
    }
}
