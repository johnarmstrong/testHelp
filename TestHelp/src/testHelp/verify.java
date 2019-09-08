package testHelp;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * A collection of static methods to verify assertions in tests. Using
 * verify.that(object) returns some type of assertion, depending on what
 * the object is. Additional verifications can then be chained, e.g.
 * verify.that("foobar").matches("foo").
 * 
 * @author ejewart
 *
 */
public class verify 
{
	/** Verify things about a String */
	public static StringAssertion that(String subject)
	{
		return new StringAssertion(subject);
	}

	/** Verify things about a runnable snippet of code */
	public static RunnableAssertion that(Runnable subject)
	{
		return new RunnableAssertion(subject);
	}
	
	/** Verify things about a callable snippet of code */
	public static CallableAssertion that(Callable subject)
	{
		return new CallableAssertion(subject);
	}
	
	/** Verify things about an object (used when we don't have a more specific
	 * type to verify)
	 * @param subject
	 * @return
	 */
	public static ObjectAssertion that(Object subject)
	{
		return new ObjectAssertion(subject);
	}
	
	/** Verify things about a collection, like an array or ArrayList */
	public static CollectionAssertion that(Collection<?> subject)
	{
		return new CollectionAssertion(subject);
	}
	
	/** Verify things about a double */
	public static DoubleAssertion that(double subject)
	{
		return new DoubleAssertion(subject);
	}
	
	/** Verify things about an int */
	public static IntAssertion that(int subject)
	{
		return new IntAssertion(subject);
	}
	
	/** Verify things about a boolean value */
	public static BooleanAssertion that(boolean subject)
	{
		return new BooleanAssertion(subject);
	}
}
