package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about an object
 * 
 * @author ejewart
 *
 */

public class ObjectAssertion 
{
	private Object subject;
	String subjectDesc;
	
	/** Constructs a new ObjectAssertion. This is exposed so that
	 * consumers of the testHelp library can add their own assertion 
	 * classes that are subclasses of ObjectAssertion.
	 * 
	 * @param subject the object assertions are focused on
	 * @param subjectDescArg (optional) description of the subject
	 */
	public ObjectAssertion(Object subject, String ... subjectDescArg)
	{
		this.subject = subject;
		this.subjectDesc = getDesc(subjectDescArg, "object");
	}
	
	/**
	 * Verifies that an object is null
	 * 
	 * @return this if subject is null
	 * @throws java.lang.AssertionError if subject is not null 
	 */
	public ObjectAssertion isNull()
	{
		if (subject != null)
			fail("Expected null reference but got " + subject.toString());

		return this;
	}
	
	/**
	 * Verifies that an object is not null
	 * 
	 * @return this if subject is not null
	 * @throws java.lang.AssertionError if subject is null
	 */
	public ObjectAssertion isNotNull()
	{
		if (subject == null)
			fail("Expected non-null object but object is null");
		
		return this;
	}
	
	/**
	 * Verifies that an object is of a particular type
	 * 
	 * @param type to test that object is instance of
	 * @return this  if subject is an instance of type
	 * @throws java.lang.AssertionError if subject is not an instance of type
	 */
	public ObjectAssertion isOfType(Class<?> type)
	{
		if (subject == null)
			fail("Expected object of type " + type.getSimpleName() + " but got null");
		
		if (!subject.getClass().equals(type))
			fail("Expected object of type " + type.getSimpleName() + " but got object of type " + subject.getClass().getSimpleName());
		
		return this;
	}

	/**
	 * Verifies that an object is equal to another object, using Object.equals
	 * 
	 * @param other other object to compare subject to
	 * @return this if subject is equal to other object
	 * @throws java.lang.AssertionError if subject is not equal to other object
	 */
	public ObjectAssertion isEqualTo(Object other)
	{
		if (subject == null)
		{
			if (other == null)
				return this;
			
			fail("Expected " + other + " but object is null.");
		}
		
		if (!subject.equals(other))
			fail("Expected " + subject + " to be equal to " + other + " but it is not.");
		
		return this;
	}
	
	/**
	 * Verifies that this object is not equal to the specified other one.
	 * 
	 * @param other other object to compare subject to
	 * @return this if subject is not equal to other object
	 * @throws java.lang.AssertionError if subject is equal to other object
	 */
	public ObjectAssertion isNotEqualTo(Object other)
	{
		if (subject == null)
		{
			if (other != null)
				return this;
			
			fail("Did not expect null.");
		}
		
		if (subject.equals(other))
			fail("Expected " + subject + " to be different from " + other + " but they are equal.");
		
		return this;
	}
}
