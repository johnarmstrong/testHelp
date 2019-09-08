package testHelp;

import static org.junit.Assert.*;
import java.util.concurrent.Callable;

/**
 * Represents an assertion about a snippet of code that returns some
 * value.
 * @author ejewart
 *
 */
public class CallableAssertion extends ExecutableAssertion 
{
	private Callable<?> subject;
	private Object result;
	private boolean called;
	
	CallableAssertion(Callable<?> subject)
	{
		this.subject = subject;
		this.result = null;
		this.called = false;
	}
	
	/**
	 * Verifies that a snippet of code returns the correct type
	 * of result
	 * 
	 * @param type
	 * @return
	 */
	public CallableAssertion returnsInstanceOf(Class<?> type)
	{
		execute();
		
		if (!this.result.getClass().equals(type))
			fail("Expected expression to return " + type.getSimpleName() + " but it returned " + result.getClass().getSimpleName());
		
		return this;
	}
	
	/**
	 * Verifies that the snippet of code returns an object equal to the
	 * specified one using the Object.equals method.
	 *  
	 * @param object
	 * @return
	 */
	public CallableAssertion returns(Object object)
	{
		execute();
		
		if (!this.result.equals(object))
			fail("Expected expression to return " + object.toString() + " but it returned " + result.toString());
		
		return this;
	}

	/**
	 * Verifies that the snippet of code returned exactly the same object (using
	 * == not .equals).
	 * 
	 * @param object
	 * @return
	 */
	public CallableAssertion returnsExactly(Object object)
	{
		execute();
		
		if (this.result != object)
			fail("Expected expression to return the object " + object.toString() + " but it returned a different object " + result.toString());
		
		return this;
	}
	
	void execute()
	{
		if (called)
			return;
		
		try
		{
			result = subject.call();
		}
		catch (Exception ex)
		{
			exception = ex;
		}
		finally
		{
			called = true;
		}
	}
	
	String getExpression()
	{
		return "code snippet"; // Callable.toString is useless and Callable doesn't seem to expose any other way to see the underlying code
	}
}
