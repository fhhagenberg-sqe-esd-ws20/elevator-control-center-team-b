package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.controlcenter.ControlCenterException;
import at.fhhagenberg.sqe.controlcenter.IFloor;

public class FloorModel extends AsyncModel implements IFloor {
	private static final String BUTTONUP = "ButtonUp";
	private static final String BUTTONDOWN = "ButtonDown";
	private static final String ID = "Id";
	private IFloor mFloor;
	
	public FloorModel(IFloor floor) {
		mFloor = floor;
		try {
			setProperty(ID, mFloor.getFloorId());
		} catch (ControlCenterException e) {
			e.printStackTrace();
		}
		setProperty(BUTTONUP, false);
		setProperty(BUTTONDOWN, false);
	}
	
	public void updateFloor(IFloor floor) {
		mFloor = floor;
	}

	@Override
	public int getFloorId() {
		return (int) getProperty(ID);
	}

	@Override
	public boolean isButtonUpPressed() {
		return (boolean)getProperty(BUTTONUP);
	}

	@Override
	public boolean isButtonDownPressed() {
		return (boolean)getProperty(BUTTONDOWN);
	}
	
	@Override
	public void run() {
		
		try {
			var id = mFloor.getFloorId();
			var btnUp = mFloor.isButtonUpPressed();
			var btnDown = mFloor.isButtonDownPressed();
			
			setProperty(ID, id);
			setProperty(BUTTONUP, btnUp);
			setProperty(BUTTONDOWN, btnDown);
			
		} catch (ControlCenterException e) {
			setProperty("Exception",e);
			
		}
		
	}

}
