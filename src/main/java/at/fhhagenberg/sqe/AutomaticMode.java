package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.controlcenter.IElevatorControl.Direction;

public class AutomaticMode {
	private int currentTarget;
	private Direction currentDirection;
	private int currentFloor;
	private int floorNum;
	
	public AutomaticMode(int floorNum) throws IllegalArgumentException{
		if (floorNum < 2) {
			throw new IllegalArgumentException("floorNum is out of range");
		}
		this.floorNum = floorNum;
	}
	
	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}
	
	public void setCurrentFloor(int currentFloor) {
		if (currentFloor < 0 || currentFloor >= floorNum) {
			throw new IllegalArgumentException("currentFloor is out of range");
		}
		this.currentFloor = currentFloor;
	}
	
	public void calculateNextTargetAndDirection() {
		if (currentDirection == Direction.DOWN && currentFloor > 0) {
			currentTarget = currentFloor - 1;
		} else if (currentDirection == Direction.UP && currentFloor < (floorNum - 1)){
			currentTarget = currentFloor + 1;
		} else {
			if (currentFloor == 0) {
				currentDirection = Direction.UP;
				currentTarget = currentFloor + 1;
			} else {
				currentDirection = Direction.DOWN;
				currentTarget = currentFloor - 1;
			}
		}
	}
	
	public int getNextTarget() {
		return currentTarget;
	}
	
	public Direction getNextDirection() {
		return currentDirection;
	}
}
