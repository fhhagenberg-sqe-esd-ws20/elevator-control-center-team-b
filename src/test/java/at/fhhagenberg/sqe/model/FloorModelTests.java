package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IFloor;

public class FloorModelTests {

	
	@Test
	public void RunCallsAllGetters() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		
		var cut = new FloorModel(floorMock);
		Mockito.reset(floorMock);
		cut.run();
		
		Mockito.verify(floorMock).getFloorId();
		Mockito.verify(floorMock).isButtonUpPressed();
		Mockito.verify(floorMock).isButtonDownPressed();
	}
	
	@Test
	public void EventIsTriggeredIfIdChanges() throws ControlCenterException
	{
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		
		var floorMock = Mockito.mock(IFloor.class);
		Mockito.when(floorMock.getFloorId()).thenReturn(1).thenReturn(2);
		
		var cut = new FloorModel(floorMock);
		cut.addListener(listenerMock);
		cut.run();
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			return evt.getSource() == cut && evt.getPropertyName() == "Id" && evt.getNewValue().equals(2);
		}));
	}
	
	@Test
	public void EventIsTriggeredIfButtonUpChanges() throws ControlCenterException {
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		
		var floorMock = Mockito.mock(IFloor.class);
		Mockito.when(floorMock.isButtonUpPressed()).thenReturn(true);
		
		var cut = new FloorModel(floorMock);
		cut.addListener(listenerMock);
		cut.run();
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			return evt.getSource() == cut && evt.getPropertyName() == "ButtonUp" && evt.getNewValue().equals(true);
		}));
	}
	
	@Test
	public void EventIsTriggeredIfButtonDownChanges() throws ControlCenterException {
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		
		var floorMock = Mockito.mock(IFloor.class);
		Mockito.when(floorMock.isButtonDownPressed()).thenReturn(true);
		
		var cut = new FloorModel(floorMock);
		cut.addListener(listenerMock);
		cut.run();
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			return evt.getSource() == cut && evt.getPropertyName() == "ButtonDown" && evt.getNewValue().equals(true);
		}));
	}
	
	@Test
	public void GetFloorIdReturnsCorrectIdAfterRun() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		Mockito.when(floorMock.getFloorId()).thenReturn(1);
		
		var cut = new FloorModel(floorMock);
		
		cut.run();
		
		assertEquals(1,cut.getFloorId());
	}
	
	@Test
	public void IsButtonUpPressedReturnsCorrectAfterRun() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		Mockito.when(floorMock.isButtonUpPressed()).thenReturn(true);
		
		var cut = new FloorModel(floorMock);
		
		cut.run();
		
		assertTrue(cut.isButtonUpPressed());
	}
	
	@Test
	public void IsButtonDownPressedReturnsCorrectAfterRun() throws ControlCenterException {
		var floorMock = Mockito.mock(IFloor.class);
		Mockito.when(floorMock.isButtonDownPressed()).thenReturn(true);
		
		var cut = new FloorModel(floorMock);
		
		cut.run();
		assertTrue(cut.isButtonDownPressed());
	}
	
}
