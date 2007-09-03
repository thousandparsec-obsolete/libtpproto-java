package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

	void startPacket(File targetDir, String basePacket, String packetName, int id) throws IOException;

	void endPacket(List<Property> properties) throws IOException;

	void endProtocol(File targetDir, List<Packet> packets) throws IOException;

	void startPacketType() throws IOException;

	void startComment(int level, int correction) throws IOException;

	void continueComment(int level, int correction, char[] ch, int start, int length) throws IOException;

	void endComment(int level, int correction) throws IOException;

	void printPropertyDef(int level, Property property) throws IOException;

	void printPropertyGetter(int level, Property property) throws IOException;

	void printPropertySetter(int level, Property property) throws IOException;

	void startEnum(int level, String enumNname, String valueType) throws IOException;

	void printEnumValue(int level, String name, String value) throws IOException;

	void endEnum(int level, String enumName, String valueType) throws IOException;

	void startInnerType(int level, String name) throws IOException;

	void endInnerType(int level, String name, List<Property> properties) throws IOException;
}
