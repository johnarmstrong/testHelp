package testHelp;

import static testHelp.AssertionTools.*;

import java.util.concurrent.Callable;

/**
 * Represents an assertion about a snippet of code (functional object that implements the standard Java
 * Callable interface) that returns some value.
 * <p>
 * Note that the snippet is evaluated once and the result (value or exception) saved.
 * 
 * @author ejewart
 *
 */
public class CallableAssertion extends ExecutableAssertion 
{
	private static final String DFT_DESC = "callable code snippet";
	private Callable<?> subject;
	private Object result; // not in ExecutableEsecution
	private boolean called;
	
	CallableAssertion(Callable<?> subject, String ... subjectDescArg)
	{
		super(getDesc(subjectDescArg,DFT_DESC));
		this.subject = subject;
		this.result = null;
		this.called = false;
	}
	
	/**
	 * Verifies that a snippet of code returns the correct type of result.
	 * <p>
	 * Contrary to what the name suggests, the method tests for exact type and does not accept subtypes
	 * in the Class.isInstance or Class.isAssignableFrom
	 * 
	 * @param type type that the return value of the call is expected to be an instance of
	 * @return this if the return value is of expected type
	 * @throws java.lang.AssertionError if the return value is not of the expected type
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
	 * @param object a value of type Object or class derived from Object that the return value of the call is expected to be equal to
	 * @return this if the return value is equal to the expected value
	 * @throws java.lang.AssertionError if the return value is not equal to the expected value
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
	 * == not .equals).  (sameObjectAs might be a clearer name for the method.)
	 * 
	 * @param object a value of type Object or class derived from Object that the return value of the call is expected to be the same object as
	 * @return this if the return value is not the same as the expected value
	 * @throws java.lang.AssertionError if the return value is not the same as the expected value
	 */
	public CallableAssertion returnsExactly(Object object)
	{
		execute();
		
		if (this.result != object)
			fail("Expected expression to return the object " + object.toString() + " but it returned a different object " + result.toString());
		
		return this;
	}
	
	@Override
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
	
	// inherits throwsException and doesNotThrow from ExecutableException
}
