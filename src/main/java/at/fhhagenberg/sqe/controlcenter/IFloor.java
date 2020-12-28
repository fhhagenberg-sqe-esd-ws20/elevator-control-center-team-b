package at.fhhagenberg.sqe.controlcenter;

public interface IFloor {

	
	/*
	 * returns id of the floor.
	 */
	public int getFloorId() throws ControlCenterException;
	
	/*
	 * returns whether the button up is pressed.
	 */
	public boolean isButtonUpPressed() throws ControlCenterException;
	
	/*
	 * returns whether the button down is pressed.
	 */
	public boolean isButtonDownPressed() throws ControlCenterException;
}
