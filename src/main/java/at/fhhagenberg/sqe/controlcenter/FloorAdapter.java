package at.fhhagenberg.sqe.controlcenter;

import java.rmi.RemoteException;

import sqelevator.IElevator;

public class FloorAdapter implements IFloor {

	private IElevator mElevator;
	private int mId;
	
	public FloorAdapter(IElevator elevator, int id) {
		mElevator = elevator;
		mId = id;
	}

	@Override
	public int getFloorId() throws ControlCenterException {
		return mId;
	}

	@Override
	public boolean isButtonUpPressed() throws ControlCenterException {
		try {
			return mElevator.getFloorButtonUp(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public boolean isButtonDownPressed() throws ControlCenterException {
		try {
			return mElevator.getFloorButtonDown(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

}
