package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;
import at.fhhagenberg.sqe.controlcenter.IFloor;

public class BuildingModelTest {

	
	
	
	@Test
	public void CreatesCorrectAmountOfElevatorModels() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		var elevatorMock = Mockito.mock(IElevatorControl.class);
		
		var buildingMock = Mockito.mock(IBuilding.class);
		Mockito.when(buildingMock.getNumberOfElevators()).thenReturn(2);
		Mockito.when(buildingMock.getNumberOfFloors()).thenReturn(3);
		Mockito.when(buildingMock.getFloor(Mockito.anyInt())).thenReturn(floorMock);
		Mockito.when(buildingMock.getElevator(Mockito.anyInt())).thenReturn(elevatorMock);
		
		var cut = new BuildingModel(buildingMock);
		
		assertEquals(2, cut.getElevatorNum());
		assertEquals(3, cut.getFloorNum());
	}
	
	@Test
	public void RunCallsEveryModel() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		var elevatorMock = Mockito.mock(IElevatorControl.class);
		
		var buildingMock = Mockito.mock(IBuilding.class);
		Mockito.when(buildingMock.getNumberOfElevators()).thenReturn(2);
		Mockito.when(buildingMock.getNumberOfFloors()).thenReturn(3);
		Mockito.when(buildingMock.getFloor(Mockito.anyInt())).thenReturn(floorMock);
		Mockito.when(buildingMock.getElevator(Mockito.anyInt())).thenReturn(elevatorMock);
		
		var cut = new BuildingModel(buildingMock);
		
		cut.run();
		
		Mockito.verify(floorMock, Mockito.times(3)).getFloorId();
		Mockito.verify(elevatorMock, Mockito.times(2)).getAcceleration();
	}
	
	@Test
	public void GetElevatorThrowsExceptionIfOutOfRange() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		var elevatorMock = Mockito.mock(IElevatorControl.class);
		
		var buildingMock = Mockito.mock(IBuilding.class);
		Mockito.when(buildingMock.getNumberOfElevators()).thenReturn(1);
		Mockito.when(buildingMock.getNumberOfFloors()).thenReturn(1);
		Mockito.when(buildingMock.getFloor(Mockito.anyInt())).thenReturn(floorMock);
		Mockito.when(buildingMock.getElevator(Mockito.anyInt())).thenReturn(elevatorMock);
		
		var cut = new BuildingModel(buildingMock);
		
		assertThrows(IllegalArgumentException.class, () -> {
			cut.getElevator(1);
		});
	}
	
	@Test
	public void GetFloorThrowsExceptionIfOutOfRange() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		var elevatorMock = Mockito.mock(IElevatorControl.class);
		
		var buildingMock = Mockito.mock(IBuilding.class);
		Mockito.when(buildingMock.getNumberOfElevators()).thenReturn(1);
		Mockito.when(buildingMock.getNumberOfFloors()).thenReturn(1);
		Mockito.when(buildingMock.getFloor(Mockito.anyInt())).thenReturn(floorMock);
		Mockito.when(buildingMock.getElevator(Mockito.anyInt())).thenReturn(elevatorMock);
		
		var cut = new BuildingModel(buildingMock);
		
		assertThrows(IllegalArgumentException.class, () -> {
			cut.getFloor(1);
		});
	}
	
	@Test
	public void GetElevatorThrowsExceptionIfNegative() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		var elevatorMock = Mockito.mock(IElevatorControl.class);
		
		var buildingMock = Mockito.mock(IBuilding.class);
		Mockito.when(buildingMock.getNumberOfElevators()).thenReturn(1);
		Mockito.when(buildingMock.getNumberOfFloors()).thenReturn(1);
		Mockito.when(buildingMock.getFloor(Mockito.anyInt())).thenReturn(floorMock);
		Mockito.when(buildingMock.getElevator(Mockito.anyInt())).thenReturn(elevatorMock);
		
		var cut = new BuildingModel(buildingMock);
		
		assertThrows(IllegalArgumentException.class, () -> {
			cut.getElevator(-1);
		});
	}
	
	@Test
	public void GetFloorThrowsExceptionIfNegative() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		var elevatorMock = Mockito.mock(IElevatorControl.class);
		
		var buildingMock = Mockito.mock(IBuilding.class);
		Mockito.when(buildingMock.getNumberOfElevators()).thenReturn(1);
		Mockito.when(buildingMock.getNumberOfFloors()).thenReturn(1);
		Mockito.when(buildingMock.getFloor(Mockito.anyInt())).thenReturn(floorMock);
		Mockito.when(buildingMock.getElevator(Mockito.anyInt())).thenReturn(elevatorMock);
		
		var cut = new BuildingModel(buildingMock);
		
		assertThrows(IllegalArgumentException.class, () -> {
			cut.getFloor(-1);
		});
	}
}
