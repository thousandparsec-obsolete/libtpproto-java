/**
 * This class defines a generic TP frame.
 *   
 * @author Ishwor Gurung
 * @see http://www.thousandparsec.net/tp/dev/documents/protocol3.php#FrameFormat
 * 
 *
 **/

public abstract class Frame  {
	
	
	String frameHeader = "TP03";//always fixed to version number. "TP03" is version 0.3
	
	int protocolVersion;
	
	int sequenceNumber;// = DataType.UNDEFINED; //should be unsigned integer > 0
	
	FrameType frameType;// = DataType.UNDEFINED; //should be unsigned integer
	
	int frameLength;// = DataType.UNDEFINED; //should be unsigned integer
	
	Buffer frameData;// = null;//new Data(frameLength*8,); //(size,data)
			
	/**
	 * Create a base TP frame based on TP Specification version 0.3
	 * 
	 * @see http://www.thousandparsec.net/tp/dev/documents/protocol3.php#FrameFormat
	 * @param sequenceNumber
	 * @param frameType
	 * @param frameLength
	 * @param data
	 */
	public abstract void buildFrame(String frameHeader,
					int sequenceNumber, 
					FrameType frameType, 
					int frameLength, 
					Buffer frameData);
	
	
	public abstract void setFrameHeader(String frameHeader);
	
	public abstract void setSequenceNumber(int sequenceNumber);
	public abstract void setFrameType(int frameType);
	public abstract void setFrameLength(int frameLength);
	public abstract void setFrameData(Buffer data);
	
	public abstract void setProtocolVersion(int ver);
	public abstract int getProtocolVersion();
	
	public abstract int getSequenceNumber();
	public abstract int getFrameType();
	public abstract int getFrameLength();
	public abstract Buffer getFrameData();
	public abstract void packBuffer(Buffer data);
	
	
	
}
