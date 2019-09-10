package testHelp;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import testHelp.VFunctionsAndVUnboundMethods.VBiFunction;
import testHelp.VFunctionsAndVUnboundMethods.VBiUnboundMethod;
import testHelp.VFunctionsAndVUnboundMethods.VFunction;
import testHelp.VFunctionsAndVUnboundMethods.VUnboundMethod;

import static testHelp.VFunctionsAndVUnboundMethods.makeVFunction; // used by getOutput and getOutputFromMainClassName

/** 
 * Simplifies testing of a Java application with a main method that reads input from System.in and/or writes output to System.out through getOutput and related methods.
 * <p>
 * This version is backward compatible with the original version, but extends it in two directions:
 * 
 * <ol start="1">
 * 	<li>Addition of optional informational output written to System.out, controlled by a settable verbosityLevel</li>
 * 	<li>Extensions to the core ConsoleTester getOutput functionality</li>
 * </ol>
 * <p>
 * These are described in more detail in the next two sections.
 * <ol start="1">
 *  	<li>Addition of optional informational output written to System.out, controlled by a settable verbosityLevel</li>
 * </ol>
 * <p>
 * The extended version has a verboseLevel parameter whose initial value is 0 but which can be set to 1 or 2, normally via a static initializer
 * in the test class, as:
 * <pre>
  static 
  {
	  ConsoleTester.setVerbose(1); // or 2
  }</pre>
 *
 * If verboseLevel is 0, ConsoleTester getOutput and other library components behave essentially as they do on the original version of the library
 * and write nothing to System.out, and write only stacktraces of uncaught exceptions (JUnit errors) to System.err.  But if verboseLevel
 * is 1 or 2, they write additional information to System.out, including the name of the test class, the name of the test within the test class,
 * and other information.  The only difference between 1 and 2 is that the test names are printed for all tests with verboseLevel 2 and only those
 * with messages with verboseLevel 1.
 * <p>
 * Note:  When running inside eclipse both System.out and System.err go to the eclipse console, System.out appearing in black, System.err in red.
 * Also filename + line number strings in both System.out and System.err text are clickable as they are in stacktraces and transfer focus to the
 * file and line. And finally, the console output can be searched, and the full content or any part of it can be easily selected and copied
 * to the clipboard for further use.
 * <p>
 * For more on informational output produced by getOutput see {@link OutputInfo}.
 *<br>
 * <ol start="2">
 * 	<li>Extensions to the core ConsoleTester getOutput functionality</li>
 * </ol>
 * <p>
 * The core getOutput functionality is now provided in two forms:
 * 
<ol start=1>
  <li>Bundled - the original form - everything happens in a single call to one of the overloads of the getOutput method, which executes the main
      method internally, as:</li>
</ol>
<pre>

  String output = ConsoleTester.getOutput("MyMainClass","some input\n");
</pre>

<ol start=2>
  <li>Unbundled - added in the new version - a direct invocation of main, wrapped in a try-catch together with {@literal getOutput<stage>} methods
  that perform different stages of the complete getOutput process, as:</li>
</ol>
<pre>

  String output;
  try 
  {
      ConsoleTester.getOutputStart("some input\n");
      MyMainClass.main(null);
      output = ConsoleTester.getOutputFinish();
  }
  catch (Throwable ex)
  {
      output = getOutputFromException(ex);
  }
</pre>
 * The bundled form is the original one; the unbundled form is an enhancement.
 * <p>
 * The code between getOutputStart and getOutputFinish in the  unbundled form is not limited to a call to a main routine, but can be
 * any statement or sequence of statements for example:
<pre>
  String output;
  try 
  {
      ConsoleTester.getOutputStart(null); // called methods do not read from System.in
      Grid grid = new Grid(10,12);
      grid.set(0,0,"First cell");
      grid.set(9,11,"Last cell");
      //grid.set(10,12,"Last cell"); // would throw an out-of-bounds exception that would be processed by getOutputFromException, 
      grid.print();
     output = getOutputFinish();
  }
  catch (Throwable ex)
  {
      output = ConsoleTester.getOutputFromException(ex);
  }
</pre>
<p>
 * The enhanced version also provides new forms of bundled getOutput for calling main routines in different ways and also for calling methods
 * other than main.  Among them are:
 * <br>
 * <ul><li>The ability to provide With descriptions of what is being run, optional in backward compatible overloads, required new ones, as:</li></ul>
<pre>

  String output = ConsoleTester.getOutput("MyMainClass","some input\n","MyMainClass::main + no args");
</pre>
 *
 * <ul><li>An overload of getOutput which allows an array of String arguments to be passed to the main routine, as:</li></ul>
<pre>

  String output = ConsoleTester.getOutput("MyMainClass",new String[]{"arg1","arg2"},"some input\n","MyMainClass.main + two args");
</pre>
 * <br>
 * <ul><li>An overload of getOutput which allows the main routine to be passed as a method reference rather than a class name, as:</li></ul>
 * <pre>
  String output = ConsoleTester.getOutput(MyMainClass::main, null, "some input\n","MyMainClass::main + no args");

or:

  String output = ConsoleTester.getOutput(MyMainClass::main, new String[]{"arg1","arg2"},"some input\n","MyMainClass.main + two args");
</pre>
 * <br>
 * <ul><li>A set of extended overloads of the form getOutputFromVFunction and getOutputFromVUnboundMethod and variants that make it possible to call 
 * methods other than main which can tae arguments of any type and return values of any type.  The methods are normally passed as method references
 * wrapped in overloaded converter functions makeVFunction and makeVUnbounMethod and variant, and can be references to static methods. bound instance methods
 * and unbound instance methods (which require an instance of the class to be passed), as</li></ul>
 * <pre>
 * 
  String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithSpecializedMethods::staticFunction_arg_int_return_int),
                                                       10,null,"TestClass::staticFunction_arg_int_return_int");
  Object value = ConsoleTester.getValueFromInfo();

or:

  TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();		
  String output = ConsoleTester.getOutputFromVFunction(makeVFunction(instance::instanceFunction_arg_int_return_int),
                                                       10,null,"instance::instanceFunction_arg_int_return_int");
  Object value = ConsoleTester.getValueFromInfo();

or:

  TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();
  String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod(TestClassWithSpecializedMethods::instanceFunction_arg_int_return_int),
                                                            instance,10,null,"TestClassWithSpecializedMethods::instanceFunction_arg_int_return_int");
  Object value = ConsoleTester.getValueFromInfo();

</pre>




 * Examples of bundled and unbundled getOutput method calls can be found in various unittest files in test/testHelp including:
 * 
<ul>
  <li>ConsoleTesterTests.java</li>
  <li>ConsoleTesterVFunctionAndVUnboundMethodTest.javas</li>
  <li>MiscTests.java</li>
  <li>SampleOutputInfoPrintingTests.java - output in SampleOutputInfoPrintingTests-output.txt</li>
</ul>
 *    
 * @author ejewart
 * @author John Armstrong
 *
 */
public class ConsoleTester
{
	/**
	 * Never called private do-nothing constructor to suppress default constructor in javadoc
	 */
	private ConsoleTester()
	{

	}

	// ==========================================================================================
	//
	// getOutput overloads
	//
	// ==========================================================================================


	/**
	 * A reimplementation of the original getOutput.  Runs the main method in className, redirecting input to its System.in
	 * stream and capturing output to its System.out (and System.err) until the main routine returns, then returns captured output
	 * as a string.  If the main routine throws an exception and does not return, or if an internal exception is thrown from within 
	 * testHelp, returns instead a string representation of the exception. 
	 * <p>
	 * Calls {@link #getOutput(String className, String [] mainArgs, String input, String [] descArg)} with null value for mainArgs.
	 * 
	 * @param className qualified name of class to load (e.g. MyClass or MyPackage.MyClass)
	 * @param input text to feed to System.in after main is called
	 * @param descArg an optional description of what is being run - defaults to {@literal "<className>::main"}
	 * @return any text output to System.out when main runs, of string representation of an uncaught exception
	 */
	public static String getOutput(String className, String input, String ...descArg)
	{
		return getOutput(className,null,input,descArg);
	}

	/**
	 * Like {@link #getOutput(String className, String input, String [] descArg)}, but allows an array of string arguments to be passed to the method.
	 * <p>
	 * Loads the class if it has not already been loaded (which it ordinarily has been) and makes a Consumer functional object from the main method
	 * via {@link #makeStringArrayConsumerFromMainClassName}, makes a vFunction from the functional object via a
	 * {@code makeVFunction} overload, and passes the vFunction and the other arguments to {@link #getOutputFromVFunction}.
	 * 
	 * @param className the qualified name of class to load (e.g. MyClass or MyPackage.MyClass)
	 * @param mainArgs an array of strings to be passed as args to main - no args may be passed as null or as <code>new String[0]</code>
	 * @param input text to feed to System.in after main is called
	 * @param descArg an optional description of what is being run - defaults to {@literal "<className>::main"}
	 * @return any text output to System.out when main runs
	 */
	public static String getOutput(String className, String [] mainArgs, String input, String ...descArg)
	{
		String desc = AssertionTools.getDesc(descArg, className + "::Main");
		Consumer<String []> mainClassAsStringArrayConsumer = makeStringArrayConsumerFromMainClassName(className,classLoader);
		return getOutputFromVFunction(makeVFunction(mainClassAsStringArrayConsumer),mainArgs,input,desc);

	}

	/**
	 * An alternative implementation of getOutput, which runs the main method directly via a method reference to the class and main method
	 * of the form {@literal <simple-class-name>::main}.  Allows an array of string arguments to be passed to the method.
	 * <p>
	 * Makes a vFunction from the functional object via a {@code makeVFunction} overload, and passes the vFunction
	 * and the other arguments to {@link #getOutputFromVFunction}.
	 * 
	 * @param mainMethodReference a functional object of type {@literal consumer<String []>}, typically passed as a method reference
	 * @param mainArgs an array of strings to be passed as args to main - no args may be passed as null or as <code>new String[0]</code>
	 * @param input text to feed to System.in after main is called
	 * @param desc a description of what is being run
	 * @return any text output to System.out when main runs
	 */
	public static String getOutput(Consumer<String []> mainMethodReference, String [] mainArgs, String input, String desc)
	{
		return getOutputFromVFunction(makeVFunction(mainMethodReference),mainArgs,input,desc);
	}


	// ==========================================================================================
	//
	// Unbundled getOutput methods
	//
	// ==========================================================================================

	/**
	 * Reset OutputInfo.info - remembers testNameInfo, testClassNamPrinted and testNamPrinted, resets all other fields to initial state.
	 * <p>
	 * Allows getOutputStart-getOutputFinish-getOutputFromException and similar sequences to be run more that once in
	 * the same test with output assigned to separate strings.
	 */
	public static void getOutputReset()
	{
		// we want to keep the testName and whether it's printed, reset everything else
		// XXX this should probably be a Info method for proper encapsulation - compare sync()
		if (OutputInfo.info == null)
		{
			OutputInfo.sync();
		}
		else
		{
			OutputInfo.TestNameInfo savedTestNameInfo = OutputInfo.info.testNameInfo;
			boolean savedTestClassNamePrinted = OutputInfo.info.testClassNamePrinted;
			boolean savedTestNamePrinted = OutputInfo.info.testNamePrinted;
			OutputInfo.info = null;
			OutputInfo.sync();
			OutputInfo.info.testNameInfo = savedTestNameInfo;
			OutputInfo.info.testClassNamePrinted = savedTestClassNamePrinted;
			OutputInfo.info.testNamePrinted = savedTestNamePrinted;
		}


	}

	/**
	 * Initialize ConsoleTester for presenting input to and getting output from interactive code.
	 * <p>
	 * This method must be called before any other getOutput.. methods except getOutputReset.  It can be called
	 * after a call to getOutputReset, and can be called more than once if a call to getOutputRest is made
	 * between calls.
	 * 
	 * @param input string representing input
	 * @param desc a description of what is being run
	 * @throws TestHelpError if called twice without intervening call to getOutput
	 */
	public static void getOutputStart(String input, String desc)
	{
		AssertionTools.syncInfo(); // Will print test class name and test name verboseLevel permitting

		if (OutputInfo.info.getOutputInitialized)
		{
			TestHelpError ex =  new TestHelpError("getOutputStart called twice");
			ex.printStackTrace();
			throw ex;
		}

		OutputInfo.info.getOutputInitialized = true;
		
		OutputInfo.info.expectValue = false; // will be set to true in bundled getOutput methods

		OutputInfo.info.description = desc;

		OutputInfo.info.expectOutput = true;

		OutputInfo.info.output = "";

		// Make sure delegating streams are in sync with System streams

		systemIn.setInputStream(System.in);
		systemOut.setPrintStream(System.out);
		systemErr.setPrintStream(System.err);

		// Following are referenced below so have to be declared
		OutputInfo.info.savedSystemIn = System.in;
		OutputInfo.info.savedSystemOut = System.out;
		OutputInfo.info.savedSystemErr = System.err; // For including stacktraces in output (verbose only)

		OutputInfo.info.outputBytes = null; // accessed outside try
		OutputInfo.info.errorBytes = null; // accessed outside try

		// turn the input string into a stream and make System.in use
		// this stream instead of the console

		OutputInfo.info.input = (input == null) ? "" : input;

		// set up input stream to replace System.in
		OutputInfo.info.inputBytes = new ByteArrayInputStream(OutputInfo.info.input.getBytes());
		System.setIn(OutputInfo.info.inputBytes);
		systemIn.setInputStream(System.in);


		// set up an output stream to replace System.out
		OutputInfo.info.outputBytes = new ByteArrayOutputStream();
		OutputInfo.info.outputStream = makePrintStreamFromOutputBytes(OutputInfo.info.outputBytes);
		System.setOut(OutputInfo.info.outputStream);
		systemOut.setPrintStream(System.out);

		if (verboseLevel > 0)
		{
			// set up an output stream to replace System.err
			// do only for verbose - otherwise let stacktraces go to System.err as in original code
			OutputInfo.info.errorBytes = new ByteArrayOutputStream();
			OutputInfo.info.errorStream = makePrintStreamFromOutputBytes(OutputInfo.info.errorBytes);
			System.setErr(OutputInfo.info.errorStream);
			systemErr.setPrintStream(System.err);
		}

	}

	/** Third step in an unbundled {@literal getOutputStart-<run-user-code>-getOutputFinish-getOutputFromException} sequence.
	 * If the run-user-code step does not throw an exception that it does not handle itself getOutputFinish will be called
	 * and the and will call getOutputFinalize internally and return a string representing the output from the 
	 * 
	 * @return any text output to System.out when main runs
	 * @throws TestHelpError if getoutputStart has not previously been called
	 */
	public static String getOutputFinish()
	{
		OutputInfo.sync();

		if (!OutputInfo.info.getOutputInitialized)
		{
			throw new TestHelpError("getOutputFinish called but not getOutputStart");
		}
		// return the string version of whatever was written to the output stream

		OutputInfo.info.output = outputBytesToString(OutputInfo.info.outputBytes).trim();

		return getOutputFinal();


	}

	/**
	 * Alternative third step in {@literal getOutputStart-<run-user-code>-getOutputFinish-getOutputFromException} sequence.
	 * The normal sequence {@literal getOutputStart-<run-user-code>-getOutputFinish} should be placed in a try block and
	 * getOutputFromException should be placed in the catch block.  Returns a string representation of the exception, just
	 * as bundled getOutput does when an exception is thrown and caught within the code.
	 * 
	 * @param ex exception thrown by previously executed code
	 * @return a string representation of the exception
	 * @throws TestHelpError if getoutputStart has not previously been called
	 */
	public static String getOutputFromException(Throwable ex)
	{
		OutputInfo.sync();

		if (!OutputInfo.info.getOutputInitialized)
		{
			ex = new TestHelpError("getOutputFromException called but not getOutputStart"); // make like it's already been thrown
		}

		if (ex instanceof TestHelpError)
		{
			if (OutputInfo.info.savedSystemErr != null)
			{
				System.setErr(OutputInfo.info.savedSystemErr); // Don't need to set it back to bytestream (?)
			}
			printChunkedStackTrace(ex); // goes to real System.err
			throw (TestHelpError)ex;
		}

		// =========================================
		// core exception handling
		// =========================================

		OutputInfo.info.outputIsException = true;

		OutputInfo.info.outputBeforeException = outputBytesToString(OutputInfo.info.outputBytes).trim();

		if (ex instanceof WrappedException)
		{
			Throwable wrappedException = ex.getCause();
			boolean internal = ((WrappedException) ex).getInternal();
			if (verboseLevel > 0)
			{
				wrappedException.printStackTrace(); // goes to buffer, will be chunked on output
			}
			OutputInfo.info.exception = wrappedException;
			OutputInfo.info.exceptionIsInternal = internal;
			OutputInfo.info.output = OutputInfo.info.exception.toString();
		}
		else
		{
			if (verboseLevel > 0)
			{
				ex.printStackTrace(); // goes to buffer, will be chunked on output
			}

			OutputInfo.info.exception = ex;
			OutputInfo.info.output = OutputInfo.info.exception.toString();
		}


		getOutputFinal();

		return OutputInfo.info.output;

	}


	/**
	 * Finalize output.  Called internally at end of {@link #getOutputFinish} and {@link #getOutputFromException}, including within
	 * {@link #getOutput} and all top-level getOutput.. methods.  Should not be called dir3ectly.
	 * 
	 * @return any text output to System.out when main runs
	 */
	public static String getOutputFinal()
	{
		OutputInfo.sync();

		if (!OutputInfo.info.getOutputInitialized)
		{
			throw new TestHelpError("getOutputFinal called but not getOutputStart");
		}

		System.setIn(OutputInfo.info.savedSystemIn); // no change if input was null
		systemIn.setInputStream(System.in); // no change if input was null
		System.setOut(OutputInfo.info.savedSystemOut);
		systemOut.setPrintStream(System.out);

		if (verboseLevel > 0)
		{

			System.setErr(OutputInfo.info.savedSystemErr);
			systemErr.setPrintStream(System.err);

			OutputInfo.info.errorOutput = outputBytesToString(OutputInfo.info.errorBytes).trim();		

			if (!OutputInfo.info.errorOutput.isEmpty()) // Following processing works with all errorOutput, not just exceptions
			{
				OutputInfo.info.errorOutputContainsErrorOrException = true;
				String testName = OutputInfo.info.testNameInfo.getTestName();
				String [] pair = ErrorOutputChunker.getErrorAndExceptionSummaryAndChunkedErrorOutput(OutputInfo.info.errorOutput,testName);
				OutputInfo.info.errorAndExceptionSummary = pair[0];
				OutputInfo.info.chunkedErrorOutput = pair[1]; 
			}


			if (OutputInfo.info.outputIsException || OutputInfo.info.errorOutputContainsErrorOrException)
			{
				printTestNameIfNotAlreadyPrinted();
				printInfoIfNotAlreadyPrinted();
			}
		}

		return OutputInfo.info.output;

	}


	// ==========================================================================================
	//
	// New getOutput implementations
	//
	// ==========================================================================================


	/**
	 * Make a {@literal Consumer<String []>} object (the functional type of the standard Java static main method) from a string consisting of a
	 * package name and the name of the class containing the main routine to run.
	 * <p>
	 * May throw WrappedException in two situations:
	 * <ul>
	 * <li>Could not load class or execute its main method - internal flag = true, wrapped exception = exception thrown by code in this method</li>
	 * <li>Loaded class main and executed main but main threw exception - internal flag = false, wrapped exception = exception thrown from within main</li>
	 * </ul>
	 * @param className full name of class containing main method top be executed
	 * @param classLoader classLoader object if different from default or null for default
	 * @return runnable functional object that throws no checked exceptions
	 * @throws WrappedException as explained above
	 */
	public static Consumer<String []> makeStringArrayConsumerFromMainClassName(String className, ClassLoader classLoader) {
		// load the main class, using either the system class loader or a custom one

		Consumer<String []> stringArrayConsumer = (String [] args) -> {
			Class<?> mainClass = null;
			try {
				if (classLoader == null)
					mainClass = Class.forName(className);
				else
					mainClass = classLoader.loadClass(className);

				// run mainClass's main method
				Method mainMethod = mainClass.getMethod("main",String[].class);
				mainMethod.invoke(null,(Object)args); // cast for disambiguation
			} catch (InvocationTargetException ex) {
				throw new WrappedException(ex.getTargetException(),false);
			} catch (Exception ex) {
				throw new WrappedException(ex,true);

			}
		};

		return stringArrayConsumer;

	}

	/**
	 * Do the bundled getOutput sequence running with a generic vFunction functional object, normally passed as method reference, together
	 * with an argument of the appropriate type.
	 * <p>
	 * Provided primarily to run standard Java main routines, which implement the {@literal Consumer<String []>)} and can
	 * converted to a vFunction, but can be used to run any functional object which can be converted to a vFunction, including ones
	 * of the following types:
	 * 
	 * <ul>
	 * <li>Runnable</li>
	 * <li>{@literal Callable<R>} and {@literal Supplier<R>} and specializations</li>
	 * <li>{@literal Consumer<A>} and specializations</li>
	 * <li>{@literal Function<A,R>} and specializations</li> 
	 * </ul>
	 * 
	 * The functional object may expressed as a method references to a static method, a method reference to a bound instance method,
	 * a lambda expression, or a variable to which any of these has been previously assigned.
	 * <p>
	 * For usage with a main routine see {@link #getOutput(Consumer mainMethodReference, String [] mainArgs, String input, String desc)}, which calls through
	 * to this method.
	 * <p>
	 * @param <A> the type of the argument passed to the vFunction
	 * @param <R> the type of the value returned by the vFunction - will be VoidR for Runnable and Consumer
	 * @param vFunction a vFunction functional object which takes an argument of type {@literal <A>} and returns a value of {@literal <R>}
	 * @param arg an argument of of type {@literal <A>} - will be VoidA for Runnable, Callable and Supplier
	 * @param input text to feed to System.in after method is called
	 * @param desc a description of what is being run 
	 * @return any text output to System.out when method runs
	 */	
	public static <A,R> String getOutputFromVFunction(VFunction<A,R> vFunction, A arg,  String input, String desc)
	{
		getOutputStart(input,desc);

		try
		{
			OutputInfo.info.expectValue = true;
			
			OutputInfo.info.value = vFunction.apply(arg);

			OutputInfo.info.output = outputBytesToString(OutputInfo.info.outputBytes).trim();
		} 
		catch (Throwable ex) 
		{
			getOutputFromException(ex);

		}

		getOutputFinal();

		return OutputInfo.info.output;
	}

	/**
	 * Do the bundled getOutput sequence running with a generic vUnboundMethod functional object, normally passed as method reference, together
	 * with an instance and an  argument of the appropriate type.
	 * <p>
	 * Can be used to run any unbound instance method functional object which can be converted to a vUnboundMethod via an overload
	 * of the static method makeVUnboundMethod, including ones of the following types:
	 * 
	 * <ul>
	 * <li>{@literal RunnableUnboundMethod<C>}</li>
	 * <li>{@literal CallableUnboundMethod<C,R>} and {@literal SupplierUnboundMethod<C,R>} and specializations</li>
	 * <li>{@literal ConsumerUnboundMethod<C,A>} and specializations</li>
	 * <li>{@literal FunctionUnboundMethod<C,A,R>} and specializations</li> 
	 * </ul>
	 * 
	 * The functional object will normally be expressed as a method reference to an unbound method or variable to which such
	 * a reference has previously been assigned
	 * 
	 * @param <C> the type of the instance passed to the vUnboundMethod
	 * @param <A> the type of the argument passed to the vUnboundMethod
	 * @param <R> the type of the value returned by the vFunction - will be VoidR for RunnableUnboundMethod and ConsumerUnboundMethod  
	 * @param vUnboundMethod a vUnboundMethod functional object which takes an instance argument of type {@literal <C>} and an argument 
	 * 	of type {@literal <A>} and returns a value of {@literal <R>}
	 * @param instance an argument of of type {@literal <C>} 
	 * @param arg an argument of of type {@literal <A>} - will be VoidA for RunnableUnboundMethod, CallableUnboundMethod and SupplierUnboundMethod 
	 * @param input text to feed to System.in after method is called
	 * @param desc a description of what is being run
	 * @return any text output to System.out when method runs
	 */	
	public static <C,A,R> String getOutputFromVUnboundMethod(VUnboundMethod<C,A,R> vUnboundMethod, C instance, A arg,  String input, String desc)
	{
		getOutputStart(input,desc);

		try
		{
			OutputInfo.info.expectValue = true;
			
			OutputInfo.info.value = vUnboundMethod.applyWithClassInstanceAsFirstArg(instance,arg);

			OutputInfo.info.output = outputBytesToString(OutputInfo.info.outputBytes).trim();
		} 
		catch (Throwable ex) 
		{
			getOutputFromException(ex);

		}

		getOutputFinal();

		return OutputInfo.info.output;
	}

	/**
	 * Do the bundled getOutput sequence running with a generic vFunction functional object, normally passed as method reference, together
	 * with an argument of the appropriate type.
	 * <p>
	 * Can be used to run any functional object which can be converted to a vBiFunction, including ones of the following types:
	 * 
	 * <ul>
	 * <li>{@literal BiConsumer<A1,A2>} and specializations</li>
	 * <li>{@literal BiFunction<A1,A2,R>} and specializations</li> 
	 * </ul>
	 *
	 * @param <A1> the type of the first argument passed to the vBiFunction
	 * @param <A2> the type of second the argument passed to the vBiFunction
	 * @param <R> the type of the value returned by the vBiFunction - will be VoidR for BiConsumer
	 * @param vBiFunction a vBiFunction functional object which takes two arguments of type {@literal <A1> and <A2>} and returns a value
	 * of {@literal <R>}
	 * @param arg1 an argument of type {@literal <A1>} 
	 * @param arg2 an argument of type {@literal <A2>} 
	 * @param input text to feed to System.in after consumer is called
	 * @param desc a description of what is being run 
	 * @return any text output to System.out when consumer runs
	 */	
	public static <A1,A2,R> String getOutputFromVBiFunction(VBiFunction<A1,A2,R> vBiFunction, A1 arg1, A2 arg2, String input, String desc)
	{
		getOutputStart(input,desc);

		try
		{
			OutputInfo.info.expectValue = true;
			
			OutputInfo.info.value = vBiFunction.apply(arg1,arg2);

			OutputInfo.info.output = outputBytesToString(OutputInfo.info.outputBytes).trim();
		} 
		catch (Throwable ex) 
		{
			getOutputFromException(ex);

		}

		getOutputFinal();

		return OutputInfo.info.output;
	}

	/**
	 * Do the bundled getOutput sequence running with a generic vBiUnboundMethod functional object, normally passed as method reference,
	 * together with an instance and two arguments of the appropriate types.
	 * <p>
	 * Can be used to run any functional object which can be converted to a vBiUnboundMethod via an overload of the static method
	 * makeVBiUnboundMethod, including ones of the following types:
	 * 
	 * <ul>
	 * <li>{@literal BiConsumerUnboundMethod<C,A1,A2>} and specializations</li>
	 * <li>{@literal BiFunctionUnboundMethod<C,A1,A2,R>} and specializations</li> 
	 * </ul>.
	 * 
	 * @param <C> the type of the instance passed to the vBiUnboundMethod
	 * @param <A1> the type of the first argument passed to the vBiUnboundMethod
	 * @param <A2> the type of the second argument passed to the vBiUnboundMethod
	 * @param <R> the type of the value returned by the vBiUnboundMethod - will be VoidR for BiConsumerUnboundMethod
	 * @param vBiUnboundMethod a vBiUnboundMethod functional object which takes an instance argument of type {@literal <C>} and two arguments
	 * of type {@literal <A1>} and {@literal <A2>} and returns a value of {@literal <R>}
	 * @param instance an argument of type {@literal <C>} 
	 * @param arg1 an argument of type {@literal <A1>}
	 * @param arg2 an argument of type {@literal <A2>} 
	 * @param input text to feed to System.in after consumer is called
	 * @param desc a description of what is being run 
	 * @return any text output to System.out when consumer runs
	 */	
	public static <C,A1,A2,R> String getOutputFromVBiUnboundMethod(VBiUnboundMethod<C,A1,A2,R> vBiUnboundMethod, C instance, A1 arg1, A2 arg2, String input, String desc)
	{
		getOutputStart(input,desc);

		try
		{
			OutputInfo.info.value = vBiUnboundMethod.applyWithClassInstanceAsFirstArg(instance,arg1,arg2);

			OutputInfo.info.output = outputBytesToString(OutputInfo.info.outputBytes).trim();
		} 
		catch (Throwable ex) 
		{
			getOutputFromException(ex);

		}

		getOutputFinal();

		return OutputInfo.info.output;
	}
	
	// ==========================================================================================
	//
	// Supplementary getOutput methods for getting value and exception as objects
	//
	// ==========================================================================================

	/**
	 * Get return value from OutputInfo.info if there is one, else null.
	 * <p>
	 * Value will be non-null if method executed by getOutput has been called and returns normally.  The value for void
	 * methods will be {@link testHelp.VFunctionsAndVUnboundMethods#voidR}.  It will be null only if no output method is is called or one
	 * is called but throws an exception.
	 * 
	 * @return Object or null
	 */
	public static Object getValueFromInfo()
	{
		OutputInfo.sync(); // so we don't get left-over value from previous test
		return OutputInfo.info.value;
	}

	/**
	 * Get exception (actually Throwable - may be either Exception or Error) from OutputInfo.info
	 * if there is one, else null.
	 * 
	 * @return Throwable or null
	 */
	public static Throwable getExceptionFromInfo()
	{
		OutputInfo.sync(); // so we don't get left-over value from previous test
		return OutputInfo.info.exception;
	}

	// ==========================================================================================
	//
	// Static fields and related static methods - classLoader - in original version
	//
	// ==========================================================================================

	private static ClassLoader classLoader = null;

	/**
	 * Allows an alternate class loader to be specified. This is useful when
	 * compiling code on the fly.
	 * 
	 * @param loader alternate class loader
	 */
	public static void setLoader(ClassLoader loader)
	{ 
		classLoader = loader;
	}


	// ==========================================================================================
	//
	// Static fields and related static methods - console encoding - set internally
	//
	// ==========================================================================================

	private static final String consoleEncoding;

	/**
	 * Gets the encoding of System.out as of when this class was loaded.
	 * Calls {@link #getEncodingFromPrintStream} with System.out as argument.
	 * 
	 * @return the standard name of the encoding
	 */
	public static String getConsoleEncoding()
	{
		return consoleEncoding;
	}

	static
	{
		String systemOutEncoding = getEncodingFromPrintStream(System.out);
		String systemErrEncoding = getEncodingFromPrintStream(System.err);
		if (!systemErrEncoding.equals(systemOutEncoding))
		{
			throw new TestHelpError(String.format(
					"Unexpected: systemErrEncoding %s != systemOutEncoding %s",
					systemErrEncoding, systemOutEncoding));
		}
		consoleEncoding = systemOutEncoding;
	}

	/**
	 * Get the encoding of a PrintStream such as System.out or System.err
	 * 
	 * @param ps the PrintStream
	 * @return the standard name of the encoding
	 */
	public static String getEncodingFromPrintStream(PrintStream ps)
	{
		OutputStreamWriter osw = new OutputStreamWriter(ps);
		return osw.getEncoding();
	}

	/**
	 * Make a PrintStream from a ByteArrayOutputStream.  Sets the encoding to the detected console encoding.
	 * @param outputBytes the ByteArrayOutputStream
	 * @return a new PrintStream with the appropriate encoding
	 */
	public static PrintStream makePrintStreamFromOutputBytes(ByteArrayOutputStream outputBytes)
	{
		try
		{
			return new PrintStream(outputBytes, true, consoleEncoding);
		} catch (UnsupportedEncodingException e)
		{
			throw new TestHelpError("PrintStream(outputBytes,true," + consoleEncoding
					+ ") threw UnsupportedEncodingException");
		}
	}

	/**
	 * Convert bytes accumulated in a ByteArrayOutputStream to a string using the detected console encoding
	 * 
	 * @param outputBytes a ByteOutputStream that has been created and (normally) written to by println or 
	 * other method that writes strings
	 * @return a string representing the decoded version of the bytes
	 */
	public static String outputBytesToString(ByteArrayOutputStream outputBytes)
	{
		try
		{
			return outputBytes.toString(consoleEncoding);
		} catch (UnsupportedEncodingException ex)
		{
			throw new TestHelpError("outputBytes.toString(" + consoleEncoding
					+ ") threw UnsupportedEncodingException");
		}
	}


	// ==========================================================================================
	//
	// Static fields and related static methods - verboseLevel
	//
	// ==========================================================================================

	// Defined in this class because they may be referenced in user code and ConsoleTester is the most commonly referenced class.


	private static int verboseLevel;

	/**
	 * Set verboseLevel to new value - normally called once per test class in a static initializer as described above.
	 * 
	 * @param level 0 for completely silent, 1 print all messaged but not testnames for messages with no messages, 2 print all messages
	 * and all test names including ones with no messages
	 */
	public static void setVerboseLevel(int level)
	{
		verboseLevel = level;
	}

	/**
	 * Get value of verboseLevel.
	 * 
	 * @return current value of verboseLevel
	 */
	public static int getVerboseLevel()
	{
		return verboseLevel;
	}


	// ==========================================================================================
	//
	// Static fields and related static methods - inputstream systemIn, printstreams systemOut and systemErr
	//
	// ==========================================================================================

	private static DelegatingInputStream systemIn = new DelegatingInputStream(System.in);
	private static DelegatingPrintStream systemOut = new DelegatingPrintStream(System.out);
	private static DelegatingPrintStream systemErr = new DelegatingPrintStream(System.err);

	/**
	 * Get an InputStream object that getOutput methods can redirect to a buffer-based InputStream object that
	 * the routine it is calling can read input from - for passing the equivalent of System.in as a class instance
	 * or argument in a call to getOutputFromVFunction and getOutputFromVUnboundMethod and related functions.
	 * 
	 * @return an InputStream object that will be redirected by getOutput method in parallel to System.in
	 */
	public static InputStream getSystemIn()
	{
		return systemIn;
	}

	/*
	 * Get an PrintStream object that getOutput methods can redirect to a buffer-based PrintStream object that
	 * the routine it is calling can write output to - for passing the equivalent of System.out as a class instance
	 * or argument in a call to getOutputFromVFunction and getOutputFromVUnboundMethod and related functions.
	 * 
	 * @return an PrintStream object that will be redirected by getOutput method in parallel to System.out
	 */

	public static PrintStream getSystemOut()
	{
		return systemOut;
	}

	/*
	 * Get an InputStream object that getOutput methods can redirect to a buffer-based PrintStream object that
	 * the routine it is calling can write error output to - for passing the equivalent of System.err as a class instance
	 * or argument in a call to getOutputFromVFunction and getOutputFromVUnboundMethod and related functions.
	 * 
	 * @return an PrintStream object that will be redirected by getOutput method in parallel to System.err
	 */
	public static PrintStream getSystemErr()
	{
		return systemErr;
	}

	// ==========================================================================================
	//
	// Print methods
	//
	// ==========================================================================================

	/**
	 * Print test class name to System.out if it has not already been printed, else do nothing - prints regardless of verboseLevel.
	 * <p>
	 * Rarely needs to be called directly.
	 * <p>
	 * Calls {@link testHelp.OutputInfo#printTestClassNameIfNotAlreadyPrinted(PrintStream out)}.
	 * <p>
	 * Important: this method must only be called when out is the real System.out, not a substitute
	 */
	public static void printTestClassNameIfNotAlreadyPrinted() // not called as of 2019-08-31
	{
		OutputInfo.sync();
		OutputInfo.info.printTestClassNameIfNotAlreadyPrinted(System.out);
	}

	/**
	 * Print test name to System.out if it has not already been printed, else do nothing - prints regardless of verboseLevel.
	 * <p>
	 * Also prints test class name if it has not already been printed.
	 * <p>
	 * Generally does not need to be called directly, but is useful to do so in tests that write directly to System.out to make
	 * it clear what test the direct writing is happening in.
	 * <p>
	 * Calls {@link testHelp.OutputInfo#printTestNameIfNotAlreadyPrinted(PrintStream out)}.
	 * <p>
	 * Important: this method must only be called when out is the real System.out, not a substitute
	 */
	public static void printTestNameIfNotAlreadyPrinted()
	{
		OutputInfo.sync();
		OutputInfo.info.printTestNameIfNotAlreadyPrinted(System.out);
	}

	/**
	 * Print output info to System.out if it has not already been printed, else do nothing - prints regardless of verboseLevel.
	 * <p>
	 * Also prints test class name and test name if they have not already been printed.
	 * <p>
	 * Important: this method must only be called when out is the real System.out, not a substitute
	 * <p>
	 * Calls {@link testHelp.OutputInfo#printInfoIfNotAlreadyPrinted(PrintStream out)}.
	 * <p>
	 * Important: this method must only be called when out is the real System.out, not a substitute
	 */
	public static void printInfoIfNotAlreadyPrinted()
	{
		OutputInfo.sync();
		OutputInfo.info.printInfoIfNotAlreadyPrinted(System.out);
	}

	/**
	 * Print stacktrace in chunked form to System.err.
	 * <p>
	 * Calls {@link ErrorOutputChunker#printChunkedStackTrace}
	 * <p>
	 * Important: this method must only be called when out is the real System.err, not a substitute

	 * @param ex a Throwable object (exception or error)
	 */
	public static void printChunkedStackTrace(Throwable ex)
	{
		OutputInfo.sync();
		String testName = OutputInfo.info.testNameInfo.getTestName();
		ErrorOutputChunker.printChunkedStackTrace(ex,System.err,testName);
	}

	/**
	 * Set the test description - useful for cases where it is not set by a getOutput method.  Will be
	 * printed along with other info if printInfoInfNotAlreadyPrinted is later called.
	 * 
	 * @param desc a description of what is being tested
	 */
	public static void setDescription(String desc)
	{
		AssertionTools.syncInfo(); // Will print test class name and test name verboseLevel permitting
		OutputInfo.info.description = desc;
	}

	/**
	 * Get the name of the currently running test
	 * 
	 * @return the bare test method name
	 */
	public static String getTestName()
	{
		OutputInfo.sync();
		return OutputInfo.getTestNameInfo().getTestName();
	}


}
