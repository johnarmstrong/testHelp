package testHelp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Simplifies testing of a Java application with a main method
 * that accepts input through System.in and/or produces output
 * on System.out.
 * 
 * @author ejewart
 *
 */
public class ConsoleTester 
{
	private static ClassLoader classLoader = null;
	
	/**
	 * Allows an alternate class loader to be specified. This is useful when
	 * compiling code on the fly.
	 * 
	 * @param loader
	 */
	public static void setLoader(ClassLoader loader) { classLoader = loader; }

	/**
	 * Runs the main method in className, redirecting input to its System.in
	 * stream, and captures and returns the output generated to System.out.
	 * 
	 * @param className qualified className to load (e.g. MyClass or MyPackage.MyClass)
	 * @param input text to feed to System.in after main is called
	 * 
	 * @return any text outputt to System.out when main runs
	 */
	public static String getOutput(String className, String input)
	{
		try 
		{
			// turn the input string into a stream and make System.in use
			// this stream instead of the console
			ByteArrayInputStream inputBytes = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputBytes);
			
			// set up an output stream to replace System.out
			ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
			PrintStream outputStream = new PrintStream(outputBytes);
			System.setOut(outputStream);
			
			// load the main class, using either the system class loader or a custom one
			Class<?> mainClass = null;
			if (classLoader == null)
				mainClass = Class.forName(className);
			else
				mainClass = classLoader.loadClass(className);
			
			// run mainClass's main method
			Method mainMethod = mainClass.getMethod("main", String[].class);
			mainMethod.invoke(null, (Object)null);
			
			// return the string version of whatever was written to the output
			// stream
			return outputBytes.toString("UTF8").trim();
		} 
		catch (Exception ex) 
		{
			if (ex instanceof InvocationTargetException)
			{
				InvocationTargetException ite = (InvocationTargetException)ex;
				return ite.getTargetException().toString();
			}
			
			return ex.toString();
		}
	}
}
