package at.fhhagenberg.sqe.controlcenter;

import java.rmi.RemoteException;

import sqelevator.IElevator;

public class FloorAdapter implements IFloor {

	private IElevator mElevator;
	private int mId;
	private SyncVerifier mVerifier;
	
	public FloorAdapter(IElevator elevator, int id) throws ControlCenterException {
		mElevator = elevator;
		mId = id;
		try {
			mVerifier = new SyncVerifier(elevator);
		}
		catch(RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public int getFloorId() throws ControlCenterException {
		return mId;
	}

	@Override
	public boolean isButtonUpPressed() throws ControlCenterException {
		try {
			mVerifier.checkSync();
			return mElevator.getFloorButtonUp(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

	@Override
	public boolean isButtonDownPressed() throws ControlCenterException {
		try {
			mVerifier.checkSync();
			return mElevator.getFloorButtonDown(mId);
		} catch (RemoteException e) {
			throw new ControlCenterException(e);
		}
	}

}
