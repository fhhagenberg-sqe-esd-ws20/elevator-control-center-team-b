package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;

public class AutomaticModeTest {

	@Test
	void testUpwards() {
		var automaticMode = new AutomaticMode(3);
		automaticMode.SetCurrentDirection(Direction.Up);
		automaticMode.SetCurrentFloor(0);
		automaticMode.CalculateNextTargetAndDirection();
		assertEquals(1, automaticMode.GetNextTarget());
		assertEquals(Direction.Up, automaticMode.GetNextDirection());
	}
	
	@Test
	void testDownwards() {
		var automaticMode = new AutomaticMode(3);
		automaticMode.SetCurrentDirection(Direction.Down);
		automaticMode.SetCurrentFloor(2);
		automaticMode.CalculateNextTargetAndDirection();
		assertEquals(1, automaticMode.GetNextTarget());
		assertEquals(Direction.Down, automaticMode.GetNextDirection());
	}
	
	@Test
	void testUpDown() {
		var automaticMode = new AutomaticMode(3);
		automaticMode.SetCurrentDirection(Direction.Up);
		automaticMode.SetCurrentFloor(0);
		automaticMode.CalculateNextTargetAndDirection();
		assertEquals(1, automaticMode.GetNextTarget());
		assertEquals(Direction.Up, automaticMode.GetNextDirection());
		
		automaticMode.SetCurrentFloor(1);
		automaticMode.CalculateNextTargetAndDirection();
		assertEquals(2, automaticMode.GetNextTarget());
		assertEquals(Direction.Up, automaticMode.GetNextDirection());
		
		automaticMode.SetCurrentFloor(2);
		automaticMode.CalculateNextTargetAndDirection();
		assertEquals(1, automaticMode.GetNextTarget());
		assertEquals(Direction.Down, automaticMode.GetNextDirection());
		
		automaticMode.SetCurrentFloor(1);
		automaticMode.CalculateNextTargetAndDirection();
		assertEquals(0, automaticMode.GetNextTarget());
		assertEquals(Direction.Down, automaticMode.GetNextDirection());
	}
	
	@Test
	void testConstructorException() {
		assertThrows(IllegalArgumentException.class, () -> { new AutomaticMode(0); });
	}
	
	@Test
	void testSetCurrentFloorNegativeException() {
		var automaticMode = new AutomaticMode(2);
		assertThrows(IllegalArgumentException.class, () -> { automaticMode.SetCurrentFloor(-1); });
	}
	
	@Test
	void testSetCurrentFloorGreaterFloorNumException() {
		var automaticMode = new AutomaticMode(2);
		assertThrows(IllegalArgumentException.class, () -> { automaticMode.SetCurrentFloor(10); });
	}
}
