package testHelp;

import static org.junit.Assert.*;

/**
 * Represents an assertion about a double value
 * 
 * @author ejewart
 *
 */
public class DoubleAssertion 
{
	private double subject;
	private final static double DefaultTolerance = 0.0001;
	
	DoubleAssertion(double subject)
	{
		this.subject = subject;
	}

	/**
	 * Verifies that this double is equal to another one, within 
	 * the default tolerance for doubles
	 * 
	 * @param other
	 * @return
	 */
	public DoubleAssertion isEqualTo(double other)
	{
		return isEqualTo(other, DefaultTolerance);
	}
	
	/**
	 * verifies that this double is equal to another one, within
	 * the specified tolerance.
	 * 
	 * @param other double value to check against
	 * @param tolerance margin of error for equality
	 * @return
	 */
	public DoubleAssertion isEqualTo(double other, double tolerance)
	{
		if (Math.abs(subject - other) > tolerance)
			fail("Expected " + subject + " to be within " + tolerance + " of " + other);
		
		return this;
	}
}
