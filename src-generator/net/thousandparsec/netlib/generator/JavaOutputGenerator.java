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
import java.util.Map;

import net.thousandparsec.netlib.generator.UseparametersTypeField.SearchPathElement;

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

	/* for protocol */
	private int compat;

	/* common */
	private File targetDir;
	private PrintWriter out;

	/* for frames */
	private int frameType;
	private String baseFrame;
	private String frameName;

	/* for parameter sets */
	private String parameterSetName;
	private String parameterName;
	private int parameterType;
	private PrintWriter outParam;
	private PrintWriter outParamDesc;

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

	public void startFrame(File targetDir, int frameType, String baseFrame, String frameName) throws IOException
	{
		this.targetDir=createTargetDir(targetDir);
		this.out=createTargetFile(new File(this.targetDir, frameName+".java"));
		this.frameType=frameType;
		this.baseFrame=baseFrame;
		this.frameName=frameName;

		printPreamble(this.out);
	}

	private void printPreamble(PrintWriter out) throws IOException
	{
		out.printf("package %s.tp%02d;%n", TARGET_BASE_PACKAGE, compat);
		out.println();

		out.printf("import %s.*;%n", TARGET_BASE_PACKAGE);
		out.println();
		out.println("import java.io.IOException;");
		out.println();

		checkError(out);
	}

	public void endFrame(List<Property> properties) throws IOException
	{
		try
		{
			printVisitorMethod(0, "frame", baseFrame != null);
			printFindLengthMethod(0, properties);
			printWriteMethod(0, properties, true);

			printInputConstructor(0, frameName, properties, true);

			printToStringMethod(0, frameName, properties, true, out);

			//TODO: printEqualsMethod();

			out.println("}");
		}
		finally
		{
			PrintWriter bak=out;
			this.targetDir=null;
			this.out=null;
			this.frameType=0;
			this.baseFrame=null;
			this.frameName=null;

			bak.close();
			checkError(bak);
		}
	}

	public void startParameterSet(File targetDir, String name) throws IOException
	{
		this.targetDir=createTargetDir(targetDir);
		/*
		 * Carefully wrangle out, outParam and outParamDesc to correctly
		 * redirect output from structure callbacks. outParamDesc is created
		 * lazily after the first descstruct element appears, to avoid
		 * unnecessry classes when it's unused.
		 */
		this.outParam=createTargetFile(new File(this.targetDir, name+".java"));
		this.parameterSetName=name;

		out=outParam;

		printPreamble(this.outParam);
	}

	public void startParameter(String name, int id) throws IOException
	{
		this.parameterName=name;
		this.parameterType=id;
	}

	public void startParameterStruct() throws IOException
	{
		out=outParam;

		printParameterStructType(outParam, parameterSetName);

		checkError(out);
	}

	public void endParameterStruct(List<Property> properties) throws IOException
	{
		printEndParameterClass(properties, parameterSetName, outParam);
	}

	public void startParameterDescStruct() throws IOException
	{
		if (outParamDesc == null)
		{
			outParamDesc=createTargetFile(new File(targetDir, String.format("%sDesc.java", parameterSetName)));
			printPreamble(outParamDesc);
			printParameterSetType(outParamDesc, parameterSetName+"Desc");

			checkError(out);
		}

		out=outParamDesc;

		printParameterStructType(outParamDesc, parameterSetName+"Desc");

		checkError(out);
	}

	public void endParameterDescStruct(List<Property> properties) throws IOException
	{
		printEndParameterClass(properties, parameterSetName+"Desc", outParamDesc);

		out=outParam;
	}

	public void endParameter(String name) throws IOException
	{
		this.parameterName=null;
		this.parameterType=-1;
	}

	public void endParameterSet(List<NamedEntity> parameters, List<NamedEntity> parameterDescs) throws IOException
	{
		try
		{
			printToStringMethod(0, parameterSetName, Collections.<Property>emptyList(), false, outParam);
			printStaticParameterFactory(0, parameterSetName, parameters, outParam);

			if (outParamDesc != null)
			{
				printToStringMethod(0, parameterSetName+"Desc", Collections.<Property>emptyList(), false, outParamDesc);
				printStaticParameterFactory(0, parameterSetName+"Desc", parameterDescs, outParamDesc);
			}

			outParam.println("}");
			if (outParamDesc != null)
				outParamDesc.println("}");
		}
		finally
		{
			PrintWriter bakParam=outParam;
			PrintWriter bakParamDesc=outParamDesc;
			this.targetDir=null;
			this.out=null;
			this.outParam=null;
			this.outParamDesc=null;
			this.parameterSetName=null;

			bakParam.close();
			if (bakParamDesc != null)
				bakParamDesc.close();
			checkError(bakParam);
			if (bakParamDesc != null)
				checkError(bakParamDesc);
		}
	}

	private void printVisitorMethod(int level, String type, boolean overrides) throws IOException
	{
		Indent indent=new Indent(level);

		if (overrides)
			out.printf("%s	@Override%n", indent);
		out.printf("%s	public void visit(TP%02dVisitor visitor) throws TPException%n", indent, compat);
		out.printf("%s	{%n", indent);
		if (frameType != -1)
			out.printf("%s		visitor.%s(this);%n", indent, type);
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
			{
				switch (p.type)
				{
					case useparameters:
						if (p.useparametersTypeField.isIndirect())
							//this is just a blob, not even prepended with a length field
							out.printf("this.%s.length", p.name);
						else
							out.printf("findByteLength(this.%s)", p.name);
						break;
					default:
						out.printf("findByteLength(this.%s)", p.name);
						break;
				}
			}
		}
		out.println(";");

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
				case descparameter:
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

				case character:
				case integer:
				case string:
					//integer and enumeration are the same type physically, so fold enumeration to integer
					out.printf("%s		out.%s(this.%s);%n", indent,
						camelPrefix("write", (p.type == Property.PropertyType.enumeration ? Property.PropertyType.integer : p.type).name()),
						p.name);
					break;

				case useparameters:
					if (p.useparametersTypeField.isIndirect())
						out.printf("%s		out.writeCharacter(this.%s);%n", indent, p.name);
					else
						out.printf("%s		this.%s.write(out, conn);%n", indent, p.name);
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
				if (p.type == Property.PropertyType.list)
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

				case string:
					out.printf("%s		this.%s=in.%s();%n", indent, p.name, camelPrefix("read", p.type.name()));
					break;

				case useparameters:
					if (p.useparametersTypeField.isIndirect())
					{
						out.printf("%s		//indirect: drain the rest of frame and decode later%n", indent);
						out.printf("%s		this.%s=in.drainFrame();%n", indent, p.name);
					}
					else
					{
						out.printf("%s		//direct: just read this sucker%n", indent);
						out.printf("%s		this.%s=%s.create(this.%s, in);%n", indent, p.name, p.targetType, p.targetSubtype);
					}
					break;

				case descparameter:
					out.printf("%s		this.%s=%s.create(this.%s, in);%n", indent, p.name, p.targetType, p.targetSubtype);
					break;
			}
		}
		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	private void printToStringMethod(int level, String typeName, List<Property> properties, boolean overrides, PrintWriter out) throws IOException
	{
		Indent indent=new Indent(level);

		out.printf("%s	@Override%n", indent);
		out.printf("%s	public String toString()%n", indent);
		out.printf("%s	{%n", indent);
		out.printf("%s		StringBuilder buf=new StringBuilder();%n", indent);
		out.printf("%s		buf.append(\"{%s\");%n", indent, typeName);
		for (Property p : properties)
		{
			out.printf("%s		buf.append(\"; %s: \");%n", indent, p.name);
			switch (p.type)
			{
				case character:
					out.printf("%s		buf.append(java.util.Arrays.toString(this.%s));%n", indent, p.name);
					break;

				case enumeration:
				case group:
				case integer:
				case list:
				case string:
				case descparameter:
					out.printf("%s		buf.append(String.valueOf(this.%s));%n", indent, p.name);
					break;

				case useparameters:
					if (p.useparametersTypeField.isIndirect())
						out.printf("%s		buf.append(\"<indirect>\");%n", indent);
					else
						out.printf("%s		buf.append(String.valueOf(this.%s));%n", indent, p.name);
					break;
			}
		}
		if (overrides && baseFrame != null)
			out.printf("%s		buf.append(\"; super:\").append(super.toString());%n", indent);
		out.printf("%s		buf.append(\"}\");%n", indent);
		out.printf("%s		return buf.toString();%n", indent);
		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	private void printStaticParameterFactory(int level, String className, List<NamedEntity> parameters, PrintWriter out) throws IOException
	{
		Indent indent=new Indent(level);

		out.printf("%s	public static %s create(int id, TPDataInput in) throws IOException%n", indent, className);
		out.printf("%s	{%n", indent);
		out.println("		switch (id)");
		out.println("		{");
		for (NamedEntity parameter : parameters)
			out.printf("			case %s.PARAM_TYPE: return new %s(id, in);%n", parameter.name, parameter.name);
		out.println("			//this is necessary for marshall/unmarshall tests");
		out.printf("			case -1: return new %s(id, in);%n", className);
		out.printf("			default: throw new IllegalArgumentException(\"Invalid %s id: \"+id);%n", className);
		out.println("		}");
		out.printf("%s	}%n", indent);
		out.println();

		checkError(out);
	}

	private void printVisitorClass(File targetDir, Map<String, List<NamedEntity>> entities) throws IOException
	{
		PrintWriter visitor=createTargetFile(new File(createTargetDir(targetDir), String.format("TP%02dVisitor.java", compat)));
		try
		{
			visitor.printf("package %s.tp%02d;%n", TARGET_BASE_PACKAGE, compat);
			visitor.println();

			visitor.printf("import %s.Visitor;%n", TARGET_BASE_PACKAGE);
			visitor.printf("import %s.TPException;%n", TARGET_BASE_PACKAGE);
			visitor.println();

			visitor.printf("public class TP%02dVisitor extends Visitor%n", compat);
			visitor.println("{");
			visitor.println("	/**");
			visitor.println("	 * @see Visitor#Visitor()");
			visitor.println("	 */");
			visitor.printf("	public TP%02dVisitor()%n", compat);
			visitor.println("	{");
			visitor.println("	}");
			visitor.println();
			visitor.println("	/**");
			visitor.println("	 * @see Visitor#Visitor(boolean)");
			visitor.println("	 */");
			visitor.printf("	public TP%02dVisitor(boolean errorOnUnhandled)%n", compat);
			visitor.println("	{");
			visitor.println("		super(errorOnUnhandled);");
			visitor.println("	}");
			visitor.println();
			for (Map.Entry<String, List<NamedEntity>> group : entities.entrySet())
			{
				if (!group.getKey().equals("frame"))
				{
					visitor.printf("	public void unhandled%s(%s %s) throws TPException%n", camelPrefix("", group.getKey()), camelPrefix("", group.getKey()), group.getKey());
					visitor.println("	{");
					visitor.println("		if (errorOnUnhandled)");
					//we're not doing frames, so this is a parameter -> getParameterType()
					visitor.printf("			throw new TPException(String.format(\"Unexpected %s: type %%d (%%s)\", %s.getParameterType(), %s.toString()));%n", group.getKey(), group.getKey(), group.getKey());
					visitor.println("	}");
					visitor.println();
				}
				for (NamedEntity frame : group.getValue())
					if (frame.id != -1)
					{
						visitor.printf("	public void %s(%s %s) throws TPException%n", group.getKey(), frame.name, group.getKey());
						visitor.println("	{");
						visitor.printf("		unhandled%s(%s);%n", camelPrefix("", group.getKey()), group.getKey());
						visitor.println("	}");
						visitor.println();
					}
			}
			visitor.println("}");

			checkError(visitor);
		}
		finally
		{
			visitor.close();
		}
	}

	private void printFrameDecoder(File targetDir, Map<String, List<NamedEntity>> entities) throws IOException
	{
		PrintWriter frameDecoder=createTargetFile(new File(createTargetDir(targetDir), String.format("TP%02dDecoder.java", compat)));
		try
		{
			frameDecoder.printf("package %s.tp%02d;%n", TARGET_BASE_PACKAGE, compat);
			frameDecoder.println();

			frameDecoder.println("import java.io.IOException;");
			frameDecoder.println();
			frameDecoder.println("import java.net.URI;");
			frameDecoder.println("import java.net.UnknownHostException;");
			frameDecoder.println();
			frameDecoder.printf("import %s.*;%n", TARGET_BASE_PACKAGE);
			frameDecoder.println();

			frameDecoder.printf("public class TP%02dDecoder implements FrameDecoder<TP%02dVisitor>%n", compat, compat);
			frameDecoder.println("{");
			//we're of course assuming that the Okay and Fail frame will be generated along with this file ;)
			frameDecoder.printf("	private static final TP%02dVisitor CHECK_LOGIN_VISITOR=new TP%02dVisitor()%n", compat, compat);
			frameDecoder.println("		{");
			frameDecoder.println("			@Override");
			frameDecoder.println("			public void unhandledFrame(Frame<?> frame) throws TPException");
			frameDecoder.println("			{");
			frameDecoder.println("				throw new TPException(String.format(\"Unexpected frame type %d\", frame.getFrameType()));");
			frameDecoder.println("			}");
			frameDecoder.println();
			frameDecoder.println("			@Override");
			frameDecoder.println("			public void frame(Okay frame)");
			frameDecoder.println("			{");
			frameDecoder.println("				//all's good, capt'n!");
			frameDecoder.println("			}");
			frameDecoder.println();
			frameDecoder.println("			@Override");
			frameDecoder.println("			public void frame(Fail frame) throws TPException");
			frameDecoder.println("			{");
			frameDecoder.println("				throw new TPException(String.format(\"Server said 'Fail': %d (%s)\", frame.getCode().value, frame.getResult()));");
			frameDecoder.println("			}");
			frameDecoder.println("		};");
			frameDecoder.println();
			frameDecoder.printf("	public Connection<TP%02dVisitor>%n", compat);
			frameDecoder.printf("		makeConnection(URI serverUri, boolean autologin, TP%02dVisitor asyncVisitor)%n", compat);
			frameDecoder.println("		throws UnknownHostException, IOException, TPException");
			frameDecoder.println("	{");
			frameDecoder.printf("		Connection<TP%02dVisitor> connection=Connection.makeConnection(this, serverUri, asyncVisitor);%n", compat);
			frameDecoder.println("		if (autologin)");
			frameDecoder.println("		{");
			frameDecoder.println("			String userInfo=serverUri.getUserInfo();");
			frameDecoder.println("			if (userInfo == null)");
			frameDecoder.println("				throw new TPException(\"Autologin enabled but no login info provided in the URI\");");
			frameDecoder.println("			String[] data=userInfo.split(\":\", -1);");
			frameDecoder.println("			if (data.length != 2)");
			frameDecoder.println("				throw new TPException(\"Autologin enabled but login info provided in the URI is invalid\");");
			frameDecoder.println();
			frameDecoder.printf("			SequentialConnection<TP%02dVisitor> seqConnection=new SimpleSequentialConnection<TP%02dVisitor>(connection);%n", compat, compat);
			frameDecoder.println("			Connect connect=new Connect();");
			frameDecoder.println("			connect.setString(\"libtpproto-java-test\");");
			frameDecoder.println("			seqConnection.sendFrame(connect, CHECK_LOGIN_VISITOR);");
			frameDecoder.println("			Login login=new Login();");
			frameDecoder.println("			login.setUsername(data[0]);");
			frameDecoder.println("			login.setPassword(data[1]);");
			frameDecoder.println("			seqConnection.sendFrame(login, CHECK_LOGIN_VISITOR);");
			frameDecoder.println("			//if we're here, all's fine!");
			frameDecoder.println("		}");
			frameDecoder.println("		return connection;");
			frameDecoder.println("	}");
			frameDecoder.println();

			frameDecoder.printf("	public Frame<TP%02dVisitor> decodeFrame(int id, TPDataInput in) throws IOException%n", compat);
			frameDecoder.println("	{");
			frameDecoder.println("		switch (id)");
			frameDecoder.println("		{");
			for (NamedEntity frame : entities.get("frame"))
				if (frame.id != -1)
					frameDecoder.printf("			case %s.FRAME_TYPE: return new %s(id, in);%n", frame.name, frame.name);
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

	public void endProtocol(File targetDir, Map<String, List<NamedEntity>> entities) throws IOException
	{
		try
		{
			printVisitorClass(targetDir, entities);
			printFrameDecoder(targetDir, entities);
		}
		finally
		{
			this.compat=0;
		}
	}

	public void startFrameType() throws IOException
	{
		out.write("public ");
		if (frameType == -1)
			out.write("abstract ");
		out.printf("class %s extends %s%n", frameName, baseFrame == null ? String.format("Frame<TP%02dVisitor>", compat) : baseFrame);
		out.println("{");
		if (frameType != -1)
		{
			out.printf("	public static final int FRAME_TYPE=%d;%n", frameType);
			out.println();
		}

		//first constructor, for general public and subclasses
		out.printf("	protected %s(int id)%n", frameName);
		out.println("	{");
		out.println("		super(id);");
		out.println("	}");
		out.println();
		
		//(note: id == -1 is no id is a base class for other frames)
		//(but then, it should not have readonly properties...)
		if (frameType != -1)
		{
			out.printf("	public %s()%n", frameName);
			out.println("	{");
			out.println("		super(FRAME_TYPE);");
			out.println("	}");
			out.println();
		}

		checkError(out);
	}

	public void startParameterSetType() throws IOException
	{
		out=outParam;
		printParameterSetType(outParam, parameterSetName);
	}

	private void printParameterSetType(PrintWriter out, String className) throws IOException
	{
		out.printf("public class %s extends TPObject<TP%02dVisitor> implements Visitable<TP%02dVisitor>%n", className, compat, compat);
		out.println("{");
		out.println("	private final int id;");
		out.println();
		out.printf("	protected %s(int id)%n", className);
		out.println("	{");
		out.println("		this.id=id;");
		out.println("	}");
		out.println();
		out.println("	@SuppressWarnings(\"unused\")");
		out.printf("	%s(int id, TPDataInput in) throws IOException%n", className);
		out.println("	{");
		out.println("		this(id);");
		out.println("		//nothing to read");
		out.println("	}");
		out.println();
		out.println("	public int getParameterType()");
		out.println("	{");
		out.println("		return id;");
		out.println("	}");
		out.println();
		out.println("	@Override");
		out.println("	public int findByteLength()");
		out.println("	{");
		out.println("		return 0;");
		out.println("	}");
		out.println();
		out.println("	public void write(TPDataOutput out, Connection<?> conn) throws IOException");
		out.println("	{");
		out.println("		//NOP");
		out.println("	}");
		out.println();
		out.printf("	public void visit(TP%02dVisitor visitor) throws TPException%n", compat);
		out.println("	{");
		out.println("		throw new RuntimeException();");
		out.println("	}");
		out.println();

		checkError(out);
	}

	private void printParameterStructType(PrintWriter out, String baseClass)
	{
		Indent indent=new Indent(1);

		out.printf("%spublic static class %s extends %s%n", indent, parameterName, baseClass);
		out.printf("%s{%n", indent);
		out.printf("%s	public static final int PARAM_TYPE=%d;%n", indent, parameterType);
		out.println();
		out.printf("%s	/**%n", indent);
		out.printf("%s	 * A default constructor for subclasses, which initialises properties to their defaults.%n", indent);
		out.printf("%s	 */%n", indent);
		out.printf("%s	protected %s(int id)%n", indent, parameterName);
		out.printf("%s	{%n", indent);
		out.printf("%s		super(id);%n", indent, parameterName);
		out.printf("%s	}%n", indent);
		out.println();
		out.printf("%s	/**%n", indent);
		out.printf("%s	 * A default constructor for general public, which initialises properties to their defaults.%n", indent);
		out.printf("%s	 */%n", indent);
		out.printf("%s	public %s()%n", indent, parameterName);
		out.printf("%s	{%n", indent);
		out.printf("%s		super(PARAM_TYPE);%n", indent, parameterName);
		out.printf("%s	}%n", indent);
		out.println();
	}

	private void printEndParameterClass(List<Property> properties, String baseClass, PrintWriter out) throws IOException
	{
		Indent indent=new Indent(1);

		printFindLengthMethod(1, properties);
		printWriteMethod(1, properties, true);

		printInputConstructor(1, parameterName, properties, true);

		printToStringMethod(1, parameterName, properties, true, out);

		printVisitorMethod(1, baseClass.substring(0, 1).toLowerCase()+baseClass.substring(1), true);

		out.printf("%s}%n", indent);
		out.println();

		checkError(out);
	}

	public void startComment(int level) throws IOException
	{
		out.printf("%s/**", new Indent(level));
		out.println();

		checkError(out);
	}

	public void continueComment(int level, char[] ch, int start, int length) throws IOException
	{
		out.printf("%s * ", new Indent(level));
		out.write(ch, start, length);
		out.println();

		checkError(out);
	}

	public void endComment(int level) throws IOException
	{
		out.printf("%s */", new Indent(level));
		out.println();

		checkError(out);
	}

	public void generatePropertyDefinition(int level, Property property) throws IOException
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
				out.printf("%sprivate %s %s;%n", indent, property.targetType, property.name);
				break;

			case enumeration:
				if (property.size < 0)
					out.printf("%sprivate %s %s;%n", indent, property.targetSubtype, property.name);
				else
					out.printf("%sprivate %s %s=%s.$none$;%n", indent, property.targetType, property.name, property.targetType);
					
				break;

			case group:
			case string:
				out.printf("%sprivate %s %s=new %s();%n", indent, property.targetType, property.name, property.targetType);
				break;

			case useparameters:
				if (property.useparametersTypeField.isIndirect())
					out.printf("%sprivate byte[] %s=new byte[0];%n", indent, property.name);
				else
				{
					//even direct requires hacks!
					out.printf("%sprivate %s %s=new %s(-1);%n", indent, property.targetType, property.name, property.targetType);
					out.printf("%s{%n", indent);
					out.printf("%s	//hackity hack :) [for tests only!]%n", indent);
					out.printf("%s	this.%s=-1;%n", indent, property.targetSubtype);
					out.printf("%s}%n", indent);
				}
				break;

			case descparameter:
				out.printf("%sprivate %s %s=new %s(-1);%n", indent, property.targetType, property.name, property.targetType);
				out.printf("%s{%n", indent);
				out.printf("%s	//hackity hack :) [for tests only!]%n", indent);
				out.printf("%s	this.%s=-1;%n", indent, property.targetSubtype);
				out.printf("%s}%n", indent);
				break;
		}
		out.println();

		printPropertyGetter(level, property);
		//print setter even if read-only (*might* be useful)
		printPropertySetter(level, property);

		checkError(out);
	}

	private void printPropertyGetter(int level, Property property) throws IOException
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

			case enumeration:
				out.printf("%spublic %s %s()%n", indent, property.size < 0 ? property.targetSubtype : property.targetType, camelPrefix("get", property.name));
				out.printf("%s{%n", indent);
				out.printf("%s	return this.%s;%n", indent, property.name);
				break;

			case group:
			case integer:
			case string:
				out.printf("%spublic %s %s()%n", indent, property.targetType, camelPrefix("get", property.name));
				out.printf("%s{%n", indent);
				out.printf("%s	return this.%s;%n", indent, property.name);
				break;

			case useparameters:
				if (property.useparametersTypeField.isIndirect())
				{
					//FIXME: somehow return nested groups as nested objects
					out.printf("%spublic java.util.List<%s> %s(%s template) throws TPException%n", indent, property.targetType, camelPrefix("get", property.name), property.useparametersTypeField.indirectFrame);
					out.printf("%s{%n", indent);
					out.printf("%s	try%n", indent);
					out.printf("%s	{%n", indent);
					out.printf("%s		if (template.%s() != %s())%n", indent, camelPrefix("get", property.useparametersTypeField.indirectFrameCheckField), camelPrefix("get", property.useparametersTypeField.localField));
					out.printf("%s			throw new TPException(String.format(\"ParameterSet id does not match frame's parameter set id: %%d != %%d\", template.%s(), %s()));%n", indent, camelPrefix("get", property.useparametersTypeField.indirectFrameCheckField), camelPrefix("get", property.useparametersTypeField.localField));
					out.printf("%s		TPDataInput in=new TPInputStream(new java.io.ByteArrayInputStream(this.%s));%n", indent, property.name);
					out.printf("%s		java.util.List<%s> ret=new java.util.ArrayList<%s>();%n", indent, property.targetType, property.targetType);

					//please, take a seat, it's not a pretty sight!
					int nest=0;
					int varCnt=0;
					Indent nestIndent=indent;
					String srcVar="template";
					String varType=property.useparametersTypeField.indirectFrame;
					for (SearchPathElement elem : property.useparametersTypeField.searchPath)
					{
						String varName=String.format("template%d", varCnt++);
						varType=elem.type == SearchPathElement.Type.FINAL
							? "int" //int?
							: varType+"."+elem.property.substring(0, 1).toUpperCase()+elem.property.substring(1)+"Type";

						switch (elem.type)
						{
							case FIELD:
								out.printf("%s		%s %s=%s.%s();%n", nestIndent, varType, varName, srcVar, camelPrefix("get", elem.property));
								break;

							case LIST:
								out.printf("%s		for (%s %s : %s.%s())%n", nestIndent, varType, varName, srcVar, camelPrefix("get", elem.property));
								out.printf("%s		{%n", nestIndent);
								nestIndent=new Indent(level + ++nest);
								break;

							case FINAL:
								out.printf("%s		ret.add(%s.create(%s.%s(), in));%n", nestIndent, property.targetType, srcVar, camelPrefix("get", elem.property));
								break;
						}
						srcVar=varName;
					}
					while (nest != 0)
						out.printf("%s		}%n", new Indent(level + --nest));

					out.printf("%s		return ret;%n", indent);
					out.printf("%s	}%n", indent);
					out.printf("%s	catch (IOException ex)%n", indent);
					out.printf("%s	{%n", indent);
					out.printf("%s		//rather unlikely, unless you pass a wrong template and hit EOFException%n", indent);
					out.printf("%s		throw new TPException(ex);%n", indent);
					out.printf("%s	}%n", indent);
				}
				else
				{
					out.printf("%spublic %s %s()%n", indent, property.targetType, camelPrefix("get", property.name));
					out.printf("%s{%n", indent);
					out.printf("%s	return this.%s;%n", indent, property.name);
				}
				break;

			case descparameter:
				out.printf("%spublic %s %s()%n", indent, property.targetType, camelPrefix("get", property.name));
				out.printf("%s{%n", indent);
				out.printf("%s	return this.%s;%n", indent, property.name);
				break;
		}
		out.printf("%s}%n", indent);
		out.println();

		checkError(out);
	}

	private void printPropertySetter(int level, Property property) throws IOException
	{
		//to modify list property use the getter and modify the List
		Indent indent=new Indent(level);

		boolean isPublic=!property.readOnly && property.type != Property.PropertyType.list;
		if (!isPublic)
			out.printf("%s@SuppressWarnings(\"unused\")%n", indent);

		String valueType;
		switch (property.type)
		{
			case list:
				valueType=String.format("java.util.List<%s>", property.targetType);
				break;
			case enumeration:
				valueType=property.size < 0
					? property.targetSubtype
					: property.targetType;
				break;
			case useparameters:
				if (property.useparametersTypeField.isIndirect())
				{
					valueType=String.format("java.util.List<%s>", property.targetType);
					break;
				}
				//else FALLTHRU!
			default:
				valueType=property.targetType;
		}

		if (property.type == Property.PropertyType.group)
		{
			out.printf("%s/**%n", indent);
			out.printf("%s * NOTE: this method does not copy the value object.%n", indent);
			out.printf("%s */%n", indent);
		}
		out.printf("%s%s void %s(%s value%s)%s%n",
			indent,
			isPublic
				? "public"
				: "private",
			camelPrefix("set", property.name),
			valueType,
			property.type == Property.PropertyType.useparameters && property.useparametersTypeField.isIndirect()
				? String.format(", %s template", property.useparametersTypeField.indirectFrame)
				: "",
			property.type == Property.PropertyType.useparameters && property.useparametersTypeField.isIndirect()
				? " throws TPException"
				: "");
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
			case enumeration:
			case integer:
			case string:
				out.printf("%s	this.%s=value;%n", indent, property.name);
				break;

			case useparameters:
				if (property.useparametersTypeField.isIndirect())
				{
					out.printf("%s	try%n", indent);
					out.printf("%s	{%n", indent);
					out.printf("%s		if (template.%s() != %s())%n", indent, camelPrefix("get", property.useparametersTypeField.indirectFrameCheckField), camelPrefix("get", property.useparametersTypeField.localField));
					out.printf("%s			throw new TPException(String.format(\"ParameterSet id does not match frame's parameter set id: %%d != %%d\", template.%s(), %s()));%n", indent, camelPrefix("get", property.useparametersTypeField.indirectFrameCheckField), camelPrefix("get", property.useparametersTypeField.localField));
					out.printf("%s		java.io.ByteArrayOutputStream bout=new java.io.ByteArrayOutputStream();%n", indent);
					out.printf("%s		TPOutputStream out=new TPOutputStream(bout);%n", indent);
					out.printf("%s		java.util.Iterator<%s> pit=value.iterator();%n", indent, property.targetType);

					//please, take a seat, it's not a pretty sight!
					//(copy-paste warning!)
					int nest=0;
					int varCnt=0;
					Indent nestIndent=indent;
					String srcVar="template";
					String varType=property.useparametersTypeField.indirectFrame;
					for (SearchPathElement elem : property.useparametersTypeField.searchPath)
					{
						String varName=String.format("template%d", varCnt++);
						varType=elem.type == SearchPathElement.Type.FINAL
							? "int" //int?
							: varType+"."+elem.property.substring(0, 1).toUpperCase()+elem.property.substring(1)+"Type";

						switch (elem.type)
						{
							case FIELD:
								out.printf("%s		%s %s=%s.%s();%n", nestIndent, varType, varName, srcVar, camelPrefix("get", elem.property));
								break;

							case LIST:
								out.printf("%s		for (%s %s : %s.%s())%n", nestIndent, varType, varName, srcVar, camelPrefix("get", elem.property));
								out.printf("%s		{%n", nestIndent);
								nestIndent=new Indent(level + ++nest);
								break;

							case FINAL:
								out.printf("%s		if (!pit.hasNext())%n", nestIndent);
								out.printf("%s			throw new TPException(\"Insufficient values for ParameterSet %s\");%n", nestIndent, property.name);
								out.printf("%s		%s param=pit.next();%n", nestIndent, property.targetType);
								out.printf("%s		if (%s.getType() != param.getParameterType())%n", nestIndent, srcVar);
								out.printf("%s			throw new TPException(String.format(\"Invalid parameter type; expected %%d, got %%d\", %s.getType(), param.getParameterType()));%n", nestIndent, srcVar);
								out.printf("%s		param.write(out, null);%n", nestIndent, srcVar);
								break;
						}
						srcVar=varName;
					}
					while (nest != 0)
						out.printf("%s		}%n", new Indent(level + --nest));

					out.printf("%s		out.close();%n", indent);
					out.printf("%s		this.%s=bout.toByteArray();%n", indent, property.name);
					out.printf("%s	}%n", indent);
					out.printf("%s	catch (IOException fatal)%n", indent);
					out.printf("%s	{%n", indent);
					out.printf("%s		//this should not happen with ByteArrayOutputStream%n", indent);
					out.printf("%s		throw new RuntimeException(fatal);%n", indent);
					out.printf("%s	}%n", indent);
				}
				else
					out.printf("%s	this.%s=value;%n", indent, property.name);
				break;

			case descparameter:
				//TODO: should this be copyable?
				out.printf("%s	throw new RuntimeException();%n", indent, property.name);
				break;
		}
		out.printf("%s}%n", indent);
		out.println();

		checkError(out);
	}

	public void startEnumeration(int level, String enumName, String valueType) throws IOException
	{
		Indent indent=new Indent(level);
		out.printf("%spublic enum %s%n", indent, enumName);
		out.printf("%s{%n", indent);

		//print the "null-object" enum value
		//assume that no enumeration has a value with @id=-1 (so far it's true)
		generateEnumerationValue(level, "$none$", "-1");

		checkError(out);
	}

	public void generateEnumerationValue(int level, String name, String value) throws IOException
	{
		Indent indent=new Indent(level);
		out.printf("%s	%s(%s),%n", indent, name, value);
		out.println();

		checkError(out);
	}

	public void endEnumeration(int level, String enumName, String valueType) throws IOException
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

		printToStringMethod(level, name, properties, false, out);

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
