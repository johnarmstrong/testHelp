package testHelp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

/**
 * A derivative of PrintStream which delegates to an internally stored reference to a "real" PrintStream which can be changed.  Can be used to
 * create a substitute for System.out or System.err that ConsoleTester getOutput methods can redirect to string buffers dfr sending output to
 * and later switch back to the original version to the original versions as they do System.out and System.err.
 * <p>
 * Useful for passing a PrintStream class instance or argument with a method reference that requires one.  
 * 
 * @author John Armstrong
 */
public class DelegatingPrintStream extends PrintStream
{
	
	private static class UnusableOutputStream extends OutputStream
	{
		@Override
		public void write(int b) throws IOException
		{
			throw new TestHelpError("Attempt to use unusable outputstream");
		}
	}

	private PrintStream ps = null;

	/** 
	 * Create a DelegatingPrintStream with an initial value for the "real" PrintStream it delegates to - typically System.out or System.err.
	 * 
	 * @param ps - a PrintScreen object 
	 */
	public DelegatingPrintStream(PrintStream ps)
	{
		super(new UnusableOutputStream()); // Not used - don't directly call any OutputStream methods

		this.ps = ps;
	}
	
	/**
	 * Set the value of the PrintStream object to delegate to - typically a buffer-based one used for output capture or, after capture is done,
	 * the one originally passed in the constructor call.
	 * 
	 * @param ps a PrintStream object
	 */
	public void setPrintStream(PrintStream ps)
	{
		this.ps = ps;
	}

	/**
	 * Get the PrintStream being delegated to
	 * 
	 * @return a PrintStream object
	 */
	public PrintStream getPrintStream()
	{
		return ps;
	}

	@Override
	public void flush()
	{
		ps.flush();
	}

	@Override
	public void close()
	{
		ps.close();
	}

	@Override
	public boolean checkError()
	{
		return ps.checkError();
	}

	@Override
	public void write(int b)
	{
		ps.write(b);
	}

	@Override
	public void write(byte buf[], int off, int len)
	{
		ps.write(buf,off,len);
	}

	@Override
	public void print(boolean b)
	{
		ps.print(b);
	}

	@Override
	public void print(char c)
	{
		ps.print(c);
	}

	@Override
	public void print(int i)
	{
		ps.print(i);
	}

	@Override
	public void print(long l)
	{
		ps.print(l);
	}

	@Override
	public void print(float f)
	{
		ps.print(f);
	}

	@Override
	public void print(double d)
	{
		ps.print(d);
	}

	@Override
	public void print(char s[])
	{
		ps.print(s);
	}

	@Override
	public void print(String s)
	{
		ps.print(s);
	}

	@Override
	public void print(Object obj)
	{
		ps.print(obj);
	}

	@Override
	public void println()
	{
		ps.println();
	}

	@Override
	public void println(boolean x)
	{
		ps.println(x);
	}

	@Override
	public void println(char x)
	{
		ps.println(x);
	}

	@Override
	public void println(int x)
	{
		ps.println(x);
	}

	@Override
	public void println(long x)
	{
		ps.println(x);
	}

	@Override
	public void println(float x)
	{
		ps.println(x);
	}

	@Override
	public void println(double x)
	{
		ps.println(x);
	}

	@Override
	public void println(char x[])
	{
		ps.println(x);
	}

	@Override
	public void println(String x)
	{
		ps.println(x);
	}

	@Override
	public void println(Object x)
	{
		ps.println(x);
	}

	public PrintStream printf(String format, Object... args)
	{
		return ps.format(format,args);
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args)
	{
		return ps.printf(l,format,args);
	}

	@Override
	public PrintStream format(String format, Object... args)
	{
		return ps.format(format,args);
	}

	@Override
	public PrintStream format(Locale l, String format, Object... args)
	{
		return ps.format(l,format,args);
	}

	@Override
	public PrintStream append(CharSequence csq)
	{
		return ps.append(csq);
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end)
	{
		return ps.append(csq,start,end);
	}

	@Override
	public PrintStream append(char c)
	{
		return ps.append(c);
	}

}
