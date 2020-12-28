package at.fhhagenberg.sqe.controlcenter.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FloorTests {

	
	@Test
	public void GetFloorIdReturnsCorrectId() {
		var floor = new FloorMock(1);
		
		assertEquals(1,floor.getFloorId());
	}
	
	@Test
	public void ButtonUpReturnsFalseInitial() {
		var floor = new FloorMock(1);
		assertFalse(floor.isButtonUpPressed());
	}
	
	@Test
	public void ButtonDownReturnsFalseInitial() {
		var floor = new FloorMock(1);
		assertFalse(floor.isButtonDownPressed());
	}
	
	@Test
	public void SetButtonUpPressedChangesState() 
	{
		var floor = new FloorMock(1);
		floor.setButtonUpPressed(true);
		assertTrue(floor.isButtonUpPressed());
	}
	
	@Test
	public void SetButtonDownPressedChangesState() 
	{
		var floor = new FloorMock(1);
		floor.setButtonDownPressed(true);
		assertTrue(floor.isButtonDownPressed());
	}
}
