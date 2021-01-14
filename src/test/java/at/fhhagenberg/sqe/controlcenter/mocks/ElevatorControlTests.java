package at.fhhagenberg.sqe.controlcenter.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.IElevatorControl;

public class ElevatorControlTests {

	
	@Test
	public void WeightIsNotZero() {
		var elevator = new ElevatorControlMock(3);
		assertNotEquals(0, elevator.getWeight());
	}
	
	@Test
	public void AccelerationIsZero() {
		var elevator = new ElevatorControlMock(3);
		assertEquals(0, elevator.getAcceleration());
	}
	
	@Test
	public void DoorStatusIsOpen() {
		var elevator = new ElevatorControlMock(2);
		assertEquals(IElevatorControl.DoorStatus.Open, elevator.getCurrentDoorStatus());
	}
	
	@Test
	public void InitialDirectionIsUncommited()
	{
		var elevator = new ElevatorControlMock(2);
		assertEquals(IElevatorControl.Direction.Uncommited, elevator.getCurrentDirection());
	}
	
	@Test
	public void SetDirectionChangesDirection()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setDirection(IElevatorControl.Direction.Up);
		assertEquals(IElevatorControl.Direction.Up, elevator.getCurrentDirection());
	}
	
	@Test
	public void PressedFloorButtonsListIsEmpty()
	{
		var elevator = new ElevatorControlMock(2);
		assertTrue(elevator.getPressedFloorButtons().isEmpty());
	}
	
	@Test
	public void IsPressedFloorButtonReturnsFalse()
	{
		var elevator = new ElevatorControlMock(2);
		assertFalse(elevator.isFloorButtonPressed(0));
	}
	
	@Test
	public void CurrentFloorIs0Initially()
	{
		var elevator = new ElevatorControlMock(2);
		assertEquals(0,elevator.getCurrentFloor());
	}
	
	@Test
	public void TargetFloorIs0Initially()
	{
		var elevator = new ElevatorControlMock(2);
		assertEquals(0,elevator.getTarget());
	}
	
	@Test
	public void SetTargetChangesTarget()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setTarget(1);
		assertEquals(1,elevator.getTarget());
	}
	
	@Test
	public void TargetOutOfRangeThrowsException()
	{
		var elevator = new ElevatorControlMock(2);
		assertThrows(IllegalArgumentException.class, () -> {
			elevator.setTarget(3);
	    });
	}
	
	@Test
	public void ServicedFloorsAreAllInitially()
	{
		var elevator = new ElevatorControlMock(2);
		var expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		assertEquals(expected,elevator.getServicedFloors());
	}
	
	@Test
	public void IsServicedReturnsTrueIfFloorIsServiced()
	{
		var elevator = new ElevatorControlMock(2);
		assertTrue(elevator.isServiced(0));
	}
	
	@Test
	public void IsServicedReturnsFalseIfFloorIsNotAvailable()
	{
		var elevator = new ElevatorControlMock(2);
		assertFalse(elevator.isServiced(2));
	}
	
	@Test
	public void IsServicedReturnsFalseIfFloorIsNotServiced()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setServiceFloor(0, false);
		assertFalse(elevator.isServiced(0));
	}
	
	@Test
	public void SetNotServicedChangesServicedList()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setServiceFloor(0, false);
		var expected = new ArrayList<Integer>();
		expected.add(1);
		assertEquals(expected,elevator.getServicedFloors());
	}
	
	@Test
	public void SetServicedChangesServicedList()
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
	public void SetServicedThrowsWhenOutOfRange()
	{
		var elevator = new ElevatorControlMock(2);
		assertThrows(IllegalArgumentException.class, () -> {
			elevator.setServiceFloor(3, false);
	    });
	}
	
	@Test
	public void CurrentFloorChangesWithSecondCallOnGetCurrentFloor()
	{
		var elevator = new ElevatorControlMock(2);
		elevator.setTarget(1);
		assertEquals(0,elevator.getCurrentFloor());
		assertEquals(1,elevator.getCurrentFloor());
	}
	
	@Test
	public void DoesReturnCorrectNumberOfFloors() {
		var elevator = new ElevatorControlMock(2);
		assertEquals(2, elevator.getFloorNum());
	}
	
}
