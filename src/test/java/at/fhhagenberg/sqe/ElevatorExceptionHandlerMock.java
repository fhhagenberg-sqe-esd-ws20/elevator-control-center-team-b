package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.controlcenter.IBuilding;
import at.fhhagenberg.sqe.exceptionhandler.ElevatorExceptionHandler;

public class ElevatorExceptionHandlerMock extends ElevatorExceptionHandler {

	private IBuilding mBuilding;
	
	public ElevatorExceptionHandlerMock(IBuilding building) {
		mBuilding = building;
	}
	
	@Override
	protected boolean tryFix() {
		return true;
	}

	@Override
	protected IBuilding getBuilding() {
		return mBuilding;
	}

}
