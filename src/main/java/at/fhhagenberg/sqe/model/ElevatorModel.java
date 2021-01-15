package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl;

public class ElevatorModel extends AsyncModel implements IElevatorControl{
	private static final String DIRECTION = Messages.getString("ElevatorModel.0"); //$NON-NLS-1$
	private static final String DOORSTATUS = Messages.getString("ElevatorModel.1"); //$NON-NLS-1$
	private static final String PRESSEDFLOORBUTTONS = Messages.getString("ElevatorModel.2"); //$NON-NLS-1$
	private static final String SERVICEDFLOORS = Messages.getString("ElevatorModel.3"); //$NON-NLS-1$
	private static final String CURRENTFLOOR = Messages.getString("ElevatorModel.4"); //$NON-NLS-1$
	private static final String SPEED = Messages.getString("ElevatorModel.5"); //$NON-NLS-1$
	private static final String WEIGHT = Messages.getString("ElevatorModel.6"); //$NON-NLS-1$
	private static final String ACCELERATION = Messages.getString("ElevatorModel.7"); //$NON-NLS-1$
	private static final String TARGET = Messages.getString("ElevatorModel.8"); //$NON-NLS-1$
	private static final String FLOORNUM = Messages.getString("ElevatorModel.9"); //$NON-NLS-1$
	private IElevatorControl mElevator;
	
	public ElevatorModel(IElevatorControl elevator) {
		mElevator = elevator;
		// set initial values of properties
		try {
			setProperty(FLOORNUM, mElevator.getFloorNum());
		} catch (ControlCenterException e) {
			e.printStackTrace();
		}
		setProperty(DIRECTION, IElevatorControl.Direction.UNCOMMITTED);
		setProperty(DOORSTATUS, IElevatorControl.DoorStatus.CLOSED);
		setProperty(PRESSEDFLOORBUTTONS, new ArrayList<Integer>());
		setProperty(SERVICEDFLOORS, new ArrayList<Integer>());
		setProperty(CURRENTFLOOR, 0);
		setProperty(SPEED, 0.0);
		setProperty(ACCELERATION, 0.0);
		setProperty(WEIGHT, 0.0);
		setProperty(TARGET, 0);
	}
	
	public void updateElevator(IElevatorControl elevator) {
		mElevator = elevator;
	}


	@Override
	public int getFloorNum(){
		return (int)getProperty(FLOORNUM);
	}


	@Override
	public DoorStatus getCurrentDoorStatus() {
		return (DoorStatus)getProperty(DOORSTATUS);
	}


	@Override
	public Direction getCurrentDirection() {
		return (Direction)getProperty(DIRECTION);
	}


	@Override
	public double getAcceleration() {
		return (double) getProperty(ACCELERATION);
	}


	@Override
	public List<Integer> getPressedFloorButtons() {
		return (List<Integer>) getProperty(PRESSEDFLOORBUTTONS);
	}


	@Override
	public boolean isFloorButtonPressed(int floor) {
		return getPressedFloorButtons().contains(floor);
	}


	@Override
	public int getCurrentFloor() {
		return (int) getProperty(CURRENTFLOOR);
	}


	@Override
	public double getSpeed() {
		return (double)getProperty(SPEED);
	}


	@Override
	public double getWeight() {
		return (double)getProperty(WEIGHT);
	}


	@Override
	public int getTarget() {
		return (int)getProperty(TARGET);
	}


	@Override
	public void setTarget(int target) throws ControlCenterException {
		var floorNums = getFloorNum();
		var currentTarget = getTarget();
		if(target >= floorNums) {
			throw new ControlCenterException(new IllegalArgumentException(Messages.getString("ElevatorModel.10") + target)); //$NON-NLS-1$
		}
		if(target != currentTarget) {
			mElevator.setTarget(target);
			setProperty(TARGET, target);
		}
	}


	@Override
	public List<Integer> getServicedFloors() {
		return (List<Integer>) getProperty(SERVICEDFLOORS);
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
			throw new ControlCenterException(new IllegalArgumentException(Messages.getString("ElevatorModel.11") + floor)); //$NON-NLS-1$
		}
		if(service && !servicedFloors.contains(floor)) {
			mElevator.setServiceFloor(floor, service);
			servicedFloors.add(floor);
			Collections.sort(servicedFloors);
			setProperty(SERVICEDFLOORS, servicedFloors);
		}
		if(!service && servicedFloors.contains(floor)) {
			mElevator.setServiceFloor(floor, service);
			servicedFloors.remove((Object)floor);
			setProperty(SERVICEDFLOORS, servicedFloors);
		}
	}


	@Override
	public void setDirection(Direction dir) throws ControlCenterException {
		var currentDirection = getCurrentDirection();
		if(currentDirection != dir) {
			mElevator.setDirection(dir);
			setProperty(DIRECTION, dir);
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
			
			setProperty(FLOORNUM, floornums);
			setProperty(DIRECTION, direction);
			setProperty(DOORSTATUS, doorstatus);
			setProperty(PRESSEDFLOORBUTTONS, pressedFloorButtons);
			setProperty(SERVICEDFLOORS, servicedFloors);
			setProperty(CURRENTFLOOR, currentFloor);
			setProperty(SPEED, speed);
			setProperty(ACCELERATION, acceleration);
			setProperty(WEIGHT, weight);
			setProperty(TARGET, target);
		} catch (ControlCenterException e) {
			
			setProperty(Messages.getString("ElevatorModel.12"),e); //$NON-NLS-1$
		}
		
	}
	
}
