package net.thousandparsec.netlib.generator;

import java.util.List;

public class UseparametersTypeField
{
	public final String localField;
	public final String indirectFrame;
	public final String indirectFrameCheckField;
	public final List<SearchPathElement> searchPath;

	UseparametersTypeField(String localField, String indirectFrame, String indirectFrameCheckField, List<SearchPathElement> searchPath)
	{
		this.localField=localField;
		this.indirectFrame=indirectFrame;
		this.indirectFrameCheckField=indirectFrameCheckField;
		this.searchPath=searchPath;
	}

	public boolean isIndirect()
	{
		return this.indirectFrame != null;
	}

	public static class SearchPathElement
	{
		public static enum Type
		{
			FIELD,
			LIST,
			FINAL;
		}

		public final Type type;
		public final String property;

		SearchPathElement(Type type, String property)
		{
			this.type=type;
			this.property=property;
		}
	}
}
