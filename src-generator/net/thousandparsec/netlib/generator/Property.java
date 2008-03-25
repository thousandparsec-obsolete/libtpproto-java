package net.thousandparsec.netlib.generator;

import net.thousandparsec.netlib.generator.StructureHandler.PropertyType;

public class Property
{
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
