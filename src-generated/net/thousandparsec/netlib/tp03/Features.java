package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Features
 */
public class Features extends Response
{
	protected Features(int id)
	{
		super(id);
	}

	public Features()
	{
		super(26);
	}

	/**
	 * List of available features
	 */
	public static class FeaturesType extends TPObject<TP03Visitor>
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
		public enum Feature
		{
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

		private Feature feature;

		public Feature getFeature()
		{
			return this.feature;
		}

		public void setFeature(Feature value)
		{
			this.feature=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
		@SuppressWarnings("unused")
		FeaturesType(TPDataInput in) throws IOException
		{
			feature: {
				int value=in.readInteger32();
				for (Feature e : Feature.values())
					if (e.value == value)
					{
						this.feature=e;
						break feature;
					}
				throw new IOException("Invalid value for enum 'feature': "+value);
			}
		}

	}

	private java.util.List<FeaturesType> features=new java.util.ArrayList<FeaturesType>();

	public java.util.List<FeaturesType> getFeatures()
	{
		return this.features;
	}

	@SuppressWarnings("unused")
	private void setFeatures(java.util.List<FeaturesType> value)
	{
		for (FeaturesType object : value)
			this.features.add(new FeaturesType(object));
	}

	@Override
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.features);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.features.size());
		for (FeaturesType object : this.features)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Features(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.features.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.features.add(new FeaturesType(in));
	}

}
