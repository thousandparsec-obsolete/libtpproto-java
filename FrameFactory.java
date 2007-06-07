


public class FrameFactory {

	private int protocolVersion;
	public void setProtocolLayer(ProtocolLayer layer) {
		
	
		
	}
   /**
    * Sets the protocol version the frames should have.
    * @param pv The protocol version number.
    **/
    void setProtocolVersion(int pv){
        protocolVersion = pv;
    }
    
    /*! \brief Gets the protocol version.
    \return The protocol version number.
    */
    public int getProtocolVersion() {
        return protocolVersion;
    }

    public Account createAccountCreate() {
    	 Account f = new Account(null,null,null,null);
    	 f.setProtocolVersion(protocolVersion);
    	 return f;
    }
	public Frame createOK() {
        OkFrame ok = new OkFrame();
        ok.setProtocolVersion(this.protocolVersion);
        return ok;
	}

	public Frame createFail() {
		// TODO Auto-generated method stub
		return null;
	}

	public Frame createSequence() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createOrderDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createTimeRemaining() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createResourceDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createRedirect() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createFeatures() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createObjectIdsList() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createOrderTypesList() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createBoardIdsList() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createResourceTypesList() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createCategory() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createCategoryIdsList() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createDesign() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createDesignIdsList() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createComponent() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createComponentIdsList() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createProperty() {
		// TODO Auto-generated method stub
		return null;
	}
	public Frame createPropertyIdsList() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
