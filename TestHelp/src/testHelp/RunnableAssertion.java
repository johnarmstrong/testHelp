package testHelp;

/**
 * Represents an assertion about a runnable snippet of code, with no 
 * return value.
 * 
 * @author ejewart
 *
 */
public class RunnableAssertion extends ExecutableAssertion
{
	private Runnable subject;
	private boolean ran;
	
	RunnableAssertion(Runnable subject)
	{
		this.subject = subject;
		this.ran = false;
	}
	
	String getExpression()
	{
		return "code snippet"; // Runnable.toString doesn't do what we want, and I don't see another way.
	}
	
	void execute()
	{
		if (ran)
			return;
		
		try
		{
			subject.run();
		}
		catch (Exception ex)
		{
			exception = ex;
		}
		finally
		{
			ran = true;
		}
	}
}
