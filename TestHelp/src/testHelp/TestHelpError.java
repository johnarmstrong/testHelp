package testHelp;

/**
 *  An exception thrown from within testHelp.  Indicates an internal error or incorrect usage of the library.
 *  
 *  @author John Armstrong
 */
public class TestHelpError extends Error
{
	private static final long serialVersionUID = 1L;

	public TestHelpError(String message, Throwable cause) {
		super(message,cause);
	}

	public TestHelpError(String message) {
		super(message);
	}
	
}