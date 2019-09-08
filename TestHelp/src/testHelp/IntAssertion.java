package testHelp;

import static org.junit.Assert.*;

/**
 * Represents an assertion about an integer.
 * 
 * @author ejewart
 *
 */
public class IntAssertion 
{
	private int subject;
	
	IntAssertion(int subject)
	{
		this.subject = subject;
	}

	/**
	 * Verifies that this is exactly equal to the specified other value
	 * 
	 * @param other
	 * @return
	 */
	public IntAssertion isEqualTo(int other)
	{
		if (subject != other)
			fail("Expected " + other + " but got " + subject);
		
		return this;
	}
	
	/**
	 * Verifies that an integer is not equal to the specified other value.
	 * 
	 * @param other
	 * @return
	 */
	public IntAssertion isNotEqualTo(int other)
	{
		if (subject == other)
			fail("Did not expect " + other + " but got it.");
		
		return this;
	}

	/**
	 * verify whether the int equals an expected value
	 * @param other
	 * @return
	 * @deprecated use isEqualTo instead
	 */
	@Deprecated
	public IntAssertion equals(int other)
	{
		return isEqualTo(other);
	}
}
