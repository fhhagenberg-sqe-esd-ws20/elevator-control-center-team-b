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
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;
import at.fhhagenberg.sqe.controlcenter.IFloor;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.DoorStatus;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.controller.ElevatorControlController;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class ElevatorControlGuiTest {
    private ElevatorControlController controller;
    private IBuilding mBuildingMock;
    private IElevatorControl mElevatorMock;
    private IFloor mFloorMock;
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws ControlCenterException 
     * @throws InterruptedException 
     */
    @Start
    public void start(Stage stage) throws ControlCenterException, InterruptedException, ControlCenterException {
    	Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
    	
    	
    	mBuildingMock = Mockito.mock(IBuilding.class);
    	
    	
    	mElevatorMock = Mockito.mock(IElevatorControl.class);
    	mFloorMock = Mockito.mock(IFloor.class);
    	Mockito.when(mFloorMock.getFloorId()).thenReturn(0).thenReturn(1).thenReturn(2);
    	
    	Mockito.when(mElevatorMock.getFloorNum()).thenReturn(3);
    	Mockito.when(mElevatorMock.getCurrentDoorStatus()).thenReturn(DoorStatus.OPEN);
    	Mockito.when(mElevatorMock.getWeight()).thenReturn(123.5);
    	
    	Mockito.when(mBuildingMock.getElevator(Mockito.anyInt())).thenReturn(mElevatorMock);
    	Mockito.when(mBuildingMock.getFloor(Mockito.anyInt())).thenReturn(mFloorMock);
    	
    	Mockito.when(mBuildingMock.getNumberOfElevators()).thenReturn(4);
    	Mockito.when(mBuildingMock.getNumberOfFloors()).thenReturn(3);
    	Mockito.when(mBuildingMock.getHeightOfFloor()).thenReturn(2.0);
    	
        var app = new ElevatorControl(null,new ElevatorExceptionHandlerMock(mBuildingMock));
        app.start(stage);
        controller = app.getController();
        controller.setTimerInterval(1000);
        //controller.setBuildingModel(new BuildingModel(mBuildingMock));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws InterruptedException 
     * @throws TimeoutException 
     * @throws ControlCenterException 
     */
    @Test
    void testDefaultValues(FxRobot robot) throws InterruptedException, TimeoutException, ControlCenterException {
    	Mockito.when(mElevatorMock.getTarget()).thenReturn(1);
    	Mockito.when(mElevatorMock.getCurrentDirection()).thenReturn(Direction.UP);
    	
    	WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#directionLabel").queryAs(Label.class).getText().equals("up");
            }
        });
    	
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
     * @throws TimeoutException 
     * @throws ControlCenterException 
     */
    @Test
    void testManualMode(FxRobot robot) throws InterruptedException, TimeoutException, ControlCenterException {
        robot.clickOn("#automaticModeCheckBox");
        robot.clickOn("#elevatorFloorHBox");
        Mockito.when(mElevatorMock.getTarget()).thenReturn(2);
        
        WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#destinationLabel").queryAs(Label.class).getText().equals("2");
            }
        });
        FxAssert.verifyThat("#destinationLabel", LabeledMatchers.hasText("2"));
        FxAssert.verifyThat("#directionLabel", LabeledMatchers.hasText("up"));
        
        Mockito.verify(mElevatorMock).setTarget(2);
    }
}