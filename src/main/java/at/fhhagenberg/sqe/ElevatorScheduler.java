package at.fhhagenberg.sqe;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import at.fhhagenberg.sqe.model.AsyncModel;

public class ElevatorScheduler extends TimerTask {
	private List<AsyncModel> models;
	private List<ElevatorController> controllers;
	
	/*
	 * CTOR for allocating a new list
	 */
	public ElevatorScheduler() {
		models = new ArrayList<AsyncModel>();
		controllers = new ArrayList<ElevatorController>();
	}
	
	/*
	 * add an asynchronous model in order to schedule run function
	 * @param model the asynchronous model
	 */
	public void addAsyncModel(AsyncModel model)
	{
		models.add(model);
	}
	
	/*
	 * attach a callable elevator controller in order to run the update function
	 * @param controller the elevator controller
	 */
	public void addElevatorController(ElevatorController controller) {
		controllers.add(controller);
	}
	
	/*
	 * the overriden run function from TimerTask
	 */
	@Override
	public void run() {
		for(ElevatorController c : controllers) {
			c.run();
		}
		for(AsyncModel m : models) {
			m.run();
		}
	}
}
