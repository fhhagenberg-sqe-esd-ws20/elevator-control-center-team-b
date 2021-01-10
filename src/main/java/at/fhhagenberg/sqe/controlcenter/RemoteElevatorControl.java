package at.fhhagenberg.sqe.controlcenter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import sqelevator.IElevator;

public class RemoteElevatorControl implements IElevatorControl {

	private String mRemoteUri;
	private int mElevatorId;
	private IElevator mRemote;
	private int mFloors;
	
	
	public RemoteElevatorControl(String remoteUri, int idx, int floors) throws MalformedURLException, RemoteException, NotBoundException {
		mRemoteUri = remoteUri;
		mElevatorId = idx;
		mRemote = (IElevator)Naming.lookup(remoteUri);
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
			status = mRemote.getElevatorDoorStatus(mElevatorId);
			System.out.println("Doorstatus Elevator " + mElevatorId + " is " + status);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(status) {
		case IElevator.ELEVATOR_DOORS_CLOSED:
			return DoorStatus.Closed;
		case IElevator.ELEVATOR_DOORS_CLOSING:
			return DoorStatus.Closing;
		case IElevator.ELEVATOR_DOORS_OPEN:
			return DoorStatus.Open;
		case IElevator.ELEVATOR_DOORS_OPENING:
			return DoorStatus.Opening;
		}
		
		return null;
	}

	@Override
	public Direction getCurrentDirection() throws ControlCenterException {
		int direction = -1;
		try {
			direction = mRemote.getCommittedDirection(mElevatorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(direction) {
		case IElevator.ELEVATOR_DIRECTION_DOWN:
			return Direction.Down;
		case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED:
			return Direction.Uncommited;
		case IElevator.ELEVATOR_DIRECTION_UP:
			return Direction.Up;
		}
		return null;
	}

	@Override
	public double getAcceleration() throws ControlCenterException {
		try {
			return mRemote.getElevatorAccel(mElevatorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
			return mRemote.getElevatorButton(mElevatorId, floor);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getCurrentFloor() throws ControlCenterException {
		try {
			return mRemote.getElevatorFloor(mElevatorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public double getSpeed() throws ControlCenterException {
		try {
			return mRemote.getElevatorSpeed(mElevatorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public double getWeight() throws ControlCenterException {
		try {
			return mRemote.getElevatorWeight(mElevatorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getTarget() throws ControlCenterException {
		try {
			return mRemote.getTarget(mElevatorId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void setTarget(int floor) throws ControlCenterException {
		try {
			mRemote.setTarget(mElevatorId, floor);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Target of Elevator " + mElevatorId + " was set to " + floor);
	}

	@Override
	public List<Integer> getServicedFloors() throws ControlCenterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isServiced(int floor) throws ControlCenterException {
		try {
			return mRemote.getServicesFloors(mElevatorId, floor);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void setServiceFloor(int floor, boolean service) throws ControlCenterException {
		try {
			mRemote.setServicesFloors(mElevatorId, floor, service);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setDirection(Direction dir) throws ControlCenterException {
		int direction = 0;
		switch(dir) {
		case Uncommited:
			direction = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
			break;
		case Up:
			direction = IElevator.ELEVATOR_DIRECTION_UP;
			break;
		case Down:
			direction = IElevator.ELEVATOR_DIRECTION_DOWN;
			break;
		}
		
		try {
			mRemote.setCommittedDirection(mElevatorId, direction);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
