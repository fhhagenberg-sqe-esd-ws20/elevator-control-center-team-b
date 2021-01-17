package at.fhhagenberg.sqe.exceptionhandler;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import at.fhhagenberg.sqe.IElevatorConnector;
import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controller.ElevatorControlController;
import sqelevator.IElevator;

public class RemoteElevatorExceptionHandlerTest {

	
	@Test
	void callsCreateConnectionOnTryFix() throws MalformedURLException, RemoteException, NotBoundException {
		var mockConnectionCreator = Mockito.mock(IElevatorConnector.class);
		var mockController = Mockito.mock(ElevatorControlController.class);
		var elevatorMock = Mockito.mock(IElevator.class);
		Mockito.when(mockConnectionCreator.createConnection("localhost")).thenReturn(elevatorMock);
		var cut = new RemoteElevatorExceptionHandler("localhost",mockConnectionCreator);
		cut.setController(mockController);
		
		cut.run();
		
		Mockito.verify(mockConnectionCreator).createConnection("localhost");
	}
	
	@Test
	void callsSetErrorOnMalformedURLException() throws MalformedURLException, RemoteException, NotBoundException {
		var mockConnectionCreator = Mockito.mock(IElevatorConnector.class);
		var mockController = Mockito.mock(ElevatorControlController.class);
		
		Mockito.when(mockConnectionCreator.createConnection(Mockito.anyString())).thenThrow(MalformedURLException.class);
		var cut = new RemoteElevatorExceptionHandler("localhost",mockConnectionCreator);
		cut.setController(mockController);
		
		cut.run();
		
		Mockito.verify(mockController).setError(Mockito.anyString());
	}
	
	@Test
	void callsSetErrorOnRemoteException() throws MalformedURLException, RemoteException, NotBoundException {
		var mockConnectionCreator = Mockito.mock(IElevatorConnector.class);
		var mockController = Mockito.mock(ElevatorControlController.class);
		
		Mockito.when(mockConnectionCreator.createConnection(Mockito.anyString())).thenThrow(RemoteException.class);
		var cut = new RemoteElevatorExceptionHandler("localhost",mockConnectionCreator);
		cut.setController(mockController);
		
		cut.run();
		
		Mockito.verify(mockController).setError(Mockito.anyString());
	}
	
	@Test
	void callsSetErrorOnNotBoundException() throws MalformedURLException, RemoteException, NotBoundException {
		var mockConnectionCreator = Mockito.mock(IElevatorConnector.class);
		var mockController = Mockito.mock(ElevatorControlController.class);
		
		Mockito.when(mockConnectionCreator.createConnection(Mockito.anyString())).thenThrow(NotBoundException.class);
		var cut = new RemoteElevatorExceptionHandler("localhost",mockConnectionCreator);
		cut.setController(mockController);
		
		cut.run();
		
		Mockito.verify(mockController).setError(Mockito.anyString());
	}
	
	@Test
	void afterCorrectFixModelIsUpdated() throws MalformedURLException, RemoteException, NotBoundException, ControlCenterException {
		var mockConnectionCreator = Mockito.mock(IElevatorConnector.class);
		var mockController = Mockito.mock(ElevatorControlController.class);
		var elevatorMock = Mockito.mock(IElevator.class);
		Mockito.when(mockConnectionCreator.createConnection("localhost")).thenReturn(elevatorMock);
		var cut = new RemoteElevatorExceptionHandler("localhost",mockConnectionCreator);
		cut.setController(mockController);
		
		cut.run();
		
		Mockito.verify(mockController).updateModel(Mockito.any(IBuilding.class));
	}
	
}
