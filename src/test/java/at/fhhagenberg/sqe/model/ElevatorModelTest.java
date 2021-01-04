package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;

public class ElevatorModelTest {

	
	@Test
	public void RunCallsAllGetters() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		
		var cut = new ElevatorModel(elevatorMock);
		Mockito.reset(elevatorMock);
		cut.run();
		
		Mockito.verify(elevatorMock).getAcceleration();
		Mockito.verify(elevatorMock).getFloorNum();
		Mockito.verify(elevatorMock).getCurrentDirection();
		Mockito.verify(elevatorMock).getCurrentDoorStatus();
		Mockito.verify(elevatorMock).getPressedFloorButtons();
		Mockito.verify(elevatorMock).getServicedFloors();
		Mockito.verify(elevatorMock).getCurrentFloor();
		Mockito.verify(elevatorMock).getSpeed();
		Mockito.verify(elevatorMock).getAcceleration();
		Mockito.verify(elevatorMock).getWeight();
		Mockito.verify(elevatorMock).getTarget();
	}
	
	@Test
	public void SetTargetCallsImplementation() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2);
		
		
		var cut = new ElevatorModel(elevatorMock);
		
		cut.run();
		// ignore calls mock calls of run method
		Mockito.reset(elevatorMock);
		
		
		cut.setTarget(1);
		Mockito.verify(elevatorMock, Mockito.times(1)).setTarget(1);
	}
	
	@Test
	public void SetTargetDoesTriggerEvent() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2);
		
		var cut = new ElevatorModel(elevatorMock);
		cut.run();
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		
		cut.setTarget(1);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("Target") && (int)evt.getNewValue() == 1;
		}));
	}
	
	@Test
	public void SetTargetDoesNotUpdatePropertyIfExceptionOccurs() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2);
		
		var cut = new ElevatorModel(elevatorMock);
		cut.run();
		// ignore calls mock calls of run method
		Mockito.reset(elevatorMock);
		
		Mockito.doThrow(ControlCenterException.class).when(elevatorMock).setTarget(Mockito.anyInt());
		
		assertThrows(ControlCenterException.class, () -> {
			cut.setTarget(1);
		});
		
		Mockito.verify(elevatorMock, Mockito.times(1)).setTarget(1);
		assertEquals(0,cut.getTarget());
	}
	
	@Test
	public void SetDirectionCallsImplementation() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		
		var cut = new ElevatorModel(elevatorMock);
		
		cut.setDirection(IElevatorControl.Direction.Up);
		
		Mockito.verify(elevatorMock).setDirection(IElevatorControl.Direction.Up);
	}
	
	@Test
	public void SetDirectionDoesTriggerEvent() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		
		var cut = new ElevatorModel(elevatorMock);
		cut.addListener(listenerMock);
		
		cut.setDirection(IElevatorControl.Direction.Up);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("Direction") && evt.getNewValue().equals(IElevatorControl.Direction.Up);
		}));
	}
	
	@Test
	public void SetDirectionDoesNotUpdatePropertyIfExceptionOccurs() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.doThrow(ControlCenterException.class).when(elevatorMock).setDirection(Mockito.any());
		
		var cut = new ElevatorModel(elevatorMock);
		
		
		assertThrows(ControlCenterException.class, () -> {
			cut.setDirection(IElevatorControl.Direction.Up);
		});
		
		Mockito.verify(elevatorMock, Mockito.times(1)).setDirection(IElevatorControl.Direction.Up);
		assertEquals(IElevatorControl.Direction.Uncommited,cut.getCurrentDirection());
	}
	
	@Test
	public void SetSerivceFloorRemovesFloorIfSetToFalse() throws ControlCenterException {
		var servicedList = new ArrayList<Integer>();
		servicedList.add(0);
		servicedList.add(1);
		
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2);
		Mockito.when(elevatorMock.getServicedFloors()).thenReturn(servicedList);
		
		var cut = new ElevatorModel(elevatorMock);
		cut.run();
		// ignore calls mock calls of run method
		Mockito.reset(elevatorMock);
		
		
		cut.setServiceFloor(0, false);
		
		
		var expectedList = new ArrayList<Integer>();
		expectedList.add(1);
		
		assertEquals(expectedList,cut.getServicedFloors());
	}
	
	@Test
	public void SetSerivceFloorAddsFloorIfSetToTrue() throws ControlCenterException {
		var servicedList = new ArrayList<Integer>();
		servicedList.add(1);
		
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2);
		Mockito.when(elevatorMock.getServicedFloors()).thenReturn(servicedList);
		
		var cut = new ElevatorModel(elevatorMock);
		cut.run();
		// ignore calls mock calls of run method
		Mockito.reset(elevatorMock);
		
		
		cut.setServiceFloor(0, true);
		
		
		var expectedList = new ArrayList<Integer>();
		expectedList.add(0);
		expectedList.add(1);
		
		assertEquals(expectedList,cut.getServicedFloors());
	}
	
	@Test
	public void SetServiceFloorTriggersEvent() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2);
		
		var cut = new ElevatorModel(elevatorMock);
		cut.run();
		// ignore calls mock calls of run method
		Mockito.reset(elevatorMock);
		
		cut.addListener(listenerMock);
		
		
		cut.setServiceFloor(1, true);
		
		var expectedNewList = new ArrayList<Integer>();
		expectedNewList.add(1);
		
		var expectedOldList = new ArrayList<Integer>();
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("ServicedFloors") && 
					evt.getNewValue().equals(expectedNewList) && evt.getOldValue().equals(expectedOldList);
		}));
	}
	
	@Test
	public void SetServiceTriggersImplementation() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2);
		
		var cut = new ElevatorModel(elevatorMock);
		cut.run();
		// ignore calls mock calls of run method
		Mockito.reset(elevatorMock);
		
		cut.setServiceFloor(1, true);
		
		Mockito.verify(elevatorMock).setServiceFloor(1, true);
	}
	
	@Test
	public void RunTriggersEventIfFloorNumChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(2).thenReturn(3);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("FloorNum") && evt.getNewValue().equals(3);
		}));
		
	}
	
	@Test
	public void RunTriggersEventIfDirectionChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getCurrentDirection()).thenReturn(IElevatorControl.Direction.Up);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("Direction") && evt.getNewValue().equals(IElevatorControl.Direction.Up);
		}));
		
	}
	
	@Test
	public void RunTriggersEventIfDoorStatusChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getCurrentDoorStatus()).thenReturn(IElevatorControl.DoorStatus.Open);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("DoorStatus") && evt.getNewValue().equals(IElevatorControl.DoorStatus.Open);
		}));
		
	}
	
	@Test
	public void RunTriggersEventIfPressedFloorStatusChanges() throws ControlCenterException {
		var pressedButtons = new ArrayList<Integer>();
		pressedButtons.add(1);
		
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getPressedFloorButtons()).thenReturn(pressedButtons);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("PressedFloorButtons") && evt.getNewValue().equals(pressedButtons);
		}));
		
	}
	
	@Test
	public void RunTriggersEventIfServicedFloorsChanges() throws ControlCenterException {
		var servicedFloors = new ArrayList<Integer>();
		servicedFloors.add(1);
		
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getServicedFloors()).thenReturn(servicedFloors);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("ServicedFloors") && evt.getNewValue().equals(servicedFloors);
		}));
		
	}
	
	@Test
	public void RunTriggersEventIfCurrentFloorChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getCurrentFloor()).thenReturn(1);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("CurrentFloor") && evt.getNewValue().equals(1);
		}));
		
	}
	
	@Test
	public void RunTriggersEventIfSpeedChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getSpeed()).thenReturn(2.0);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("Speed") && evt.getNewValue().equals(2.0);
		}));
		
	}
	
	@Test
	public void RunTriggersEventAccelerationChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getAcceleration()).thenReturn(2.0);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("Acceleration") && evt.getNewValue().equals(2.0);
		}));
		
	}
	
	@Test
	public void RunTriggersEventWeightChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getWeight()).thenReturn(2.0);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("Weight") && evt.getNewValue().equals(2.0);
		}));
		
	}
	
	@Test
	public void RunTriggersEventTargetChanges() throws ControlCenterException {
		var elevatorMock = Mockito.mock(IElevatorControl.class, Mockito.CALLS_REAL_METHODS);
		Mockito.when(elevatorMock.getTarget()).thenReturn(1);
		
		var cut = new ElevatorModel(elevatorMock);
		
		var listenerMock = Mockito.mock(PropertyChangeListener.class);
		cut.addListener(listenerMock);
		cut.run();
		
		// ignore this mock
		Mockito.reset(elevatorMock);
		
		Mockito.verify(listenerMock).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("Target") && evt.getNewValue().equals(1);
		}));
		
	}
	
}
