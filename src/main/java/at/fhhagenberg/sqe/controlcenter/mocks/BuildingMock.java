package at.fhhagenberg.sqe.controlcenter.mocks;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;
import at.fhhagenberg.sqe.controlcenter.IFloor;

public class BuildingMock implements IBuilding {

	private List<IFloor> mFloors;
	private List<IElevatorControl> mElevators;
	private final double mFloorHeight;
	
	public BuildingMock(int floors, int elevators, double floorHeight) {
		mFloors = new ArrayList<IFloor>();
		for(int i = 0; i < floors; i++) {
			mFloors.add(new FloorMock(i));
		}
		mElevators = new ArrayList<IElevatorControl>();
		for(int i = 0; i < elevators; i++) {
			mElevators.add(new ElevatorControlMock(floors));
		}
		mFloorHeight = floorHeight;
	}
	
	@Override
	public int getNumberOfFloors() {
		return mFloors.size();
	}

	@Override
	public IFloor getFloor(int id) throws IllegalArgumentException {
		if(id >= mFloors.size()) {
			throw new IllegalArgumentException("FloorId is out of range");
		}
		return mFloors.get(id);
	}

	@Override
	public double getHeightOfFloor() {
		return mFloorHeight;
	}

	@Override
	public int getNumberOfElevators() {
		return mElevators.size();
	}

	@Override
	public IElevatorControl getElevator(int id) throws IllegalArgumentException {
		if(id >= mElevators.size()) {
			throw new IllegalArgumentException("ElevatorId is out of range");
		}
		return mElevators.get(id);
	}

}
