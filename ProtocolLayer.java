
/**
 * 
 * ProtocolLayer holds a central reference to FrameCodec, FrameFactory
 * and FrameBuilder; It essentially sets the protocol layer which can then 
 * be accessed from other classes.
 * 
 * @author Ishwor Gurung
 *
 */
public class ProtocolLayer {
	
	private FrameCodec frameCodec;
	private FrameFactory frameFactory;
	private FrameBuilder frameBuilder;
	
	public ProtocolLayer() {
	     frameCodec = new FrameCodec();
	     frameFactory = new FrameFactory();
	     frameBuilder = new FrameBuilder();
	     
	     frameCodec.setProtocolLayer(this);
	     frameFactory.setProtocolLayer(this);
	     frameBuilder.setProtocolLayer(this);
	}
	
	public void setFrameCodec(FrameCodec frameCodec) {
		
		this.frameCodec = frameCodec;
		
		if (frameCodec == null) {
			this.frameCodec = new FrameCodec();			
		}
		
		//Set the object that called this method as the protocol layer
		this.frameCodec.setProtocolLayer(this);  
		
	}
	
	public void setFrameFactory(FrameFactory frameFactory) {
		
		this.frameFactory = frameFactory;
		
		if(frameFactory == null) {
			this.frameFactory = new FrameFactory();
		}
		
		this.frameFactory.setProtocolLayer(this);
		
	}
	
	public void setFrameBuilder(FrameBuilder frameBuilder) {
		
		this.frameBuilder = frameBuilder;
		
		if(frameBuilder == null) {
			this.frameBuilder = new FrameBuilder();
		}
		
		this.frameBuilder.setProtocolLayer(this);		
	}
	
	
	public FrameCodec getFrameCodec() {
		return this.frameCodec;
	}
	
	public FrameFactory getFrameFactory() {
		return this.frameFactory;
	}

	public FrameBuilder getFrameBuilder() {
		return this.frameBuilder;
	}
	
	
	
}
