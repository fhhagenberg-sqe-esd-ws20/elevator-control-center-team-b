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
	
	public void SetCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}
	
	public void SetCurrentFloor(int currentFloor) {
		if (currentFloor < 0 || currentFloor >= floorNum) {
			throw new IllegalArgumentException("currentFloor is out of range");
		}
		this.currentFloor = currentFloor;
	}
	
	public void CalculateNextTargetAndDirection() {
		if (currentDirection == Direction.Down && currentFloor > 0) {
			currentTarget = currentFloor - 1;
		} else if (currentDirection == Direction.Up && currentFloor < (floorNum - 1)){
			currentTarget = currentFloor + 1;
		} else {
			if (currentFloor == 0) {
				currentDirection = Direction.Up;
				currentTarget = currentFloor + 1;
			} else {
				currentDirection = Direction.Down;
				currentTarget = currentFloor - 1;
			}
		}
	}
	
	public int GetNextTarget() {
		return currentTarget;
	}
	
	public Direction GetNextDirection() {
		return currentDirection;
	}
}
