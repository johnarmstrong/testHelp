package testHelp;

import java.io.IOException;
import java.io.InputStream;

/**
 * A derivative of InputStream which delegates to an internally stored reference to a "real" InputStream which can be changed.  Can be used to
 * create a substitute for System.in that ConsoleTester getOutput methods can redirect to string buffers for getting input from and later switch back
 *  to the original version as they do System.in.
 * <p>
 * Useful for passing a PrintStream class instance or argument with a method reference that requires one.  
 * 
 * @author John Armstrong
 */
public class DelegatingInputStream extends InputStream
{
	
	private InputStream is;

	/** 
	 * Create a DelegatingInputStream with an initial value for the "real" InputStream it delegates to - typically System.in.
	 * 
	 * @param is - a InputSTream object 
	 */
	public DelegatingInputStream(InputStream is)
	{
		this.is = is;
	}
	
	/**
	 * Set the value of the InputStream object to delegate to - typically a buffer-based one used for getting input from a string or, after that is done,
	 * the one originally passed in the constructor call.
	 * 
	 * @param is a InputStream object
	 */
	public void setInputStream(InputStream is)
	{
		this.is = is;
	}

	/**
	 * Get the InputStream being delegated to
	 * 
	 * @return a InputStream object
	 */
	public InputStream getInputStream()
	{
		return is;
	}

	@Override
	public int read() throws IOException
	{
		return is.read();
	}

}
