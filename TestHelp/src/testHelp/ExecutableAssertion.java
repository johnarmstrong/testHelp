package testHelp;

import static org.junit.Assert.fail;

/**
 * Represents an assertion about some executable piece of code (Callable
 * or Runnable, in Java terms). 
 * <p>
 * For example, imagine you have a constructor and you want to verify that
 * it does not throw exceptions. You can write something like 
 * verify.that(() -> { Car c = new Car(); }).doesNotThrow();. The () -> { ... }
 * syntax is called a lambda and enables you to pass code around as if it
 * were an object.
 * 
 * @author ejewart
 *
 */
public abstract class ExecutableAssertion
{
	Exception exception;

	/**
	 * Verifies that the snippet of code throws an exception.
	 * 
	 * @return an ExceptionAssertion containing the exception that was 
	 * thrown, so you can verify things about that Exception
	 */
	public ExceptionAssertion throwsException()
	{
		execute();
		
		if (exception == null)
		{
			fail("Expected '" + getExpression() + "' to throw an exception");
			return null;
		}
		
		return new ExceptionAssertion(exception);
	}

	/**
	 * Verifies that a snippet of code does not throw any exceptions.
	 * 
	 * @return
	 */
	public ExecutableAssertion doesNotThrow()
	{
		execute();
		
		if (exception != null)
		{
			fail("Did not expect '" + getExpression() + "' to throw an exception, but it threw " + exception.toString());
		}
		
		return this;
	}

	abstract void execute();
	abstract String getExpression();
}
