package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about an integer.
 * 
 * @author ejewart
 *
 */
public class IntAssertion 
{
	private final static String DFT_DESC = "int";
	private int subject;
	private String subjectDesc;
	
	IntAssertion(int subject, String ... subjectDescArg)
	{
		this.subject = subject;
		this.subjectDesc = getDesc(subjectDescArg,DFT_DESC);
	}
	
	/**
	 * Verify that subject int is in the specified comparison relationship with another int.
	 * 
	 * @param compareType one of the enum values in {@link testHelp.NumericCompareType}
	 * @param other the int value to compare the subject value with
	 * @param otherDescArg optional description of the other int
	 * @return this if comparison relationship holds
	 * @throws java.lang.AssertionError if the comparison does not hold
	 */
	public IntAssertion comparesTo(NumericCompareType compareType, int other, String ... otherDescArg)
	{
		String otherDesc = getDesc(otherDescArg,DFT_DESC);
		boolean fail = false;
		switch (compareType)
		{
		case equalTo:
			if (!(subject == other))
			{
				fail = true;
			}
			break;
		case notEqualTo:
			if (!(subject != other))
			{
				fail = true;
			}
			break;			
		case greaterThan:
			if (!(subject > other))
			{
				fail = true;
			}
			break;
		case greaterThanOrEqualTo:
			if (!(subject >= other))
			{
				fail = true;
			}
			break;
		case lessThan:
			if (!(subject < other))
			{
				fail = true;
			}
			break;
		case lessThanOrEqualTo:
			if (!(subject <= other))
			{
				fail = true;
			}
			break;
		
		}
		
		if (fail)
		{
			failF("Expected %s %s to be %s %s %s but was not",subjectDesc,subject,compareType.toString(),otherDesc,other);
			return null; // not reached
		}
		
		return this;
		
	}

	/**
	 * Verifies that this is exactly equal to the specified other value
	 * 
	 * @param other value to compare subject to
	 * @param otherDescArg optional description of other value
	 * @return this if value is equal to other value
	 * @throws java.lang.AssertionError if value is not equal to other value
	 */
	public IntAssertion isEqualTo(int other, String ... otherDescArg)
	{
		//if (subject != other)
		//	AssertWrapper.fail("Expected " + other + " but got " + subject);
		//
		//return this;
		
		return comparesTo(NumericCompareType.equalTo,other,otherDescArg);
	}
	
	/**
	 * Verifies that an integer is not equal to the specified other value.
	 * 
	 * @param other value to compare subject value to
	 * @param otherDescArg optional description of other value 
	 * @return this if value is not equal to other value
	 * @throws java.lang.AssertionError if value is equal to other value
	 */
	public IntAssertion isNotEqualTo(int other, String ...otherDescArg)
	{
		//if (subject == other)
		//	AssertWrapper.fail("Did not expect " + other + " but got it.");
		//
		//return this;
		
		return comparesTo(NumericCompareType.notEqualTo,other,otherDescArg);
	}

	/**
	 * verify whether the int equals an expected value
	 * @param other value to compare subject to
	 * @return this if value is equal to other value
	 * @throws java.lang.AssertionError if value is not equal to other value
	 * @deprecated use isEqualTo instead
	 */
	@Deprecated
	public IntAssertion equals(int other)
	{
		return isEqualTo(other);
	}
	
	/**
	 * Verify that subject int value is between two other int values (inclusive)
	 * @param otherLow value that subject must be equal to or greater than
	 * @param otherHigh value that subject must be less than or equal to
	 * @param otherDescArg optional description of the other int value
	 * @return this if value is between other two values
	 * @throws java.lang.AssertionError if value is not between other two values
	 * @throws IllegalArgumentException if otehr low is greater than other high
	 */
	public IntAssertion isBetween(int otherLow, int otherHigh, String ... otherDescArg) // inclusive semantics - if otherDescArg contains comma treat it as other1Desc,other2desc
	{
		String rawOtherDesc = getDesc(otherDescArg,DFT_DESC);
		String otherDescLow;
		String otherDescHigh;
		String[] otherDescParts = rawOtherDesc.split(",");
		if (otherDescParts.length == 1)
		{
			if (rawOtherDesc.equals(DFT_DESC))
			{
				otherDescLow = "low";
				otherDescHigh = "high";
			}
			else
			{
				otherDescLow = "low " + rawOtherDesc;
				otherDescHigh = "high " + rawOtherDesc;
			}
		}
		else if (otherDescParts.length == 2)
		{
			otherDescLow = otherDescParts[0];
			otherDescHigh = otherDescParts[1];
		}
		else
		{
			throw new TestHelpError("Bad isBetween otherDesc: " + rawOtherDesc);
			
		}
		
		if (otherLow > otherHigh)
		{
			throw new TestHelpError(String.format("Bad range %s %d is greater than %s %d", otherDescLow,otherLow,otherDescHigh,otherHigh));
		}
		
		if (subject < otherLow || subject > otherHigh)
		{
			failF("%s %d is outside range %s %d - %s %d", subjectDesc,subject,otherDescLow,otherLow,otherDescHigh,otherHigh);
		}
		
		return this;
	}
	
	
}
