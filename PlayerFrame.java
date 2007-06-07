
import FrameType;

/**
 * 
 * @author Ishwor Gurung
 *
 */
public class PlayerFrame extends Frame{
	
	private int playerID;
	private String playerName;
	private String raceName;
	
	//need to create a player object frame
	private FrameBuilder frameBuilder;
	
	public PlayerFrame(int id, String playerName, String raceName) {
		this.playerID = id;
		this.playerName = playerName;
		this.raceName = raceName;
		//FIXME construct new Frame using this id
		
	}
	
	/**
	 * Get player's id
	 * @return
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * set player's id
	 * @param id player's id should be unique [???]
	 */
	public void setPlayerID(int id) {
		this.playerID = id ; //set new player id		
	}
	
	
	/**
	 * Given the player id, return the playername
	 * @param id player's id
	 * @return player's name
	 */
	public String getPlayerName(int id) {
		
		//TODO return player's name by id
		return null;
	}
	/**
	 * Set player's name
	 * @param playerName player's name
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Get Player's race name
	 * @return name of player's race
	 */
	public String getPlayerRaceName() {
		
		//TODO return player's race name
		return null;
	}
	
	/**
	 * TODO Can Players set their race name ?
	 * @param playerRaceName player's race name
	 */
	public void setPlayerRaceName(String playerRaceName) {
		this.raceName = playerRaceName;
		
	}

	
	public void buildFrame(String frameHeader, int sequenceNumber, int frameType, int frameLength, Buffer frameData) {
		// TODO Auto-generated method stub
		
	}

	
	public Buffer getFrameData() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getFrameLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getFrameType() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getSequenceNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setFrameData(Buffer data) {
		// TODO Auto-generated method stub
		
	}

	
	public void setFrameHeader(String frameHeader) {
		// TODO Auto-generated method stub
		
	}

	
	public void setFrameLength(int frameLength) {
		// TODO Auto-generated method stub
		
	}

	
	public void setFrameType(int frameType) {
		// TODO Auto-generated method stub
		
	}

	
	public void setSequenceNumber(int sequenceNumber) {
		// TODO Auto-generated method stub
		
	}

	
	public void buildFrame(String frameHeader, int sequenceNumber, FrameType frameType, int frameLength, Buffer frameData) {
		// TODO Auto-generated method stub
		
	}
}
