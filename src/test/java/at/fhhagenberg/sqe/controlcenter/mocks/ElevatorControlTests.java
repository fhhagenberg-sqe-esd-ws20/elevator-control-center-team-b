package at.fhhagenberg.sqe.controlcenter.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.IElevatorControl;

class ElevatorControlTests {

	
	@Test
	void WeightIsNotZero() {
		var elevator = new ElevatorControlMock(3);
		assertNotEquals(0, elevator.getWeight());
	}
	
	@Test
	void AccelerationIsZero() {
		var elevator = new ElevatorControlMock(3);
		assertEquals(0, elevator.getAcceleration());
	}
	
	@Test
	void DoorStatusIsOpen() {
		var elevator = new ElevatorControlMock(2);
		assertEquals(IElevatorControl.DoorStatus.OPEN, elevator.getCurrentDoorStatus());
	}
	
	@Test
	void InitialDirectionIsUncommited()
	{
		var elevator = new ElevatorControlMock(2);
		assertEquals(IElevatorControl.Direction.UNCOMMITTED, elevator.getCurrentDirection());
	}
	
	@Test
	void SetDirectionChangesDirection()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setDirection(IElevatorControl.Direction.UP);
		assertEquals(IElevatorControl.Direction.UP, elevator.getCurrentDirection());
	}
	
	@Test
	void PressedFloorButtonsListIsEmpty()
	{
		var elevator = new ElevatorControlMock(2);
		assertTrue(elevator.getPressedFloorButtons().isEmpty());
	}
	
	@Test
	void IsPressedFloorButtonReturnsFalse()
	{
		var elevator = new ElevatorControlMock(2);
		assertFalse(elevator.isFloorButtonPressed(0));
	}
	
	@Test
	void CurrentFloorIs0Initially()
	{
		var elevator = new ElevatorControlMock(2);
		assertEquals(0,elevator.getCurrentFloor());
	}
	
	@Test
	void TargetFloorIs0Initially()
	{
		var elevator = new ElevatorControlMock(2);
		assertEquals(0,elevator.getTarget());
	}
	
	@Test
	void SetTargetChangesTarget()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setTarget(1);
		assertEquals(1,elevator.getTarget());
	}
	
	@Test
	void TargetOutOfRangeThrowsException()
	{
		var elevator = new ElevatorControlMock(2);
		assertThrows(IllegalArgumentException.class, () -> {
			elevator.setTarget(3);
	    });
	}
	
	@Test
	void ServicedFloorsAreAllInitially()
	{
		var elevator = new ElevatorControlMock(2);
		var expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		assertEquals(expected,elevator.getServicedFloors());
	}
	
	@Test
	void IsServicedReturnsTrueIfFloorIsServiced()
	{
		var elevator = new ElevatorControlMock(2);
		assertTrue(elevator.isServiced(0));
	}
	
	@Test
	void IsServicedReturnsFalseIfFloorIsNotAvailable()
	{
		var elevator = new ElevatorControlMock(2);
		assertFalse(elevator.isServiced(2));
	}
	
	@Test
	void IsServicedReturnsFalseIfFloorIsNotServiced()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setServiceFloor(0, false);
		assertFalse(elevator.isServiced(0));
	}
	
	@Test
	void SetNotServicedChangesServicedList()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setServiceFloor(0, false);
		var expected = new ArrayList<Integer>();
		expected.add(1);
		assertEquals(expected,elevator.getServicedFloors());
	}
	
	@Test
	void SetServicedChangesServicedList()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setServiceFloor(0, false);
		elevator.setServiceFloor(0, true);
		var expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		assertEquals(expected,elevator.getServicedFloors());
	}
	
	@Test
	void SetServicedThrowsWhenOutOfRange()
	{
		var elevator = new ElevatorControlMock(2);
		assertThrows(IllegalArgumentException.class, () -> {
			elevator.setServiceFloor(3, false);
	    });
	}
	
	@Test
	void CurrentFloorChangesWithSecondCallOnGetCurrentFloor()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setTarget(1);
		assertEquals(0,elevator.getCurrentFloor());
		assertEquals(1,elevator.getCurrentFloor());
	}
	
	@Test
	void DoesReturnCorrectNumberOfFloors() {
		var elevator = new ElevatorControlMock(2);
		assertEquals(2, elevator.getFloorNum());
	}
	
}
