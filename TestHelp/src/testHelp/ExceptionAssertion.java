package testHelp;

import static org.junit.Assert.*;

/**
 * Represents an assertion about an Exception
 * 
 * @author ejewart
 *
 */
public class ExceptionAssertion extends ObjectAssertion
{
	private Exception subject;
	
	ExceptionAssertion(Exception subject)
	{
		super(subject);
		this.subject = subject;
	}
	
	/**
	 * Verifies that an exception is of a specified type, e.g.&nbsp;
	 * IllegalArgumentException
	 * 
	 * @param type
	 * @return
	 */
	public ExceptionAssertion ofType(Class<?> type)
	{
		if (!subject.getClass().equals(type))
			fail("Expected " + type.getSimpleName() + " but got " + subject.getClass().getSimpleName());
		
		return this;
	}
	
	/**
	 * Verifies that an expection has the specified message
	 * 
	 * @param expected the expected message
	 * @param compareType how to do the comparison (substring, case-insensitive, etc.)
	 * 
	 * @return
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
