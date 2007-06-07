


/** 
 * This class defines a socket (host,port) for communicating with the network layer.
 * It also has various utility methods for checking the status of the socket.
 * @author Ishwor Gurung
 *
 */
public abstract class TPSocket {
	
	//Construct a TCP socket
	public TPSocket () {
		
	}
 	
	//Checks if the socket is connected.
    public abstract boolean 	isConnected ();/* {
    	return false;
    }*/
    //Connects to the server.
    //open the socket here.
    public boolean 	connect () {
    	return false;
    }
 	//Disconnects from the server.
    public abstract void 	disconnect (); /*{
    	
    }*/
    /**
     * Get server information and send it off to server
     */
    //sends some header+data to the server.
    public abstract void 	send (String header, int hlen, String data, int len); /*{
    	  //get server information
    	 
    }*/
 	
    //Receive a header from the socket.
    public abstract int 	recvHeader (int len, String data); /*{
    	return -1;
    }*/
 	
    //Receive the data from the socket.
    public abstract int 	recvBody (int len, String data);/* {
    	return -1;
    }
 	*/
    
    //Poll the socket for data.
    //If a socket does not support polling, it should 
    //always return false and does not need to override 
    //this method as the default is to return false.
    public boolean poll ()  {
    	return false;
    }

    public abstract void setServerAddress(String host, int port);
 	 
}
