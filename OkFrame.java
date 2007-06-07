
public class OkFrame extends Frame {

	private String info;
	private int type;

	public OkFrame() {
		info = "";			
	}

	public void setProtocolVersion(int pversion) {
		this.protocolVersion = pversion;
	}

	public String getInfo(){		
		return info;		  
	}	

	public void packBuffer(Buffer buf){
		// should never send an OK frame
		assert(true);
	}

	/*! \brief Unpack from a Buffer.
		    \param buf The Buffer to unpack out of.
		    \return True if successful, false otherwise.
	 */
	public boolean unpackBuffer(Buffer buf){

		info = buf.unpackString();  

		type = FrameType.FT.ft02_OK;

		return true;
	}


	public void buildFrame(String frameHeader, int sequenceNumber, FrameType frameType, int frameLength, Buffer frameData) {
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



}
