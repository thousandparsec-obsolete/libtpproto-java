/*import GameLayer.GameStatus;*/


//test class 
public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//test class instantiation
		LoginFrame login = new LoginFrame("username","password");
		
		login.packUserNameAndPassword();	
		
		System.out.println(login);
		//test factory method
		//LoginFrame newLogin = login.getLoginFrameFactory();
		
		

		/*
		//test Buffer class
		//succeeded - 21/05/07		
		Buffer buf = new Buffer();
		buf.createHeader(222, 23232, 2222, 2222);// error here
		buf.setData("<a> helo <b> helo1",0); //0 is superficial
		
		Buffer newBuf = buf.readHeader();
		System.out.println(newBuf.getData());*/
		
		/*
		//test Account class
		//test succeeded - 22/05/07
		Account account = new Account("username","password","comment","username@host.com");
		Buffer buf = new Buffer();
		
		account.packBuffer(buf);
		System.out.println(account.getUser());
		System.out.println(account.getPassword());
		
		
		System.out.println(buf.getData()); */
		
		
		/*
		test GameLayer.connect()
		//test succeeded - 25/05/07		 
		//System.out.println(connect("https://www.thousandparsec.net:80"));
		*/
		
		
		
	}
	/*public static String connect(String address){
        if(status != GameStatus.gsDisconnected){
            logger.log("Already connected, ignoring connection attempt",Logger.WARN);
            return false;
        }
		
		for (int i = 0; i < address.length(); i++ ) {
			System.out.println("at " + i + ": " + address.charAt(i));
		}
		
        String type = null, host = null , port = null;
        
        //parse address to type, host, and port
        if (address.startsWith("tps") ) {
        	type = "tps";//address.substring(0, 3); //has "tps" now
        	port = "6924";
        	host = address.substring(6,address.lastIndexOf(':'));
        	//sock = new TcpSocket();
        }
        else if (address.startsWith("https") ) {
        	type = "https";//address.substring(0,5);
        	port = "443";
        	host = address.substring(8,address.lastIndexOf(':'));
        }
        else if (address.substring(0,2).equals("tp") && !address.substring(0,3).equals("tps")) {
        	type = "tp";//address.substring(0,2);
        	port = "6923";
        	host = address.substring(5,address.lastIndexOf(':'));
        }
        else if (address.substring(0,4).equals("http") && !address.substring(0,5).equals("https")) {
        	type = "http";//address.substring(0,4);
        	port = "80";
        	//System.out.println(": is at- " + address.lastIndexOf(':'));
        	host = address.substring(7,address.lastIndexOf(':'));
        }
        
        String returned = type+host+port;
        return returned;
        
	}*/

}
