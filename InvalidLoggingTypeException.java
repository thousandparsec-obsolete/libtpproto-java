

/**
 * This class is responsible for printing out a loggingtype exception if it occurs.
 * @author Ishwor Gurung
 *
 */
public class InvalidLoggingTypeException extends Exception {
	public InvalidLoggingTypeException(String message) {
		System.out.println(message);
	}
}
