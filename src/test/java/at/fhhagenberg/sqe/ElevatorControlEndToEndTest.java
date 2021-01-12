package at.fhhagenberg.sqe;

import java.rmi.RemoteException;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.ListViewMatchers;

import at.fhhagenberg.sqe.controlcenter.BuildingAdapter;
import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.stage.Stage;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
public class ElevatorControlEndToEndTest {
    private ElevatorControlController controller;
    private IElevator mElevatorMock;
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws ControlCenterException 
     * @throws RemoteException 
     */
    @Start
    public void start(Stage stage) throws ControlCenterException, RemoteException {
    	Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
    	
    	mElevatorMock = Mockito.mock(IElevator.class, Mockito.CALLS_REAL_METHODS);
    	Mockito.when(mElevatorMock.getFloorNum()).thenReturn(4);
    	Mockito.when(mElevatorMock.getElevatorNum()).thenReturn(4);
    	Mockito.when(mElevatorMock.getElevatorDoorStatus(Mockito.anyInt())).thenReturn(IElevator.ELEVATOR_DOORS_OPEN);
    	
    	var buildingmock = new BuildingAdapter(mElevatorMock);
        var app = new ElevatorControl(new BuildingModel(buildingmock), new ElevatorExceptionHandlerMock(buildingmock));
        app.start(stage);
        controller = app.GetController();
    }
    
    @Test
    public void setTargetTriggersIElevator(FxRobot robot) throws RemoteException {
    	//set into manual mode
    	robot.clickOn("#automaticModeCheckBox");
    	//click on upper floor of first elevator
    	robot.clickOn("#elevatorFloorHBox");
    	
    	// which will lead to setting the target of elevator 0 to floor 3
    	Mockito.verify(mElevatorMock).setTarget(0, 3);
    }
}