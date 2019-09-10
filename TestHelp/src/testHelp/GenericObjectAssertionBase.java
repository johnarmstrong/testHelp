package testHelp;

import static testHelp.AssertionTools.*;

import java.util.function.Predicate;

/**
 * Abstract base class for assertion classes for object (as opposed to primitive) types.  It implements covariance of subclass
 * assertion return types via a generic type argument.  The only abstract method is {@link #getDftDesc}, which simplifies initialization
 * of field DFT_DESC.
 * <p>
 * Note: this class is a genericized version of the reusable functionality in class {@link ObjectAssertion}.
 * @param <T> the object type, for storing object to be examined
 * @param <AssertionT> the assertion type, for downcasting returned this
 * 
 * @author John Armstrong
 */
public abstract class GenericObjectAssertionBase<T,AssertionT> {

	static final String DFT_TYPE_DESC = "type";
	//private static final String DFT_PREDICATE_DESC = "predicate";
	
	final String DFT_DESC; // not static, gotten from derived class, set in constructor  - shadowed in derived class (change this?)
	T subject;
	String subjectDesc;
	
	abstract String getDftDesc();
	
	/** Constructs a new ObjectAssertion. This is exposed so that
	 * consumers of the testHelp library can add their own assertion 
	 * classes that are subclasses of ObjectAssertion.
	 * 
	 * @param subject the object assertions are focused on
	 * @param subjectDescArg optional description
	 */
	public GenericObjectAssertionBase(T subject, String ... subjectDescArg)
	{
		DFT_DESC = getDftDesc();
		this.subject = subject;
		this.subjectDesc = getDesc(subjectDescArg, DFT_DESC);
	}
	
	/**
	 * Verifies that an object is null
	 * 
	 * @return this (cast to AssertionT) if object is null
	 * @throws java.lang.AssertionError if object is not null
	 */
	public AssertionT isNull()
	{
		if (subject != null)
		{
			failF("Expected %s to be null but is %s",subjectDesc,subject.toString());
		}

		return (AssertionT)this;
	}
	
	/**
	 * Verifies that an object is not null
	 * 
	 * @return this (cast to AssertionT) if object is not null
	 * @throws java.lang.AssertionError if object is null
	 */
	public AssertionT isNotNull()
	{
		if (subject == null)
			failF("Expected %s to be non-null but is null", subjectDesc);
		
		return (AssertionT)this;
	}
	
	/**
	 * Verifies that an object is of a particular type
	 * 
	 * Useful when subject has been cast to Object to get effect of old non-generic ObjectAssertion
	 * 
	 * @param type type to test that object is instance of
	 * @param typeDescArg an optional description of the type
	 * @return this (cast to AssertionT) if object is of specified type
	 * @throws java.lang.AssertionError if object is not of specified type
	 */
	public AssertionT isOfType(Class<?> type, String ... typeDescArg) // typeDesc should be substitutable for "type"
	{
		if (subject == null)
			failF("%s is null so cannot test type",subjectDesc);
		
		String typeDesc = getDesc(typeDescArg,DFT_TYPE_DESC);
		
		if (!subject.getClass().equals(type))
		{
			failF("Expected %s [%s] to be of %s %s but it is of %s %s",
					subjectDesc,subject.toString(),
					typeDesc,type.getSimpleName(),
					DFT_TYPE_DESC,subject.getClass().getSimpleName());
		}
		
		return (AssertionT)this;
	}

	
	/**
	 * Verifies that an object is an instance (including its own type or one of its superclasses or interfaces
	 * 
	 * @param type type to test that object is instance of
	 * @param typeDescArg an optional description of the type
	 * @return this (cast to AssertionT) if object is instance of type
	 * @throws java.lang.AssertionError if object is not instance of type
	 */
	public AssertionT isInstanceOf(Class<?> type, String ... typeDescArg) // typeDesc should be substitutable for "type"
	{
		if (subject == null)
			failF("%s is null so cannot test type",subjectDesc);
		
		String typeDesc = getDesc(typeDescArg,DFT_TYPE_DESC);
		
		if (!type.isInstance(subject))
		{
			failF("Expected %s [%s] to be instance of %s %s but it is of type %s",subjectDesc,subject.toString(),typeDesc,type.getSimpleName(),subject.getClass().getSimpleName());
		}
		
		return (AssertionT)this;
	}


	/**
	 * Verifies that an object is equal to another object, using (override of) Object.equals
	 * 
	 * @param other another object to compare subject to
	 * @param otherDescArg optional description of other object
	 * @return this (cast to AssertionT) if object is equal to other object
	 * @throws java.lang.AssertionError if object is not equal to other object
	 */
	public AssertionT isEqualTo(Object other, String ... otherDescArg) // user-defined types need equals override
	{
		String otherDesc = getDesc(otherDescArg,DFT_DESC);
		
		if (subject == null)
		{
			if (other == null)
				return (AssertionT)this;
			
			failF("Expected %s [%s] to be non-null but is null",subjectDesc,subject);
		}
		
		if (!subject.equals(other))
		{
			failF("Expected %s [%s] to be equal to %s [%s] but is not", subjectDesc,subject.toString(),otherDesc,other.toString());
		}
		
		return (AssertionT)this;
	}
	
	/**
	 * Verifies that this object is not equal to the specified other one.
	 * 
	 * @param other another object to compare subject to
	 * @param otherDescArg optional description of other object
	 * @return this (cast to AssertionT) if object is not equal to other object
	 * @throws java.lang.AssertionError if object is equal to other object
	 */
	public AssertionT isNotEqualTo(Object other, String ... otherDescArg)
	{
		String otherDesc = getDesc(otherDescArg,DFT_DESC);
		
		if (subject == null)
		{
			if (other != null)
				return (AssertionT)this;
			
			failF("%s and %s are both null",subjectDesc,otherDesc);
		}
		
		if (subject.equals(other))
		{
			failF("Expected %s [%s] to be different from  %s [%s] but they are equal",subjectDesc,subject.toString(),otherDesc,other.toString());
		}
		
		return (AssertionT)this;
	}
	
	/**
	 * @param predicate a functional object which implements a testable property of an object of type {@literal <T>} and returns
	 * true or false 
	 * @param predicateDesc a description of the predicate (not optional)
	 * @return this (cast to AssertionT) if predicate returns true
	 * @throws java.lang.AssertionError if predicate returns false or if the predicate throws exception
	 */
	public AssertionT passesTest(Predicate<T> predicate, String predicateDesc) // must specify predicateDesc
	{
		
		if (subject == null)
		{
			failF("%s is null, cannot test %s",subjectDesc,predicateDesc);
		}
		
		boolean result;
		try
		{
			result = predicate.test(subject);
		}
		catch (Throwable ex)
		{
			failF("Expected %s [%s] to pass test %s but test threw exception %s",subjectDesc,subject.toString(),predicateDesc,ex.toString());	
			result = false; // not reached
		}
		
		if (result)
		{
			return (AssertionT)this;
		}
			
		failF("Expected %s [%s] to pass test %s but it failed",subjectDesc,subject.toString(),predicateDesc);
		
		return null; // not reached

	}
	
	/**
	 * @param predicate a functional object which implements a testable property of an object of type {@literal <T>} and returns
	 * true or false 
	 * @param predicateDesc a description of the predicate 
	 * @param predicateDesc a description of the predicate (not optional)
	 * @return this (cast to AssertionT) if predicate returns false
	 * @throws java.lang.AssertionError if predicate returns true or if the predicate throws exception
	 */
	public AssertionT failsTest(Predicate<T> predicate, String predicateDesc) // must specify predicateDesc
	{
		
		if (subject == null)
		{
			failF("%s is null, cannot test %s",subjectDesc,predicateDesc);
		}
		
		boolean result;
		try
		{
			result = predicate.test(subject);
		}
		catch (Throwable ex)
		{
			failF("Expected %s [%s] to fail test %s but test threw exception %s",subject,subject.toString(),predicateDesc,ex.toString());	
			result = false; // not reached
		}

			
		if (!result)
		{
			return (AssertionT)this;
		}
			
		failF("Expected subject %s [%s] to fail test %s but it passed",subject,subject.toString(),predicateDesc);

		return null; //. not reached

	}
	
}
