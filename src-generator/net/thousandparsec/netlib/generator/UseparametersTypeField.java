package net.thousandparsec.netlib.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseparametersTypeField
{
	private static final Pattern TYPEFIELD_REGEX=Pattern.compile("(\\w+)(=(\\w+)(\\[(\\w+)\\])?.(((\\w+)([.:]|$))+))?");
	private static final int TYPEFIELD_REGEX_GROUP_LOCAL=1;
//	private static final int TYPEFIELD_REGEX_GROUP_INDIRECT_CLAUSE=2;
	private static final int TYPEFIELD_REGEX_GROUP_INDIRECT_FRAME_NAME=3;
//	private static final int TYPEFIELD_REGEX_GROUP_INDIRECT_CHECK_CLAUSE=4;
	private static final int TYPEFIELD_REGEX_GROUP_INDIRECT_CHECK_NAME=5;
	private static final int TYPEFIELD_REGEX_GROUP_SEARCH_PATH=6;
//	private static final int TYPEFIELD_REGEX_GROUP_SEARCH_PATH_ELEMENT=7;
//	private static final int TYPEFIELD_REGEX_GROUP_SEARCH_NAME=8;
//	private static final int TYPEFIELD_REGEX_GROUP_SEARCH_TYPE=9;
	private static final Pattern TYPEFIELD_REGEX_PATH=Pattern.compile("(\\w+)([.:]|$)");
	private static final int TYPEFIELD_REGEX_PATH_NAME=1;
	private static final int TYPEFIELD_REGEX_PATH_TYPE=2;

	private static SearchPathElement.Type typeFor(String typeStr)
	{
		if (typeStr == null || typeStr.length() == 0)
			return SearchPathElement.Type.FINAL;
		if (typeStr.length() != 1)
			throw new IllegalArgumentException("Internal error?");
		switch (typeStr.charAt(0))
		{
			case '.':
				return SearchPathElement.Type.FIELD;
			case ':':
				return SearchPathElement.Type.LIST;
			default:
				throw new IllegalArgumentException("Internal error?");
		}
	}

	public final String localField;
	public final String indirectFrame;
	public final String indirectFrameCheckField;
	public final List<SearchPathElement> searchPath;

	public UseparametersTypeField(String value)
	{
		Matcher matcher=TYPEFIELD_REGEX.matcher(value);
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("Typefield value \"%s\" does not match typefield regular expression", value));

		this.localField=matcher.group(TYPEFIELD_REGEX_GROUP_LOCAL);
		this.indirectFrame=matcher.group(TYPEFIELD_REGEX_GROUP_INDIRECT_FRAME_NAME);
		List<SearchPathElement> searchPath;
		if (indirectFrame == null)
		{
			this.indirectFrameCheckField=null;
			searchPath=null;
		}
		else
		{
			this.indirectFrameCheckField=matcher.group(TYPEFIELD_REGEX_GROUP_INDIRECT_CHECK_NAME);
			searchPath=new ArrayList<SearchPathElement>();

			matcher=TYPEFIELD_REGEX_PATH.matcher(matcher.group(TYPEFIELD_REGEX_GROUP_SEARCH_PATH));
			while (matcher.find())
				searchPath.add(
					new SearchPathElement(
						typeFor(matcher.group(TYPEFIELD_REGEX_PATH_TYPE)),
						matcher.group(TYPEFIELD_REGEX_PATH_NAME)));
		}
		this.searchPath=searchPath == null ? null : Collections.unmodifiableList(searchPath);
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
