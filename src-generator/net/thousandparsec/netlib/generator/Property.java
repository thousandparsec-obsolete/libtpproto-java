package net.thousandparsec.netlib.generator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Property
{
	static boolean booleanFromString(String s)
	{
		return "yes".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s);
	}

	public static enum PropertyType
	{
		character
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				int size=Integer.parseInt(xmlAtts.getValue("size"));
				return new PropertyHandler(parent, character, "byte[]", null, size, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		integer
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				int size=Integer.parseInt(xmlAtts.getValue("size"));
				return new PropertyHandler(parent, integer, generator.generator.getIntegerTypeName(size), null, size / 8, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		string
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				return new PropertyHandler(parent, string, "String", null, 0, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		enumeration
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				int size=Integer.parseInt(xmlAtts.getValue("size"));
				return new EnumerationHandler(parent, generator.generator.getIntegerTypeName(size), size / 8, xmlAtts.getValue("style"), booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		list
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				return new GroupHandler(parent, list, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		group
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				//DTD says that group is never read only, hmm
				return new GroupHandler(parent, group, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		useparameters
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				//I assume that useparameters is always read only
				return new UseparametersHandler(parent, xmlAtts.getValue("ref"));
			}
		},
		descparameter
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				//I assume that descparameter is always read only
				return new PropertyHandler(parent, descparameter, xmlAtts.getValue("ref")+"Desc", xmlAtts.getValue("typefield"), 0, true);
			}
		};

		@SuppressWarnings("unused") //stupid Eclipse :P
		abstract <P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> structureHandler, Generator generator, Attributes xmlAtts) throws NumberFormatException, SAXException;
	}

	public final String name;
	public final PropertyType type;
	public final String targetType;
	public final String targetSubtype;
	/**
	 * {@code 0} means that it's variable; otherwise in bytes. Negative size for
	 * {@link PropertyType#enumeration} means that the enumeration is a mask,
	 * not a single value.
	 */
	public final int size;
	public final boolean readOnly;
	/**
	 * I told ya it won't be pretty!
	 */
	public final UseparametersTypeField useparametersTypeField;

	Property(String name, PropertyType type, String targetType, String targetSubtype, int size, boolean readOnly, UseparametersTypeField useparametersTypeField)
	{
		this.name=name;
		this.type=type;
		this.targetType=targetType;
		this.targetSubtype=targetSubtype;
		this.size=size;
		this.readOnly=readOnly;
		this.useparametersTypeField=useparametersTypeField;
	}
}
