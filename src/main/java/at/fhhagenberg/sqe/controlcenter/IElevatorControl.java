package at.fhhagenberg.sqe.controlcenter;

import java.util.List;

public interface IElevatorControl {
	/*
	 * Type for status of door
	 */
	public enum DoorStatus {
		Open,
		Closed,
		Opening,
		Closing
	}
	
	/*
	 * Type of elevator direction
	 */
	public enum Direction {
		Uncommited,
		Up,
		Down
	}
	
	/*
	 * Returns the door status of the elevator.
	 */
	public DoorStatus getCurrentDoorStatus() throws ControlCenterException;
	
	/*
	 * Returns the current direction of the elevator.
	 */
	public Direction getCurrentDirection() throws ControlCenterException;
	
	/*
	 * Return the acceleration of the elevator in meters per second^2.
	 */ 
	public double getAcceleration() throws ControlCenterException;
	
	/*
	 * Returns the list of pressed buttons of the elevator.
	 */
	public List<Integer> getPressedFloorButtons() throws ControlCenterException;
	
	/*
	 * Returns if the button of a specified floor is pressed.
	 */
	public boolean isFloorButtonPressed(int floor) throws ControlCenterException;
	
	/*
	 * Returns the nearest floor where the elevator is positioned.
	 */
	public int getCurrentFloor() throws ControlCenterException;
	
	/*
	 * Returns the speed of the elevator in meters per second.
	 */
	public double getSpeed() throws ControlCenterException;
	
	/*
	 * Returns the weight of the passengers in kilograms.
	 */
	public double getWeight() throws ControlCenterException;
	
	/*
	 * Returns the current target of the elevator.
	 */
	public int getTarget() throws ControlCenterException;
	
	/*
	 * Sets the target of the elevator.
	 * @param floor to target
	 */
	public void setTarget(int floor) throws ControlCenterException;
	
	/*
	 * Returns a list of floors which are serviced.
	 */
	public List<Integer> getServicedFloors() throws ControlCenterException;
	
	/*
	 * Returns whether the specified floor is serviced by the elevator.
	 * @param floor to check.
	 */
	public boolean isServiced(int floor) throws ControlCenterException;
	
	/*
	 * Sets whether a floor should be serviced by the elevator.
	 * @param floor to change service state
	 * @param service if true floor will be serviced
	 */
	public void setServiceFloor(int floor, boolean service) throws ControlCenterException;
	
	/*
	 * Sets the current direction of the elevator which will be displayed
	 * @param dir direction to set
	 */
	public void setDirection(Direction dir) throws ControlCenterException;
}
