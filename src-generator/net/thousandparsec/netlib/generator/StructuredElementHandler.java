package net.thousandparsec.netlib.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.thousandparsec.netlib.generator.StructureHandler.PropertyType;

/**
 * This is an abstract base class for elements which may contain a
 * {@code structure} element. It is used to collect properties described by the
 * structure and return them as a {@link List} of {@link Property} objects.
 * 
 * @author ksobolewski
 */
abstract class StructuredElementHandler<P extends StackedHandler<?>> extends StackedHandler<P>
{
	private final List<Property> properties;

	public StructuredElementHandler(P parent)
	{
		super(parent);
		this.properties=new ArrayList<Property>();
	}

	protected Property addProperty(String name, PropertyType type, String targetType, String targetSubtype, int size, boolean readOnly, UseparametersTypeField useparametersTypeField)
	{
		Property property=new Property(name, type, targetType, targetSubtype, size, readOnly, useparametersTypeField);
		properties.add(property);
		return property;
	}

	public List<Property> getProperties()
	{
		return Collections.unmodifiableList(properties);
	}
}
