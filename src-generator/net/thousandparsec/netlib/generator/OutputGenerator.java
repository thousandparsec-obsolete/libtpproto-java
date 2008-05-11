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
 * <p>
 * The sequence of calls is approximately as follows:
 * <ul>
 * 	<li>{@link #startProtocol(int)}</li>
 * 	<li>For each parameterset:
 * 		<ul>
 * 			<li>{@link #startParameterSet(File, String)}</li>
 * 			<li>{@link #startComment(int)}, {@link #continueComment(int, char[], int, int)}, {@link #endComment(int)} (if present)</li>
 * 			<li>{@link #startParameterSetType()}</li>
 * 			<li>For each parameter:
 * 				<ul>
 * 					<li>{@link #startParameter(String, int)}</li>
 * 					<li>IF it has a {@code descstruct} element:
 * 						<ul>
 * 							<li>{@link #startParameterDescStruct()}</li>
 * 							<li>[the same as "For each property" below]</li>
 * 							<li>{@link #endParameterDescStruct(List)}</li>
 * 						</ul>
 * 					</li>
 * 					<li>{@link #startParameterStruct()}</li>
 * 					<li>[the same as "For each property" below]</li>
 * 					<li>{@link #endParameterStruct(List)}</li>
 * 					<li>{@link #endParameter(String)}</li>
 * 				</ul>
 * 			</li>
 * 			<li>{@link #endParameterSet(List, List)}</li>
 * 		</ul>
 * 	</li>
 * 	<li>For each packet/frame:
 * 		<ul>
 * 			<li>{@link #startFrame(File, int, String, String)}</li>
 * 			<li>{@link #startComment(int)}, {@link #continueComment(int, char[], int, int)}, {@link #endComment(int)} (if present)</li>
 * 			<li>{@link #startFrameType()}</li>
 * 			<li>For each property:
 * 				<ul>
 * 					<li>IF it is a {@link Property.PropertyType#enumeration enumeration}:
 * 						<ul>
 * 							<li>{@link #startEnumeration(int, String, String)}</li>
 * 							<li>For each enumeration value:
 * 								<ul>
 * 									<li>{@link #generateEnumerationValue(int, String, String)}</li>
 * 								</ul>
 * 							</li>
 * 							<li>{@link #endEnumeration(int, String, String)}</li>
 * 						</ul>
 * 					</li>
 * 					<li>
 * 						IF it is a {@link Property.PropertyType#group group} or {@link Property.PropertyType#list list}:
 * 						<ul>
 * 							<li>{@link #startComment(int)}, {@link #continueComment(int, char[], int, int)}, {@link #endComment(int)} (if present)</li>
 * 							<li>{@link #startInnerType(int, String)}</li>
 * 							<li>[the same as "For each property" above]</li>
 * 							<li>{@link #endInnerType(int, String, List)}</li>
 * 						</ul>
 * 					</li>
 * 					<li>{@link #startComment(int)}, {@link #continueComment(int, char[], int, int)}, {@link #endComment(int)} (if present)</li>
 * 					<li>{@link #generatePropertyDefinition(int, Property)}</li>
 * 				</ul>
 * 			</li>
 * 			<li>{@link #endFrame(List)}</li>
 * 		</ul>
 * 	</li>
 * 	<li>{@link #endProtocol(File, Map)}</li>
 * </ul>
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
	 * The actual start of frame is indicated by {@link #startFrameType()}
	 * event.
	 * <p>
	 * TODO: reorg this to include the comment as one of the frame parameters
	 * 
	 * @param targetDir
	 *            the directory where output should be written to (used to set
	 *            up an output stream and forget)
	 * @param id
	 *            the frame's identifier
	 * @param baseFrame
	 *            the base frame which this frame "extends"
	 * @param frameName
	 *            the frame's name
	 * @throws IOException
	 */
	void startFrame(File targetDir, int id, String baseFrame, String frameName) throws IOException;

	/**
	 * Called at the actual start of frame definition, after comments and before
	 * the definitions of properties.
	 * 
	 * @throws IOException
	 */
	void startFrameType() throws IOException;

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
	void endFrame(List<Property> properties) throws IOException;

	/**
	 * Called at the start of a parameterset; parametersets are another type of
	 * protocol-level objects, equal to frames, which define additional
	 * structure associated with a particular property (of
	 * {@link Property.PropertyType#useparameters useparameters} type).
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
	 * @param parameters
	 *            a list of parameters in this parameterset
	 * @param parameterDescs
	 *            a list of parameters in this parameterset that have a
	 *            description structure
	 * @throws IOException
	 */
	void endParameterSet(List<NamedEntity> parameters, List<NamedEntity> parameterDescs) throws IOException;

	/**
	 * Called at the end of protocol definition. The {@code entities} parameter
	 * is a map from a group name to a list of entities; there is at least one
	 * group named "frame" and the rest are lists of parameters (keyed by
	 * parameterset name) and parameter descriptions (keyed by parameterset name
	 * with "Desc" prefix). This method is usually used to create "plumbing"
	 * such as visitor classes (which need to know all entities present in the
	 * protocol).
	 * 
	 * @param targetDir
	 *            the directory where any output should be written to
	 * @param entities
	 *            a map of entities: frames and parameters in parametersets
	 * @throws IOException
	 */
	void endProtocol(File targetDir, Map<String, List<NamedEntity>> entities) throws IOException;

	/**
	 * Called when a comment is about to be generated. This is a heavily reused
	 * event as comments are generated before many elements (such as frames and
	 * parameters). This event is used to indicate a start of the comment; the
	 * contents are indicated by one or more calls to
	 * {@link #continueComment(int, char[], int, int)}.
	 * 
	 * @param nestingLevel
	 *            a nesting level of the element being commented
	 * @throws IOException
	 */
	void startComment(int nestingLevel) throws IOException;

	/**
	 * Called as a continuation of a comment with a chunk of characters.
	 * 
	 * @param nestingLevel
	 *            a nesting level of the element being commented
	 * @param ch
	 *            array of characters of the comment chunk
	 * @param start
	 *            index of the start of the first character of the chunk
	 * @param length
	 *            length of the chunk
	 * @throws IOException
	 */
	void continueComment(int nestingLevel, char[] ch, int start, int length) throws IOException;

	/**
	 * Called at the end of a comment.
	 * 
	 * @param nestingLevel
	 *            a nesting level of the element being commented
	 * @throws IOException
	 */
	void endComment(int nestingLevel) throws IOException;

	/**
	 * Called to generate a property definition (which might or might not
	 * include getters and setters, depending on the target language). This
	 * event is heavily reused, just as {@code structure} element is reused in
	 * the protocol definition; it is called for each property definition of
	 * every structure which means that the implementation should remember for
	 * previous events which object this property belongs to. Hopefully this
	 * doesn't matter anyway.
	 * 
	 * @param nestingLevel
	 *            a nesting level of the object containing this property
	 * @param property
	 *            the data of this property as a {@link Property} object
	 * @throws IOException
	 */
	void generatePropertyDefinition(int nestingLevel, Property property) throws IOException;

	/**
	 * Called at the start of an enumeration. This happens for all properties
	 * (of any structure) of {@link Property.PropertyType#enumeration enumeration}
	 * type and happens before the property itself is being generated. This call
	 * indicates the start of enumeration; the individual values are indicated
	 * by calls to {@link #generateEnumerationValue(int, String, String)}.
	 * 
	 * @param nestingLevel
	 *            a nesting level of the object containing this enumeration
	 * @param enumName
	 *            the name of this enumeration in camel case (with the first
	 *            character upper-cased)
	 * @param valueType
	 *            name of the type of this enumeration's values
	 * @throws IOException
	 */
	void startEnumeration(int nestingLevel, String enumName, String valueType) throws IOException;

	/**
	 * Called once for each enueration value (see
	 * {@link #startEnumeration(int, String, String)}).
	 * 
	 * @param nestingLevel
	 *            a nesting level of the object containing this enumeration
	 * @param name
	 *            name of this enumeration value
	 * @param value
	 *            the exact value of this enumeration, er, value
	 * @throws IOException
	 */
	void generateEnumerationValue(int nestingLevel, String name, String value) throws IOException;

	/**
	 * Called at the end of an enumeration. The name and value type are the same
	 * as in the call to corresponding
	 * {@link #startEnumeration(int, String, String)}.
	 * 
	 * @param nestingLevel
	 *            a nesting level of the object containing this enumeration
	 * @param enumName
	 *            the name of this enumeration in camel case (with the first
	 *            character upper-cased)
	 * @param valueType
	 *            name of the type of this enumeration's values
	 * @throws IOException
	 */
	void endEnumeration(int nestingLevel, String enumName, String valueType) throws IOException;

	/**
	 * Called for inner types in structures (only). Which structure it is (under
	 * {@code packet}, under {@code parameter/usestruct} or under
	 * {@code parameter/descstruct}) should be remembered from previous calls
	 * to {@code start*()} methods. An inner type is generated for each property
	 * of types {@link Property.PropertyType#group group} and
	 * {@link Property.PropertyType#list list}; the
	 * {@link Property.PropertyType#enumeration enumeration} is handled specially
	 * (see {@link #startEnumeration(int, String, String)}). Note that each
	 * group (and list) can have its own structure with groups and lists; this
	 * can create a sinificantly complex hierarchy of nested inner types.
	 * 
	 * @param nestingLevel
	 *            a nesting level of the object containing this inner type
	 * @param name
	 *            name of this inner type in camel case
	 * @throws IOException
	 */
	void startInnerType(int nestingLevel, String name) throws IOException;

	/**
	 * Called at the end of an inner type (see
	 * {@link #startInnerType(int, String)}).
	 * 
	 * @param nestingLevel
	 *            a nesting level of the object containing this inner type
	 * @param name
	 *            name of this inner type in camel case
	 * @param properties
	 *            a list of properties of this inner type as {@link Property}
	 *            objects
	 * @throws IOException
	 */
	void endInnerType(int nestingLevel, String name, List<Property> properties) throws IOException;
}
