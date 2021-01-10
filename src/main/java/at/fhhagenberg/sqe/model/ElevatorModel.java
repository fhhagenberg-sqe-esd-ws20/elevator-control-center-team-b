package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;

public class ElevatorModel extends AsyncModel implements IElevatorControl{
	
	private IElevatorControl mElevator;
	
	public ElevatorModel(IElevatorControl elevator) {
		mElevator = elevator;
		// set initial values of properties
		try {
			setProperty("FloorNum", mElevator.getFloorNum());
		} catch (ControlCenterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setProperty("Direction", IElevatorControl.Direction.Uncommited);
		setProperty("DoorStatus", IElevatorControl.DoorStatus.Closed);
		setProperty("PressedFloorButtons", new ArrayList<Integer>());
		setProperty("ServicedFloors", new ArrayList<Integer>());
		setProperty("CurrentFloor", 0);
		setProperty("Speed", 0.0);
		setProperty("Acceleration", 0.0);
		setProperty("Weight", 0.0);
		setProperty("Target", 0);
	}
	
	public void updateElevator(IElevatorControl elevator) {
		mElevator = elevator;
	}


	@Override
	public int getFloorNum(){
		return (int)getProperty("FloorNum");
	}


	@Override
	public DoorStatus getCurrentDoorStatus() {
		return (DoorStatus)getProperty("DoorStatus");
	}


	@Override
	public Direction getCurrentDirection() {
		return (Direction)getProperty("Direction");
	}


	@Override
	public double getAcceleration() {
		return (double) getProperty("Acceleration");
	}


	@Override
	public List<Integer> getPressedFloorButtons() {
		return (List<Integer>) getProperty("PressedFloorButtons");
	}


	@Override
	public boolean isFloorButtonPressed(int floor) {
		return getPressedFloorButtons().contains(floor);
	}


	@Override
	public int getCurrentFloor() {
		return (int) getProperty("CurrentFloor");
	}


	@Override
	public double getSpeed() {
		return (double)getProperty("Speed");
	}


	@Override
	public double getWeight() {
		return (double)getProperty("Weight");
	}


	@Override
	public int getTarget() {
		return (int)getProperty("Target");
	}


	@Override
	public void setTarget(int target) throws ControlCenterException {
		var floorNums = getFloorNum();
		//System.out.println("The number of the target is " + target);
		var currentTarget = getTarget();
		if(target >= floorNums) {
			throw new ControlCenterException(new IllegalArgumentException("Elevator does not serve floor " + target));
		}
		if(target != currentTarget) {
			mElevator.setTarget(target);
			setProperty("Target", target);
		}
	}


	@Override
	public List<Integer> getServicedFloors() {
		return (List<Integer>) getProperty("ServicedFloors");
	}


	@Override
	public boolean isServiced(int floor) {
		return getServicedFloors().contains(floor);
	}


	@Override
	public void setServiceFloor(int floor, boolean service) throws ControlCenterException {
		var floorNums = getFloorNum();
		var servicedFloors = new ArrayList<Integer>(getServicedFloors());
		if(floor >= floorNums) {
			throw new ControlCenterException(new IllegalArgumentException("Elevator cant reach floor " + floor));
		}
		if(service && !servicedFloors.contains(floor)) {
			mElevator.setServiceFloor(floor, service);
			servicedFloors.add(floor);
			Collections.sort(servicedFloors);
			setProperty("ServicedFloors", servicedFloors);
		}
		if(!service && servicedFloors.contains(floor)) {
			mElevator.setServiceFloor(floor, service);
			servicedFloors.remove((Object)floor);
			setProperty("ServicedFloors", servicedFloors);
		}
	}


	@Override
	public void setDirection(Direction dir) throws ControlCenterException {
		var currentDirection = getCurrentDirection();
		if(currentDirection != dir) {
			mElevator.setDirection(dir);
			setProperty("Direction", dir);
		}
	}
	
	@Override
	public void run() {
		try {
			var floornums = mElevator.getFloorNum();
			var direction = mElevator.getCurrentDirection();
			var doorstatus = mElevator.getCurrentDoorStatus();
			var pressedFloorButtons = mElevator.getPressedFloorButtons();
			var servicedFloors = mElevator.getServicedFloors();
			var currentFloor = mElevator.getCurrentFloor();
			var speed = mElevator.getSpeed();
			var acceleration = mElevator.getAcceleration();
			var weight = mElevator.getWeight();
			var target = mElevator.getTarget();
			
			setProperty("FloorNum", floornums);
			setProperty("Direction", direction);
			setProperty("DoorStatus", doorstatus);
			setProperty("PressedFloorButtons", pressedFloorButtons);
			setProperty("ServicedFloors", servicedFloors);
			setProperty("CurrentFloor", currentFloor);
			setProperty("Speed", speed);
			setProperty("Acceleration", acceleration);
			setProperty("Weight", weight);
			setProperty("Target", target);
		} catch (ControlCenterException e) {
			
			setProperty("Exception",e);
		}
		
	}
	
}
