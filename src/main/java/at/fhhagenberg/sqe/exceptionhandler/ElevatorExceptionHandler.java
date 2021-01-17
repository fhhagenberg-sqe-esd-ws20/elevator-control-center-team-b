package at.fhhagenberg.sqe.exceptionhandler;

import java.util.TimerTask;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.controller.ElevatorControlController;

public abstract class ElevatorExceptionHandler extends TimerTask{
	
	protected ElevatorControlController mController;

	protected ElevatorExceptionHandler() {
	}
	
	public void setController(ElevatorControlController controller) {
		mController = controller;
	}

	@Override
	public void run() {
		if(tryFix()) {
			try {
				mController.updateModel(getBuilding());
			} catch (ControlCenterException e) {
				// we havent finished retry again next loop
			}
		}
	}
	
	protected abstract boolean tryFix();
	protected abstract IBuilding getBuilding();
	
}
