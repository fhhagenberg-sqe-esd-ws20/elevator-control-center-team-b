package at.fhhagenberg.sqe;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import sqelevator.IElevator;

public interface IElevatorConnector {
	public IElevator createConnection(String uri) throws MalformedURLException, RemoteException, NotBoundException;
}
