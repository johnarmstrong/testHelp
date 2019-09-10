package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about some executable piece of code (Callable
 * or Runnable, in Java terms). 
 * <p>
 * For example, imagine you have a constructor and you want to verify that
 * it does not throw exceptions. You can write something like 
 * {@code verify.that(() -> { Car c = new Car(); }).doesNotThrow();. The () ->} { ... }
 * syntax is called a lambda and enables you to pass code around as if it
 * were an object.
 * 
 * @author ejewart
 *
 */
public abstract class ExecutableAssertion
{
	//private static final String DFT_DESC = "code snippet";
	// both of these are used by derived classes
	String subjectDesc;
	Exception exception;
	
	/**
	 * Construct the base class part of an Assertion type derived from the class
	 * 
	 * Stores subjectDesc but not subject itself, but not subject itself, which is a function and can't 
	 * be stored because there is not type that is assignable from all function types.
	 * 
	 * @param subjectDesc description of executable function object - required
	 */

	public ExecutableAssertion(String subjectDesc)
	{
		this.subjectDesc = subjectDesc;
		
	}

	/**
	 * Verifies that the snippet of code throws an exception.
	 * 
	 * @return an ExceptionAssertion containing the exception that was thrown, so you can verify things about that Exception
	 * @throws java.lang.AssertionError if no exception was thrown
	 */
	public ExceptionAssertion throwsException()
	{
		execute();
		
		if (exception == null)
		{
			failF("Expected '%s' to throw an exception but it didn't",subjectDesc);
			return null;
		}
		
		return new ExceptionAssertion(exception);
	}

	/**
	 * Verifies that a snippet of code does not throw any exceptions.
	 * 
	 * @return this (an instance of a derived assertion type but statically typed as ExecutableAssertion) if executing
	 * the code snippet does not throw an exception
	 * @throws java.lang.AssertionError if executing the code snippet does throw an exception
	 */
	public ExecutableAssertion doesNotThrow()
	{
		execute();
		
		if (exception != null)
		{
			failF("Did not expect '%s' to throw an exception, but it threw %s",subjectDesc,exception.toString());
		}
		
		return this;
	}

	/**
	 * Abstract method for executing the executable snippet, needs to be overridden in all derived classes.
	 * 
	 * It doesn't take arguments or return a value or throw an exception.  Implementations can do all those things, 
	 * but they must do it within the call and use instance variables to store arguments, return value and exception.
	 * 
	 */
	abstract void execute();
}
