package at.fhhagenberg.sqe;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.mocks.BuildingMock;
import at.fhhagenberg.sqe.model.BuildingModel;
import javafx.stage.Stage;

public class ElevatorControlEndToEndExceptionTest {
    private ElevatorControlController controller;
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws ControlCenterException 
     */
    @Start
    public void start(Stage stage) throws ControlCenterException {
    	Locale locale = new Locale("en_GB");
    	Locale.setDefault(locale);
    	var buildingmock = new BuildingMock(5,2,2.0);
        var app = new ElevatorControl(new BuildingModel(buildingmock),new RemoteElevatorExceptionHandler("rmi://localhost/ElevatorSim", new ElevatorConnectorMock()));
        app.start(stage);
        controller = app.GetController();
        controller.GetElevator(0);  
    }

}
