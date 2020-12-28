package at.fhhagenberg.sqe.model;

public abstract class AsyncModel extends Model {

	/*
	 * Function must be called in a fixed interval to update model
	 */
	public abstract void run();
	
}
