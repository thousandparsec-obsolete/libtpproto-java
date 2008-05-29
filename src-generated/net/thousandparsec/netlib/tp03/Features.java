package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Features
 */
public class Features extends Response
{
	public static final int FRAME_TYPE=26;

	protected Features(int id)
	{
		super(id);
	}

	public Features()
	{
		super(FRAME_TYPE);
	}

	/**
	 * List of available features
	 */
	public static class FeaturesType extends TPObject
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public FeaturesType()
		{
		}

		/**
		 * Features which the server supports.
		 */
                class FeatureCode{
                    public final int VALUE;
                    
                    private FeatureCode(int value){
                        VALUE= value;
                    }
                }
                class Feature{
                    
                }
		public enum Feature
		{
			$none$(-1),

			/**
			 * Secure Connection available on this port.
			 */
			SecureHere(0x1),

			/**
			 * Secure Connection available on another port.
			 */
			SecureThere(0x2),

			/**
			 * HTTP Tunneling available on this port.
			 */
			HTTPHere(0x3),

			/**
			 * HTTP Tunneling available on another port.
			 */
			HTTPThere(0x4),

			/**
			 * Support Keep alive frames.
			 */
			KeepAlive(0x5),

			/**
			 * Support server side property calculation.
			 */
			PropertyCalc(0x6),

			/**
			 * Account creation is allowed.
			 */
			AccountCreate(0x3E8),

			/**
			 * Sends Object ID Sequences in descending modified time order.
			 */
			DescObjectID(0x10000),

			/**
			 * Sends Order Description ID Sequences in descending modified time order.
			 */
			DescOrderID(0x10001),

			/**
			 * Sends Board ID Sequences in descending modified time order.
			 */
			DescBoardID(0x10002),

			/**
			 * Sends Resource Description ID Sequences in descending modified time order.
			 */
			DescResourceID(0x10003),

			/**
			 * Sends Category Description ID Sequences in descending modified time order.
			 */
			DescCategoryID(0x10004),

			/**
			 * Sends Design ID Sequences in descending modified time order.
			 */
			DescDesignID(0x10005),

			/**
			 * Sends Component ID Sequences in descending modified time order.
			 */
			DescComponentID(0x10006),

			/**
			 * Sends Property ID Sequences in descending modified time order.
			 */
			DescPropertyID(0x10007),

			;
			public final int value;
			private Feature(int value)
			{
				this.value=value;
			}
		}

		private Feature feature=Feature.$none$;

		public Feature getFeature()
		{
			return this.feature;
		}

		public void setFeature(Feature value)
		{
			this.feature=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			out.writeInteger(this.feature.value);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public FeaturesType(Feature feature)
		{
			setFeature(feature);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public FeaturesType(FeaturesType copy)
		{
			setFeature(copy.getFeature());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		FeaturesType(TPDataInput in) throws IOException
		{
			feature: {
				int value=in.readInteger32();
                                Feature[] fval = Feature.values();
                                for (int i = 0; i < fval.length; i++){
                                    if( fval[i].value == value){
                                        this.feature=fval[i];
                                        break feature;
                                    }
                                }

				throw new IOException("Invalid value for enum 'feature': "+value);
			}
		}

		public String toString()
		{
			return "{FeaturesType"
                            + "; feature: "
                            + String.valueOf(this.feature)
                            + "}";
			
		}

	}

	private java.util.Vector features = new java.util.Vector();
	public java.util.Vector getFeatures()
	{
		return this.features;
	}

	private void setFeatures(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i ++){
                    this.features.addElement(new FeaturesType((FeaturesType)value.elementAt(i)));
                }

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
			 + findByteLength(this.features);
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.features.size());
                for (int i =0; i < features.size(); i++){
                    ((FeaturesType)features.elementAt(i)).write(out, conn);
                }
		
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Features(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.features.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.features.addElement(new FeaturesType(in));
	}

	public String toString()
	{
		return "{Features"
                    + "; features: "
                    + String.valueOf(this.features)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
