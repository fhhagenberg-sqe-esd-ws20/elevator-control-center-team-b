package at.fhhagenberg.sqe.controlcenter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;
import at.fhhagenberg.sqe.controlcenter.IElevatorControl.DoorStatus;
import sqelevator.IElevator;

public class ElevatorAdapter implements IElevatorControl {

	private IElevator mElevator;
	private int mId;
	private int mFloors;
	
	public ElevatorAdapter(IElevator elevator, int id, int floors) {
		mElevator = elevator;
		mId = id;
		mFloors = floors;
	}
	
	@Override
	public int getFloorNum() throws ControlCenterException {
		return mFloors;
	}

	@Override
	public DoorStatus getCurrentDoorStatus() throws ControlCenterException {
		int status = -1;
		try {
			status = mElevator.getElevatorDoorStatus(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
		
		switch(status) {
		case IElevator.ELEVATOR_DOORS_CLOSED:
			return DoorStatus.CLOSED;
		case IElevator.ELEVATOR_DOORS_CLOSING:
			return DoorStatus.CLOSING;
		case IElevator.ELEVATOR_DOORS_OPEN:
			return DoorStatus.OPEN;
		case IElevator.ELEVATOR_DOORS_OPENING:
			return DoorStatus.OPENING;
		default:
			throw new ControlCenterException("Received undefined door status");
		}
		
		
	}

	@Override
	public Direction getCurrentDirection() throws ControlCenterException {
		int direction = -1;
		try {
			direction = mElevator.getCommittedDirection(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
		
		switch(direction) {
		case IElevator.ELEVATOR_DIRECTION_DOWN:
			return Direction.DOWN;
		case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED:
			return Direction.UNCOMMITTED;
		case IElevator.ELEVATOR_DIRECTION_UP:
			return Direction.UP;
		default:
			throw new ControlCenterException("Received undefined direction");
		}
		
	}

	@Override
	public double getAcceleration() throws ControlCenterException {
		try {
			return mElevator.getElevatorAccel(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public List<Integer> getPressedFloorButtons() throws ControlCenterException {
		var returnList = new ArrayList<Integer>();
		for(int i = 0; i < mFloors; i++) {
			if(isFloorButtonPressed(i)) {
				returnList.add(i);
			}
		}
		return returnList;
	}

	@Override
	public boolean isFloorButtonPressed(int floor) throws ControlCenterException {
		try {
			return mElevator.getElevatorButton(mId, floor);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public int getCurrentFloor() throws ControlCenterException {
		try {
			return mElevator.getElevatorFloor(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public double getSpeed() throws ControlCenterException {
		try {
			return mElevator.getElevatorSpeed(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public double getWeight() throws ControlCenterException {
		try {
			return mElevator.getElevatorWeight(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public int getTarget() throws ControlCenterException {
		try {
			return mElevator.getTarget(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public void setTarget(int floor) throws ControlCenterException {
		try {
			mElevator.setTarget(mId, floor);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public List<Integer> getServicedFloors() throws ControlCenterException {
		return null;
	}

	@Override
	public boolean isServiced(int floor) throws ControlCenterException {
		try {
			return mElevator.getServicesFloors(mId, floor);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public void setServiceFloor(int floor, boolean service) throws ControlCenterException {
		try {
			mElevator.setServicesFloors(mId, floor, service);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public void setDirection(Direction dir) throws ControlCenterException {
		int direction = 0;
		switch(dir) {
		case UNCOMMITTED:
			direction = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
			break;
		case UP:
			direction = IElevator.ELEVATOR_DIRECTION_UP;
			break;
		case DOWN:
			direction = IElevator.ELEVATOR_DIRECTION_DOWN;
			break;
		}
		
		try {
			mElevator.setCommittedDirection(mId, direction);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}

	}

}
