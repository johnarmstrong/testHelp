package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Generic object assertion class, for use where the assertion methods defined in GenericObjectAssertionBase are sufficient.
 * If additional type-specific methods are needed, define a GenericObjectAssertionBase and add the methods to it.  See {@link StringAssertion}
 * for an example of how to do it.
 * <p>
 * To use this class to examine an object of unknown or possibly incorrect type, cast the subject constructor argument to Object.
 * <p>
 * Note: this class is a genericized version of {@link ObjectAssertion}.  Most of the functionality of the latter has
 * been refactored into the generic abstract class {@link GenericObjectAssertionBase}.
 * 
 * @param <T> the type of the object to examine
 * 
 * @author John Armstrong
 */
public class GenericObjectAssertion<T> extends GenericObjectAssertionBase<T,GenericObjectAssertion<T>> {
	
	private static final String _DFT_DESC = "object of type T";
	
	@Override
	String getDftDesc() {
		return _DFT_DESC;
	}
	
	
	/** Constructs a new ObjectAssertion. This is exposed so that
	 * consumers of the testHelp library can add their own assertion 
	 * classes that are subclasses of ObjectAssertion.
	 * 
	 * @param subject the object assertions are focused on
	 * @param subjectDescArg an optional description of the object
	 */
	public GenericObjectAssertion(T subject, String ... subjectDescArg)
	{
		super(subject,getDescAsDescArg(subjectDescArg, _DFT_DESC));

	}

}
