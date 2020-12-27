package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.Button;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ElevatorControlTest {
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
        // with controller floors and elevators can be accessed
        controller = app.GetController();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testButtonWithText(FxRobot robot) {
        FxAssert.verifyThat("#automaticModeToggleButton", LabeledMatchers.hasText("Automatic Mode"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testButtonClick(FxRobot robot) {
        robot.clickOn("#automaticModeToggleButton");
        FxAssert.verifyThat("#automaticModeToggleButton", LabeledMatchers.hasText("Automatic Mode"));
        assertTrue(controller.GetAutomaticModeActive());
    }
}