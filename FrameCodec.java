
public class FrameCodec {

	private ProtocolLayer protocolLayer;
	private Logger logger;
	private TPSocket sock;
	private long nextseqnum;
	private Random random;
	
	public FrameCodec(){
		random = new Random();
		nexseqnum = random.
		
	}
	
	public void setSocket(TPSocket socket) {
		this.sock = socket;
		
	}
	public void setProtocolLayer(ProtocolLayer layer) {
		
		this.protocolLayer = layer;
		
	}

	public void setAsyncFrameListener(GameLayer.GameLayerAsyncFrameListener asyncframes) {
		// TODO Auto-generated method stub
	
	}

	public void setLogger(Logger nlog) throws InvalidLoggingTypeException {
		// TODO Auto-generated method stub
		logger = nlog;
		logger.log("logger set", Logger.DEBUG);		
	}

	public long sendFrame(Frame f) {
		// TODO Auto-generated method stub
		if (sock.isConnected()) {
			Buffer data = new Buffer();
			f.packBuffer(data);
			Buffer header = new Buffer();
			long real_seqnum = nextseqnum;
			
			//f.packBuffer(data);
			
			
		}
		
		return 0;
	}

	

}
