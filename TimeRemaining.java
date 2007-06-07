
/**
 * Notification of the time remaining before the end of turn.
 * @author Ishwor Gurung
 *
 */
public class TimeRemaining {

	private int time;
	private int type;
	
	public TimeRemaining() {
		
	}
	public void packBuffer(Buffer buf) {
		
		assert(true);
		
	}
	public void unpackBuffer(Buffer buf) {
		
		time = buf.unpackInt();		
		type = FrameType.FT.ft02_Time_Remaining; 
	}
	
	/*! \brief Gets the time remaining before the end of turn.
    \return The time in seconds before the end of turn.
  */
   public int getTimeRemaining(){
	   return time;
   }

	
}
