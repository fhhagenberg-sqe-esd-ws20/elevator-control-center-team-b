package at.fhhagenberg.sqe.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;
import at.fhhagenberg.sqe.controlcenter.IFloor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BuildingModel extends AsyncModel {

	private IBuilding mBuilding;
	private ObservableList<ElevatorModel> mElevators;
	private ObservableList<FloorModel> mFloors;
	private static String EXCEPTION = "Exception";
	
	
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
		addExceptionProperty();
	}
	
	public void updateBuilding(IBuilding building) throws ControlCenterException {
		
		var floors = mBuilding.getNumberOfFloors();
		var elevators = mBuilding.getNumberOfElevators();
		if(floors != mFloors.size()) {
			throw new ControlCenterException("New building has a new amount of floors.");
		}
		if(elevators != mElevators.size()) {
			throw new ControlCenterException("New building has a new amount of evelators.");
		}
		
		// verify first if all floors and elevators are reachable
		var newFloorList = new ArrayList<IFloor>();
		var newElevatorList = new ArrayList<IElevatorControl>();
		
		for(int i = 0; i < floors; i++) {
			newFloorList.add(building.getFloor(i));
		}
		
		for(int i = 0; i < elevators; i++) {
			newElevatorList.add(mBuilding.getElevator(i));
		}
		
		for(int i = 0; i < floors; i++) {
			mFloors.get(i).updateFloor(newFloorList.get(i));
		}
		
		for(int i = 0; i < elevators; i++) {
			mElevators.get(i).updateElevator(newElevatorList.get(i));
		}
		
		mBuilding = building;
	}
	
	private void addExceptionProperty() {
		var me = this;
		for(FloorModel model : mFloors) {
			model.addListener(new PropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals(EXCEPTION)) {
						me.setProperty(EXCEPTION, evt.getNewValue());
					}
					
				}
				
			});
		}
		
		for(ElevatorModel model : mElevators) {
			model.addListener(new PropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals(EXCEPTION)) {
						me.setProperty(EXCEPTION, evt.getNewValue());
					}
					
				}
				
			});
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
