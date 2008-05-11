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

	void endPacket(List<Property> properties) throws IOException;

	void startParameterSet(File targetDir, String name) throws IOException;

	void startParameterSetType() throws IOException;

	void startParameter(String name, int id) throws IOException;

	void startParameterStruct() throws IOException;

	void endParameterStruct(List<Property> properties) throws IOException;

	void startParameterDescStruct() throws IOException;

	void endParameterDescStruct(List<Property> properties) throws IOException;

	void endParameter(String name) throws IOException;

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
