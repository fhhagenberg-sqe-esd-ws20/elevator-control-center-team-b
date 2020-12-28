package at.fhhagenberg.sqe.controlcenter.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;

public class BuildingTests {

	@Test
	void GetHeightOfFloorReturnsCorrectHeight() {
		var building = new BuildingMock(2, 2, 2.2);
		assertEquals(2.2, building.getHeightOfFloor(), 0.1);
	}
	
	@Test 
	void GetNumberOfFloorsReturnsCorrectAmount() {
		var building = new BuildingMock(2,2,2.2);
		assertEquals(2,building.getNumberOfFloors());
	}
	
	@Test
	public void GetFloorReturnsNotNullWithId()
	{
		var building = new BuildingMock(2,2,2.2);
		assertNotNull(building.getFloor(0));
	}
	
	@Test
	public void GetFloorThrowsIfIfOutOfRange() {
		var building = new BuildingMock(2,2,2.2);
		assertThrows(IllegalArgumentException.class, () -> {
			building.getFloor(2);
	    });
	}
	
	@Test
	public void GetElevatorReturnsNotNullWithId()
	{
		var building = new BuildingMock(2,2,2.2);
		assertNotNull(building.getElevator(0));
	}
	
	@Test
	public void GetElevatorThrowsIfIfOutOfRange() {
		var building = new BuildingMock(2,2,2.2);
		assertThrows(IllegalArgumentException.class, () -> {
			building.getElevator(2);
	    });
	}
	
	@Test 
	void GetNumberOfElevatorsReturnsCorrectAmount() {
		var building = new BuildingMock(2,2,2.2);
		assertEquals(2,building.getNumberOfElevators());
	}
}
