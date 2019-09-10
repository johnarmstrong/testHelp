package testHelp;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static testHelp.SupplementaryFunctionalInterfaces.*;
import static testHelp.UnboundMethodInterfaces.*;

/*
 * XXX need to deal with functions and methods with checked exceptions
 */

/**
 * An interface with no abstract or default methods that bundles "voidable" VFunction and VUnboundMethod interfaces and static methods that convert fully typed to "voidable"
 * functional and unbound method types.
 * <p>
 * A "voidable" type is a generic type which can have the equivalent of a void type as a possible type value.  There are two kinds of voidable type, each with its own distinctive
 * class as singleton instance:
 *
 * <ul>
 *   	<li>Type VoidA, instance voidA - type/value that can be passed as argument as the equivalent of no ("void") argument</li>
 *	<li>Type VoidR, instance voidR - type/value that can be returned as the equivalent no ("void") return</li>
 * </ul>
 * 
 * @author John Armstrong
 */
public interface VFunctionsAndVUnboundMethods {
	
	// Note - interfaces declared inside interface or class are implicitly static
	
	/* 
	 * Void stuff
	 */

    	/**
    	 * Class of void argument (possible in C and C++ function/method declarations though not in Java).
    	 * <p>
    	 * Has private constructor to limit instance creation to this file.
    	 */
	public static class VoidA {
	    private VoidA() {
	    }
	    
	    @Override
	    public String toString() {
		    return "voidA"; // only instance
	    }
	}
	
	/**
	 * Class of void return value (a standard feature of C, C++ and Java function/method declarations).
	 * <p>
    	 * Has private constructor to limit instance creation to this file.
	 */
	public static class VoidR {
	    private VoidR() {
	    }
	    
	    @Override
	    public String toString() {
		    return "voidR"; // only instance
	    }
	}
	
	/**
	 * Void argument value - the one and only instance of class VoidA.  Represents a pseudo-argument that can optionally
	 * be passed to a vFunction made from a functional object which takes no arguments - compare function(void) signatures
	 * in C and C++.
	 */
	public static final VoidA voidA = new VoidA(); // void argument
	
	/**
	 * Void return value - the one and only instance of class VoidR.  Represents the result of calling a vFunction made from
	 * a functional object with void return type.
	 */
	public static final VoidR voidR = new VoidR(); // void return value

	/************************************************************************************
	 *
	 * 	"Voidable" function interfaces - a way of representing all functional types as function types using VoidA and VoidR
	 * 
	 ************************************************************************************/
	
	/**
	 * A "voidable" functional interface which takes no arg or a single arg which may be of any generic type {@literal <A>} including VoidA
	 * and returns a value of any generic type {@literal <R>} including VoidR.
	 * <p>
	 * Function objects of the following types may be converted to this type:
	 * 
	 * <ol type="1">
  	 *	<li>{@literal Runnable -> VFunction<VoidA,VoidR>}</li>
  	 *	<li>{@literal Callable<R> -> VFunction<VoidA,R>}</li>
  	 *	<li>{@literal Supplier<R> -> VFunction<VoidA,R>}</li>
  	 *	<li>{@literal Consumer<A> -> VFunction<A,VoidR>}</li>
  	 *	<li>{@literal Function<A,R> -> VFunction<A,R>}</li>
	 * </ol> 
	 * 
	 * VFunctions made from Runnable, {@literal Callable<R>} and {@literal Supplier<R>} may be called either with no arg or with single arg voidA; VFunctions made 
	 * from {@literal Consumer<A>} and {@literal Function<A,R>} are called with one arg of generic type {@literal <A>}.
	 * <p>
	 * VFunctions made from Runnable and {@literal Consumer<A>} return voidR; VFunctions made from {@literal Callable<R>}, {@literal Supplier<R>} and 
	 * {@literal Function<A,R>} return objects of generic type {@literal <R>}.
	 * <p>  
	 * @param <A> the generic type of the argument, with VoidA as a possibility
	 * @param <R> the generic type of the return value, with VoidR as a possibility
	 */
	@FunctionalInterface
	public interface VFunction<A,R> extends Function<A,R> { // both A and R voidable
		default R apply() { // Will fail except for functions of type <Void,R>
			return this.apply((A)voidA);
		}
	}

	/**
	 * A "voidable" functional interface which takes two args of any generic types {@literal <A1,A2>}
	 * and returns a value of any generic type {@literal <R>} including VoidR.
	 * <p>
	 * Function objects of the following types may be converted to this type:
	 * 
	 * <ol type="1">
 
  	 *	<li>{@literal BiConsumer<A1,A2> -> VBiFunction<A1,A2,VoidR>}</li>
  	 *	<li>{@literal BiFunction<A1,A2,R> -> VBiFunction<A1,A2,R>}</li>
	 * </ol> 
	 * 
	 * VBiFunctions made from {@literal BiConsumer<A1,A2>} return voidR; VBiFunctions made from {@literal BiFunction<A1,A2,R>} return objects of 
	 * generic type {@literal <R>}.
	 * <p>  
	 * @param <A1> the generic type of the first argument
	 * @param <A2> the generic type of the second argument
	 * @param <R> the generic type of the return value, with VoidR as a possibility
	 */
	@FunctionalInterface
	public interface VBiFunction<A1,A2,R> extends BiFunction<A1,A2,R> { // R voidable 
	}
	
	/**
	 * A "voidable" functional interface which takes three args any generic types {@literal <A1,A2,A3>}
	 * and returns a value of any generic type {@literal <R>} including VoidR.
	 * <p>
	 * Function objects of the following types may be converted to this type:
	 * 
	 * <ol type="1">
 
  	 *	<li>{@literal TriConsumer<A1,A2,A3> -> VTriFunction<A1,A2,A3,VoidR>}</li>
  	 *	<li>{@literal TriFunction<A1,A2,A3,R> -> VBiFunction<A1,A2,A3,R>}</li>
	 * </ol> 
	 * 
	 * VTriFunctions made from {@literal TriConsumer<A1,A2,A3>} return voidR; VTriFunctions made from {@literal TriFunction<A1,A2,A3,R>} return objects of 
	 * generic type {@literal <R>}.
	 * <p>  
	 * @param <A1> the generic type of the first argument
	 * @param <A2> the generic type of the second argument
	 * @param <A3> the generic type of the third argument
	 * @param <R> the generic type of the return value, with VoidR asd a possibility
	 */
	@FunctionalInterface
	public interface VTriFunction<A1,A2,A3,R> extends TriFunction<A1,A2,A3,R> { // R voidable
	}
	
	/************************************************************************************
	*
	*	Conversions from non-function to function types using VoidA and VoidR
	*
	*************************************************************************************/

	/**
	 * Converts a Runnable functional object to a VFunction. Result can be called with no argument or 
	 * with a single argument voidA, and will return voidR.
	 * 
	 * @param runnable a lambda expression, bound method, or implementation of the standard Runnable interface
	 * @return a VFunction which wraps the runnable object
	 */
	public static VFunction<VoidA,VoidR> makeVFunction(Runnable runnable) {
		return (VoidA voidA) -> { 
			runnable.run();
			return voidR;
		};
	}

	/**
	 * Converts a Callable functional object to a VFunction. Result can be called with no argument or 
	 * with a single argument voidA, and will return the value returned by the wrapped callable object.
	 * Catches any checked exception thrown by the callable object and rethrows it as a runtime exception.  
	 * (Callable is the only standard Java functional interfaces which throws a checked exception.)
	 * <p>
	 * This and the method @link #makeVFunction(Supplier) both accept function objects that take no argument and return
	 * values that do not declare a checked exception. A cast can be used to force one override over the other.
	 * 
	 * @param <R> the generic return type of the passed supplier object
	 * @param callable a lambda expression, bound method, or implementation of the standard Callable interface
	 * @return a VFunction which wraps the callable object
	 */
	public static <R> VFunction<VoidA,R> makeVFunction(Callable<R> callable)
	{
		return (VoidA voidA) -> { 
				try {
					return callable.call();
				} catch (Exception ex) {
					throw new WrappedException(ex,false);
				}
		};
	}
	
	/**
	 * Converts a Supplier functional object to a VFunction. Result can be called with no argument or 
	 * with a single argument voidA, and will return the value returned by the wrapped supplier.
	 * <p>
	 * This and the method @link #makeVFunction(Callable) both accept function objects that take no argument and return
	 * a value that do not declare a checked exception. A cast can be used to force one override over the other.
	 *  
	 * @param <R> the generic return type of the passed supplier object
	 * @param supplier a lambda expression, bound method, or implementation of the standard Supplier interface
	 * @return a VFunction which wraps the supplier
	 */	
	public static <R> VFunction<VoidA,R> makeVFunction(Supplier<R> supplier)
	{
		return (VoidA voidA) -> { 
			return supplier.get();
		};
	}
	
	// Convert Consumer
	
//	public static <A> Function<T,VoidR> makeVoidRFunction(Consumer<A> consumer) // standard function type, if ever needed
//	{
//		return (T t) -> { 
//				consumer.accept(t);
//				return voidR;
//		};
//	}
	
	/**
	 * Converts a Consumer functional object to a VFunction. Result can be called with an argument of the specified type
	 * and will return the value voidR.
	 *  
	 * @param <A> generic argument type of passed consumer object
	 * @param consumer a lambda expression, bound method, or implementation of the standard Consumer interface
	 * @return a VFunction which wraps the consumer
	 */
	public static <A> VFunction<A,VoidR> makeVFunction(Consumer<A> consumer)
	{
		return (A a) -> { 
				consumer.accept(a);
				return voidR;
		};
	}
	
	// Convert BiConsumer
	
//	public static <T, U> BiFunction<T,U,VoidR> makeVoidRBiFunction(BiConsumer<T,U> biConsumer) // standard function type, if ever needed
//	{
//		return (T t,U u) -> { 
//				biConsumer.accept(t,u);
//				return voidR;
//		};
//	}
	
	/**
	 * Converts a BiConsumer functional object to a VBiFunction. Result can be called with two arguments of the specified types
	 * and will return the value voidR.
	 *  
	 * @param <A1> generic first argument type of passed biConsumer object
	 * @param <A2> generic second argument type of passed biConsumer object
	 * @param biConsumer a lambda expression, bound method, or implementation of the standard BiConsumer interface
	 * @return a BiVFunction which wraps the biConsumer
	 */
	public static <A1,A2> VBiFunction<A1,A2,VoidR> makeVBiFunction(BiConsumer<A1,A2> biConsumer)
	{
		return (A1 a1, A2 a2) -> { 
				biConsumer.accept(a1,a2);
				return voidR;
		};
	}
	
	/**
	 * Converts a TriConsumer functional object to a VBiFunction. Result can be called with three arguments of the specified types
	 * and will return the value voidR.
	 * 
	 * @param <A1> generic first argument type of passed triConsumer object
	 * @param <A2> generic second argument type of passed triConsumer object
	 * @param <A3> generic third argument type of passed triConsumer object
	 * @param triConsumer a lambda expression, bound method, or implementation of the supplementary TriConsumer interface
	 * @return a VTriFunction which wraps the triConsumer
	 */
	public static <A1,A2,A3> VTriFunction<A1,A2,A3,VoidR> makeVTriFunction(TriConsumer<A1,A2,A3> triConsumer)
	{
		return (A1 a1, A2 a2, A3 a3) -> { 
				triConsumer.accept(a1,a2,a3);
				return voidR;
		};
	}
	
	/**
	 * Converts a Function functional object to a VFunction. No change in functionality, just retypes the object.
	 *
	 * @param <A> generic argument type of passed function object
	 * @param <R> generic return type of passed function object
	 * @param function a lambda expression, bound method, or implementation of the standard Function interface
	 * @return a VFunction which wraps the function
	 */
	public static <A,R> VFunction<A,R> makeVFunction(Function<A,R> function) {
		return (A a) -> { 
			return function.apply(a);
		};

	}
	
	/**
	 * Converts a BiFunction functional object to a VBiFunction. No change in functionality, just retypes the object.
	 * 
	 * @param <A1> generic first argument type of passed biFunction object
	 * @param <A2> generic second argument type of passed biFunction object
	 * @param <R> generic return type of passed biFunction object
	 * @param biFunction a lambda expression, bound method, or implementation of the standard BiFunction interface
	 * @return a VFunction which wraps the function
	 */
	public static <A1,A2,R> VBiFunction<A1,A2,R> makeVBiFunction(BiFunction<A1,A2,R> biFunction) {
		return (A1 a1, A2 a2) -> { 
			return biFunction.apply(a1,a2);
		};

	}

	/**
	 * Converts a TriFunction functional object to a VBiFunction. No change in functionality, just retypes the object.
	 *
	 * @param <A1> generic first argument type of passed triFunction object
	 * @param <A2> generic second argument type of passed triFunction object
	 * @param <A3> generic third argument type of passed triFunction object
	 * @param <R> generic return type of passed triFunction object
	 * @param triFunction a lambda expression, bound method, or implementation of the supplementary TriFunction interface
	 * @return a VFunction which wraps the function
	 */
	public static <A1,A2,A3,R> VTriFunction<A1,A2,A3,R> makeVTriFunction(TriFunction<A1,A2,A3,R> triFunction) {
		return (A1 a1, A2 a2, A3 a3) -> { 
			return triFunction.apply(a1,a2,a3);
		};

	}
	
	/*********************************************************
	 *
	 *
	 * Unbound method interfaces
	 * 
	 * 
	 *********************************************************/
	
	/*
	 * "Voidable" unbound method function interfaces -  a way of representing all unbound method functional types as unbound function method types
	 */

	/**
	 * A "voidable" unbound method interface which takes, after an initial class instance arg, no arg or a single arg which may be of any generic type
	 * {@literal <A>} including VoidA and returns a value of any generic type {@literal <R>} including VoidR.
	 * <p>
	 * Function objects of the following types may be converted to this type:
	 * 
	 * <ol type="1">
  	 *	<li>{@literal RunnableUnboundMethod<C> -> VUnboundMethod<C,VoidA,VoidR>}</li>
  	 *	<li>{@literal CallableUnboundMethod<C,R> -> VFUnboundMethod<VoidA,R>}</li>
  	 *	<li>{@literal SupplierUnboundMethod<R> -> VUnboundMethod<VoidA,R>}</li>
  	 *	<li>{@literal ConsumerUnboundMethod<A> -> VUnboundMethod<A,VoidR>}</li>
  	 *	<li>{@literal FunctionUnboundMethod<A,R> -> VUnboundMethod<A,R>}</li>
	 * </ol> 
	 * 
	 * VUnboundMethods made from {@literal RunnableUnboundMethod<C>}, {@literal CallableUnboundMethod<C,R>} and {@literal SupplierUnboundMethod<C,R>} may be called with the
	 * class instance arg alone or with a class instance arg and additional arg voidA; VUnboundMethods made from {@literal ConsumerUnboundMethod<C,A>} and 
	 * {@literal FunctionUnboundMethod<C,aA,R>} are called with a class instance arg and one arg of generic type {@literal <A>}.
	 * <p>
	 * VUnboundMethods made from {@literal RunnableUnboundMethod<C>} and {@literal ConsumerUnboundMethod<C,A>} return voidR; VFunctions made from {@literal Callable<R>},  
	 * {@literal Supplier<R>} and {@literal Function<A,R>} return objects of generic type {@literal <R>}.
	 * <p>  
	 * @param <C> the generic type of the the class of the unbopund method, which is also the type of the class instance argument
	 * @param <A> the generic type of the argument after the class instance argument, with VoidA as a possibility
	 * @param <R> the generic type of the return value, with VoidR as a possibility
	 */
	@FunctionalInterface
	public interface VUnboundMethod<C,A,R> extends FunctionUnboundMethod<C,A,R> {
		default R applyWithClassInstanceAsFirstArg(C c) { // Will fail except for unbound (bi)function methods of type <C,VoidA,R>
			return this.applyWithClassInstanceAsFirstArg(c,(A)voidA);
		}
	}
	
	/**
	 * A "voidable" unbound methoid interface which takes, in addtional to an initial class instance argument, two arguments of any generic types
	 * {@literal <A1,A2>} and returns a value of any generic type {@literal <R>} including VoidR.
	 * <p>
	 * Function objects of the following types may be converted to this type:
	 * 
	 * <ol type="1">
  	 *	<li>{@literal BiConsumeUnboundMethod<C,A1,A2> -> VBiFunctionUnboundMethod<C,A1,A2,VoidR>}</li>
  	 *	<li>{@literal BiFunctionUnboundMethod<C,A1,A2,R> -> VBiUnboundMethod<C,A1,A2,R>}</li>
	 * </ol> 
	 * 
	 * VBiFunctions made from {@literal BiConsumer<A1,A2>} return voidR; VBiFunctions made from {@literal BiFunction<A1,A2,R>} return objects of 
	 * generic type {@literal <R>}.
	 * <p>  
	 * @param <C> the generic type of the the class of the unbound method, which is also the type of the class instance argument
	 * @param <A1> the generic type of the first argument after the class instance argument
	 * @param <A2> the generic type of the second argument after the class instance argument
	 * @param <R> the generic type of the return value, with VoidR as a possibility
	 */
	@FunctionalInterface
	public interface VBiUnboundMethod<C,A1,A2,R> extends BiFunctionUnboundMethod<C,A1,A2,R> {
	}
	
	
	//@FunctionalInterface
	//public interface VTriFunctionUnboundMethod<C,A1,A2,A3,R> extends TriFunctionUnboundMethod<C,A1,A2,A3,R> {
	//}
	
	
	/************************************************************************************
	*
	*	Conversions from non-function to function unbound method types using VoidA and VoidR
	*
	*************************************************************************************/

	/**
	 * Converts a Runnable unbound method functional object to a VFunction. Result can be called with a class instance or a class instance and 
	 * argument voidA, and will return voidR.
	 * 
	 * @param <C> generic type of class instance argument
	 * @param runnableUnboundMethod a lambda expression, unbound instance method, or implementation of the RunnableUnboundMethod interface 
	 * @return a VUnboundMethod which wraps the runnable unbound method object
	 */
	public static <C> VUnboundMethod<C,VoidA,VoidR> makeVUnboundMethod(RunnableUnboundMethod<C> runnableUnboundMethod)
	{
		return (C c, VoidA a) -> { 
		    			runnableUnboundMethod.runWithClassInstanceAsFirstArg(c);
					return voidR;
		};
	}
	
	/**
	 * Converts a CallableUnboundMethod functional object to a VUnboundMethod. Result can be called with a class instance or a class instance and 
	 * argument voidA, and will return the value returned by the wrapped callable unbound method object.
	 * <p>
	 * This and the method @link #makeVFunction(SupplierUnboundMethod) both accept unbound method objects that take no argument and return
	 * values that do not declare a checked exception. A cast can be used to force one override over the other.
	 * 
	 * @param <C> generic type of class instance argument
	 * @param <R> the generic return type of the passed supplier object
	 * @param callableUnboundMethod a lambda expression, bound method, or implementation of the CallableUnboundMethod interface
	 * @return a VUnboundMethod which wraps the callable unbound method object
	 */
	public static <C,R> VUnboundMethod<C,VoidA,R> makeVUnboundMethod(CallableUnboundMethod<C,R> callableUnboundMethod)
	{
		return (C c, VoidA a) -> { 
			return callableUnboundMethod.callWithClassInstanceAsFirstArg(c);
		};
	}


	/**
	 * Converts a SupplierUnboundMethod functional object to a VUnboundMethod. Result can be called with a class instance or a class instance and 
	 * argument voidA, and will return the value returned by the wrapped supplier unbound method object.
	 * <p>
	 * This and the method @link #makeVFunction(CallableUnboundMethod) both accept unbound method objects that take no argument and return
	 * values that do not declare a checked exception. A cast can be used to force one override over the other.
	 * 
	 * @param <C> generic type of class instance argument
	 * @param <R> the generic return type of the passed supplier object
	 * @param supplierUnboundMethod a lambda expression, bound method, or implementation of the SupplierUnboundMethod interface
	 * @return a VUnboundMethod which wraps the supplier unbound method object
	 */
	public static <C,R> VUnboundMethod<C,VoidA,R> makeVUnboundMethod(SupplierUnboundMethod<C,R> supplierUnboundMethod)
	{
		return (C c, VoidA a) -> { 
			return supplierUnboundMethod.getWithClassInstanceAsFirstArg(c);
		};
	}
	
	/**
	 * Converts a Consumer unbound method functional object to a VUnboundMethod. Result can be called with with a class instance and an argument
	 * of the specified type and will return the value voidR.
	 *  
	 * @param <C> generic type of class instance argument
	 * @param <A> generic argument type of passed consumer unbound method object after the class instance
	 * @param consumerUnboundMethod a lambda expression, bound method, or implementation of the ConsumerUnbouncMethod interface
	 * @return a VUnmoundMethod which wraps the consumer unbound method
	 */	
	public static <C,A> VUnboundMethod<C,A,VoidR> makeVUnboundMethod(ConsumerUnboundMethod<C,A> consumerUnboundMethod)
	{
		return (C c, A a) -> { 
		    consumerUnboundMethod.acceptWithClassInstanceAsFirstArg(c,a);
				return voidR;
		};
	}
	
	/**
	 * Converts a BiConsumer unbound method functional object to a VUnboundMethod. Result can be called with with a class instance and two arguments
	 * of the specified types and will return the value voidR.
	 *  
	 * @param <C> generic type of class instance argument
	 * @param <A1> generic first argument type of passed consumer unbound method object after the class instance
	 * @param <A2> generic second argument type of passed consumer unbound method object after the class instance
	 * @param biConsumerUnboundMethod a lambda expression, bound method, or implementation of the BiConsumerUnboundMethod interface
	 * @return a VBiUnboundMethod which wraps the biConsumer unbound method
	 */	
	public static <C,A1,A2> VBiUnboundMethod<C,A1,A2,VoidR> makeVBiUnboundMethod(BiConsumerUnboundMethod<C,A1,A2> biConsumerUnboundMethod) {
		return (C c, A1 a1, A2 a2) -> {
		    	biConsumerUnboundMethod.acceptWithClassInstanceAsFirstArg(c,a1,a2);
			return voidR;
		};
	}
	
	/**
	 * Converts a Function unbound method functional object to a VFunction. No change in functionality, just retypes the object.
	 *
	 * @param <C> generic type of class instance argument
	 * @param <A> generic argument type of passed function unbound method object after the class instance
	 * @param <R> generic return type of passed function object
	 * @param unboundFunctionMethod a lambda expression, bound method, or implementation of the FunctionUnboundMethod interface
	 * @return a VUnboundMethod which wraps the function unbound method object
	 */
	public static <C,A,R> VUnboundMethod<C,A,R> makeVUnboundMethod(FunctionUnboundMethod<C,A,R> unboundFunctionMethod)
	{
		return (C c, A a) -> { 
				return unboundFunctionMethod.applyWithClassInstanceAsFirstArg(c,a);
		};
	}
	
	/**
	 * Converts a BiFunction unbound method functional object to a VFunction. No change in functionality, just retypes the object.
	 *
	 * @param <C> generic type of class instance argument
	 * @param <A1> generic first argument type of passed function unbound method object after the class instance
	 * @param <A2> generic second argument type of passed function unbound method object after the class instance
	 * @param <R> generic return type of passed function object
	 * @param biFunctionUnboundMethod a lambda expression, bound method, or implementation of the BiFunctionUnboundMethod interface
	 * @return a VBiFunction which wraps the biFunction unbound method object
	 */
	public static <C,A1,A2,R> VBiUnboundMethod<C,A1,A2,R> makeVBiUnboundMethod(BiFunctionUnboundMethod<C,A1,A2,R>biFunctionUnboundMethod) {
		return (C c, A1 a1, A2 a2) -> {
			return biFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(c,a1,a2);
		
		};
	}
	
	
	
	
}
