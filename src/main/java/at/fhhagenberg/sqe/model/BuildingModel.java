package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BuildingModel extends AsyncModel {

	private IBuilding mBuilding;
	private ObservableList<ElevatorModel> mElevators;
	private ObservableList<FloorModel> mFloors;
	
	
	public BuildingModel(IBuilding building) throws ControlCenterException {
		mBuilding = building;
		var floors = mBuilding.getNumberOfFloors();
		var elevators = mBuilding.getNumberOfElevators();
		mElevators = FXCollections.observableArrayList();
		mFloors = FXCollections.observableArrayList();
		for(int i = 0; i < floors; i++) {
			mFloors.add(new FloorModel(mBuilding.getFloor(i)));
		}
		
		for(int i = 0; i < elevators; i++) {
			mElevators.add(new ElevatorModel(mBuilding.getElevator(i)));
		}
	}
	
	/*
	 * Returns the amount of available elevators.
	 */
	public int getElevatorNum() {
		return mElevators.size();
	}
	
	/*
	 * Returns the specified elevator model
	 */
	public ElevatorModel getElevator(int id) {
		if(id >= mElevators.size() || id < 0) throw new IllegalArgumentException("Elevator " + id + " is not available");
		return mElevators.get(id);
	}
	
	/*
	 * Returns the amount of available floors
	 */
	public int getFloorNum() {
		return mFloors.size();
	}
	
	/*
	 * returns the specified floor model
	 */
	public FloorModel getFloor(int id) {
		if(id >= mFloors.size() || id < 0) throw new IllegalArgumentException("Floor " + id + " is not available");
		return mFloors.get(id);
	}
	
	@Override
	public void run() {
		for(FloorModel model : mFloors) {
			model.run();
		}
		
		for(ElevatorModel model : mElevators) {
			model.run();
		}
	}
 
}