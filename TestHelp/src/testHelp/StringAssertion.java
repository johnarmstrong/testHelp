package testHelp;

import static testHelp.AssertionTools.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an assertion about a String
 * 
 * @author ejewart
 *
 */
public class StringAssertion extends GenericObjectAssertionBase<String,StringAssertion>
{
	
	static final String _DFT_DESC = "string";
	
	@Override
	String getDftDesc() {
		return _DFT_DESC;
	}
	
	/**
	 * Construct a StringAssertion object
	 * @param subject the string to be examined
	 * @param subjectDescArg and optional description of the subject string
	 */
	StringAssertion(String subject, String ... subjectDescArg)
	{
		super(subject,getDescAsDescArg(subjectDescArg,_DFT_DESC));
		this.subject = subject;
	}
	
	/**
	 * verify whether this equals another string
	 * @param other a string to compare the subject string to
	 * @return whether the two strings are equal
	 * @deprecated use isEqualTo instead
	 */
	@Deprecated
	public StringAssertion equals(String other)
	{
		return isEqualTo(other);
	}
	
	
//	/**
//	 * Verifies whether this equals another string
//	 * 
//	 * @param other
//	 * @return
//	 */
//	public StringAssertion isEqualTo(String other, String ... otherDescArg)
//	{
//		String otherDesc = getDesc(otherDescArg, DFT_DESC);
//		
//		if (!subject.equals(other))
//			failF("Expected %s '%s' to be equal to %s '%s'",subjectDesc,subject,otherDesc,other);
//		
//		return this;
//	}

	
	/**
	 * Verifies that a string matches some pattern.
	 * <p>
	 * Uses java.util.regex.Matcher.find() which means pattern doesn't have to match whole string.
	 * 
	 * @param pattern a regular expression representing the pattern to match (e.g. "foo" or "\s+hello\s+")
	 * @param patternDescArg an optional description of the pattern
	 * @return this if the string matches the pattern
	 * @throws java.lang.AssertionError if the string does not match the pattern
	 * @throws java.util.regex.PatternSyntaxException if the pattern does not compile
	 */
	public StringAssertion matches(String pattern, String ... patternDescArg)
	{
		String patternDesc = getDesc(patternDescArg,"pattern");
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(subject);
		if (!matcher.find())
			failF("Expected to find %s '%s' but did not in %s '%s'",patternDesc,pattern,subjectDesc,subject);
		
		return this;
	}
	
	/**
	 * Verifies that a string does not match some pattern.
	 * 
	 * @param pattern a regular expression representing the pattern to match (e.g. "foo" or "\s+hello\s+")
	 * @param patternDescArg an optional description of the pattern
	 * @return this if the string does not match the pattern
	 * @throws java.lang.AssertionError if the string matches the pattern
	 * @throws java.util.regex.PatternSyntaxException if the pattern does not compile
	 */
	public StringAssertion doesNotMatch(String pattern, String ... patternDescArg)
	{
		String patternDesc = getDesc(patternDescArg,"pattern");
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(subject);
		if (matcher.find())
			failF("Did not expect to find %s '%s' but did in %s '%s'", patternDesc,pattern,subjectDesc,subject);
		
		return this;
	}
	
	/**
	 * Verifies that a string is non-null but is empty
	 * @return this if string is empty
	 * @throws java.lang.AssertionError is string is null or not empty
	 * 
	 */
	public StringAssertion isEmpty()
	{
		if (subject == null || !subject.isEmpty())
			failF("Expected %s to be empty string but found '%s'", subjectDesc,subject);
		
		return this;
	}
	
	/**
	 * Verifies that a string is null or empty
	 * 
	 * @return this if string is null or empty
	 * @throws java.lang.AssertionError is string is not null or empty
	 */
	public StringAssertion isNullOrEmpty()
	{
		if (subject != null && !subject.isEmpty())
			failF("Expected %s to be null or empty string but found '%s'", subjectDesc,subject);
		
		return this;
	}
	
	/**
	 * Verifies that a string is not empty
	 * 
	 * @return this if string is not empty
	 * @throws java.lang.AssertionError is string is null or empty
	 */
	public StringAssertion isNotEmpty()
	{
		if (subject == null || subject.isEmpty())
			failF("Expected %s to be non-empty string but found '%s'", subjectDesc,subject);
		
		return this;

		
	}
	
	/**
	 * Verifies that a string is a fraction (whole_num/den or num/den).
	 * 
	 * @return a new FractionAssertion object for making additional verifications about the fraction
	 * @throws java.lang.AssertionError if the subject string is null or empty or cannot be converted to an
	 * object of type {@link testHelp.FractionAssertion.Fraction}
	 */
	public FractionAssertion isFraction()
	{
		if (subject == null || subject.isEmpty())
			failF("Expected %s to be a mixed fraction string but found null or empty string");
		
		try
		{
			return new FractionAssertion(subject);
		}
		catch (Exception e)
		{
			failF("Expected %s '%s' to be a mixed fraction string but attempt to parse it threw exception %s",subjectDesc,subject,e.toString());
		}
		
		return null; // will not be reached

	}



}
