package testHelp;

import static testHelp.AssertionTools.*;

import java.util.Collection;
import java.util.concurrent.Callable; 

/**
 * A collection of static methods to verify assertions in tests. Using
 * verify.that(object) returns some type of assertion, depending on what
 * the object is. Additional verifications can then be chained, e.g.
 * verify.that("foobar").matches("foo").
 * 
 * @author ejewart
 *
 */
public class verify 
{
	/**
	 * Never called private do-nothing constructor to suppress default constructor in javadoc
	 */
	private verify()
	{
		
	}

	/**
	 * verify things about a String 
	 * 
	 * @param subject the string to examine
	 * @param subjectDescArg an optional description of the string
	 * @return a new StringAssertion initialized with subject string and description
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static StringAssertion that(String subject, String ... subjectDescArg)
	{
		syncInfo();
		noExceptionThrown(false);
		return new StringAssertion(subject,subjectDescArg);
	}

	/** Verify things about a runnable snippet of code
	 * 
	 * @param subject the runnable functional object to run (once) and examine
	 * @param subjectDescArg an optional description of the runnable
	 * @return a new StringAssertion initialized with subject string and description
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static RunnableAssertion that(Runnable subject, String ... subjectDescArg)
	{
		syncInfo();
		noExceptionThrown(false);
		return new RunnableAssertion(subject,subjectDescArg);
	}
	
	/** Verify things about a callable snippet of code 
	 * 
	 * @param subject the callable functional object to call (once) and examine
	 * @param subjectDescArg an optional description of the runnable
	 * @return a new CallableAssertion initialized with subject callable and description
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static CallableAssertion that(Callable<?> subject, String ... subjectDescArg)
	{
		syncInfo();
		noExceptionThrown(false);
		return new CallableAssertion(subject,subjectDescArg);
	}
	
	/** Verify things about a callable snippet of code - generic version 
	 * 
	 * @param subject the callable functional object to call (once) and examine
	 * @param subjectDesc a (non-optional) description of the callable
	 * @return a new GenericCallableAssertion with return type {@literal <T>} initialized with subject callable and description
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static CallableAssertion that(Callable<?> subject, String subjectDesc)
	{
		syncInfo();
		noExceptionThrown(false);
		return new CallableAssertion(subject,subjectDesc);
	}
	
	/** Verify things about an object (used when we don't have a more specific type to verify)
	 * 
	 * @param <T> the type of the object to examine
	 * @param subject the object to examine
	 * @param subjectDescArg an optional description of the object
	 * @return a new GenericObjectAssertion of type {@literal <T>} initialized with subject object and description
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static <T> GenericObjectAssertion<T> thatObject(T subject, String ... subjectDescArg) // needs special name due to overload ambiguity with variadic methods
	{
		syncInfo();
		noExceptionThrown(false); // subject better not be throwable
		return new GenericObjectAssertion(subject,subjectDescArg);
	}
	
	/** Verify things about a collection, like an array or ArrayList
	 *
	 * @param <T> teh type of the objects in the collection
	 * @param subject a collection (typically an ArrayList) or objects of type {@literal <T>} to examine
	 * @param subjectDescArg an optional description of the collection
	 * @return a new instance of {@literal CollectionAssertion<T>}
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static <T> CollectionAssertion that(Collection<T> subject, String ... subjectDescArg)
	{
		syncInfo();
		noExceptionThrown(false);
		return new CollectionAssertion(subject,subjectDescArg);
	}
	
	/**
	 * Verify things about a double value
	 * 
	 * @param subject the double value to examine
	 * @param subjectDescArg an optional description of the subject double value
	 * @return a new instance of DoubleAssertion
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static DoubleAssertion that(double subject, String ... subjectDescArg)
	{
		syncInfo();
		noExceptionThrown(false);
		return new DoubleAssertion(subject,subjectDescArg);
	}
	
	/**
	 * Verify things about an int value
	 * 
	 * @param subject the int value to examine
	 * @param subjectDescArg an optional description of the subject int value
	 * @return a new instance of IntAssertion
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static IntAssertion that(int subject, String ... subjectDescArg)
	{
		syncInfo();
		noExceptionThrown(false);

		return new IntAssertion(subject,subjectDescArg);
	}
	
	/**
	 * Verify things about a boolean value
	 * 
	 * @param subject the boolean value to examine
	 * @param subjectDescArg an optional description of the subject boolean value
	 * @return a new instance of BooleanAssertion
	 * @throws java.lang.AssertionError if an exception has previously been caught and stored in outputInfo
	 */
	public static BooleanAssertion that(boolean subject, String ... subjectDescArg)
	{
		syncInfo();
		noExceptionThrown(false);
		return new BooleanAssertion(subject,subjectDescArg);
	}
	
	/**
	 * Verify that an exception was previously caught and stored in outputInfo  
	 * 
	 * @return a new instance of ExceptionAssertion
	 * @throws java.lang.AssertionError if an exception was not previously caught and stored in outputInfo
	 */
	public static ExceptionAssertion exceptionThrown() {
		syncInfo();
		Throwable ex = ConsoleTester.getExceptionFromInfo();
		if (ex != null) {
			return new ExceptionAssertion(ex);
		} else {
			testHelp.AssertionTools.fail("Expected exception but none thrown");
			return null; // not reached
		}
	}
	
	/**
	 * Verify that an exception was not previously caught and stored in outputInfo  
	 * 
	 * @throws java.lang.AssertionError if an exception was previously caught and stored in outputInfo
	 */
	public static void noExceptionThrown()
	{
		noExceptionThrown(true);
	}
	
	/**
	 * Private versions of noExceptionThrown which allows suppression of @link testHelp.AssertionTools#syncInfo
	 * @param doSyncInfo if true, call syncInfo, if not, don't
	 */
	private static void noExceptionThrown(boolean doSyncInfo) 
	{
		if (doSyncInfo)
		{
			syncInfo();
		}
		Throwable ex = ConsoleTester.getExceptionFromInfo();
		if (ex != null) 
		{
			testHelp.AssertionTools.fail("Did not expect exception but one thrown: " + ex.toString());
		}
	}
	
	/**
	 * Force assertion failure - useful in if statements and catch blocks or replacing non-compiling code in test.
	 * <p>
	 * Calls {@link AssertionTools#fail(String message)}
	 * 
	 * @param message message to include in assertion failure
	 * @throws java.lang.AssertionError with message included
	 */
	public static void fail(String message)
	{
		testHelp.AssertionTools.fail(message);
			
	}

	/**
	 * Like {@link #fail} but with but with String.format-style message formatting.
	 * <p>
	 * Calls {@link AssertionTools#failF(String format, Object [] args)}
	 * 
	 * @param messageFormat String.format-style format string
	 * @param messageArgs zero or more objects to insert into format string
	 * @throws java.lang.AssertionError with message included
	 */
	public static void failF(String messageFormat,Object ... messageArgs) // for use in if statements and catch blocks or replacing non-compiling code in in test
	{
		testHelp.AssertionTools.failF(messageFormat,messageArgs);

	}

	/**
	 * Like {@link #fail} but prints message and chunked stacktrace of exception to System.out before calling org.junit.Assert.fail.
	 * <p>
	 * Calls {@link AssertionTools#failWithException(String message, Throwable ex)}
	 * @param message message to print
	 * @param ex previously caught throwable object
	 * @throws java.lang.AssertionError with message
	 */
	public static void failWithException(String message, Throwable ex) // for use in if statements and catch blocks or replacing non-compiling code in in test
	{
		testHelp.AssertionTools.failWithException(message,ex);
			
	}

	/**
	 * Like {@link #failWithException} but with String.format-style message formatting - exception is passed as format arg.
	 * <p>
	 * Calls {@link AssertionTools#failWithExceptionF(String format, Object[] argsEndingWithException)}
	 * 
	 * @param messageFormat String.format-style format string
	 * @param messageArgsEndingWithException one or more objects to insert into format string, with previously caught throwable object last
	 * @throws java.lang.AssertionError with message
	 */

	public static void failWithExceptionF(String messageFormat,Object ... messageArgsEndingWithException)
	{
		testHelp.AssertionTools.failWithExceptionF(messageFormat,messageArgsEndingWithException);

	}

	/**
	 * For communicating issue with test without causing it to fail.
	 * <p>
	 * Calls {@link AssertionTools#warning(String message)}
	 * 
	 * @param message massage to print
	 */
	public static void warning(String message)
	{
		testHelp.AssertionTools.warning(message);
			
	}

	/**
	 * Like {@link #warning} but with String.format-style message formatting
	 * <p>
	 * Calls {@link AssertionTools#warningF(String format, Object[] args)}
	 * 
	 * @param messageFormat String.format-style format string
	 * @param messageArgs one or more objects to insert into format string
	 */	
	public static void warningF(String messageFormat,Object ... messageArgs)
	{
		testHelp.AssertionTools.warningF(messageFormat,messageArgs);

	}

	/**
	 * For communicating supplementary information about test. 
	 * 
	 * @param message message to print
	 */
	public static void info(String message)
	{
		testHelp.AssertionTools.info(message);
			
	}
	/**
	 * Like {@link #info} but with String.format-style message formatting
	 * 
	 * @param messageFormat String.format-style format string
	 * @param messageArgs one or more objects to insert into format string
	 */
	public static void infoF(String messageFormat,Object ... messageArgs)
	{
		testHelp.AssertionTools.infoF(messageFormat,messageArgs);

	}

	

}
