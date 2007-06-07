


/**
 * 
 * This class is responsible for defining system-wide logging facility
 * 
 * @author Ishwor Gurung
 *
 */
public class Logger {
	/**
	 * TODO
	 * If the logging has to be done into a file instead, specify here.
	 */
	
	//log levels
	static final int ERROR =0,
					 MSG = 1,
					 WARN = 2,
					 DEBUG = 3;
					
	
	/**
	 * This method logs a message according to its log level.
	 * @param logmsg message to log
	 * @param type type of logging level; ERROR = 0; MSG = 1; WARN = 2, DEBUG=3;
	 * @throws InvalidLoggingTypeException
	 */	
	public void log(String logmsg, int type) throws InvalidLoggingTypeException {
		if (type < 0 && type > 3){ //0,1,2,3
			throw new InvalidLoggingTypeException("Invalid type logging type: " + type);
		}
			
		switch(type){
		//TODO
		//when have time write the logmsg to file instead of standard error stream
			case 0:
				
				System.err.println("ERROR: " + logmsg);
				
			case 1:
				System.err.println("MESSAGE: " + logmsg);
			case 2:
				System.err.println("WARNING: " + logmsg);
			case 3:
				System.err.println("DEBUG: " + logmsg);
			default:
				System.err.println("UNDEFINED ERROR: " + logmsg);
				
		}
	}
	
}
