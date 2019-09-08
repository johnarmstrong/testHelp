package testHelp;

import static org.junit.Assert.fail;

/**
 * Represents an assertion about a boolean variable.
 * 
 * @author ejewart
 *
 */
public class BooleanAssertion
{
	private boolean subject;
	
	BooleanAssertion(boolean subject) 
	{
		this.subject = subject;
	}

	/**
	 * Verifies that the boolean variable is true, and fails the test if not.
	 * 
	 * @return 
	 */
	public BooleanAssertion isTrue()
	{
		if (!subject)
			fail("Expected true but variable was false");
		
		return this;
	}
	
	/**
	 * Verifies that the boolean variable is true, and fails the test with the
	 * specified reason if not.
	 * 
	 * @param reason text that is included in the failure message saying why the
	 * variable was expected to be true.
	 * @return 
	 */
	public BooleanAssertion isTrue(String reason)
	{
		if (!subject)
			fail("Expected true " + (reason.startsWith("because") ? reason.trim() : "because " + reason.trim()) + " but found false");
		
		return this;
	}

	/**
	 * Verifies that the boolean variable is false, and causes the test
	 * to fail if not.
	 * @return
	 */
	public BooleanAssertion isFalse()
	{
		if (subject)
			fail("Expected false but variable was true");
		
		return this;
	}
	
	/**
	 * Verifies that the boolean variable is false, and fails the test with the
	 * specified reason if not.
	 * 
	 * @param reason text that is included in the failure message saying why the
	 * variable was expected to be false.
	 * @return 
	 */
	public BooleanAssertion isFalse(String reason)
	{
		if (subject)
			fail("Expected false " + (reason.startsWith("because") ? reason.trim() : "because " + reason.trim()) + " but found true");
		
		return this;
	}
}
