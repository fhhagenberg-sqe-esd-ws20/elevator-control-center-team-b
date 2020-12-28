package at.fhhagenberg.sqe.controlcenter;

/*
 * Custom Elevator Exception
 */
public class ControlCenterException extends Exception {
	
	/**
	 * Generated Serial Version Id
	 */
	private static final long serialVersionUID = -2646795096508889285L;

	/*
	 * Create a new instance of ElevatorException with a message
	 * @param message: message to forward
	 */
	public ControlCenterException(String message) {
		super(message);
	}
	
	/*
	 * Create a new instance of ElevatorException with a message and inner exception
	 * @param message: message to forward
	 * @param innerException: exception to forward
	 */
	public ControlCenterException(String message, Exception innerException) {
		super(message,innerException);
	}
}
