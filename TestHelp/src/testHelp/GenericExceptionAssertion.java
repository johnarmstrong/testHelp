package testHelp;

import static testHelp.AssertionTools.fail;
import static testHelp.AssertionTools.getDescAsDescArg;

/**
 * Generic exception assertion class. Type may be either Exception or Error.
 * <p>
 * To use this class to examine an exception object of unknown or possibly incorrect type, cast the subject constructor argument to Throwable.
 * <p>
 * Note: this class is a genericized version of {@link CallableAssertion}.
 *  
 * @param <T> the type of the object to examine
 * 
 * @author John Armstrong
 */

public class GenericExceptionAssertion<T extends Throwable> extends GenericObjectAssertionBase<T,GenericObjectAssertion<T>>
{
	private static final String _DFT_DESC = "throwable object of type T";
	
	@Override
	String getDftDesc() {
		return _DFT_DESC;
	}
		
	/** Constructs a new ExceptionAssertion. This is exposed so that
	 * consumers of the testHelp library can add their own assertion 
	 * classes that are subclasses of ExceptionAssertion.
	 * 
	 * @param subject the exception (throwable) object assertions are focused on
	 * @param subjectDescArg an optional description of the object
	 */
	public GenericExceptionAssertion(T subject, String ... subjectDescArg)
	{
		super(subject,getDescAsDescArg(subjectDescArg, _DFT_DESC));

	}
	
	/**
	 * Verifies that an exception has the specified message (copied from ExceptionAssertion)
	 * 
	 * @param expected the expected message
	 * @param compareType how to do the comparison (substring, case-insensitive, etc.)
	 * 
	 * @return this if message in exception object matches expected
	 * @throws java.lang.AssertionError if message in exception object does not match expected
	 */
	public GenericExceptionAssertion<T> withMessage(String expected, CompareType compareType)
	{
		String actual = subject.getMessage();
		switch (compareType)
		{
		case Equal:
			if (!actual.equals(expected))
				fail("Expected message '" + expected + "' but found '" + actual + "'");
			break;
		case Equivalent:
			if (!actual.equalsIgnoreCase(expected))
				fail("Expected the equivalent of '" + expected + "' but found '" + actual + "'");
			break;
		case Substring:
			if (!actual.contains(expected))
				fail("Expected to find '" + expected + "' in '" + actual + "'");
			break;
		default:
			if (!actual.toLowerCase().contains(expected.toLowerCase()))
				fail("Expected to find the equivalent of '" + expected + "' in '" + actual + "'");
			break;
		}
		
		return this;
	}


}
