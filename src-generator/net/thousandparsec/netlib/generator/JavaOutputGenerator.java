package net.thousandparsec.netlib.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import net.thousandparsec.netlib.generator.StructureHandler.PropertyType;

public class JavaOutputGenerator implements OutputGenerator
{
	private static final String TARGET_BASE_PACKAGE="net.thousandparsec.netlib";

	private static String camelPrefix(String prefix, String s)
	{
		return prefix+s.substring(0, 1).toUpperCase()+s.substring(1);
	}

	private static void checkError(PrintWriter out) throws IOException
	{
		if (out.checkError())
			throw new IOException("Couldn't write to output file");
	}

	private File targetDir;
	private PrintWriter out;
	private String basePacket;
	private String packetName;
	private int id;
	private int compat;

	/**
	 * The constructor has no arguments to allow it eventually become easier to
	 * create by reflection, for example if the main generator class decides to
	 * create an instance of {@link OutputGenerator} dynamically.
	 */
	public JavaOutputGenerator()
	{
	}

	private File createTargetDir(File targetDir) throws IOException
	{
		for (String part : TARGET_BASE_PACKAGE.split("\\.", -1))
			targetDir=new File(targetDir, part);
		targetDir=new File(targetDir, String.format("tp%02d", compat));

		if (!targetDir.isDirectory() && !targetDir.mkdirs())
			throw new IOException("Can't create target directory: "+targetDir);

		return targetDir;
	}

	private PrintWriter createTargetFile(File targetFile) throws IOException
	{
		System.out.printf("Creating file %s%n", targetFile);
		return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8")));
	}

	public String getIntegerTypeName(int size) throws IllegalArgumentException
	{
		switch (size)
		{
			case 8:
				return "byte";
			case 32:
				return "int";
			case 64:
				return "long";
			default:
				throw new IllegalArgumentException("Invalid integer size: "+size);
		}
	}

	public void startProtocol(int compat)
	{
		this.compat=compat;
	}

	public void startPacket(File targetDir, String basePacket, String packetName, int id) throws IOException
	{
		this.targetDir=createTargetDir(targetDir);
		this.out=createTargetFile(new File(this.targetDir, packetName+".java"));
		this.basePacket=basePacket;
		this.packetName=packetName;
		this.id=id;

		printPreamble(basePacket);
	}

	private void printPreamble(String basePacket) throws IOException
	{
		out.printf("package %s.tp%02d;%n", TARGET_BASE_PACKAGE, compat);
		out.println();

		out.printf("import %s.*;%n", TARGET_BASE_PACKAGE);
		//ugly special case
		if (packetName.equals("Object"))
			out.printf("import %s.objects.*;%n", TARGET_BASE_PACKAGE);
		out.println();
		out.println("import java.io.IOException;");
		out.println();

		checkError(out);
	}

	public void endPacket(List<Property> properties) throws IOException
	{
		try
		{
			//ugly special cases (ugh!)
			if (packetName.equals("Object"))
			{
				Property objectProp=new Property(
					"object",
					PropertyType.object,
					String.format("GameObject<TP%02dVisitor>", compat),
					null,
					0,
					true);
				properties=new ConcatenationList<Property>(
					properties,
					Collections.singletonList(objectProp));

				printPropertyDef(1, objectProp);
				printPropertyGetter(1, objectProp);
				printPropertySetter(1, objectProp);
			}

			printVisitorMethod(0);
			printFindLengthMethod(0, properties);
			printWriteMethod(0, properties, true);

			printInputConstructor(0, packetName, properties, true);

			out.println("}");
		}
		finally
		{
			PrintWriter bak=out;
			this.targetDir=null;
			this.out=null;
			this.packetName=null;
			this.id=0;
			this.basePacket=null;

			bak.close();
			checkError(bak);
		}
	}

	private void printVisitorMethod(int level) throws IOException
	{
		Indent indent=new Indent(level);

		if (basePacket != null)
			out.printf("%s	@Override%n", indent);
		out.printf("%s	public void visit(TP%02dVisitor visitor)%n", indent, compat);
		out.printf("%s	{%n", indent);
		if (id != -1)
			out.printf("%s		visitor.frame(this);%n", indent);
		else
			out.printf("%s		//NOP (not a leaf class)%n", indent);
		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	private void printFindLengthMethod(int level, List<Property> properties) throws IOException
	{
		Indent indent=new Indent(level);

		out.printf("%s	@Override%n", indent);
		out.printf("%s	public int findByteLength()%n", indent);
		out.printf("%s	{%n", indent);

		out.printf("%s		return super.findByteLength()", indent);
		for (Property p : properties)
		{
			out.printf("%n%s			 + ", indent);

			if (p.size != 0)
				out.printf("%d", Math.abs(p.size)); //size can be negative for enums
			else
				out.printf("findByteLength(this.%s)", p.name);
		}
		out.printf(";%n");

		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	private void printWriteMethod(int level, List<Property> properties, boolean overrides) throws IOException
	{
		Indent indent=new Indent(level);

		if (overrides)
			out.printf("%s	@Override%n", indent);
		out.printf("%s	public void write(TPDataOutput out, Connection<?> conn) throws IOException%n", indent);
		out.printf("%s	{%n", indent);
		if (overrides)
			out.printf("%s		super.write(out, conn);%n", indent);
		for (Property p : properties)
		{
			switch (p.type)
			{
				case group:
				case object: //special case
					out.printf("%s		this.%s.write(out, conn);%n", indent, p.name);
					break;

				case list:
					out.printf("%s		out.writeInteger(this.%s.size());%n", indent, p.name);
					out.printf("%s		for (%s object : this.%s)%n", indent, p.targetType, p.name);
					out.printf("%s			object.write(out, conn);%n", indent);
					break;

				case enumeration:
					if (p.size < 0)
						out.printf("%s		out.writeInteger(this.%s);%n", indent, p.name);
					else
						out.printf("%s		out.writeInteger(this.%s.value);%n", indent, p.name);
					break;

				default:
					//integer and enumeration are the same type physically, so fold enumeration to integer
					out.printf("%s		out.%s(this.%s);%n", indent,
						camelPrefix("write", (p.type == PropertyType.enumeration ? PropertyType.integer : p.type).name()),
						p.name);
					break;
			}
		}
		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	private void printConvenienceConstructor(int level, String typeName, List<Property> properties) throws IOException
	{
		List<Property> writableProperties=new ArrayList<Property>(properties.size());
		for (Property p : properties)
			if (!p.readOnly)
				writableProperties.add(p);
		Indent indent=new Indent(level);

		if (!writableProperties.isEmpty())
		{
			out.printf("%s	/**%n", indent);
			out.printf("%s	 * A convenience constructor for easy initialisation of non-read only fields.%n", indent);
			out.printf("%s	 */%n", indent);
			out.printf("%s	public %s(", indent, typeName);
			for (Iterator<Property> pit=writableProperties.iterator(); pit.hasNext(); )
			{
				Property p=pit.next();
				if (p.type == PropertyType.list)
					out.printf("java.util.List<%s> %s", p.targetType, p.name);
				else
					out.printf("%s %s", p.targetType, p.name);
				if (pit.hasNext())
					out.print(", ");
			}
			out.println(")");

			out.printf("%s	{%n", indent);
			for (Property property : writableProperties)
				out.printf("%s		%s(%s);%n", indent, camelPrefix("set", property.name), property.name);
			out.printf("%s	}%n", indent);
			out.println();
			checkError(out);
		}

		out.printf("%s	/**%n", indent);
		out.printf("%s	 * A copy constructor for (among others) deep-copying groups and lists.%n", indent);
		out.printf("%s	 */%n", indent);
		out.printf("%s	public %s(%s copy)%n", indent, typeName, typeName);
		out.printf("%s	{%n", indent);
		for (Property property : properties)
			out.printf("%s		%s(%s.%s());%n", indent, camelPrefix("set", property.name), "copy", camelPrefix("get", property.name));
		out.printf("%s	}%n", indent);
		out.println();
		checkError(out);
	}

	private void printInputConstructor(int level, String typeName, List<Property> properties, boolean overrides) throws IOException
	{
		Indent indent=new Indent(level);

		out.printf("%s	/**%n", indent);
		out.printf("%s	 * A special \"internal\" constructor that reads contents from a stream.%n", indent);
		out.printf("%s	 */%n", indent);
		//we'll do everything to have warning-less generated code! :)
		out.printf("%s	@SuppressWarnings(\"unused\")%n", indent);
		if (overrides)
			out.printf("%s	%s(int id, TPDataInput in) throws IOException%n", indent, typeName);
		else
			out.printf("%s	%s(TPDataInput in) throws IOException%n", indent, typeName);
		out.printf("%s	{%n", indent);
		if (overrides)
			out.printf("%s		super(id, in);%n", indent);
		for (Property p : properties)
		{
			switch (p.type)
			{
				case group:
					out.printf("%s		this.%s=new %s(in);%n", indent, p.name, p.targetType);
					break;

				case object: //special case
					out.printf("%s		this.%s=GameObject.createGameObject(id, in);%n", indent, p.name, p.targetType);
					break;

				case list:
					out.printf("%s		this.%s.clear();%n", indent, p.name);
					out.printf("%s		for (int length=in.readInteger32(); length > 0; length--)%n", indent);
					out.printf("%s			this.%s.add(new %s(in));%n", indent, p.name, p.targetType);
					break;

				case integer:
					out.printf("%s		this.%s=in.readInteger%d();%n", indent, p.name, p.size * 8);
					break;

				case enumeration:
					if (p.size < 0)
						out.printf("%s		this.%s=in.readInteger%d();%n", indent, p.name, -p.size * 8);
					else
					{
						//this is gonna be slightly inefficient...
						out.printf("%s		%s: {%n", indent, p.name);
						out.printf("%s			%s value=in.readInteger%d();%n", indent, p.targetSubtype, p.size * 8);
						out.printf("%s			for (%s e : %s.values())%n", indent, p.targetType, p.targetType);
						out.printf("%s				if (e.value == value)%n", indent);
						out.printf("%s				{%n", indent);
						out.printf("%s					this.%s=e;%n", indent, p.name);
						out.printf("%s					break %s;%n", indent, p.name);
						out.printf("%s				}%n", indent);
						out.printf("%s			throw new IOException(\"Invalid value for enum '%s': \"+value);%n", indent, p.name);
						out.printf("%s		}%n", indent);
					}
					break;

				case character:
					out.printf("%s		in.readCharacter(this.%s);%n", indent, p.name, p.size * 8);
					break;

				default:
					out.printf("%s		this.%s=in.%s();%n", indent, p.name, camelPrefix("read", p.type.name()));
					break;
			}
		}
		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	private void printVisitorClass(File targetDir, List<Packet> packets) throws IOException
	{
		PrintWriter visitor=createTargetFile(new File(createTargetDir(targetDir), String.format("TP%02dVisitor.java", compat)));
		try
		{
			visitor.printf("package %s.tp%02d;%n", TARGET_BASE_PACKAGE, compat);
			visitor.println();

			visitor.printf("import %s.Visitor;%n", TARGET_BASE_PACKAGE);
			visitor.println();

			visitor.printf("public class TP%02dVisitor extends Visitor<TP%02dVisitor>%n", compat, compat);
			visitor.println("{");
			for (Packet packet : packets)
				if (packet.id != -1)
				{
					visitor.printf("	public void frame(%s packet)%n", packet.name);
					visitor.println("	{");
					visitor.println("		unhandledFrame(packet);");
					visitor.println("	}");
					visitor.println();
				}
			visitor.println("}");

			checkError(visitor);
		}
		finally
		{
			visitor.close();
		}
	}

	private void printFrameDecoder(File targetDir, List<Packet> packets) throws IOException
	{
		PrintWriter frameDecoder=createTargetFile(new File(createTargetDir(targetDir), String.format("TP%02dDecoder.java", compat)));
		try
		{
			frameDecoder.printf("package %s.tp%02d;%n", TARGET_BASE_PACKAGE, compat);
			frameDecoder.println();

			frameDecoder.println("import java.io.IOException;");
			frameDecoder.println();
			frameDecoder.printf("import %s.Frame;%n", TARGET_BASE_PACKAGE);
			frameDecoder.printf("import %s.FrameDecoder;%n", TARGET_BASE_PACKAGE);
			frameDecoder.printf("import %s.TPDataInput;%n", TARGET_BASE_PACKAGE);
			frameDecoder.println();

			frameDecoder.printf("public class TP%02dDecoder implements FrameDecoder<TP%02dVisitor>%n", compat, compat);
			frameDecoder.println("{");

			frameDecoder.printf("	public Frame<TP%02dVisitor> decodeFrame(int id, TPDataInput in) throws IOException%n", compat);
			frameDecoder.println("	{");
			frameDecoder.println("		switch (id)");
			frameDecoder.println("		{");
			for (Packet packet : packets)
				if (packet.id != -1)
					frameDecoder.printf("			case %d: return new %s(id, in);%n", packet.id, packet.name);
			frameDecoder.println("			default: throw new IllegalArgumentException(\"Invalid Frame id: \"+id);");
			frameDecoder.println("		}");
			frameDecoder.println("	}");
			frameDecoder.println();

			frameDecoder.println("	public int getCompatibility()");
			frameDecoder.println("	{");
			frameDecoder.printf("		return %d;%n", compat);
			frameDecoder.println("	}");
			frameDecoder.println("}");

			checkError(frameDecoder);
		}
		finally
		{
			frameDecoder.close();
		}
	}

	public void endProtocol(File targetDir, List<Packet> packets) throws IOException
	{
		try
		{
			printVisitorClass(targetDir, packets);
			printFrameDecoder(targetDir, packets);
		}
		finally
		{
			this.compat=0;
		}
	}

	public void startPacketType() throws IOException
	{
		out.write("public ");
		if (id == -1)
			out.write("abstract ");
		out.printf("class %s extends %s%n", packetName, basePacket == null ? String.format("Frame<TP%02dVisitor>", compat) : basePacket);
		out.println("{");

		printConstructors();

		checkError(out);
	}

	private void printConstructors() throws IOException
	{
		//first constructor, for general public and subclasses
		out.printf("	protected %s(int id)%n", packetName);
		out.println("	{");
		out.println("		super(id);");
		out.println("	}");
		out.println();

		//(note: id == -1 is no id is a base class for other packets)
		//(but then, it should not have readonly properties...)
		if (id != -1)
		{
			out.printf("	public %s()%n", packetName);
			out.println("	{");
			out.printf("		super(%d);%n", id);
			out.println("	}");
			out.println();
		}

		checkError(out);
	}

	public void startComment(int level, int correction) throws IOException
	{
		out.printf("%s/**", new Indent(level + correction));
		out.println();

		checkError(out);
	}

	public void continueComment(int level, int correction, char[] ch, int start, int length) throws IOException
	{
		out.printf("%s * ", new Indent(level + correction));
		out.write(ch, start, length);
		out.println();

		checkError(out);
	}

	public void endComment(int level, int correction) throws IOException
	{
		out.printf("%s */", new Indent(level + correction));
		out.println();

		checkError(out);
	}

	public void printPropertyDef(int level, Property property) throws IOException
	{
		Indent indent=new Indent(level);
		switch (property.type)
		{
			case character:
				out.printf("%sprivate %s %s=new byte[%d];%n", indent, property.targetType, property.name, property.size);
				break;

			case list:
				out.printf("%sprivate java.util.List<%s> %s=new java.util.ArrayList<%s>();%n", indent, property.targetType, property.name, property.targetType);
				break;

			case integer:
			case enumeration:
			case object: //special case
				out.printf("%sprivate %s %s;%n", indent, property.targetType, property.name);
				break;

			default:
				out.printf("%sprivate %s %s=new %s();%n", indent, property.targetType, property.name, property.targetType);
				break;
		}
		out.println();

		checkError(out);
	}

	public void printPropertyGetter(int level, Property property) throws IOException
	{
		Indent indent=new Indent(level);
		switch (property.type)
		{
			case character:
				out.printf("%spublic %s %s()%n", indent, property.targetType, camelPrefix("get", property.name));
				out.printf("%s{%n", indent);
				out.printf("%s	return this.%s.clone();%n", indent, property.name);
				break;

			case list:
				out.printf("%spublic java.util.List<%s> %s()%n", indent, property.targetType, camelPrefix("get", property.name));
				out.printf("%s{%n", indent);
				if (property.readOnly)
					out.printf("%s	return java.util.Collections.unmodifiableList(%s);%n", indent, property.name);
				else
					out.printf("%s	return this.%s;%n", indent, property.name);
				break;

			default:
				out.printf("%spublic %s %s()%n", indent, property.targetType, camelPrefix("get", property.name));
				out.printf("%s{%n", indent);
				out.printf("%s	return this.%s;%n", indent, property.name);
				break;
		}
		out.printf("%s}%n", indent);
		out.println();

		checkError(out);
	}

	public void printPropertySetter(int level, Property property) throws IOException
	{
		//to modify list property use the getter and modify the List
		Indent indent=new Indent(level);

		boolean isPublic=!property.readOnly && property.type != PropertyType.list;
		if (!isPublic)
			out.printf("%s@SuppressWarnings(\"unused\")%n", indent);
		out.printf("%s%s void %s(%s value)%n",
			indent,
			isPublic
				? "public"
				: "private",
			camelPrefix("set", property.name),
			property.type != PropertyType.list
				? property.targetType
				: String.format("java.util.List<%s>", property.targetType));
		out.printf("%s{%n", indent);
		switch (property.type)
		{
			case character:
				//will bail if the source doesn't have enough bytes
				out.printf("%s	System.arraycopy(value, 0, this.%s, 0, this.%s.length);%n", indent, property.name, property.name);
				break;

			case list:
				out.printf("%s	for (%s object : value)%n", indent, property.targetType);
				out.printf("%s		this.%s.add(new %s(object));%n", indent, property.name, property.targetType);
				break;

			case group:
				out.printf("%s	this.%s=new %s(value);%n", indent, property.name, property.targetType);
				break;

			case object: //special case
				out.printf("%s	this.%s=value.copy();%n", indent, property.name);
				break;

			default:
				out.printf("%s	this.%s=value;%n", indent, property.name);
				break;
		}
		out.printf("%s}%n", indent);
		out.println();

		checkError(out);
	}

	public void startEnum(int level, String enumName, String valueType) throws IOException
	{
		Indent indent=new Indent(level);
		out.printf("%spublic enum %s%n", indent, enumName);
		out.printf("%s{%n", indent);

		checkError(out);
	}

	public void printEnumValue(int level, String name, String value) throws IOException
	{
		Indent indent=new Indent(level);
		out.printf("%s	%s(%s),%n", indent, name, value);
		out.println();

		checkError(out);
	}

	public void endEnum(int level, String enumName, String valueType) throws IOException
	{
		Indent indent=new Indent(level);
		out.printf("%s	;%n", indent);
		out.printf("%s	public final %s value;%n", indent, valueType);
		out.printf("%s	private %s(%s value)%n", indent, enumName, valueType);
		out.printf("%s	{%n", indent);
		out.printf("%s		this.value=value;%n", indent);
		out.printf("%s	}%n", indent);
		out.printf("%s}%n", indent);
		out.println();

		checkError(out);
	}

	public void startInnerType(int level, String name) throws IOException
	{
		Indent indent=new Indent(level);
		out.printf("%spublic static class %s extends TPObject<TP%02dVisitor>%n", indent, name, compat);
		out.printf("%s{%n", indent);
		out.printf("%s	/**%n", indent);
		out.printf("%s	 * A default constructor which initialises properties to their defaults.%n", indent);
		out.printf("%s	 */%n", indent);
		out.printf("%s	public %s()%n", indent, name);
		out.printf("%s	{%n", indent);
		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	public void endInnerType(int level, String name, List<Property> properties) throws IOException
	{
		Indent indent=new Indent(level);

		printFindLengthMethod(level, properties);
		printWriteMethod(level, properties, false);

		printConvenienceConstructor(level, name, properties);

		printInputConstructor(level, name, properties, false);

		out.printf("%s}%n", indent);
		out.println();

		checkError(out);
	}

	private static class Indent implements Formattable
	{
		private final int indent;

		Indent(int indent)
		{
			this.indent=indent;
		}

		public void formatTo(Formatter formatter, int flags, int width, int precision)
		{
			//this isn't supposed to be ultra-efficient...
			char[] tabs=new char[Math.min(Math.max(indent, width), precision == -1 ? indent : precision)];
			Arrays.fill(tabs, '\t');
			formatter.format(new String(tabs));
		}
	}
}
