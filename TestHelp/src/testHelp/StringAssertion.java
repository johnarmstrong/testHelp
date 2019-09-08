package testHelp;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an assertion about a String
 * 
 * @author ejewart
 *
 */
public class StringAssertion extends ObjectAssertion
{
	String subject;
	
	StringAssertion(String subject)
	{
		super(subject);
		this.subject = subject;
	}
	
	/**
	 * verify whether this equals another string
	 * @param other
	 * @return
	 * @deprecated use isEqualTo instead
	 */
	@Deprecated
	public StringAssertion equals(String other)
	{
		return isEqualTo(other);
	}
	
	/**
	 * Verifies whether this equals another string
	 * 
	 * @param other
	 * @return
	 */
	public StringAssertion isEqualTo(String other)
	{
		if (!subject.equals(other))
			fail("Expected '" + other + "' but found '" + subject + "'");
		
		return this;
	}
	
	/**
	 * Verifies whether this string matches some pattern.
	 * @param pattern a regular expression representing the pattern to 
	 * match (e.g. "foo" or "\s+hello\s+")
	 * 
	 * @return
	 */
	public StringAssertion matches(String pattern)
	{
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(subject);
		if (!matcher.find())
			fail("Expected to find '" + pattern + "' but did not in '" + subject + "'");
		
		return this;
	}
	
	/**
	 * Verifies whether this string does not match some pattern.
	 * @param pattern a regular expression representing the pattern to 
	 * match (e.g. "foo" or "\s+hello\s+")
	 * 
	 * @return
	 */
	public StringAssertion doesNotMatch(String pattern)
	{
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(subject);
		if (matcher.find())
			fail("Did not expect to find '" + pattern + "' but did in '" + subject + "'");
		
		return this;
	}
	
	/**
	 * Verifies that a string is empty
	 * @return
	 */
	public StringAssertion isEmpty()
	{
		if (subject == null || !subject.isEmpty())
			fail("Expected empty string but found '" + subject + "'");
		
		return this;
	}
	
	/**
	 * Verifies that a string is null or empty
	 * 
	 * @return
	 */
	public StringAssertion isNullOrEmpty()
	{
		if (subject != null && !subject.isEmpty())
			fail("Expected null or empty string but found '" + subject + "'");
		
		return this;
	}
	
	/**
	 * Verifies that a string is a fraction (whole_num/den or 
	 * num/den).
	 * 
	 * @return a FractionAssertion for making additional verifications
	 * about the fraction
	 */
	public FractionAssertion isFraction()
	{
		if (subject == null || subject.isEmpty())
			fail("Expected a fraction but found null or empty string");
		
		try
		{
			return new FractionAssertion(subject);
		}
		catch (Exception e)
		{
			fail("Expected " + subject + " to be a fraction but could not parse it as one (" + e.toString() + ")");
		}
		
		return null; // will not be reached
	}
}
