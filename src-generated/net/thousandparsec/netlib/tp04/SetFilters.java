package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Set Filters
 */
public class SetFilters extends Request
{
	public static final int FRAME_TYPE=64;

	protected SetFilters(int id)
	{
		super(id);
	}

	public SetFilters()
	{
		super(FRAME_TYPE);
	}

	/**
	 * List of filters to set.
	 */
	public static class FiltersType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public FiltersType()
		{
		}

		/**
		 * The feature id of the filter to set
		 */
		public enum Filter
		{
			$none$(-1),

			/**
			 * SSL filter is to be set up.
			 */
			FilterSSL(0x1000),

			/**
			 * Padding filter is to be set up.
			 */
			FilterPadding(0x1D00),

			;
			public final int value;
			private Filter(int value)
			{
				this.value=value;
			}
		}

		private Filter filter=Filter.$none$;

		public Filter getFilter()
		{
			return this.filter;
		}

		public void setFilter(Filter value)
		{
			this.filter=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.filter.value);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public FiltersType(Filter filter)
		{
			setFilter(filter);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public FiltersType(FiltersType copy)
		{
			setFilter(copy.getFilter());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		FiltersType(TPDataInput in) throws IOException
		{
			filter: {
				int value=in.readInteger32();
				for (Filter e : Filter.values())
					if (e.value == value)
					{
						this.filter=e;
						break filter;
					}
				throw new IOException("Invalid value for enum 'filter': "+value);
			}
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{FiltersType");
			buf.append("; filter: ");
			buf.append(String.valueOf(this.filter));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<FiltersType> filters=new java.util.ArrayList<FiltersType>();

	public java.util.List<FiltersType> getFilters()
	{
		return this.filters;
	}

	@SuppressWarnings("unused")
	private void setFilters(java.util.List<FiltersType> value)
	{
		for (FiltersType object : value)
			this.filters.add(new FiltersType(object));
	}

	@Override
	public void visit(TP04Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.filters);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.filters.size());
		for (FiltersType object : this.filters)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	SetFilters(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.filters.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.filters.add(new FiltersType(in));
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{SetFilters");
		buf.append("; filters: ");
		buf.append(String.valueOf(this.filters));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
