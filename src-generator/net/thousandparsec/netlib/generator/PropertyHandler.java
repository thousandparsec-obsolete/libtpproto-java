package net.thousandparsec.netlib.generator;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * {@link PropertyHandler} and its subclasses handle elements contained in a
 * {@code structure} element, where each represents one "property" of a packet
 * or another property ({@link Property.PropertyType#group} or
 * {@link Property.PropertyType#list}).
 * <p>
 * A property has additional properties, like {@link #getValueType() value type}
 * and {@link #getName() name}; value type is the name of a type used (or
 * generated) for the property. Name is inferred from the {@code name}
 * subelement and the value type can be given upfront, if it is known, or
 * created by the subclass; this happens after this handler is created, and so
 * these two properties cannot be {@code final} and have to be set before the
 * element ends and the handler is ready to generate code.
 * 
 * @author ksobolewski
 */
class PropertyHandler extends StructuredElementHandler<StructureHandler<?>>
{
	private String valueType;
	private String valueSubtype;
	protected final Property.PropertyType type;
	private final int size;
	protected final boolean readOnly;
	private String name;
	private StringBuilder nameCollector;
	private UseparametersTypeField useparametersTypeField;

	/**
	 * Initialises wothout a target's language type name, when it's unknown at
	 * the beginning of this element. Remember to set it with
	 * {@link #setValueType(String)} later.
	 */
	PropertyHandler(StructureHandler<?> parent, Property.PropertyType type, int size, boolean readOnly)
	{
		this(parent, type, null, null, size, readOnly);
	}

	PropertyHandler(StructureHandler<?> parent, Property.PropertyType type, String valueType, String valueSubtype, int size, boolean readOnly)
	{
		super(parent);
		this.valueType=valueType;
		this.valueSubtype=valueSubtype;
		this.type=type;
		this.size=size;
		this.readOnly=readOnly;
		this.name=null;
	}

	protected String getValueType()
	{
		return valueType;
	}

	protected void setValueType(String valueType)
	{
		this.valueType=valueType;
	}

	protected String getValueSubtype()
	{
		return valueSubtype;
	}

	protected void setValueSybtype(String valueSubtype)
	{
		this.valueSubtype=valueSubtype;
	}

	protected String getName()
	{
		return name;
	}

	protected void setName(String name)
	{
		this.name=name;
	}

	protected UseparametersTypeField getUseparametersTypeField()
	{
		return useparametersTypeField;
	}

	protected void setUseparametersTypeField(UseparametersTypeField useparametersIndirectFrame)
	{
		this.useparametersTypeField=useparametersIndirectFrame;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		try
		{
			if (getDepth() == 0)
			{
				super.startElement(uri, localName, name, atts);
				if (localName.equals("name"))
					//do you hate XML already?
					pushHandler(new TextCollectorHandler(this, nameCollector=new StringBuilder()));
				else if (localName.equals("description"))
					pushHandler(new TextCommentHandler(this, parent.generator.generator, parent.level));
				else if (localName.equals("subtype"))
				{
					//subtype means that the value is taken from the subclass - we handle it manually where it's needed
				}
			}
			else
				super.startElement(uri, localName, name, atts);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		try
		{
			if (getDepth() == 1 && localName.equals("name"))
			{
				setName(nameCollector.toString());
				nameCollector=null;
			}
			else if (getDepth() == 0)
			{
				if (this.name == null)
					throw new SAXException(String.format("Property without a name (type: %s)", type));
				if (this.valueType == null)
					throw new SAXException(String.format("Property with an unknown type (%s)", type));

				Property prop=parent.parent.addProperty(this.name, type, valueType, valueSubtype, size, readOnly, useparametersTypeField);

				parent.generator.generator.generatePropertyDefinition(parent.level, prop);
			}
			super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
