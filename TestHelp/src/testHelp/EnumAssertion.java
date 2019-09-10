package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about an enum value of a given enum type
 * 
 * @param <EnumType> the enum type
 * 
 * @author John Armstrong
 */
public class EnumAssertion<EnumType> extends ObjectAssertion {

	private EnumType subject;
	public EnumAssertion(EnumType subject)
	{
		super(subject);
		this.subject = subject;
	}
	
	/**
	 * Verify that the subject enum value is equal to the specified other enum value of same enum type
	 * 
	 * @param other other enum value
	 * @return this if the subject enum value is equal to specified other enum value
	 * @throws java.lang.AssertionError if the subject enum value is not equal to specified other enum value
	 */
	public EnumAssertion<EnumType> is(EnumType other)
	{
		if (subject != other)
			fail("Expected " + other + " but got " + subject);
		
		return this;
	}
	
	/**
	 * Verify that the subject enum value is not equal to the specified other enum value of same enum type
	 * 
	 * @param other other enum value
	 * @return this if the subject enum value is not equal to specified other enum value
	 * @throws java.lang.AssertionError if the subject enum value is equal to specified other enum value
	 */
	public EnumAssertion<EnumType> isNot(EnumType other)
	{
		if (subject == other)
			fail("Did not expect " + other + " but got it.");
		
		return this;
	}
	

}
