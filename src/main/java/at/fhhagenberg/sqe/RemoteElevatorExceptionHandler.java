package at.fhhagenberg.sqe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.fhhagenberg.sqe.controlcenter.BuildingAdapter;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import sqelevator.IElevator;

public class RemoteElevatorExceptionHandler extends ElevatorExceptionHandler {

	private String mRemote;
	private BuildingAdapter mBuilding;
	private IElevatorConnector mConnector;
	
	public RemoteElevatorExceptionHandler(String remoteUri, IElevatorConnector connector) {
		mConnector = connector;
		mRemote = remoteUri;
	}
	
	@Override
	protected boolean tryFix() {
		try {
			var remoteElevator = mConnector.CreateConnection("rmi://localhost/ElevatorSim");
			mBuilding = new BuildingAdapter(remoteElevator);
			return true;
		} catch (MalformedURLException e) {
			mController.setError("Invalid Uri " + mRemote + " was set.");
			//e.printStackTrace();
		} catch (RemoteException e) {
			mController.setError("Could not connect to: " + mRemote);
			//e.printStackTrace();
		} catch (NotBoundException e) {
			mController.setError("Could not bind to: " + mRemote);
			//e.printStackTrace();
		}
		return false;
	}

	@Override
	protected IBuilding getBuilding() {
		return mBuilding;
	}

}
