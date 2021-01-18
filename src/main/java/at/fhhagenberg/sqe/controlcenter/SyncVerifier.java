package at.fhhagenberg.sqe.controlcenter;

import java.rmi.RemoteException;

import sqelevator.IElevator;

public class SyncVerifier {
	
	private IElevator mElevator;
	private long mLastTick;
	
	public SyncVerifier(IElevator elevator) throws RemoteException {
		mElevator = elevator;
		mLastTick = mElevator.getClockTick();
	}
	
	void checkSync() throws RemoteException, ControlCenterException {
		var tick = mElevator.getClockTick();
		boolean ret = tick > mLastTick + 1;
		mLastTick = tick;
		if(ret) {
			throw new ControlCenterException("Control out of sync");
		}
	}
}
