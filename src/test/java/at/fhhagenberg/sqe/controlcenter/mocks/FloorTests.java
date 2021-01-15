package at.fhhagenberg.sqe.controlcenter.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FloorTests {

	
	@Test
	void GetFloorIdReturnsCorrectId() {
		var floor = new FloorMock(1);
		
		assertEquals(1,floor.getFloorId());
	}
	
	@Test
	void ButtonUpReturnsFalseInitial() {
		var floor = new FloorMock(1);
		assertFalse(floor.isButtonUpPressed());
	}
	
	@Test
	void ButtonDownReturnsFalseInitial() {
		var floor = new FloorMock(1);
		assertFalse(floor.isButtonDownPressed());
	}
	
	@Test
	void SetButtonUpPressedChangesState() 
	{
		var floor = new FloorMock(1);
		floor.setButtonUpPressed(true);
		assertTrue(floor.isButtonUpPressed());
	}
	
	@Test
	void SetButtonDownPressedChangesState() 
	{
		var floor = new FloorMock(1);
		floor.setButtonDownPressed(true);
		assertTrue(floor.isButtonDownPressed());
	}
}
