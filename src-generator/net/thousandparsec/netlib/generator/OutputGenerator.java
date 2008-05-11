package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This is an interface of receivers of "domain events" from the
 * {@link Generator}.
 * <p>
 * The generator converts a protocol description file to a stream of events, for
 * each of which there is a corresponding method called in this interface. In
 * addition to event handlers, this interface also provides some additional
 * information specific to the target language, such as concrete integer type
 * names.
 * <p>
 * <stron>This is a stateful object</strong>: the information given to the
 * event handler methods is usually not the full context, so the implementation
 * should keep track of previous invocations (events) and react accordingly when
 * the behaviour depends on it (fortunately there are not that many places where
 * it matters).
 * 
 * @see Generator
 * @see Property
 * @see NamedEntity
 * @see UseparametersTypeField
 * @author ksobolewski
 */
public interface OutputGenerator
{
	/**
	 * Returns a type name corresponding to integer types defined by Thousand
	 * Parser protocol (any version).
	 * 
	 * @param size
	 *            size of the type in bits
	 * @return type name of the integer type in target language
	 * @throws IllegalArgumentException
	 *             if the bit width is invalid
	 */
	String getIntegerTypeName(int size) throws IllegalArgumentException;

	/**
	 * Called at the start of a protocol version definition. Please note that
	 * this method <strong>may</strong> (theoretically) be called more than
	 * once during the lifetime of this object.
	 * 
	 * @param compat
	 *            the protocol version number (compatibility level)
	 * @throws IOException
	 */
	void startProtocol(int compat) throws IOException;

	/**
	 * Called at the start of a definition of a protocol frame (AKA packet). Not
	 * all parts of the definition are given here (yet); for example the frame
	 * direction is missing.
	 * <p>
	 * This is an event used only to set things up, because it hapens before any
	 * comments associated with the frame (in {@code packet/longname} element).
	 * The actual start of frame is indicated by {@link #startPacketType()}
	 * event.
	 * <p>
	 * TODO: reorg this to include the comment as one of the frame parameters
	 * 
	 * @param targetDir
	 *            the directory where output should be written to (used to set
	 *            up an output stream and forget)
	 * @param id
	 *            the frame's identifier
	 * @param basePacket
	 *            the base frame which this frame "extends"
	 * @param packetName
	 *            the frame's name
	 * @throws IOException
	 */
	void startPacket(File targetDir, int id, String basePacket, String packetName) throws IOException;

	/**
	 * Called at the actual start of frame definition, after comments and before
	 * the definitions of properties.
	 * 
	 * @throws IOException
	 */
	void startPacketType() throws IOException;

	/**
	 * Called at the end of frame definition. The list of properties given is
	 * just a useful coda that frees the implementation from remembering them
	 * itself, because all the properties had already been indicated by separate
	 * events.
	 * 
	 * @param properties
	 *            the list of properties as {@link Property} objects
	 * @throws IOException
	 */
	void endPacket(List<Property> properties) throws IOException;

	/**
	 * Called at the start of a parameterset; parametersets are another type of
	 * protocol-level objects, equal to frames, which define additional
	 * structure associated with a particular property (of
	 * {@link StructureHandler.PropertyType#useparameters} type).
	 * <p>
	 * This is an event used only to set things up, because it hapens before any
	 * comments associated with the parameterset (in {@code packet/description}
	 * element). The actual start of parameterset is indicated by
	 * {@link #startParameterSetType()} event.
	 * <p>
	 * TODO: reorg this to include the comment as one of the parameterset
	 * parameters
	 * 
	 * @param targetDir
	 *            the directory where output should be written to (used to set
	 *            up an output stream and forget)
	 * @param name
	 *            the parameterset's name
	 * @throws IOException
	 */
	void startParameterSet(File targetDir, String name) throws IOException;

	/**
	 * Called at the actual start of parameterset definition, after comments and
	 * before the definitions of individual parameters.
	 * 
	 * @throws IOException
	 */
	void startParameterSetType() throws IOException;

	/**
	 * Called at the start of individual parameter of parameterset. This happens
	 * after the comments for the parameter are generated and before calls to
	 * {@link #startParameterStruct()} and/or
	 * {@link #startParameterDescStruct()}.
	 * <p>
	 * TODO: reorg this to include the comment as one of the parameter
	 * parameters
	 * 
	 * @param name
	 *            the parameter's name
	 * @param id
	 *            the parameter's identifier (which is unique in scope of the
	 *            enclosing parameterset)
	 * @throws IOException
	 */
	void startParameter(String name, int id) throws IOException;

	/**
	 * Called at the actual start of individual parameterset's parameter
	 * structure, after comments and before properties.
	 * 
	 * @throws IOException
	 */
	void startParameterStruct() throws IOException;

	/**
	 * Called at the end of parameter's structure definition.
	 * 
	 * @param properties
	 *            a list of properties in the structure as {@link Property}
	 *            objects
	 * @throws IOException
	 */
	void endParameterStruct(List<Property> properties) throws IOException;

	/**
	 * Called at the actual start of individual parameterset's parameter
	 * description structure (used in parameter description frames), after
	 * comments and before properties.
	 * 
	 * @throws IOException
	 */
	void startParameterDescStruct() throws IOException;

	/**
	 * Called at the end of parameter's description structure definition.
	 * 
	 * @param properties
	 *            a list of properties in the structure as {@link Property}
	 *            objects
	 * @throws IOException
	 */
	void endParameterDescStruct(List<Property> properties) throws IOException;

	/**
	 * Called at the end of individual parameterset's parameter.
	 * 
	 * @param name
	 *            the parameter's name
	 * @throws IOException
	 */
	void endParameter(String name) throws IOException;

	/**
	 * Called at the end of parameterset. The lists of parameters and parameter
	 * descriptions are given as a reference, because all parameters were
	 * indicated by separate events. The list of parameter descriptions is a
	 * list of parameters that have a parameter description structure (in
	 * addition to the regular structure).
	 * 
	 * @param targetDir
	 *            the directory where output should be written to (used to set
	 *            up an output stream and forget)
	 * @param parameters
	 *            a list of parameters in this parameterset
	 * @param parameterDescs
	 *            a list of parameters in this parameterset that have a
	 *            description structure
	 * @throws IOException
	 */
	void endParameterSet(File targetDir, List<NamedEntity> parameters, List<NamedEntity> parameterDescs) throws IOException;

	/**
	 * The {@code entities} parameter is a map from a group name to a list of
	 * entities; there is at least one group named "frame".
	 */
	void endProtocol(File targetDir, Map<String, List<NamedEntity>> entities) throws IOException;

	void startComment(int nestingLevel, int correction) throws IOException;

	void continueComment(int nestingLevel, int correction, char[] ch, int start, int length) throws IOException;

	void endComment(int nestingLevel, int correction) throws IOException;

	void printPropertyDef(int nestingLevel, Property property) throws IOException;

	void printPropertyGetter(int nestingLevel, Property property) throws IOException;

	void printPropertySetter(int nestingLevel, Property property) throws IOException;

	void startEnum(int nestingLevel, String enumNname, String valueType) throws IOException;

	void printEnumValue(int nestingLevel, String name, String value) throws IOException;

	void endEnum(int nestingLevel, String enumName, String valueType) throws IOException;

	/**
	 * Called for inner types in structures (only). Which structure it is (under
	 * packet, under parameter/usestruct or under packet/descstruct) should be
	 * remembered from calls to {@code start*()} methods.
	 */
	void startInnerType(int nestingLevel, String name) throws IOException;

	void endInnerType(int nestingLevel, String name, List<Property> properties) throws IOException;
}
