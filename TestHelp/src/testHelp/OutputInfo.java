package testHelp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * An internal (package-private) class which holds data accumulated and used in ConsoleTester.getOutput and Assertion classes and provides methods for
 * printing the data.  It is not possible to access the data or call the printing methods directly in testHelp-based unittest code, but the functionality
 * is generally available via calls to public methods in other testHelp classes and interfaces, particularly ConsoleTester and AssertionUtils.
 * <p>
 * The printing methods can be called directly in tests, but they are normally called indirectly through methods in other classes/interfaces,
 * which in turn may be called directly in tests but are normally called internally within the other classes/interfaces.
 * <p>
 * Three static methods, which print only data stored in OutputInfo (or more exactly, in {@link OutputInfo#info}), are indirectly callable through
 * corresponding public static methods in public class ConsoleTester: 
 * <br>
 * <ul>
 * 	<li>{@link #printTestClassName(PrintStream out)} - {@link ConsoleTester#printTestClassNameIfNotAlreadyPrinted()}</li>
 *	<li>{@link #printTestName(PrintStream out)} - {@link ConsoleTester#printTestNameIfNotAlreadyPrinted()}</li>
 * 	<li>{@link #printInfo(PrintStream out)} - {@link ConsoleTester#printInfoIfNotAlreadyPrinted()}</li>
 * </ul>
 * <p>
 * Three other static methods, which print passed in messages along with data stored in OutputInfo, are indirectly callable through corresponding 
 * public static methods in public interface AssertionTools:
 * <br>
 * <ul>
 * 	<li>{@link #printFailMessage(PrintStream out, String message)} - {@link AssertionTools#fail(String message)} and {@link AssertionTools#failF(String format, Object[] args)} </li>
 *	<li>{@link #printWarningMessage(PrintStream out, String message)} - {@link AssertionTools#warning(String message)} and {@link AssertionTools#warningF(String format, Object[] args)} </li>
 * 	<li>{@link #printInfoMessage(PrintStream out, String message)} - {@link AssertionTools#info(String message)} and {@link AssertionTools#infoF(String format, Object[] args)}</li>
 * </ul>
 * 
 * Examples of outputs from all the print methods can be seen in the file test/testHelp/SampleOutputInfoPrintingTests-output.txt, was generated by
 * running test/testHelp/SampleOutputInfoPrintingTests.java as a junit test under eclipse and saving the console output.
 * 
 *@author John Armstrong
 */
class OutputInfo
{
	/**
	 * Information on the current test in the test file being run.  Used for syncing (making sure OutputInfo is up-to-date and not
	 * left over from a previous test) and for printing the test class name and test name.  Contains four fields:
	 * 
	 * <ul>
	 * 	<li>testClassName - the name of the class of the unittest being run</li>
	 * 	<li>testName - the name of the individual test within the unittest currently being run</li>
	 * 	<li>fileName - the name of the file containing the class of the unittest being run - normally className + '.java'</li>
	 * 	<li>lineNumber - the number of the line in the test file - will be within the code block of the individual test currently being run</li> 
	 * </ul>
	 */
	static class TestNameInfo
	{
		private String testName;
		private String testClassName;
		private String fileName;
		private int lineNumber;

		private TestNameInfo(String testName, String testClassName, String fileName, int lineNumber)
		{
			this.testName = testName;
			this.testClassName = testClassName;
			this.fileName = fileName;
			this.lineNumber = lineNumber;
		}

		/**
		 * Get the testClassName.
		 * 
		 * @return testClassName
		 */
		String getTestClassName()
		{
			return testClassName;
		}

		/**
		 * Get the bare testName.
		 * 
		 * @return testName
		 */
		String getTestName()
		{
			return testName;
		}

		/**
		 * Get the unittest lileName and lineNumber as a string of form {@literal (<fileName>:<lineNumber>)}.
		 * <p>
		 * Note that the eclipse console renderer understands strings of this form and converts them to hypertext links
		 * that when clicked will shift focus to the indicated filename and line number.  
		 * 
		 * @return a string of the described form
		 */
		String getFileNameAndLineNumber()
		{
			String s = String.format("(%s:%d)", fileName, lineNumber);
			return s;

		}

	}

	// Static fields

	// static - persists for lifetime of unittest run under normal junit operation where test class
	// is loaded only once

	/**
	 * The number of the last test detected.  Starts at zero, is incremented each time a new test is detected.
	 * <p>
	 * Persists for lifetime of unittest run under normal junit operation where test class is loaded only once.
	 */
	static int testNum = 0;


	/**
	 * Static reference to the one and only instance (at any time) of an OutputInfo object.
	 */
	static OutputInfo info;
	
	/**
	 * Used in diagnostics that identify caller of test method - used by {@link #getTestNameInfo()}.
	 */
	static final String testClassCallerClassName = "sun.reflect.NativeMethodAccessorImpl";
	
	// Instance fields (in OutputInfo.info)

	// ======================================
	//
	//	Objects containing values to be printed
	//
	// ======================================
	
	/**
	 * object containing test name info for current test.
	 */
	TestNameInfo testNameInfo;
	
	/**
	 * return value from vFunction or vUnBoundMethod when called within getOutput method, otherwise null
	 */
	Object value;

	/**
	 * set by getOutputFromException called directly or within bundled getOutput method when there is an exception,
	 * otherwise null.
	 */
	Throwable exception;
	
	// ======================================
	//
	// 	Strings - values to be printed
	//
	// ======================================

	/**
	 * An optional description, passed in a call to a getOutput method, of what is being run.
	 * <p>
	 * Printed by printInfo as "DESCRIPTION: " plus value or '[null]' if not set
	 */
	String description;

	/** 
	 * The input string passed to getOutputStart and bundled getOutput methods that calls it.
	 * <p>
	 * Printed by printInfo as "INPUT: " + value.
	 */
	String input;
	
	/** 
	 * The string returned by by a getOutput method, normally assigned to a variable that can be tested or otherwise used.  Value will
	 * represent two different things depending on what happens in the getOutput call:
	 * <br>
	 * <ul>
	 * <li>Successful completion: captured output to System.out - returned by bundled getOutput or unbundled getOutputFinish - printed by printInfo as "INPUT: " + value</li>
	 * <li>Exception thrown: string representation of the exception - returned by bundled getOutput or unbundled getOutputFromException - printed by printInfo as "INPUT [EXCEPTION]: " + value.</li>
	 * </ul>
	 * <p>
	 * Note: this behavior is for backwards compatibility with the original version of testHelp.
	 */
	String output;
	
	/**
	 * Captured output to System.out up to the point when an exception is thrown.
	 * <p>
	 * Printed as "OUTPUT [BEFORE EXCEPTION]: " + value for an excweption thrown by code being tested or
	 * "OUTPUT [BEFORE INTERNAL EXCEPTION]: " + value for an exception thrown within the testHelp framework.  
	 */
	String outputBeforeException;
	
	/**
	 * Captured output to System.err.  Is not printed, but is used to make {@link #chunkedErrorOutput}, which is printed.
	 */
	String errorOutput;
	
	/**
	 * Currently not used.
	 */
	String errorAndExceptionSummary;
	
	/**
	 *  Captured output to Systenm.err as chunked by the ErrorOutputChunker.
	 *  <p>
	 *  Printed as "ERROROUTPUT: " + value.
	 */
	String chunkedErrorOutput;

	// ======================================
	//
	// 	Booleans - for control
	//
	// ======================================
	
	/**
	 * Internal flag.
	 */
	boolean getOutputInitialized;
	
	/**
	 * Internal flag.
	 */
	boolean expectOutput;
	/**
	 * 
	 * Internal flag.
	 */
	boolean outputIsException;
	
	/**
	 * Internal flag.
	 */
	boolean exceptionIsInternal;
	
	/**
	 * Internal flag.
	 */
	boolean errorOutputContainsErrorOrException;
	
	/**
	 * Internal flag.
	 */
	boolean expectValue;
	
	/**
	 * Internal flag.
	 */
	boolean testClassNamePrinted;
	
	/**
	 * Internal flag.
	 */
	boolean testNamePrinted;
	
	/**
	 * Internal flag.
	 */
	boolean infoPrinted;

	// ======================================
	//
	// 	Streams - for I/O to/to system streams and within getOutput constructs
	//
	// ======================================

	/**
	 * Internal data.
	 */
	InputStream savedSystemIn;
	
	/**
	 * Internal data.
	 */
	PrintStream savedSystemOut;
	
	/**
	 * Internal data.
	 */
	PrintStream savedSystemErr; // For including stacktraces in output (verbose only)

	/**
	 * Internal data.
	 */
	ByteArrayInputStream inputBytes; // accessed only within block, could be local

	/**
	 * Internal data.
	 */
	ByteArrayOutputStream outputBytes; // accessed outside try

	/**
	 * Internal data.
	 */
	ByteArrayOutputStream errorBytes; // accessed outside try

	/**
	 * Internal data.
	 */
	PrintStream outputStream;
	
	/**
	 * Internal data.
	 */
	PrintStream errorStream;

	/**
	 * Construct object with all fields null or equivalent
	 */
	OutputInfo()
	{
		// strings
		description = input = output = outputBeforeException = errorOutput = chunkedErrorOutput = errorAndExceptionSummary = null;

		// booleans
		expectValue = exceptionIsInternal = getOutputInitialized = expectOutput = outputIsException = errorOutputContainsErrorOrException = testClassNamePrinted = testNamePrinted = infoPrinted = false;
		inputBytes = null;
		savedSystemIn = null;
		outputStream = errorStream = savedSystemOut = savedSystemErr = null;
		outputBytes = errorBytes = null;

		testNameInfo = null;
		value = null;
		exception = null;
	}
	
	// ====================================================================
	//
	// Internal methods
	//
	// ====================================================================	
	
	/**
	 * Gets a new fully initialized TestNameInfo object for currently running test.
	 * <p>
	 * Uses a heuristic to find the currently running test - immediate caller is {@code sun.reflect.NativeMethodAccessorImpl.invoke0()}.
	 * <p>
	 * Important note: will fail with exception if not called within a test in a unittest running under junit.
	 * 
	 * @return new TestestNameInfo object
	 * @throws TestHelpError if a test cannot be found on stack
	 */
	static TestNameInfo getTestNameInfo()
	{
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

		for (int i = 2; i < stacktrace.length; i++)
		{
			String callerClassName = stacktrace[i].getClassName();
			if (callerClassName.endsWith(testClassCallerClassName)) // caller of test
				// method
			{
				String methodName = stacktrace[i - 1].getMethodName();
				String className = stacktrace[i - 1].getClassName();
				String fileName = stacktrace[i - 1].getFileName();
				int lineNumber = stacktrace[i - 1].getLineNumber();
				TestNameInfo testNameInfo = new TestNameInfo(methodName, className, fileName,
						lineNumber);
				return testNameInfo;
			}
		}
		throw new TestHelpError("Couldn't find caller class sun.reflect.NativeMethodAccessorImpl");
	}

	/**
	 * Ensures that the singleton OutputInfo object stored in static field {@link OutputInfo#info} is up-to-date
	 */
	static void sync()
	{
		if (info == null)
		{
			info = new OutputInfo();
			info.testNameInfo = getTestNameInfo();
			testNum++;

		} else
		{
			TestNameInfo curTestNameInfo = getTestNameInfo();
			boolean curTestClassNamePrinted = info.testClassNamePrinted;

			if (!curTestNameInfo.testClassName.equals(info.testNameInfo.testClassName)) // new test file - must running multiple tests
			{
				testNum = 0;
				
				info.testClassNamePrinted = false;
				curTestNameInfo = getTestNameInfo();
				curTestClassNamePrinted = false;
			}
			
			if (!curTestNameInfo.testName.equals(info.testNameInfo.testName))
			{
				info = new OutputInfo();
				info.testNameInfo = getTestNameInfo();
				testNum++;
				info.testClassNamePrinted = curTestClassNamePrinted;
				info.testNamePrinted = false;
			}
		}

	}

	/**
	 * Flushes specified Printstream.  If PrintStream is System.out or System.err, flushes both of them
	 * in that order.  Purpose of flushing both System output streams is to avoid overlapping outputs.
	 * The method also includes a 20 msec sleep to mitigate a multithreading race condition between
	 * System.out and System.err printing in the eclipse console.
	 * 
	 * @param out PrintStream to flush.
	 */
	static void flushPrintStreams(PrintStream out)
	{
		try
		{
			Thread.sleep(20); // for latency across threads (seen in stacktraces)
		} catch (InterruptedException e)
		{
			// e.printStackTrace();
		}

		out.flush();
		if (out == System.out || out == System.err)
		{
			System.out.flush();
			System.err.flush();
		}
	}


	
	// ====================================================================
	//
	//	 print info (1): printClassTestName and printTestClassNameIfNotAlreadyPrinted	
	//
	// ====================================================================
	
	/** Prints the testClassName in the following form:
	 * 
<pre>
  ==========================================================================================

  TEST CLASS: testHelp.SampleOutputInfoPrintingTests
</pre>
	 *
	 * @param out PrintStream to write to - normally System.out
	 */
	void printTestClassName(PrintStream out)
	{
		out.println("==========================================================================================\n");
		out.println(String.format("TEST CLASS: %s\n", testNameInfo.getTestClassName()));

	}

	/**
	 * Calls {@link #printTestClassName(PrintStream)} if not already called - Should print once per test file, at the top of
	 * the output to System.out.
	 * 
	 * @param out PrintStream to write to - normally System.out
	 */
	void printTestClassNameIfNotAlreadyPrinted(PrintStream out)
	{
		if (!testClassNamePrinted)
		{
			printTestClassName(out);
			testClassNamePrinted = true;
		}

	}

	
	// ====================================================================
	//
	//	print info (2): printTestName and printTestNameIfNotAlreadyPrinted	
	//
	// ====================================================================

	/**
	 * Prints the number, name and file location of the test in the following format:
	 * 
<pre>
  ==========================================================================================

  TEST (1): test_printTestName (SampleOutputInfoPrintingTests.java:32)

</pre>
	 * 
	 * Note that the parenthesized filename:linenumber is clickable in the eclipse console and when
	 * clicked will shift focus to the file and linenumber.
	 
	 * @param out PrintStream to print to - normally System.out
	 */
	void printTestName(PrintStream out)
	{
		flushPrintStreams(out);

		out.println("==========================================================================================\n");
		out.println(String.format("TEST (%d): %s %s\n", testNum, testNameInfo.getTestName(), testNameInfo.getFileNameAndLineNumber()));

	}

	/**
	 * Calls {@link #printTestName(PrintStream)} if it has not already been called for the current test, else does nothing. 
	 * <p>
	 * Whether or not this should be called is determined in the calling environment as it depends on
	 * both the situation and the verboseLevel
	 *
	 * @param out - PrintStream to print to - normally System.out
	 */
	void printTestNameIfNotAlreadyPrinted(PrintStream out)
	{
		if (!testNamePrinted)
		{
			printTestClassNameIfNotAlreadyPrinted(out);
			printTestName(out);
			testNamePrinted = true;
		}
	}
	
	// ====================================================================
	//
	//	 print info (3): printInfo and printInfoIfNotAlreadyPrinted	
	//
	// ====================================================================

	/**
	 * Prints all appropriate fields in the form {@literal <label>: <value>}.  The following is a full list of the items that may be appear:
	 * 
<br><br>
<ul>
<li>DESCRIPTION: {@link #description}  - description of what is being run in the test - set in the desc or descArg argument in a bundled getOutput.. or unbundled getOutputStart call,
or optionally by ConsoleTester.setDescription in tests where no getOutput method is called</li>

<li>INPUT: {@link #input} - the input string in a bundled getOutput call</li>

<li>OUTPUT: {@link #output} - captured output to System.out in a bundled or unbundled getOutput call that completes without throwing an exception - value will be the string returned by bundled getOutput or unbundled getOutputFinish</li>
<li>OUTPUT [BEFORE EXCEPTION] or [BEFORE INTERNAL EXCEPTION]: {@link #outputBeforeException}- captured output to System.out in a bundled or unbundled getOutput call before an except is thrown</li>
<li>OUTPUT [EXCEPTION]: {@link #output} - a string representation of exception thrown in a bundled opr unbundled getOut call - value will be the string returned by bundled getOutput.. or unbundled getOutputFromException</li>

<li>VALUE: {@link #value} - the simple classname and string representation of value returned within a bundled output call or "[not set]" in an unbundled call - a value "VoidR voidR" indicates that executed method had void return type</li>
 
<li>ERROR_OUTPUT: {@link #chunkedErrorOutput} - captured output to System.err in a bundled or unbundled getOutput call including exception traces and other outour - will be parsed into possibly truncated chunks by the ErrorOutputChunker</li>
</ul>
	 * <p>
	 * Note that the only value that is printed when no bundled or unbundled getOutput methods have been called is description, abd a value will
	 * be printed only after ConsoleTester.setDescription has been called in the test.  Doing so is useful for attaching a description to tests that
	 * don't use getOutput, for example tests that get some value and then test it using verify methods. 
	 * 
	 * @param out - PrintStream to print to - normally System.out
	 */
	void printInfo(PrintStream out)
	{
		// print statements are responsible for adding trailing newline
		// normally they shouldn't have preceding newline
		flushPrintStreams(out);

		//printTestClassNameIfNotAlreadyPrinted(out);
		//printTestNameIfNotAlreadyPrinted(out);
		AssertionTools.syncInfo();

		if (description == null)
		{
			//out.println("DESCRIPTION: [null]\n");
		}
		else
		{
			out.println("DESCRIPTION: " + description.trim() + "\n");
		}

		if (expectOutput)
		{
			if (input == null)
			{
				throw new TestHelpError("info.expectOutput is true but info.input is null"); // added 2019-009-30
			} 
			else if (input.isEmpty())
			{
				out.println("INPUT: [empty]\n");
			} 
			else
			{
				out.println("INPUT: " + input.trim() + "\n");
			}

			if (exception == null)
			{
				
				if (output == null)
				{
					throw new TestHelpError("info.expectOutput is true but info.output is null"); // added 2019-05-24
				}
				else if (output.isEmpty())
				{
					out.println("OUTPUT: [empty]\n");
				}
				else
				{
					out.println("OUTPUT: " + output.trim() + "\n");
				}

			}
			else // exception != null
			{
        			if (outputBeforeException != null) // means exception was caught
        			{
        				if (outputBeforeException.isEmpty())
        				{
        					//out.println("OUTPUT_BEFORE_EXCEPTION: [empty]\n");  // skip
        				}
        				else if (exceptionIsInternal)
        				{
        					out.println("OUTPUT [BEFORE INTERNAL EXCEPTION]: " + outputBeforeException.trim() + "\n"); 
        				}
        				else
        				{
        					out.println("OUTPUT [BEFORE EXCEPTION]: " + outputBeforeException.trim() + "\n");
        				}
        			}
				if (output == null || output.isEmpty())
				{
					throw new TestHelpError("info.exception is not null but info.output is null or empty"); // added 2019-08-30
				}
   				else if (exceptionIsInternal)
				{
					out.println("OUTPUT [INTERNAL EXCEPTION]: " + output.trim() + "\n"); 
				}
				else
				{
					out.println("OUTPUT [EXCEPTION]: " + output.trim() + "\n");
				}
        			
			}

			if (value != null)
			{
				out.println("VALUE: " + value.getClass().getSimpleName() + " " + value.toString()  + "\n"); 
			}
			else if (expectValue)
			{
				out.println("VALUE: [not set]\n"); // Will be the case in bundled getOutput with exception
			}
			else
			{
				// skip
			}
		
			if (exception != null)
			{
				//out.println("EXCEPTION: " + exception.toString() + "\n"); // don't need this - already printed as OUTPUT
			}

			if (errorOutput == null || errorOutput.isEmpty())
			{
				// skip
			} 
			else
			{
				out.println("ERROR_OUTPUT: " + chunkedErrorOutput.trim() + "\n");
			}

			if (errorAndExceptionSummary != null && !errorAndExceptionSummary.isEmpty())
			{
				// out.println("ERROR_OUTPUT ERRORS AND EXCEPTIONS: " + errorAndExceptionSummary.trim() + "\n"); // comment out 2019-08-30
			}
		}

	}
	

	/**
	 * Calls {@link printInfo} if it has not already been called for the current test, else does nothing.
	 * @param out - PrintStream to print to - normally System.out
	 */
	void printInfoIfNotAlreadyPrinted(PrintStream out)
	{
		if (!infoPrinted)
		{
			printTestNameIfNotAlreadyPrinted(out); // also does testClassName
			printInfo(out);
			infoPrinted = true;

		}
	}

	// ====================================================================
	//
	//	 print message (1): printFailMessage
	//
	// ====================================================================

	/**
	 * Prints assertion failure message in the following form:
	 * 
	 * <ul><li>printed directly</li></ul>
	 * 
<pre>
  ASSERTION FAILURE (SampleOutputInfoPrintingTests.java:272): Fail message printed with verify.fail
</pre>  
  
  	* or:
  	* <br>
	* <ul><li>printed from within and assertion class</li></ul>
 <pre> 
  ASSERTION FAILURE (SampleOutputInfoPrintingTests.java:285): Expected actual number 43 to be equalTo expected number 42 but was not

  ASSERTION FAILURE (SampleOutputInfoPrintingTests.java:298): Expected 22/7 3.142857 to be within tolerance 0.000100 of pi 3.141593 but it was not

  ASSERTION FAILURE (SampleOutputInfoPrintingTests.java:311): Expected string [foo] to be equal to string [food] but is not

  ASSERTION FAILURE (SampleOutputInfoPrintingTests.java:325): Expected to find pattern 'food' but did not in string 'some foo for you'
</pre>
	 * <p>
	 * Note that this method only prints a message, it does not throw a java.lang.AssertionError exception.
	 *  
	 * @param out PrintStream to print to - normally System.out
	 * @param message message to print
	 */
	static void printFailMessage(PrintStream out, String message)
	{
		flushPrintStreams(out);
		TestNameInfo testNameInfo = getTestNameInfo();
		String testNameFileNameAndLineNumber = testNameInfo.getFileNameAndLineNumber();
		out.println("ASSERTION FAILURE " + testNameFileNameAndLineNumber + ": " + message.trim()
		+ "\n");
	}
	
	// ====================================================================
	//
	//	 print message (2): printWarningMessage
	//
	// ====================================================================

	/**
	 * Prints warning message to output stream in the following form:
	 * 
<pre>
  ASSERTION WARNING (SampleOutputInfoPrintingTests.java:251): Warning message printed with verify.info
</pre> 
	 * @param out PrintStream to print to - normally System.out
	 * @param message message to print
	 */
	static void printWarningMessage(PrintStream out, String message)
	{
		flushPrintStreams(out);
		TestNameInfo testNameInfo = getTestNameInfo();
		String testNameFileNameAndLineNumber = testNameInfo.getFileNameAndLineNumber();
		out.println("ASSERTION WARNING " + testNameFileNameAndLineNumber + ": " + message.trim() + "\n");
	}
	
	// ====================================================================
	//
	//	 print message (3): printInfoMessage
	//
	// ====================================================================

	/**
	 * Prints info message in the following form:
	 * 
<pre>
  ASSERTION INFO (SampleOutputInfoPrintingTests.java:239): Info message printed with verify.info
 </pre>
	 *  
	 * @param out PrintStream to print to - normally System.out
	 * @param message message to print
	 */
	static void printInfoMessage(PrintStream out, String message)
	{
		flushPrintStreams(out);
		TestNameInfo testNameInfo = getTestNameInfo();
		String testNameFileNameAndLineNumber = testNameInfo.getFileNameAndLineNumber();
		out.println("ASSERTION INFO " + testNameFileNameAndLineNumber + ": " + message.trim() + "\n");
	}

}
