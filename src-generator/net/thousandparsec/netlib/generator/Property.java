package net.thousandparsec.netlib.generator;

import net.thousandparsec.netlib.generator.StructureHandler.PropertyType;

class Property
{
	final String name;
	final PropertyType type;
	final String targetType;
	final String targetSubtype;
	/**
	 * {@code 0} means that it's variable; otherwise in bytes. Negative size for
	 * {@link PropertyType#enumeration} means that the enumeration is a mask,
	 * not a single value.
	 */
	final int size;
	final boolean readOnly;

	Property(String name, PropertyType type, String targetType, String targetSubtype, int size, boolean readOnly)
	{
		this.name=name;
		this.type=type;
		this.targetType=targetType;
		this.targetSubtype=targetSubtype;
		this.size=size;
		this.readOnly=readOnly;
	}
}
