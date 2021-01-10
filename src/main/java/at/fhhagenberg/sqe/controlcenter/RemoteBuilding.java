package at.fhhagenberg.sqe.controlcenter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import sqelevator.IElevator;

public class RemoteBuilding implements IBuilding {

	private String mRemoteUri;
	private IElevator mRemote;
	
	private List<RemoteFloor> mFloors;
	private List<RemoteElevatorControl> mElevators;
	
	public RemoteBuilding(String remoteUri) throws MalformedURLException, RemoteException, NotBoundException {
		mRemoteUri = remoteUri;
		mRemote = (IElevator)Naming.lookup(mRemoteUri);
		
		mFloors = new ArrayList<RemoteFloor>();
		mElevators = new ArrayList<RemoteElevatorControl>();
		
		int floorNum = mRemote.getFloorNum();
		for(int i = 0; i < floorNum; i++) {
			mFloors.add(new RemoteFloor(remoteUri,i));
		}
		
		int elevatorNum = mRemote.getElevatorNum();
		for(int i = 0; i < elevatorNum; i++) {
			mElevators.add(new RemoteElevatorControl(remoteUri,i,floorNum));
		}
	}
	
	@Override
	public int getNumberOfFloors() throws ControlCenterException {
		return mFloors.size();
	}

	@Override
	public IFloor getFloor(int id) throws ControlCenterException {
		if(id >= mFloors.size()) {
			throw new ControlCenterException(new IllegalArgumentException("Floor not available"));
		}
		return mFloors.get(id);
	}

	@Override
	public double getHeightOfFloor() throws ControlCenterException {
		try {
			return mRemote.getFloorHeight();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int getNumberOfElevators() throws ControlCenterException {
		return mElevators.size();
	}

	@Override
	public IElevatorControl getElevator(int id) throws ControlCenterException {
		if(id >= mElevators.size()) {
			throw new ControlCenterException(new IllegalArgumentException("Elevator not available"));
		}
		return mElevators.get(id);
	}

}
