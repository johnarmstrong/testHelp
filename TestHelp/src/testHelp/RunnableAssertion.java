package testHelp;

import static testHelp.AssertionTools.*;

/**
 * Represents an assertion about a runnable snippet of code, with no 
 * return value.
 * 
 * @author ejewart
 *
 */
public class RunnableAssertion extends ExecutableAssertion
{
	private static String DFT_DESC = "runnable code snippet";
	private Runnable subject;
	private boolean ran;
	
	RunnableAssertion(Runnable subject, String ... subjectDescArg)
	{
		super(getDesc(subjectDescArg,DFT_DESC));
		this.subject = subject;
		this.ran = false;
	}
	
	@Override
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
	
	// inherits throwsException and doesNotThrow from ExecutableException
}
