package at.fhhagenberg.sqe.controlcenter.mocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;

public class ElevatorControlMock implements IElevatorControl {
	
	private Direction mDirection;
	private int mFloors;
	
	private List<Integer> mServicedFloors;
	private int mCurrentFloor;
	private int mTarget;
	
	public ElevatorControlMock(int floors) {
		mDirection = Direction.Uncommited;
		mFloors = floors;
		
		mServicedFloors = new ArrayList<Integer>();
		for(int i = 0; i < mFloors; i++) {
			mServicedFloors.add(i);
		}
		mCurrentFloor = 0;
		mTarget = 0;
	}
	
	@Override
	public DoorStatus getCurrentDoorStatus() {
		return DoorStatus.Closed;
	}

	@Override
	public Direction getCurrentDirection() {
		return mDirection;
	}

	@Override
	public double getAcceleration() {
		return 0;
	}

	@Override
	public List<Integer> getPressedFloorButtons() {
		return new ArrayList<Integer>();
	}

	@Override
	public boolean isFloorButtonPressed(int floor) {
		return false;
	}

	@Override
	public int getCurrentFloor() {
		var ret = mCurrentFloor;
		if(mCurrentFloor != mTarget) {
			mCurrentFloor = mTarget;
		}
		return ret;
	}

	@Override
	public double getSpeed() {
		if(mCurrentFloor != mTarget) {
			return 0.5;
		}
		return 0;
	}

	@Override
	public double getWeight() {
		return 123.45;
	}

	@Override
	public int getTarget() {
		return mTarget;
	}

	@Override
	public void setTarget(int floor) throws IllegalArgumentException{
		if(floor >= mFloors || floor < 0) throw new IllegalArgumentException("target is out of range.");
		mTarget = floor;
	}

	@Override
	public List<Integer> getServicedFloors() {
		Collections.sort(mServicedFloors);
		return mServicedFloors;
	}

	@Override
	public boolean isServiced(int floor) {
		return mServicedFloors.contains(floor);
	}

	@Override
	public void setServiceFloor(int floor, boolean service) throws IllegalArgumentException {
		if(floor >= mFloors || floor < 0) throw new IllegalArgumentException("Floor is not available.");
		if(service && !mServicedFloors.contains(floor)) {
			mServicedFloors.add(floor);
		}
		if(!service && mServicedFloors.contains(floor)) {
			mServicedFloors.remove((Object)floor);
		}
		
	}

	@Override
	public void setDirection(Direction dir) {
		mDirection = dir;
	}

}
