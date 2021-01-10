package at.fhhagenberg.sqe.controlcenter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import sqelevator.IElevator;

public class RemoteFloor implements IFloor {

	
	private String mRemoteUri;
	private int mFloorId;
	private IElevator mRemote;
	
	
	public RemoteFloor(String remoteUri, int idx) throws MalformedURLException, RemoteException, NotBoundException {
		mRemoteUri = remoteUri;
		mFloorId = idx;
		mRemote = (IElevator)Naming.lookup(remoteUri);
	}
	
	@Override
	public int getFloorId() throws ControlCenterException {
		return mFloorId;
	}

	@Override
	public boolean isButtonUpPressed() throws ControlCenterException {
		try {
			return mRemote.getFloorButtonUp(mFloorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isButtonDownPressed() throws ControlCenterException {
		try {
			return mRemote.getFloorButtonDown(mFloorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
