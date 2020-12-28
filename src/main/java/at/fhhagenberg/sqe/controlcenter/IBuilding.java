package at.fhhagenberg.sqe.controlcenter;

public interface IBuilding {

	/*
	 * Returns count of floors in the building
	 */
	public int getNumberOfFloors() throws ControlCenterException;
	
	/*
	 * returns the specified floor.
	 * @param floor to return.
	 */
	public IFloor getFloor(int id) throws ControlCenterException;
	
	/*
	 * Returns the height of a floor in meters.
	 * @returns height of floor in meters
	 */
	public double getHeightOfFloor() throws ControlCenterException;
	
	/*
	 * returns the number of elevators.
	 */
	public int getNumberOfElevators() throws ControlCenterException;
	
	/*
	 * returns the specified elevator.
	 * @param id of elevator
	 */
	public IElevatorControl getElevator(int id) throws ControlCenterException;
}
