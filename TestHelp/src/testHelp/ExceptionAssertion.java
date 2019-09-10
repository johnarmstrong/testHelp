package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about an Exception
 * 
 * @author ejewart
 *
 */
public class ExceptionAssertion extends ObjectAssertion
{
	//private Exception subject;
	private Throwable subject;
	
	//ExceptionAssertion(Exception subject)
	ExceptionAssertion(Throwable subject)
	{
		super(subject); // if ObjectAssertion is generic this shadowing shouldn't be necessary
		this.subject = subject;
	}
	
	/**
	 * Verifies that an exception is of a specified type, e.g.&nbsp;
	 * IllegalArgumentException
	 * <p>
	 * Could be renamed to isOfType (override of superclass method)
	 * 
	 * @param type type to test that exception object is instance of
	 * @return this if exception object is of expected type
	 * @throws java.lang.AssertionError if exception object is not of expected type
	 */
	public ExceptionAssertion ofType(Class<?> type)
	{
	    	//super.isOfType(type);
		if (!subject.getClass().equals(type))
			fail("Expected " + type.getSimpleName() + " but got " + subject.getClass().getSimpleName());
		
		return this;
	}
	
	/**
	 * Verifies that an exception has the specified message
	 * 
	 * @param expected the expected message
	 * @param compareType how to do the comparison (substring, case-insensitive, etc.)
	 * @return this if exception message matches expected
	 * @throws java.lang.AssertionError if exception message does not match expected
	 */
	public ExceptionAssertion withMessage(String expected, CompareType compareType)
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
