package at.fhhagenberg.sqe;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
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
import org.testfx.matcher.control.TextInputControlMatchers;

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
    private IElevatorConnector mConnectorMock;
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws ControlCenterException 
     * @throws RemoteException 
     * @throws NotBoundException 
     * @throws MalformedURLException 
     */
    @Start
    public void start(Stage stage) throws ControlCenterException, RemoteException, MalformedURLException, NotBoundException {
    	Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
    	
    	mElevatorMock = Mockito.mock(IElevator.class, Mockito.CALLS_REAL_METHODS);
    	Mockito.when(mElevatorMock.getFloorNum()).thenReturn(4);
    	Mockito.when(mElevatorMock.getElevatorNum()).thenReturn(4);
    	Mockito.when(mElevatorMock.getElevatorDoorStatus(Mockito.anyInt())).thenReturn(IElevator.ELEVATOR_DOORS_OPEN);
    	
    	mConnectorMock = Mockito.mock(IElevatorConnector.class, Mockito.CALLS_REAL_METHODS);
    	Mockito.when(mConnectorMock.CreateConnection(Mockito.anyString())).thenReturn(mElevatorMock);
    	
    	var buildingmock = new BuildingAdapter(mElevatorMock);
        var app = new ElevatorControl(new BuildingModel(buildingmock), new RemoteElevatorExceptionHandler("localhost",mConnectorMock));
        app.start(stage);
        controller = app.GetController();
    }
    
    @Test
    public void setTargetTriggersIElevator(FxRobot robot) throws RemoteException, InterruptedException {
    	//set into manual mode
    	robot.clickOn("#automaticModeCheckBox");
    	//sleep for doors to get open
    	Thread.sleep(500);
    	//click on upper floor of first elevator
    	robot.clickOn("#elevatorFloorHBox");
    	// which will lead to setting the target of elevator 0 to floor 3
    	Mockito.verify(mElevatorMock).setTarget(0, 3);
    }
    
    @Test
    public void remoteExceptionLeadsToReconnect(FxRobot robot) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
    	Mockito.doThrow(RemoteException.class).when(mElevatorMock).getFloorButtonUp(Mockito.anyInt());
    	
    	// wait for scheduler
    	Thread.sleep(2000);
    	
    	Mockito.verify(mConnectorMock).CreateConnection("localhost");
    }
    
    @Test
    public void remoteExceptionLeadsToErrorState(FxRobot robot) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
    	Mockito.doThrow(new RemoteException("localhost not available")).when(mElevatorMock).getFloorButtonUp(Mockito.anyInt());
    	
    	// wait for scheduler
    	Thread.sleep(2000);
    	
    	FxAssert.verifyThat("#messageTextArea", TextInputControlMatchers.hasText("localhost not available\n"));
    }
}