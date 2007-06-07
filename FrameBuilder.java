


/**
 * Create a Frame 
 * 
 * @author Ishwor Gurung
 *
 */
public class FrameBuilder {

	private ProtocolLayer protocolLayer;
	
	public FrameBuilder() {}
	
	
	/**
	 * Build one frame from frametype
	 * @param frameType type of fram
	 * @param data frame's data
	 * @return return a built frame
	 */
	public Frame buildFrame(int frameType, Buffer data) {		
		
		Frame frame = null;
		
		switch(frameType){
        case FrameType.FT.ft02_OK:
            frame = protocolLayer.getFrameFactory().createOK();
            break;

        case FrameType.FT.ft02_Fail:
            frame = protocolLayer.getFrameFactory().createFail();
            break;

        case FrameType.FT.ft02_Sequence:
            frame = protocolLayer.getFrameFactory().createSequence();
            break;

        case FrameType.FT.ft02_Object:
           //TODO
        	//frame = buildObject(data.peekInt(4));
            break;

        case FrameType.FT.ft02_OrderDesc:
            frame = protocolLayer.getFrameFactory().createOrderDescription();
            break;

        case FrameType.FT.ft02_Order:
            //TODO frame = buildOrder(data.peekInt(8));
            break;

        case FrameType.FT.ft02_Time_Remaining:
            frame = protocolLayer.getFrameFactory().createTimeRemaining();
            break;

        case FrameType.FT.ft02_Board:
            frame = protocolLayer.getFrameFactory().createBoard();
            break;

        case FrameType.FT.ft02_Message:
            frame = protocolLayer.getFrameFactory().createMessage();
            break;

        case FrameType.FT.ft02_ResDesc:
            frame = protocolLayer.getFrameFactory().createResourceDescription();
            break;
            
        case FrameType.FT.ft03_Redirect:
            frame = protocolLayer.getFrameFactory().createRedirect();
            break;
            
        case FrameType.FT.ft03_Features:
            frame = protocolLayer.getFrameFactory().createFeatures();
            break;
            
        case FrameType.FT.ft03_ObjectIds:
            frame = protocolLayer.getFrameFactory().createObjectIdsList();
            break;
            
        case FrameType.FT.ft03_OrderTypes:
            frame = protocolLayer.getFrameFactory().createOrderTypesList();
            break;
            
        case FrameType.FT.ft03_BoardIds:
            frame = protocolLayer.getFrameFactory().createBoardIdsList();
            break;
            
        case FrameType.FT.ft03_ResourceTypes:
            frame = protocolLayer.getFrameFactory().createResourceTypesList();
            break;
            
        case FrameType.FT.ft03_Player:
            frame = protocolLayer.getFrameFactory().createPlayer();
            break;
            
        case FrameType.FT.ft03_Category:
            frame = protocolLayer.getFrameFactory().createCategory();
            break;
            
        case FrameType.FT.ft03_CategoryIds:
            frame = protocolLayer.getFrameFactory().createCategoryIdsList();
            break;
            
        case FrameType.FT.ft03_Design:
            frame = protocolLayer.getFrameFactory().createDesign();
            break;
            
        case FrameType.FT.ft03_DesignIds:
            frame = protocolLayer.getFrameFactory().createDesignIdsList();
            break;
            
        case FrameType.FT.ft03_Component:
            frame = protocolLayer.getFrameFactory().createComponent();
            break;
            
        case FrameType.FT.ft03_ComponentIds:
            frame = protocolLayer.getFrameFactory().createComponentIdsList();
            break;
            
        case FrameType.FT.ft03_Property:
            frame = protocolLayer.getFrameFactory().createProperty();
            break;
            
        case FrameType.FT.ft03_PropertyIds:
            frame = protocolLayer.getFrameFactory().createPropertyIdsList();
            break;
		}
		
		
		return frame;
		
	}
	
	/**
	 * set the protocol layer
	 * @param protocolLayer protocol layer
	 */
	public void setProtocolLayer(ProtocolLayer protocolLayer) {
		this.protocolLayer = protocolLayer;		
	}
	
	/**
	 * get the protocol layer 
	 * @return protocol layer
	 */
	public ProtocolLayer getProtocolLayer() {
		return this.protocolLayer;
	}
	
}
