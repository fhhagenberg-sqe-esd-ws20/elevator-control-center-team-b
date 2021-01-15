package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;

class AutomaticModeTest {

	@Test
	void testUpwards() {
		var automaticMode = new AutomaticMode(3);
		automaticMode.setCurrentDirection(Direction.UP);
		automaticMode.setCurrentFloor(0);
		automaticMode.calculateNextTargetAndDirection();
		assertEquals(1, automaticMode.getNextTarget());
		assertEquals(Direction.UP, automaticMode.getNextDirection());
	}
	
	@Test
	void testDownwards() {
		var automaticMode = new AutomaticMode(3);
		automaticMode.setCurrentDirection(Direction.DOWN);
		automaticMode.setCurrentFloor(2);
		automaticMode.calculateNextTargetAndDirection();
		assertEquals(1, automaticMode.getNextTarget());
		assertEquals(Direction.DOWN, automaticMode.getNextDirection());
	}
	
	@Test
	void testUpDown() {
		var automaticMode = new AutomaticMode(3);
		automaticMode.setCurrentDirection(Direction.UP);
		automaticMode.setCurrentFloor(0);
		automaticMode.calculateNextTargetAndDirection();
		assertEquals(1, automaticMode.getNextTarget());
		assertEquals(Direction.UP, automaticMode.getNextDirection());
		
		automaticMode.setCurrentFloor(1);
		automaticMode.calculateNextTargetAndDirection();
		assertEquals(2, automaticMode.getNextTarget());
		assertEquals(Direction.UP, automaticMode.getNextDirection());
		
		automaticMode.setCurrentFloor(2);
		automaticMode.calculateNextTargetAndDirection();
		assertEquals(1, automaticMode.getNextTarget());
		assertEquals(Direction.DOWN, automaticMode.getNextDirection());
		
		automaticMode.setCurrentFloor(1);
		automaticMode.calculateNextTargetAndDirection();
		assertEquals(0, automaticMode.getNextTarget());
		assertEquals(Direction.DOWN, automaticMode.getNextDirection());
	}
	
	@Test
	void testConstructorException() {
		assertThrows(IllegalArgumentException.class, () -> { new AutomaticMode(0); });
	}
	
	@Test
	void testSetCurrentFloorNegativeException() {
		var automaticMode = new AutomaticMode(2);
		assertThrows(IllegalArgumentException.class, () -> { automaticMode.setCurrentFloor(-1); });
	}
	
	@Test
	void testSetCurrentFloorGreaterFloorNumException() {
		var automaticMode = new AutomaticMode(2);
		assertThrows(IllegalArgumentException.class, () -> { automaticMode.setCurrentFloor(10); });
	}
}
