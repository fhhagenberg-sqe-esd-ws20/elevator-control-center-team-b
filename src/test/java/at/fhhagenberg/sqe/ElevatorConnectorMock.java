package at.fhhagenberg.sqe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.mockito.Mockito;

import sqelevator.IElevator;

public class ElevatorConnectorMock implements IElevatorConnector{
	IElevator elevatorMock;

	@Override
	public IElevator createConnection(String uri) throws MalformedURLException, RemoteException, NotBoundException {
		elevatorMock = Mockito.mock(IElevator.class);
		
		Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getCommittedDirection(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorAccel(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorButton(0, 0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorCapacity(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorDoorStatus(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorFloor(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorNum()).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorPosition(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorSpeed(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getElevatorWeight(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getFloorButtonDown(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getFloorButtonUp(0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getFloorHeight()).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getFloorNum()).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getServicesFloors(0, 0)).thenThrow(new RemoteException());
		Mockito.when(elevatorMock.getTarget(0)).thenThrow(new RemoteException());	
		
		return (IElevator)Naming.lookup(uri);
	}

}
