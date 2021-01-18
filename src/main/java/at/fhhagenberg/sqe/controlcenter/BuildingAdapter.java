package at.fhhagenberg.sqe.controlcenter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import sqelevator.IElevator;

public class BuildingAdapter implements IBuilding {

	private IElevator mElevator;
	
	private List<FloorAdapter> mFloors;
	private List<ElevatorAdapter> mElevators;
	
	private SyncVerifier mVerifier;
	
	public BuildingAdapter(IElevator elevator) throws RemoteException, ControlCenterException {
		mElevator = elevator;
		
		mFloors = new ArrayList<>();
		mElevators = new ArrayList<>();
		
		try {
			mVerifier = new SyncVerifier(elevator);
		}
		catch(RemoteException e) {
			throw new ControlCenterException(e);
		}
		
		int floorNum = mElevator.getFloorNum();
		for(int i = 0; i < floorNum; i++) {
			mFloors.add(new FloorAdapter(mElevator,i));
		}
		
		int elevatorNum = mElevator.getElevatorNum();
		for(int i = 0; i < elevatorNum; i++) {
			mElevators.add(new ElevatorAdapter(mElevator,i,floorNum));
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
			mVerifier.checkSync();
			return mElevator.getFloorHeight();
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
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
