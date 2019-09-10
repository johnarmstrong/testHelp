package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about a double value
 * 
 * @author ejewart
 *
 */

// ISSUE: uses bare %f. Does it produce the same string as Double.toString?  Do we want more control?

public class DoubleAssertion 
{
	private static final String DFT_DESC = "double";
	private final static double DefaultTolerance = 0.0001;
	
	private double subject;
	private String subjectDesc;

	static private boolean _isEqualToWithinTolerance(double subject, double other, double tolerance)
	{
		if (tolerance < 0.0)
		{
			throw new TestHelpError("Negative tolerance: " + tolerance);
		}
		return (subject > other - tolerance) && (subject < other + tolerance);
		
	}
	
	DoubleAssertion(double subject, String ... subjectDescArg)
	{
		this.subject = subject;
		this.subjectDesc = getDesc(subjectDescArg,DFT_DESC);
	}

	/**
	 * Verifies that this double is equal to another one, within 
	 * the default tolerance for doubles
	 * 
	 * @param other double value to check against
	 * @param otherDescArg an optional description of other double
	 * @return this if the subject double value is equal to the other value within the default tolerance
	 * @throws java.lang.AssertionError if the subject double value not equal to the other value within the default tolerance
	 */
	public DoubleAssertion isEqualTo(double other, String ... otherDescArg)
	{
		return isEqualToWithinTolerance(other, DefaultTolerance, otherDescArg);
	}
	
	/**
	 * verifies that this double is equal to another one, within
	 * the specified tolerance.
	 * 
	 * @param other double value to check against
	 * @param tolerance margin of error for equality
	 * @param otherDescArg an optional description of other double
	 * @return this if the subject double value is equal to the other value within the specified tolerance
	 * @throws java.lang.AssertionError if the subject double value not equal to the other value within the specified tolerance
	 */
	public DoubleAssertion isEqualToWithinTolerance(double other, double tolerance, String ... otherDescArg)
	{
		String otherDesc = getDesc(otherDescArg,DFT_DESC);
		if (Math.abs(subject - other) > tolerance)
			failF("Expected %s %f to be within tolerance %f of %s %f but it was not",subjectDesc,subject,tolerance,otherDesc,other);
		
		return this;
	}
	
	/**
	 * Verify that subject double is in the specified comparison relationship with another double within the default tolerance.
	 * 
	 * @param compareType one of the enum values in {@link testHelp.NumericCompareType}
	 * @param other the double value to compare the subject value with
	 * @param otherDescArg optional description of the other double
	 * @return this if comparison relationship holds
	 * @throws java.lang.AssertionError if the comparison does not hold
	 */
	public DoubleAssertion comparesTo(NumericCompareType compareType, double other, String ... otherDescArg)
	{
		return comparesToWithinTolerance(compareType,other,DefaultTolerance,otherDescArg);
	}

	/**
	 * Verify that subject double is in the specified comparison relationship with another double.
	 * 
	 * @param compareType one of the enum values in {@link testHelp.NumericCompareType}
	 * @param other the double value to compare the subject value with
	 * @param tolerance the tolerance
	 * @param otherDescArg optional description of the other double
	 * @return this if comparison relationship holds
	 * @throws java.lang.AssertionError if the comparison does not hold
	 */
	public DoubleAssertion comparesToWithinTolerance(NumericCompareType compareType, double other, double tolerance, String ... otherDescArg)
	{
		String otherDesc = getDesc(otherDescArg,DFT_DESC);
		boolean fail = false;
		switch (compareType)
		{
		case equalTo:
			if (!_isEqualToWithinTolerance(subject,other,tolerance))
			{
				fail = true;
			}
			break;	
		case notEqualTo:
			if (_isEqualToWithinTolerance(subject,other,tolerance))
			{
				fail = true;
			}
			break;			
		case greaterThan: // unambiguously greater than - excludes possibility of equal within tolerance
			if (!(subject >= other + tolerance))
			{
				fail = true;
			}
			break;
		case greaterThanOrEqualTo:
			if (!(subject >= other - tolerance))
			{
				fail = true;
			}
			break;
		case lessThan: // unambiguously less than - excludes possibility of equal within tolerance
			if (!(subject < other - tolerance))
			{
				fail = true;
			}
			break;
		case lessThanOrEqualTo:
			if (!(subject <= other + tolerance))
			{
				fail = true;
			}
			break;
		
		}
		
		if (fail)
		{
			failF("Expected %s %s to be %s %s %s with tolerance %f but was not",subjectDesc,subject,compareType.toString(),otherDesc,other,tolerance);
			return null; // not reached
		}
		
		return this;
		
	}

}
