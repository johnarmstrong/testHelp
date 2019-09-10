package testHelp;

/**
 * An internal class that bundles a set of tools that are used in the various assertion classes and
 * can be called directly from test code.
 * 
 * 
 * 
<ul>
	<li>{@link #fail(String message)} - {@link verify#fail(String message)}</li>
	<li>{@link #failF(String format, Object[] args)} - {@link verify#failF(String format, Object[] args)}</li>
	<li>{@link #failWithException(String message, Throwable ex)} - {@link verify#failWithException(String message, Throwable ex)}</li>
	<li>{@link #failWithExceptionF(String format, Object[] argsEndingWithException)} - {@link verify#failWithExceptionF(String format, Object[] argsEndingWithException)}</li>
	<li>{@link #warning(String message)} - {@link verify#warning(String message)}</li>
	<li>{@link #warningF(String format, Object[] args)} - {@link verify#warningF(String format, Object[] args)}</li>
	<li>{@link #info(String message)} - {@link verify#warning(String message)}</li>
	<li>{@link #infoF(String format, Object[] args)} - {@link verify#infoF(String format, Object[] args)}</li>
</ul>
 * 
 * @author John Armstrong
 */
class AssertionTools {
	
	/**
	 * Never called private do-nothing constructor to suppress default constructor in javadoc
	 */
	private AssertionTools()
	{
		
	}
	
        /**
         * Makes sure the testName in OutputInfo.info is that of current test, print testClassName and testName if they have not
         * already been printed, if verboseLevel permits.
         * <p>
         * This is the preferred call to make to print these items.
         */
	static void syncInfo()
	{
		OutputInfo.sync(); // always do this, otherwise have to deal with null info
		if (ConsoleTester.getVerboseLevel() > 0)
		{
			OutputInfo.info.printTestClassNameIfNotAlreadyPrinted(System.out);
		}	
		if (ConsoleTester.getVerboseLevel() > 1)
		{
			OutputInfo.info.printTestNameIfNotAlreadyPrinted(System.out);
		}	
		
	}
	
	/**
	 * Like org.junit.Assert.fail, but OutputInfo-aware.  Calls OutputInfo.info.printInfoIfNotAlreadyPrinted, then
	 * prints its own message, then calls org.junit.Assert.fail with message.  Called internally within assertion classes.  Can also
	 * be called directly through {@link verify#fail}.
	 * 
	 * @param message message to be printed
	 * @throws java.lang.AssertionError with message
	 */
	
	static void fail(String message)
	{
		if (ConsoleTester.getVerboseLevel() > 0)
		{
			syncInfo();
			OutputInfo.info.printTestNameIfNotAlreadyPrinted(System.out); // won't be printed by syncInfo with verboseLevel 1
			OutputInfo.info.printInfoIfNotAlreadyPrinted(System.out);
			
			OutputInfo.printFailMessage(System.out,message);
			
		}
		org.junit.Assert.fail(message); // should be the one and only call to org.junit.Assert.fail
	}
	
	/**
	 * Like {@link #fail}, but with String.format-style message formatting.
	 * 
	 * @param format String.format-style format string
	 * @param args zero or more objects to insert into format string
	 * @throws java.lang.AssertionError with message
	 */
	static void failF(String format, Object ... args)
	{
		String message = String.format(format, args);
		fail(message);
	}
	
	/**
	 * Like {@link #fail} but prints message and chunked stacktrace of exception to System.out before calling org.junit.Assert.fail.
	 * @param message message to print
	 * @param ex previously caught throwable object
	 * @throws java.lang.AssertionError with message
	 */
	static void failWithException(String message, Throwable ex)
	{
		if (ConsoleTester.getVerboseLevel() > 0)
		{
			syncInfo();
			OutputInfo.info.printInfoIfNotAlreadyPrinted(System.out);
			
			System.out.println("EXCEPTION:\n");
			ConsoleTester.printChunkedStackTrace(ex);
			
			OutputInfo.printFailMessage(System.out,message);
			
		}
		org.junit.Assert.fail(message); // should be the one and only call to org.junit.Assert.fail
	}
	
	/**
	 * Like {@link AssertionTools#failWithException} but with String.format-style message formatting.
	 * 
	 * @param format String.format-style format string
	 * @param argsEndingWithException one or more objects to insert into format string, including prevously
	 * caught throwable object as the last item 
	 * @throws java.lang.AssertionError with message
	 */
	static void failWithExceptionF(String format, Object ... argsEndingWithException)
	{
		Object lastArg = argsEndingWithException[argsEndingWithException.length-1];
		
		Throwable ex = null;
		if (lastArg instanceof Throwable)
		{
			ex = (Throwable)argsEndingWithException[argsEndingWithException.length-1];
		}
		else
		{
			throw new TestHelpError("Last arg not exception");
		}
		String message = String.format(format, argsEndingWithException);
		failWithException(message,ex);
	}
	
	/**
	 * Calls OutputInfo.info.printInfoIfNotAlreadyPrinted, then prints its own message in form:
	 * <p>
	 * {@code ASSERTION WARNING (<testName>-<fileNameAndLineNumber>): <message>}.
	 * <p>
	 * For communicating issue with test without causing it to fail.
	 * @param message massage to print
	 */
	static void warning(String message) 
	{
		if (ConsoleTester.getVerboseLevel() > 0)
		{
			syncInfo();
			OutputInfo.info.printInfoIfNotAlreadyPrinted(System.out);
		
			OutputInfo.printWarningMessage(System.out,message);
		}
	}
	
	/**
	 * Like {@link #warning} but with String.format-style message formatting 
	 * @param format String.format-style format string
	 * @param args zero or more objects to insert into format string
	 */
	static void warningF(String format, Object ... args)
	{
		String message = String.format(format, args);
		warning(message);
	}

	/**
	 * Calls OutputInfo.info.printInfoIfNotAlreadyPrinted, then prints its own message in form:
	 * <p>
	 * {@code ASSERTION INFO (<testName>-<fileNameAndLineNumber>): <message>}.
	 * <p>
	 * For communicating supplementary information about test.  May be called at any point in the test,
	 * including before code which may cause test to fail. 
	 * @param message massage to print
	 */
	static void info(String message) 
	{
		if (ConsoleTester.getVerboseLevel() > 0)
		{
			syncInfo();
			OutputInfo.info.printInfoIfNotAlreadyPrinted(System.out);
		
			OutputInfo.printInfoMessage(System.out,message);
		}
	}
	
	/**
	 * Like {@link #info} but with String.format-style message formatting 
	 * @param format String.format-style format string
	 * @param args zero or more objects to insert into format string
	 */
	static void infoF(String format, Object ... args)
	{
		String message = String.format(format, args);
		info(message);
	}

	/**
	 * Helper method for extracting optional description from variadic String array and
	 * defaulting to a specified value if array is empty.  Compare {@link getDescAsDescArg}. 
	 * @param descArg variadic String array of length zero or one
	 * @param dftDesc default description to if descArg is empty.
	 * @return description, either from descArg or default
	 * @throws IllegalArgumentException if descArg array includes more than one item
	 */
	static String getDesc(String[] descArg, String dftDesc) 
	{
		if (descArg.length == 0) 
		{
			return dftDesc;
		}
		else if (descArg.length == 1) 
		{
			return descArg[0];
		} 
		else 
		{
			throw new TestHelpError("More than one item in descArg: " + descArg.toString());
		}

	}

	/**
	 * Helper method for extracting optional description from variadic String array and
	 * defaulting to a specified value if array is empty. 
	 * <p>
	 * Works like {@link #getDesc} but packages result as a non-empty desArg which contains either the
	 * value from the passed descArg if there is one, else the passed default.   
	 * @param descArg variadic String array of length zero or one
	 * @param dftDesc default description to if descArg is empty.
	 * @return description, either from descArg or default
	 * @throws IllegalArgumentException if descArg array includes more than one item
	 */
	static String[] getDescAsDescArg(String[] descArg, String dftDesc) {
		String desc = getDesc(descArg,dftDesc);
		String [] descArgFromDesc = {desc};
		return descArgFromDesc;
	}

	
}