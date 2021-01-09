package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IFloor;

public class FloorModel extends AsyncModel implements IFloor {

	private IFloor mFloor;
	
	public FloorModel(IFloor floor) {
		mFloor = floor;
		try {
			setProperty("Id", mFloor.getFloorId());
		} catch (ControlCenterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setProperty("ButtonUp", false);
		setProperty("ButtonDown", false);
	}

	@Override
	public int getFloorId() {
		return (int) getProperty("Id");
	}

	@Override
	public boolean isButtonUpPressed() {
		return (boolean)getProperty("ButtonUp");
	}

	@Override
	public boolean isButtonDownPressed() {
		return (boolean)getProperty("ButtonDown");
	}
	
	@Override
	public void run() {
		
		try {
			var id = mFloor.getFloorId();
			var btnUp = mFloor.isButtonUpPressed();
			var btnDown = mFloor.isButtonDownPressed();
			
			setProperty("Id", id);
			setProperty("ButtonUp", btnUp);
			setProperty("ButtonDown", btnDown);
			
		} catch (ControlCenterException e) {
			// TODO Auto-generated catch block
			
		}
		
	}

}
