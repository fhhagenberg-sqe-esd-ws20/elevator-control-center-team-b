package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;

import at.fhhagenberg.sqe.controlcenter.BuildingAdapter;
import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.controller.ElevatorControlController;
import at.fhhagenberg.sqe.exceptionhandler.RemoteElevatorExceptionHandler;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
class ElevatorControlEndToEndTest {
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
    	Mockito.when(mConnectorMock.createConnection(Mockito.anyString())).thenReturn(mElevatorMock);
    	
    	var buildingmock = new BuildingAdapter(mElevatorMock);
        var app = new ElevatorControl(new BuildingModel(buildingmock), new RemoteElevatorExceptionHandler("localhost",mConnectorMock));
        app.start(stage);
        controller = app.getController();
    }
    
    @Test
    void setTargetTriggersIElevator(FxRobot robot) throws RemoteException, InterruptedException, TimeoutException {
    	//set into manual mode
    	robot.clickOn("#automaticModeCheckBox");
    	//wait for doors to get open
    	WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#doorStatusLabel").queryAs(Label.class).getText().equals("open");
            }
        });
    	//click on upper floor of first elevator
    	robot.clickOn("#elevatorFloorHBox");
    	// which will lead to setting the target of elevator 0 to floor 3
    	Mockito.verify(mElevatorMock).setTarget(0, 3);
    }
    
    @Test
    void testSetFloorButtons(FxRobot robot) throws RemoteException, InterruptedException, TimeoutException {
    	Mockito.when(mElevatorMock.getFloorButtonDown(3)).thenReturn(true);
    	Mockito.when(mElevatorMock.getFloorButtonUp(3)).thenReturn(true);
    	//sleep for buttons to get pressed
    	WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#downArrowImageView").queryAs(ImageView.class).getOpacity() == 1.0;
            }
        });
    	//check floor buttons
    	ImageView floorButtonDown = robot.lookup("#downArrowImageView").query();
    	ImageView floorButtonUp = robot.lookup("#upArrowImageView").query();
    	assertEquals(1.0, floorButtonDown.getOpacity());
    	assertEquals(1.0, floorButtonUp.getOpacity());
    }
    
    @Test
    void testSetStopButton(FxRobot robot) throws RemoteException, InterruptedException, TimeoutException {
    	Mockito.when(mElevatorMock.getElevatorButton(0, 3)).thenReturn(true);
    	//sleep for button to get pressed
    	WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#stopImageView").queryAs(ImageView.class).getOpacity() == 1.0;
            }
        });
    	//check floor buttons
    	ImageView stopButton = robot.lookup("#stopImageView").query();
    	assertEquals(1.0, stopButton.getOpacity());
    }
    
    @Test
    void remoteExceptionLeadsToReconnect(FxRobot robot) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException, TimeoutException {
    	Mockito.doThrow(RemoteException.class).when(mElevatorMock).getFloorButtonUp(Mockito.anyInt());
    	
    	// wait for scheduler
    	Thread.sleep(2000);
    	
    	Mockito.verify(mConnectorMock).createConnection("localhost");
    }
    
    @Test
    void remoteExceptionLeadsToErrorState(FxRobot robot) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException, TimeoutException {
    	Mockito.doThrow(new RemoteException("localhost not available")).when(mElevatorMock).getFloorButtonUp(Mockito.anyInt());
    	
    	// wait for scheduler
    	WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return robot.lookup("#messageTextArea").queryAs(TextArea.class).getText().length() > 0;
            }
        });
    	
    	FxAssert.verifyThat("#messageTextArea", TextInputControlMatchers.hasText("localhost not available\n"));
    }
}