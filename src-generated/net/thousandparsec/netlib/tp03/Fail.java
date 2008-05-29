package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public class Fail extends Response
{
	public static final int FRAME_TYPE=1;

	protected Fail(int id)
	{
		super(id);
	}

	public Fail()
	{
		super(FRAME_TYPE);
	}

	/**
	 * Text message of the error.
	 */
        static class ErrorCode
        {
            public final int VALUE;
            
            private ErrorCode(int value){
                VALUE= value;
            }
            
        }
        static class Code
        {
            static ErrorCode[] codes = new ErrorCode[7];
            static final ErrorCode $none$ = new ErrorCode(-1);
            static final ErrorCode protocol = new ErrorCode(0);
            static final ErrorCode Frame = new ErrorCode(1);
            static final ErrorCode UnavailablePermanently = new ErrorCode(2);
            static final ErrorCode UnavailableTemporarily = new ErrorCode(3);
            static final ErrorCode NoSuchThing = new ErrorCode(4);
            static final ErrorCode PermissionDenied = new ErrorCode(5);
            
            
            static ErrorCode[] values(){
                /* is there a better way to handle this? */
                codes[0] = $none$;
                codes[1] = protocol;
                codes[2] = Frame;
                codes[3] = UnavailablePermanently;
                codes[4] = UnavailableTemporarily;
                codes[5] = NoSuchThing;
                codes[6] = PermissionDenied;
                return codes;
            }
        }

            
	/*public enum Code
	{
		$none$(-1),

		/**
		 * Protocol Error, Something went wrong with the protocol
		 *
		Protocol(0),

		/**
		 * Frame Error, One of the frames sent was bad or corrupted
		 *
		Frame(1),

		/**
		 * Unavailable Permanently, This operation is unavailable
		 *
		UnavailablePermanently(2),

		/**
		 * Unavailable Temporarily, This operation is unavailable at this moment
		 *
		UnavailableTemporarily(3),

		/**
		 * No Such Thing, The object/order/message does not exist
		 *
		NoSuchThing(4),

		/**
		 * Permission Denied, You don't have permission to do this operation
		 *
		PermissionDenied(5),

		;
		public final int value;
		private Code(int value)
		{
			this.value=value;
		}
	}*/

	private ErrorCode code=Code.$none$;

	public ErrorCode getCode()
	{
		return this.code;
	}

	public void setCode(ErrorCode value)
	{
		this.code=value;
	}

	/**
	 * Text message of the error.
	 */
	private String result=new String();

	public String getResult()
	{
		return this.result;
	}

	public void setResult(String value)
	{
		this.result=value;
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called");
            visit(visitor);
        }
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + findByteLength(this.result);
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.code.VALUE);
		out.writeString(this.result);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Fail(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		code: {
			int value=in.readInteger32();
                        ErrorCode[] values = Code.values();
                        for (int i =0; i < values.length ; i++){
                            if (values[i].VALUE ==value){
                                this.code=values[i];
                                break code;
                            }
                        }

			throw new IOException("Invalid value for enum 'code': "+value);
		}
		this.result=in.readString();
	}

	public String toString()
	{
		return "{Fail"
                    + "; code: "
                    + String.valueOf(this.code)
                    + "; result: "
                    + String.valueOf(this.result)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
