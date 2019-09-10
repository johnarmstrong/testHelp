package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about a boolean variable.
 * 
 * @author ejewart
 *
 */
public class BooleanAssertion
{
	private boolean subject;
	private String subjectDesc;
	
	BooleanAssertion(boolean subject, String ... subjectDescArg) 
	{
		this.subject = subject;
		this.subjectDesc = getDesc(subjectDescArg,"boolean");
	}

	/**
	 * Verifies that the boolean value is true, and fails the test if not.
	 * 
	 * @param reasonArg optional text that if present is included in the failure message saying why the
	 * value was expected to be true.
	 * @return this if the value is true - probably not useful since the only methods are isTrue and isFalse and one has already been called
	 * @throws java.lang.AssertionError if the value is not true
	 */
	public BooleanAssertion isTrue(String ... reasonArg)
	{
		if (!subject)
		{
			String message = String.format("Expected %s to be true but was false",subjectDesc);
			String reason = getDesc(reasonArg, null);
			if (reason != null)
			{
				reason = (reason.startsWith("because") ? reason.trim() : "because " + reason.trim());
				message = message + " " + reason;
			}
		
			fail(message);
	}
		
		return this;
	}
	
//	/**
//	 * Verifies that the boolean variable is true, and fails the test with the
//	 * specified reason if not.
//	 * 
//	 * @param reason text that is included in the failure message saying why the
//	 * variable was expected to be true.
//	 * @return 
//	 */
//
//	public BooleanAssertion isTrue(String reason)
//	{
//		if (!subject)
//			AssertWrapper.fail("Expected true " + (reason.startsWith("because") ? reason.trim() : "because " + reason.trim()) + " but found false");
//		
//		return this;
//	}

	/**
	 * Verifies that the boolean variable is false, and causes the test
	 * to fail if not.
	 
	 * @param reasonArg optional text that if present is included in the failure message saying why the
	 * value was expected to be true.
	 * @return this if the value is false - probably not useful since the only methods are isTrue and isFalse and one has already been called
	 * @throws java.lang.AssertionError if the value is true
	 */
	public BooleanAssertion isFalse(String ... reasonArg)
	{
		if (subject)
		{
			String message = String.format("Expected %s to be false but was true",subjectDesc);
			String reason = getDesc(reasonArg, null);
			if (reason != null)
			{
				reason = (reason.startsWith("because") ? reason.trim() : "because " + reason.trim());
				message = message + " " + reason;
			}
		
			fail(message);
	}
		
		return this;
	}
	
//	/**
//	 * Verifies that the boolean variable is false, and fails the test with the
//	 * specified reason if not.
//	 * 
//	 * @param reason text that is included in the failure message saying why the
//	 * variable was expected to be false.
//	 * @return 
//	 */
//	public BooleanAssertion isFalse(String reason)
//	{
//		if (subject)
//			AssertWrapper.fail("Expected false " + (reason.startsWith("because") ? reason.trim() : "because " + reason.trim()) + " but found true");
//		
//		return this;
//	}
}
