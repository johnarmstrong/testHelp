package testHelp;

import static testHelp.VFunctionsAndVUnboundMethods.*;

import static testHelp.SupplementaryFunctionalInterfaces.*;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static testHelp.AssertionTools.failF;
import static testHelp.AssertionTools.failWithExceptionF;

// NOTE: this file is NOT generated

/** 
 * This interface with no abstract or default methods that bundles a set of static methods which call normalized functions (including static methods and bound
 * instance methods as well as non-class functions defined by lambda expressions) with the number of args appropriate to the normalized function, and either retur
 * a value or fail depending on the expectation as to whether the call will return normally or throw an exception. 
 * <p>
 * Methods that expect the function it is calling with the specified arguments to return a value are named  {@literal getReturnValueFrom<function-type>}.
 * They either return the value returned by the called function or throw a junit assertion error via a call to {@link testHelp.AssertionTools#failWithExceptionF}.
 * <p>
 * Methods that expect the function it is calling with the specified arguments to throw an exception are named {@literal getExceptionFrom<function-type>}.
 * They either return (as a value) the exception thrown by the called function or throw a junit assertion error via a call to {@link testHelp.AssertionTools#failF}.
 * <p>
 * The function types accepted by the methods are of two kinds: VFunctions ("voidable" functions) and NormFunctions (normalized functions). 
 * There is only one getValueFrom and one getExceptionFrom method for VFunctions that take a given number of arguments, but there may be several
 * overloaded methods for NormFunctions that take a given number of arguments, one for each standard or supplementary Java function type that normalizes to the same
 * normalized type (for example {@literal Runnable, Callable<R> and Supplier<R> for getValueFromNoArgNormFunction and getExceptionFromNoArgNormFunction, 
 * and Consumer<A> and Function<A,R>} for getValueFromOneArgNormFunction and getExceptionFromArgNormFunction.)
 * <p>
 * A paralled set of static methods, applicable to VUnboundMethods ("voidable" unbound methods) and NormUnboundMethods (normalized unbound methods) are
 * bundled in the interface {@link testHelp.NormalizedUnboundMethodCallers}.
 * 
 * @author John Armstrong
 */
public interface NormalizedFunctionCallers {
	
	// noArgFunction (includes noArgStaticMethod and noArgBoundMethod)

	public static <R> R getReturnValueFromNoArgVFunction(VFunction<VoidA,R> noArgFunction, String desc) {
		try {
			return noArgFunction.apply();
		} catch (Throwable ex) {
			failWithExceptionF("Function %s called with no args threw exception %s",desc,ex,ex);
			return null;
		}
	}
	
	public static <R> Throwable getExceptionFromNoArgVFunction(VFunction<VoidA,R> noArgFunction, String desc) {
		try {
			R value = noArgFunction.apply();
			failF("Function %s called with no args and returned value [%s] instead of throwing exception",desc,value);
			return null;
		} catch (java.lang.AssertionError ex) {
			throw ex;
		} catch (Throwable ex) {
			return ex;
		}
	}

	// OneArgFunction (includes oneArgStaticMethod and oneArgBoundMethod, also noArgUnboundMethod)
	
	public static <A,R> R getReturnValueFromOneArgVFunction(VFunction<A,R> oneArgFunction, A arg, String desc) {
		try {
			return oneArgFunction.apply(arg);
		} catch (Throwable ex) {
			failWithExceptionF("Function %s called with arg [%s] threw exception %s",desc,arg,ex,ex);
			return null;
		}
	}
	
	public static <A,R> Throwable getExceptionFromOneArgVFunction(VFunction<A,R> oneArgFunction, A arg, String desc) {
		try {
			R value = oneArgFunction.apply(arg);
			failF("Function %s called with arg [%s] returned value [%s] instead of throwing exception",desc,arg,value);
			return null;
		} catch (java.lang.AssertionError ex) {
			throw ex;
		} catch (Throwable ex) {
			return ex;
		}
	}

	// twoArgFunction (includes twoArgStaticMethod and twoArgBoundMethod, also OneArgUnboundMethod)

	public static <A1,A2,R> R getReturnValueFromVBiFunction(VBiFunction<A1,A2,R> twoArgFunction, A1 arg1, A2 arg2, String desc) {
		try {
			return twoArgFunction.apply(arg1,arg2);
		} catch (Throwable ex) {
			failWithExceptionF("Function %s called with arg1 [%s] arg2 [%s] threw exception %s",desc,arg1,arg2,ex,ex);
			return null;
		}
	}
	
	public static <A1,A2,R> Throwable getExceptionFromVBiFunction(VBiFunction<A1,A2,R> twoArgFunction, A1 arg1, A2 arg2, String desc) {
		try {
			R value = twoArgFunction.apply(arg1,arg2);
			failF("Function %s called with arg1 [%s] arg2 [%s] returned value [%s] instead of throwing exception",desc,arg1,arg2,value);
			return null;
		} catch (java.lang.AssertionError ex) {
			throw ex;
		} catch (Throwable ex) {
			return ex;
		}
	}

	// TriArgFunction (includes ThreeArgStaticMethod and ThreeArgBoundMethod, also TwoArgUnboundMethod)
	
	public static <A1,A2,A3,R> R getReturnValueFromVTriFunction(VTriFunction<A1,A2,A3,R> threeArgFunction, A1 arg1, A2 arg2, A3 arg3, String desc) {
		try {
			return threeArgFunction.apply(arg1,arg2,arg3);
		} catch (Throwable ex) {
			failWithExceptionF("Function %s called with arg1 [%s] arg2 [%s] arg2 [%s] threw exception %s",desc,arg1,arg2,arg3,ex,ex);
			return null;
		}
	}

	public static <A1,A2,A3,R> Throwable getExceptionFromVTriFunction(VTriFunction<A1,A2,A3,R> threeArgFunction, A1 arg1, A2 arg2, A3 arg3, String desc) {
		try {
			R value = threeArgFunction.apply(arg1,arg2,arg3);
			failF("Function %s called with arg1 [%s] arg2 [%s] arg2 [%s] returned value [%s] instead of throwing exception",desc,arg1,arg2,arg3,value);
			return null;
		} catch (java.lang.AssertionError ex) {
			throw ex;
		} catch (Throwable ex) {
			return ex;
		}
	}
	
	/* 
	 * Overloaded getValueFrom..Function and getExceptionFrom..Function methods that do conversions to V..Function types
	 */
	
	// Runnable (includes RunnableStaticMethod and RunnableBoundMethod)
	
	public static VoidR getReturnValueFromNoArgNormFunction(Runnable noArgFunction, String desc) {
		VFunction<VoidA,VoidR> vFunction = makeVFunction(noArgFunction);
		return getReturnValueFromNoArgVFunction(vFunction,desc);

	}
	
	public static Throwable getExceptionFromNoArgNormFunction(Runnable noArgFunction, String desc) {
		VFunction<VoidA,VoidR> vFunction = makeVFunction(noArgFunction);
		return getExceptionFromNoArgVFunction(vFunction,desc);

	}
	
	// Supplier (includes SupplierStaticMethod and SupplierBoundMethod)
	
	public static <R> R getReturnValueFromNoArgNormFunction(Supplier<R> noArgFunction, String desc) {
		VFunction<VoidA,R> vFunction = makeVFunction(noArgFunction);
		return getReturnValueFromNoArgVFunction(vFunction,desc);
	}

	public static <R> Throwable getExceptionFromNoArgNormFunction(Supplier<R> noArgFunction, String desc) {
		VFunction<VoidA,R> vFunction = makeVFunction(noArgFunction);
		return getExceptionFromNoArgVFunction(vFunction,desc);
	}
	
	// Callable (includes CallableStaticMethod and CallableBoundMethod)
	
	public static <R> R getReturnValueFromNoArgNormFunction(Callable<R> noArgFunction, String desc) {
		VFunction<VoidA,R> vFunction = makeVFunction(noArgFunction);
		return getReturnValueFromNoArgVFunction(vFunction,desc);
	}

	public static <R> Throwable getExceptionFromNoArgNormFunction(Callable<R> noArgFunction, String desc) {
		VFunction<VoidA,R> vFunction = makeVFunction(noArgFunction);
		return getExceptionFromNoArgVFunction(vFunction,desc);
	}
	
	// Consumer (includes ConsumerStaticMethod and ConsumerBoundMethod, also RunnableUnboundMethod) 
	
	public static <A> VoidR getReturnValueFromOneArgNormFunction(Consumer<A> oneArgFunction, A arg, String desc) {
		VFunction<A,VoidR> vFunction = makeVFunction(oneArgFunction);
		return getReturnValueFromOneArgVFunction(vFunction,arg,desc);
	}

	public static <A> Throwable getExceptionFromOneArgNormFunction(Consumer<A> oneArgFunction, String desc, A arg) {
		VFunction<A,VoidR> vFunction = makeVFunction(oneArgFunction);
		return getExceptionFromOneArgVFunction(vFunction,arg,desc);
	}

	// BiConsumer (includes BiConsumerStaticMethod and BiConsumerBoundMethod, also ConsumerUnboundMethod)
	
	public static <A1,A2> VoidR getReturnValueFromNormBiFunction(BiConsumer<A1,A2> twoArgFunction, A1 arg1, A2 arg2, String desc) {
		VBiFunction<A1,A2,VoidR> vBiFunction = makeVBiFunction(twoArgFunction);
		return getReturnValueFromVBiFunction(vBiFunction,arg1,arg2,desc);
	}

	public static <A1,A2> Throwable getExceptionFromNormBiFunction(BiConsumer<A1,A2> twoArgFunction, A1 arg1, A2 arg2, String desc) {
		VBiFunction<A1,A2,VoidR> vBiFunction = makeVBiFunction(twoArgFunction);
		return getExceptionFromVBiFunction(vBiFunction,arg1,arg2,desc);
	}
	
	// TriConsumer (includes TriConsumerStaticMethod and TriConsumerBoundMethod, also BiConsumerUnboundMethod)
	
	public static <A1,A2,A3> VoidR getReturnValueFromNormTriFunction(TriConsumer<A1,A2,A3> threeArgFunction, A1 arg1, A2 arg2, A3 arg3, String desc) {
		VTriFunction<A1,A2,A3,VoidR> vTriFunction = makeVTriFunction(threeArgFunction);
		return getReturnValueFromVTriFunction(vTriFunction,arg1,arg2,arg3,desc);
	}

	public static <A1,A2,A3> Throwable getExceptioneFromNormTriBiFunction(TriConsumer<A1,A2,A3> threeArgFunction, A1 arg1, A2 arg2, A3 arg3, String desc) {
		VTriFunction<A1,A2,A3,VoidR> vTriFunction = makeVTriFunction(threeArgFunction);
		return getExceptionFromVTriFunction(vTriFunction,arg1,arg2,arg3,desc);
	}
	
	// Function (includes FunctionStaticMethod and FunctionBoundMethod, also SupplierUnboundMethod and CallableUnboundMethod
	
	public static <A,R> R getReturnValueFromOneArgNormFunction(Function<A,R> oneArgFunction, A arg, String desc) {
		VFunction<A,R> vFunction = makeVFunction(oneArgFunction);
		return getReturnValueFromOneArgVFunction(vFunction,arg,desc);
	}

	public static <A,R> Throwable getExceptionFromOneArgNormFunction(Function<A,R> oneArgFunction, String desc, A arg) {
		VFunction<A,R> vFunction = makeVFunction(oneArgFunction);
		return getExceptionFromOneArgVFunction(vFunction,arg,desc);
	}
	
	// BiFunction (includes BiFunctionStaticMethod and BiFunctionBoundMethod, also FunctionUnboundMethod)
	
	public static <A1,A2,R> R getReturnValueFromNormBiFunction(BiFunction<A1,A2,R> twoArgFunction, A1 arg1, A2 arg2, String desc) {
		VBiFunction<A1,A2,R> vBiFunction = makeVBiFunction(twoArgFunction);
		return getReturnValueFromVBiFunction(vBiFunction,arg1,arg2,desc);
	}

	public static <A1,A2,R> Throwable getExceptionFromNormalizedBiFunction(BiFunction<A1,A2,R> twoArgFunction, A1 arg1, A2 arg2, String desc) {
		VBiFunction<A1,A2,R> vBiFunction = makeVBiFunction(twoArgFunction);
		return getExceptionFromVBiFunction(vBiFunction,arg1,arg2,desc);
	}
	
	// TriFunction (includes TriFunctionStaticMethod and TriFunctionBoundMethod, also BiFunctionUnboundMethod)

	public static <A1,A2,A3,R> R getReturnValueFromNormTriFunction(TriFunction<A1,A2,A3,R> threeArgFunction, A1 arg1, A2 arg2, A3 arg3, String desc) {
		VTriFunction<A1,A2,A3,R> vTriFunction = makeVTriFunction(threeArgFunction);
		return getReturnValueFromVTriFunction(vTriFunction,arg1,arg2,arg3,desc);
	}

	public static <A1,A2,A3,R> Throwable getExceptionFromNormTriFunction(TriFunction<A1,A2,A3,R> threeArgFunction, A1 arg1, A2 arg2, A3 arg3, String desc) {
		VTriFunction<A1,A2,A3,R> vTriFunction = makeVTriFunction(threeArgFunction);
		return getExceptionFromVTriFunction(vTriFunction,arg1,arg2,arg3,desc);
	}
	
}
