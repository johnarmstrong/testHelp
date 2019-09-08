package testHelp;

import static org.junit.Assert.*;

/**
 * Represents an assertion about an object
 * 
 * @author ejewart
 *
 */
public class ObjectAssertion 
{
	private Object subject;
	
	/** Constructs a new ObjectAssertion. This is exposed so that
	 * consumers of the testHelp library can add their own assertion 
	 * classes that are subclasses of ObjectAssertion.
	 * 
	 * @param subject the object assertions are focused on
	 */
	public ObjectAssertion(Object subject)
	{
		this.subject = subject;
	}
	
	/**
	 * Verifies that an object is null
	 * 
	 * @return
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
	 * @return
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
	 * @param type
	 * @return
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
	 * @param other
	 * @return
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
	 * @param other
	 * @return
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
