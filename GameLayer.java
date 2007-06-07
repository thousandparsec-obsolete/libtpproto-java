/*  GameLayer class
 *
 *  Copyright (C) 2007  Ishwor Gurung and the Thousand Parsec Project
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */



/**
 * GameLayer is the main interface to the higher layer of libtpproto-java
 * GameLayer abstracts away most of the underlying details of how Objects and Orders are requested and received.
 */
public class GameLayer extends TPSocket {
	
	private int status; //status is int 
    private ProtocolLayer protocol;
    private GameStatusListener statusListener;
    private Logger logger;
    private TPSocket sock;    
    private String clientid;    
    private GameLayerAsyncFrameListener asyncframes;
    
    //TODO 
    /*private Features serverfeatures; 
    private ObjectCache objectcache;
    private PlayerCache playercache;
    private BoardCache boardcache;
    private ResourceCache resourcecache;
    private CategoryCache categorycache;
    private DesignCache designcache;
    private ComponentCache componentcache;
    private PropertyCache propertycache;*/
    
   	//status of the game
	class GameStatus {
		static int  gsDisconnected = 0,
				        gsConnecting = 1,
				        gsConnected = 2,
				        gsLoggedIn = 3,
				        gsEOTInProgress = 4;
		}
	
	class GameLayerAsyncFrameListener { //extends AsyncFrameListener{

        private GameLayer layer;
        
        /*! \brief Set the GameLayer
        \param gl The GameLayer to use.
        */
        void setGameLayer(GameLayer gl){
            this.layer = gl;
        }
        
        void recvTimeRemaining(TimeRemaining trf){
            if(trf.getTimeRemaining() == 0){
                layer.status = GameStatus.gsEOTInProgress;
                if(layer.statusListener != null){
                    layer.statusListener.eotStarted();
                    layer.statusListener.timeToEot(trf.getTimeRemaining());
                }
            }else{
                if(layer.status == GameStatus.gsEOTInProgress){
                    layer.status =GameStatus.gsLoggedIn;
                    if(layer.statusListener != null){
                        layer.statusListener.eotEnded();
                        layer.updateCaches();
                    }
                }
                if(layer.statusListener != null){
                    layer.statusListener.timeToEot(trf.getTimeRemaining());
                }
            }
        }       
	}


    
    /*! \brief Constructs object and sets up defaults.

    Defaults are
        - The default ProtocolLayer
        - Disconnected state.
        - "Unknown client" for the client string
    */
    public GameLayer() {
    	//protocol = null;
    	//logger = null;
    	statusListener = null;
    	status = GameStatus.gsDisconnected;
    	clientid = "Unknown client";
    	//TODO
    	//serverfeatures = null;
    	asyncframes = new GameLayerAsyncFrameListener();
    	//TODO
    	/*objectcache = new ObjectCache();
    	playercache = new PlayerCache();
    	boardcache  = new BoardCache();
    	resourcecache = new ResourceCache();
    	categorycache = new CategoryCache();
    	designcache = new DesignCache();
    	componentcache = new ComponentCache();
        propertycache = new PropertyCache();
        */
    	protocol = new ProtocolLayer();
        logger = new Logger();//SilentLogger();
        sock = null;
        asyncframes.setGameLayer(this);
        protocol.getFrameCodec().setAsyncFrameListener(asyncframes);
        //TODO
        /*
        objectcache.setProtocolLayer(protocol);
        playercache.setProtocolLayer(protocol);
        boardcache.setProtocolLayer(protocol);
        resourcecache.setProtocolLayer(protocol);
        categorycache.setProtocolLayer(protocol);
        designcache.setProtocolLayer(protocol);
        componentcache.setProtocolLayer(protocol);
        propertycache.setProtocolLayer(protocol);*/
    }



    /*! \brief Sets the client string.

    The client string can be set to anything.  The perferred format is
    "name/version".  The library name and version is added the the client
    string later.
    \param name The client name string.
    */
    void setClientString(String name){
        clientid = name;
    }

    /*! \brief Sets the Logger to use
    \param nlog The new Logger to use.
    */
    void setLogger(Logger nlog){
        logger = nlog;
        protocol.getFrameCodec().setLogger(nlog); // set the new logger
    }

    /*! \brief Sets the GameStatusListener to use.
    \param gsl The new GameStatusListener to use.
    */
    void setGameStatusListener(GameStatusListener gsl){
        statusListener = gsl;
    }

    /*! \brief Gets the state of the game.
    \return The GameStatus enum value for the current state.
    */
    public int getStatus(){
    	
        if(sock == null || !sock.isConnected()){
            
        	status = GameStatus.gsDisconnected;
            
            if(statusListener != null)
                statusListener.disconnected();
        }
        
        return status;
    }

    /*! \brief Sets the CacheMethod for the caches to use.
    Changes the default CacheMethod used, defaults to CacheNoneMethod.
    \param prototype A CacheMethod that will be cloned for each Cache to use.
    */
    
    /*void setCacheMethod(CacheMethod prototype){
    	
        objectcache.setCacheMethod(prototype.clone());
        boardcache.setCacheMethod(prototype.clone());
        playercache.setCacheMethod(prototype.clone());
        resourcecache.setCacheMethod(prototype.clone());
        categorycache.setCacheMethod(prototype.clone());
        designcache.setCacheMethod(prototype.clone());
        componentcache.setCacheMethod(prototype.clone());
        propertycache.setCacheMethod(prototype.clone());
    }*/

    /*! \brief Gets the ProtocolLayer being used.
    This could be used to do low level calls to the protocol itself, or 
    more importantly, set the FrameFactory, FrameBuilder and/or FrameCodec
    classes in the ProtocolLayer.
    \return The pointer to the ProtocolLayer.
    */
    ProtocolLayer getProtocolLayer() {
      return protocol;
    }

    /*! \brief Connects to the given address url
    This method connects to the server given as the address. The types of url
    supported are tp, tps, https and http. Tps and https depend on TLS being enabled.
    \param address The URL to connect to.
    \return True if connected, false otherwise.
    */
    // (tp://host:port || tps://host:port || http://host:port || https://host:port )<--- address
    public boolean connect(String address){
    	
        if(status != GameStatus.gsDisconnected){
            logger.log("Already connected, ignoring connection attempt",Logger.WARN);
            return false;
        }
		
		/* DEBUG
		for (int i = 0; i < address.length(); i++ ) {
			System.out.println("at " + i + ": " + address.charAt(i));
		}*/
		
        String type = null, host = null , port = null;
        
        //parse address to type, host, and port
        if (address.startsWith("tps") ) {
        	type = "tps";//address.substring(0, 3); //has "tps" now
        	port = "6924";
        	host = address.substring(6,address.lastIndexOf(':'));
         	sock = new TPSSocket();
            sock.setServerAddress(host,Integer.parseInt(port));
        	
        }
        else if (address.startsWith("https") ) {
        	type = "https";//address.substring(0,5);
        	port = "443";
        	host = address.substring(8,address.lastIndexOf(':'));
        	sock = new HTTPSSocket();
            sock.setServerAddress(host,Integer.parseInt(port));
        }
        else if (address.substring(0,2).equals("tp") && !address.substring(0,3).equals("tps")) {
        	type = "tp";//address.substring(0,2);
        	port = "6923";
        	host = address.substring(5,address.lastIndexOf(':'));
           	sock = new TCPSocket();
            sock.setServerAddress(host,Integer.parseInt(port));
        }
        else if (address.substring(0,4).equals("http") && !address.substring(0,5).equals("https")) {
        	type = "http";//address.substring(0,4);
        	port = "80";
        	//System.out.println(": is at- " + address.lastIndexOf(':'));
        	host = address.substring(7,address.lastIndexOf(':'));
         	sock = new HTTPSocket();
            sock.setServerAddress(host,Integer.parseInt(port));
        }
        else{
            logger.log("Type of connection to create was not known", Logger.ERROR);
            return false;
        }
        
        /*
        
        int tpos = address.find("://");
        if(tpos != address.nposarg0){
            type = address.substr(0, tpos);
            tpos += 3;
        }else{
            tpos = 0;
        }
        int ppos = address.rfind(':');
        int bpos = address.rfind(']');
        if(ppos != std::string::npos && ppos > tpos){
            if(bpos == std::string::npos || ppos > bpos){
                port = address.substr(ppos + 1);
            }else{
                ppos = std::string::npos;
            }
        }else{
            ppos = std::string::npos;
        }
        host = address.substr(tpos, ppos - tpos);

        TPSocket sock = null;
        if(type.empty() || type == "tp"){
            if(port.empty()){
                port = "6923";
            }
            sock = new TcpSocket();
            static_cast<TcpSocket*>(sock).setServerAddr(host.c_str(), port.c_str());
        }else if(type == "tps"){
            if(port.empty()){
                port = "6924";
            }
            

            sock = new TpsSocket();
            static_cast<TpsSocket*>(sock).setServerAddr(host.c_str(), port.c_str());

        }else if(type == "http"){
            if(port.empty()){
                port = "80";
            }
            //TODO
        }else if(type == "https"){
            if(port.empty()){
                port = "443";
            }

            sock = new HttpsSocket();
            static_cast<HttpsSocket*>(sock).setServerAddr(host.c_str(), port.c_str());
            //need to have set proxy address already (is static)

        }else{
            logger.error("Type of connection to create was not known");
            return false;
        }
        if(sock != null){
            return connect(sock);
        }else{
            return false;
        }*/
    }

    /*! \brief Connects using a given TPSocket.
    Connects to a server using a given TPSocket.
    \param nsock The TPSocket to connect using.
    \return True if connected, false otherwise.
    */
    
    //TODO
    //Since this method is not being called, just not 
    //implemented for now.
    boolean connect(TPSocket nsock){
      /*  if(status != gsDisconnected){
            logger.warning("Already connected, ignoring connection attempt");
            return false;
        }
        sock = nsock;
        protocol.getFrameCodec().setSocket(sock);
        if(sock.connect()){
            logger.debug("Connection opened");
            status = gsConnecting;
            Connect cf = protocol.getFrameFactory().createConnect();
            cf.setClientString(std::string("libtpproto-cpp/") + VERSION + " " + clientid);
            uint32_t seqnum = protocol.getFrameCodec().sendFrame(cf);
            delete cf;
            
            std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
            Frame * reply = null;
            if(replies.size() >= 1){
                reply = replies.front();
            }
            
            if(reply != null && reply.getType() == ft02_OK){
                // expect OK back
                //  or maybe error
                status = gsConnected;
                if(statuslistener != null)
                    statuslistener.connected();
                logger.info("Connected");
                delete reply;
                updateCaches();
                return true;
            }else if(reply != null && reply.getType() == ft03_Redirect){
                //signal we are redirecting 
                if(statuslistener != null)
                    statuslistener.redirected(static_cast<Redirect*>(reply).getUrl());
                bool rtv = connect(static_cast<Redirect*>(reply).getUrl());
                delete reply;
                return rtv;
            }else{
                status = gsDisconnected;
                logger.error("Could not connect");
                sock.disconnect();
                if(reply != null)
                    delete reply;
            }
        }else{
            logger.error("Could not open socket to server");
        }*/
        return false;
    }
    
    /*! \brief Creates an account on the server.
    
    Sends a AccountCreate Frame to the server and waits for a reply.
    \param user The username to use.
    \param password The password for the account.
    \param email The user's email address.
    \param comment A comment to send.
    \return True if successful, false otherwise.
    */
    boolean createAccount(String user, String password, String email, String comment){
      if(status == GameStatus.gsConnected && sock.isConnected()){
          
    	  //create a new account
    	  Account account = protocol.getFrameFactory().createAccountCreate();
          account.setUser(user);	
          account.setPassword(password);
          account.setEmail(email);
          account.setComment(comment);
          
          long seqnum = protocol.getFrameCodec().sendFrame(account);
          //delete account;
          
          std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
          Frame  reply = null;
          if(replies.size() >= 1){
              reply = replies.front();
          }
          
          if(reply != null && reply.getType() == ft02_OK){
              // expect OK back
              //  or maybe error
              logger.info("Account created");
              delete reply;
              if(!sock.isConnected()){
                status = gsDisconnected;
                if(statuslistener != null)
                  statuslistener.disconnected();
              }else{
                status = gsLoggedIn;
                if(statuslistener != null)
                    statuslistener.loggedIn();
              }
              return true;
          }else{
              logger.warning("Did not create account");
              if(reply != null)
                  delete reply;
          }
        
      }
      if(!sock.isConnected()){
          status = gsDisconnected;
          if(statuslistener != null)
              statuslistener.disconnected();
      }
      return false;
    }

    /*! \brief Logs in to the server.
    
    Sends a Login Frame to the server and waits for a reply.
    \param username The username to connect as.
    \param password The password of the account of the username.
    \return True if successful, false otherwise.
    */
    bool login(string username, string password){
        if(status == gsConnected && sock.isConnected()){
            Login  login = protocol.getFrameFactory().createLogin();
            login.setUser(username);
            login.setPass(password);
            uint32_t seqnum = protocol.getFrameCodec().sendFrame(login);
            delete login;
            
            std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
            Frame * reply = null;
            if(replies.size() >= 1){
                reply = replies.front();
            }
            
            if(reply != null && reply.getType() == ft02_OK){
                // expect OK back
                //  or maybe error
                status = gsLoggedIn;
                if(statuslistener != null)
                    statuslistener.loggedIn();
                logger.info("Logged in");
                delete reply;
                return true;
            }else{
                logger.warning("Did not log in");
                if(reply != null)
                    delete reply;
            }
            
        }
        if(!sock.isConnected()){
            status = gsDisconnected;
            if(statuslistener != null)
                statuslistener.disconnected();
        }
        return false;
    }

    /*! \brief Disconnects from server.
    
    Closes the underlying TPSocket.
    */
    public void disconnect(){
        if(status != gsDisconnected && sock != null){
            sock.disconnect();
            logger.info("Disconnected");
            if(statuslistener != null)
                statuslistener.disconnected();
        }
        status = gsDisconnected;
    }

    /*! \brief Tells all the caches to update.
    Called automatically after logged in, and after EOT has finished.
    Call if you want the caches to be updated.
    */
    void updateCaches(){
        objectcache.update();
        boardcache.update();
        playercache.update();
        resourcecache.update();
        categorycache.update();
        designcache.update();
        componentcache.update();
        propertycache.update();
    }


    /*! \brief Gets objectids from the server.
    
    Gets the complete list of Object ids.
    \return The set of object id.
    */
    std::set<uint32_t> getObjectIds(){
        return objectcache.getObjectIds();
    }

    /*! \brief Gets an object from the server.
    
    Gets an object from the server and returns the Object.
    \param obid The Object id of the object to get.
    \return The Object.
    */
    Object* getObject(uint32_t obid){
        return objectcache.getObject(obid);
    }

    /*! \brief Gets the Universe Object.

    A handy method to get the Universe Object.
    \return The Object of the Universe.
    */
    Object* getUniverse(){
        return getObject(0);
    }

    /*! \brief Gets Orders from the server.
    
    This method sends the GetOrder Frame to the server and returns the
    Order Frames.
    \param obid The object to get the orders from.
    \param num The number of orders to get.
    \return List of Orders.
    */
    std::list<Order*> getOrders(uint32_t obid, uint32_t num){
        GetOrder* frame = protocol.getFrameFactory().createGetOrder();
        frame.setObjectId(obid);
        frame.addOrderRange(0, num);
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(frame);
        delete frame;
        std::list<Frame*> reply = protocol.getFrameCodec().recvFrames(seqnum);
        std::list<Order*> out;
        for(std::list<Frame*>::iterator itcurr = reply.begin(); itcurr != reply.end(); ++itcurr){
            Frame * ob = *itcurr;
            if(ob != null && ob.getType() == ft02_Order){
                out.push_back((Order*)ob);
            }else if(ob != null){
                logger.debug("Expecting order frames, but got %d instead", ob.getType());
                delete ob;
            }else{
                logger.debug("Expecting order frames, but got null");
                
            }
        }
        
        return out;
    }

    /*! \brief Gets an Orders from the server.
    
    This method sends the GetOrder Frame to the server and returns the
    Order Frame.
    \param obid The object to get the orders from.
    \param slot The slot number of the order to get.
    \return The order retreved.
    */
    Order* getOrder(uint32_t obid, uint32_t slot){
        GetOrder* frame = protocol.getFrameFactory().createGetOrder();
        frame.setObjectId(obid);
        frame.addOrderId(slot);
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(frame);
        delete frame;
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        
        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }
        if(reply != null){
            if(reply.getType() == ft02_Order){
                
                return dynamic_cast<Order*>(reply);
            }
            delete reply;
        }
        return null;
        
    }

    /*! \brief Creates an Order Frame of a given type.
    
    This method creates a new Order Frame, sets the protocol version and
    sets up the order for the given type, including parameters.
    \param type The type number for the order type.
    \return The new Order.
    */
    Order* createOrderFrame(int type){
        return protocol.getFrameBuilder().buildOrder(type);
    }

    /*! \brief Inserts an Order into the objects order queue.
    
    \param frame The Order to insert.
    \returns True if successful, false otherwise.
    */
    bool insertOrder(Order* frame){
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(frame);
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }
        if(reply != null){
            if(reply.getType() == ft02_OK){
                
                delete reply;
                
                return true;
            }else{
                logger.debug("Expected ok frame, got %d", reply.getType());
            }
            delete reply;
        }else{
            logger.debug("Expected ok frame, got null");
        }
        return false;
    }

    /*! \brief Replaces a current Order with a new one.
    
    First inserts the new Order, then removes the old one.
    Can fail with or without the new order in the order queue.
    \param frame The Order that will replace the current one.
    \return True if successful, false otherwise.
    */
    bool replaceOrder(Order* frame){
        if(frame.getSlot() >= 0 && insertOrder(frame)){
            if(removeOrder(frame.getObjectId(), frame.getSlot() + 1) == 1){
                return true;
            }
        }
        return false;
    }

    /*! \brief Probes an Order.
    
    Sends the order to the server to be probed, returning the order that would have been added
    to the object.
    \param frame The Order to probe.
    \return The reply Order with read-only fields filled.
    */
    Order* probeOrder(Order* frame){
        ProbeOrder * fr = protocol.getFrameFactory().createProbeOrder();
        fr.copyFromOrder(frame);
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(fr);
        delete fr;
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }
        
        if(reply == null || reply.getType() != ft02_Order){
            logger.error("The returned frame isn't an order");
        }
        
        return static_cast<Order*>(reply);
        
    }

    /*! \brief Removes an Order from the server.
    
    Sends the RemoveOrder frame to the server and receives reply.
    \param obid The Object to remove the order from.
    \param slot The slot that should have it's order removed.
    \return True if sucessful, false otherwise.
    */
    bool removeOrder(uint32_t obid, uint32_t slot){
        RemoveOrder* ro = protocol.getFrameFactory().createRemoveOrder();
        ro.setObjectId(obid);
        ro.removeOrderId(slot);
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(ro);
    
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }
        if(reply != null){
            if(reply.getType() == ft02_OK){
                
                delete reply;
                
                return true;
            }else{
                logger.debug("Expected ok frame, got %d", reply.getType());
            }
            delete reply;
        }else{
            logger.debug("Expected ok frame, got null");
        }
        return false;
    }

    /*! \brief Gets boardids from the server.
        
        Gets the complete list of Board ids.
    \return The set of board id.
    */
    std::set<uint32_t> getBoardIds(){
        return boardcache.getBoardIds();
    }

    /*! \brief Gets a Board from the server.
    
    Sends the GetBoard Frame and gets the Board back from the server.
    \param boardid The Board id for the board to get from the server.
    \return The Board, or null if error.
    */
    Board* getBoard(uint32_t boardid){
        return boardcache.getBoard(boardid);
    }

    /*! \brief Gets the logged in player's personal Board.
    
    A little easier and quicker than FrameCodec::getBoards.
    \return The Board object for the Player's Board.
    */
    Board* getPersonalBoard(){
        return getBoard(0);
    }

    /*! \brief Gets Messages from the server.
    
    Sends the GetMessage Frame and receives the Message frames.
    \param boardid The board ID of the board to get the messages from.
    \param num The number of messages to get.
    \return List of Messages.
    */
    std::list<Message*> getMessages(uint32_t boardid, uint32_t num){
        std::list<Message*> out;
        GetMessage* frame = protocol.getFrameFactory().createGetMessage();
        frame.setBoard(boardid);
        frame.addMessageRange(0, num);
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(frame);
        delete frame;
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        for(std::list<Frame*>::iterator itcurr = replies.begin(); itcurr != replies.end(); ++itcurr){
            Frame * ob = *itcurr;
            if(ob != null && ob.getType() == ft02_Message){
                out.push_back((Message*)ob);
            }else if(ob != null){
                logger.debug("Expecting message frames, but got %d instead", ob.getType());
            }else{
                logger.debug("Expecting message frames, but got null");
            }
        }
    
        return out;
    
    }

    /*! \brief Creates a Message object.
    \return A new message object.
    */
    Message* createMessage(){
        return protocol.getFrameFactory().createMessage();
    }

    /*! \brief Gets a Message from the server.
    
    Sends the GetMessage Frame and receives the Message frame.
    \param boardid The board ID of the board to get the message from.
    \param slot The slot number of the message to get.
    \return The Message.
    */
    Message* getMessage(uint32_t boardid, uint32_t slot){
        GetMessage* frame = protocol.getFrameFactory().createGetMessage();
        frame.setBoard(boardid);
        frame.addMessageId(slot);
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(frame);
        delete frame;
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);

        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }
        if(reply != null){
            if(reply.getType() == ft02_Message){
                
                return dynamic_cast<Message*>(reply);
            }
            delete reply;
        }
        return null;
    }

    /*! \brief Posts a Message to the server.
    
    Sends the Message Frame to the server.
    \param frame The Message to post.
    \return True if successful, false otherwise.
    */
    bool postMessage(Message* frame){
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(frame);
        delete frame;
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }
        if(reply != null){
            if(reply.getType() == ft02_OK){
                
                delete reply;
                
                return true;
            }
            delete reply;
        }
        return false;
    }

    /*! \brief Removes messages from the server.
    
    Sends the RemoveMessage frame and receives the replies.
    \param boardid The board id of the board to remove the message from.
    \param slot The slot to remove the message from.
    \return True if message is removed, false otherwise.
    */
    bool removeMessage(uint32_t boardid, uint32_t slot){
        RemoveMessage* frame = protocol.getFrameFactory().createRemoveMessage();
        frame.setBoard(boardid);
        frame.removeMessageId(slot);
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(frame);
        delete frame;
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        
        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }

        if(reply != null && reply.getType() == ft02_OK){
            delete reply;
            return true;
            
        }
        delete reply;
        
        return false;
    }


    /*! \brief Gets a Resource Description from the server.
    
    Gets and returns a Resource Description from the server..
    \param restype The type of resource to get the description for.
    \return The ResourceDescription..
    */
    ResourceDescription* getResourceDescription(uint32_t restype){
        return resourcecache.getResourceDescription(restype);
    }

    /*! \brief Gets a player from the server.
    
    Gets a player from the server and returns the Player.
    \param playerid The player id of the player to get.
    \return The Player.
    */
    Player* getPlayer(uint32_t playerid){
        return playercache.getPlayer(playerid);
    }

    /*! \brief Gets Category ids from the server.
    
    Gets the complete list of Category ids.
    \return The set of category id.
    */
    std::set<uint32_t> getCategoryIds(){
        return categorycache.getCategoryIds();
    }

    /*! \brief Gets a category from the server.
    
    Gets a category from the server and returns it.
    \param catid The Category id of the category to get.
    \return The Category.
    */
    Category* getCategory(uint32_t catid){
        return categorycache.getCategory(catid);
    }

    /*! \brief Creates a Category object.
    \return A new category object.
    */
    Category* createCategory(){
        return protocol.getFrameFactory().createCategory();
    }

    /*! \brief Adds a Category to the server.
    
    Sends the Category Frame to the server.
    \param cat The Category to add.
    \return True if successful, false otherwise.
    */
    bool addCategory(Category* cat){
        return categorycache.addCategory(cat);
    }

    /*! \brief Removes a category from the server.
    
    Sends the RemoveCategory frame and receives the reply.
    \param catid The Category Id to remove.
    \return True if sucessful, false otherwise.
  */
    bool removeCategory(uint32_t catid){
        return categorycache.removeCategory(catid);
    }
    

    /*! \brief Gets designids from the server.
    
    Gets the complete list of Design ids.
    \return The set of design id.
    */
    std::set<uint32_t> getDesignIds(){
        return designcache.getDesignIds();
    }

    /*! \brief Gets a design from the server.
    
    Gets a design from the server and returns the Design.
    \param designid The Design id of the design to get.
    \return The Design.
    */
    Design* getDesign(uint32_t designid){
        return designcache.getDesign(designid);
    }

    /*! \brief Creates a Design object.
    \return A new design object.
    */
    Design* createDesign(){
        return protocol.getFrameFactory().createDesign();
    }

    /*! \brief Adds a Design to the server.
    
    Sends the Design Frame to the server.
    \param d The Design to add.
    \return True if successful, false otherwise.
    */
    bool addDesign(Design* d){
        return designcache.addDesign(d);
    }

    /*! \brief Modifies a Design on the server.
    
    Sends a ModifyDesign Frame to the server.
    \param d The Design to modify.
    \return True if successful, false otherwise.
    */
    bool modifyDesign(Design* d){
        return designcache.modifyDesign(d);
    }
    
    /*! \brief Removes a design from the server.
    
    Sends the RemoveDesign frame and receives the reply.
    \param designid The Design Id to remove.
    \return True if sucessful, false otherwise.
    */
    bool removeDesign(uint32_t designid){
        return designcache.removeDesign(designid);
    }


    /*! \brief Gets component ids from the server.
    
    Gets the complete list of Component ids.
    \return The set of component id.
    */
    std::set<uint32_t> getComponentIds(){
        return componentcache.getComponentIds();
    }

    /*! \brief Gets a Component from the server.
    
    Gets a component from the server and returns the Component.
    \param compid The Component id of the component to get.
    \return The Component.
    */
    Component* getComponent(uint32_t compid){
        return componentcache.getComponent(compid);
    }


    /*! \brief Gets propertyids from the server.
    
    Gets the complete list of Property ids.
    \return The set of property id.
    */
    std::set<uint32_t> getPropertyIds(){
        return propertycache.getPropertyIds();
    }

    /*! \brief Gets a Property from the server.
    
    Gets a property from the server and returns the Property.
    \param propid The property id of the property to get.
    \return The Property.
    */
    Property* getProperty(uint32_t propid){
        return propertycache.getProperty(propid);
    }


    /*! \brief Gets the time remaining before the end of turn.
    
    Fetches the time remaining till the end of turn from the server.
    \returns The time in seconds before the end of turn, or
    -1 if there was an error.
    */
    int getTimeRemaining(){
        GetTime* gt = protocol.getFrameFactory().createGetTimeRemaining();
        
        uint32_t seqnum = protocol.getFrameCodec().sendFrame(gt);
        delete gt;
        std::list<Frame*> replies = protocol.getFrameCodec().recvFrames(seqnum);
        Frame * reply = null;
        if(replies.size() >= 1){
            reply = replies.front();
        }
        if(reply != null && reply.getType() == ft02_Time_Remaining){
            int time = ((TimeRemaining*)reply).getTimeRemaining();
            delete reply;
            if(statuslistener != null)
                statuslistener.timeToEot(time);
            return time;
        }
        if(reply != null)
            delete reply;
        return -1;
    }


}


