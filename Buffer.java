
/**
 * This class holds Buffer for the game
 * In essence, this class is a buffer
 * @author Ishwor Gurung
 * 
 *
 */

import java.io.DataInputStream;

public class Buffer {

	private StringBuffer data;
	private int datalen;
	
	
	//this buffer will be sent eventually via socket to TP server
	public Buffer() {
		data = null;
		datalen = 0;
		//DataInputStream buf = new DataInputStream();
	}
	
	public Buffer(String d) {
		data = new StringBuffer(d);
	}
	
	/**
	 * pack value into the data
	 * @param val
	 */
	public void packInt(int val) {
		//data 
	}
	
	public int unpackInt() {
		return 0; //dummy for now
	}
	
	public void packString(String d) {
		if (data == null) {
			data = new StringBuffer();
		}
		//data = new StringBuffer();
		data.append(d);
		
 	}
	public String unpackString() {
		return data.toString();
	}
	
	
	public void createHeader(int ver, int seqnum, int type, int len) {
			
		datalen = 16;		
		//create data of length equal to datalen
		data = new StringBuffer(datalen);

		/*		
		data.insert(0, 'T');
		data.insert(1, 'P');
		data.insert(2, '0');
		data.insert(3, '3');
		data.insert(arg0, arg1)
		data.insert(data.length() + 4, seqnum);
		data.insert(data.length() + 8, type);
		data.insert(data.length() + 12, len);*/
		
		data.append("TP03");
		data.append(seqnum);
		data.append(type);
		data.append(len);
		
		System.out.println(data + ": " + data.length());
			
	}

	// copy version,seqnum, type and len from data (in-memory if not null) 
	//and put 
	public Buffer readHeader() {//int ver, int seqnum, int type, int len) {
		if (data == null || data.charAt(0) != 'T' || data.charAt(1) != 'P') {
			throw  new NullPointerException("header is empty");
		}
		
		Buffer tempBuf = new Buffer(this.data.toString());		
		
		return tempBuf;
		
	}
	public void setData(String buf, int len) { // the len itself is a bit superficial since 
												// we can calculate the length from buf
		//String tmpBuf = buf;
		//System.out.println("Writing :\"" + buf + "\" to data");
		data = data.append(buf);
		
		
	}
	
	public String getData() {
		return data.toString();
	}
	
	public int getLength() {
		return data.length();
	}
	
	public Object peekInt(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
