package at.fhhagenberg.sqe.controlcenter.mocks;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IFloor;

public class FloorMock implements IFloor{
	private int mId;
	private boolean mUp;
	private boolean mDown;

	public FloorMock(int id) {
		mId = id;
		mUp = false;
		mDown = false;
	}
	
	@Override
	public int getFloorId() {
		return mId;
	}

	/*
	 * Set state of up button
	 * @param state: state of button
	 */
	public void setButtonUpPressed(boolean state) {
		mUp = state;
	}
	
	@Override
	public boolean isButtonUpPressed() {
		return mUp;
	}
	
	/*
	 * Set state of down button
	 * @param state: state of button
	 */
	public void setButtonDownPressed(boolean state) {
		mDown = state;
	}

	@Override
	public boolean isButtonDownPressed() {
		return mDown;
	}

}
