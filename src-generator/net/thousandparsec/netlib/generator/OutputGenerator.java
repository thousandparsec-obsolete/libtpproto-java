package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OutputGenerator
{
	/**
	 * @param size
	 *            size of the type in bits
	 * @return type name of the integer type in target language
	 * @throws IllegalArgumentException
	 */
	String getIntegerTypeName(int size) throws IllegalArgumentException;

	void startProtocol(int compat) throws IOException;

	void startPacket(File targetDir, int id, String basePacket, String packetName) throws IOException;

	void startPacketType() throws IOException;

	void endPacket(List<Property> properties) throws IOException;

	void startParameterSet(File targetDir, String name) throws IOException;

	void startParameterSetType() throws IOException;

	void startParameter(String name) throws IOException;

	void startParameterStruct() throws IOException;

	void endParameterStruct(List<Property> properties) throws IOException;

	void startParameterDescStruct() throws IOException;

	void endParameterDescStruct(List<Property> properties) throws IOException;

	void endParameter(String name) throws IOException;

	void endParameterSet(File targetDir, List<NamedEntity> parameters) throws IOException;

	/**
	 * The {@code entities} parameter is a map from a group name to a list of
	 * entities; there is at least one group named "frame".
	 */
	void endProtocol(File targetDir, Map<String, List<NamedEntity>> entities) throws IOException;

	void startComment(int level, int correction) throws IOException;

	void continueComment(int level, int correction, char[] ch, int start, int length) throws IOException;

	void endComment(int level, int correction) throws IOException;

	void printPropertyDef(int level, Property property) throws IOException;

	void printPropertyGetter(int level, Property property) throws IOException;

	void printPropertySetter(int level, Property property) throws IOException;

	void startEnum(int level, String enumNname, String valueType) throws IOException;

	void printEnumValue(int level, String name, String value) throws IOException;

	void endEnum(int level, String enumName, String valueType) throws IOException;

	/**
	 * Called for inner types in structures (only). Which structure it is (under
	 * packet, under parameter/usestruct or under packet/descstruct) should be
	 * remembered from calls to {@code start*()} methods.
	 */
	void startInnerType(int level, String name) throws IOException;

	void endInnerType(int level, String name, List<Property> properties) throws IOException;
}
