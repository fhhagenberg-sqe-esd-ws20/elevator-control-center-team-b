package at.fhhagenberg.sqe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import sqelevator.IElevator;

public class ElevatorConnector implements IElevatorConnector {

	@Override
	public IElevator CreateConnection(String uri) throws MalformedURLException, RemoteException, NotBoundException {
		 return (IElevator)Naming.lookup(uri);
	}

}
