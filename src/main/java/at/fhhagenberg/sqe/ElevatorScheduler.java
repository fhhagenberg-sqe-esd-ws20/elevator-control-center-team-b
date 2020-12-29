package at.fhhagenberg.sqe;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import at.fhhagenberg.sqe.model.AsyncModel;

public class ElevatorScheduler extends TimerTask {
	private List<AsyncModel> models;
	
	/*
	 * CTOR for allocating a new list
	 */
	public ElevatorScheduler() {
		models = new ArrayList<AsyncModel>();
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
	 * the overriden run function from TimerTask
	 */
	public void run() {
		for(AsyncModel m : models) {
			m.run();
		}
	}
}
