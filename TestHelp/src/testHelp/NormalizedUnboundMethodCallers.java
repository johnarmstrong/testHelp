package testHelp;

import static testHelp.VFunctionsAndVUnboundMethods.*;

import static testHelp.UnboundMethodInterfaces.*;

import static testHelp.AssertionTools.failF;
import static testHelp.AssertionTools.failWithExceptionF;

//NOTE: this file is NOT generated

/** 
* This interface with no abstract or default methods that bundles a set of static methods which call normalized unbound methods (non-static methods that are not
* bound to an object of their class) with an object of the their class as first argument plus the number of args appropriate to the normalized unbound method, and
* either return a value or fail depending on the expectation as to whether the call will return normally or throw an exception. 
* <p>
* Methods that expect the unbound method it is calling with the specified arguments to return a value are named {@literal getReturnValueFrom<unbound-method-type>}.
* They either return the value returned by the called unbound method or throw a junit assertion error via a call to {@link testHelp.AssertionTools#failWithExceptionF}.
* <p>
* Methods that expect the unbound method it is calling with the specified arguments to throw an exception are named {@literal getExceptionFrom<unbound-method-type>}.
* They either return (as a value) the exception thrown by the called unbound method or throw a junit assertion error via a call to {@link testHelp.AssertionTools#failF}.
* <p>
* The unbound method types accepted by the methods are of two kinds: VUnboundMethods ("voidable" unbound methods) and NormUnbooundMethods (normalized unbound methods). 
* There is only one getValueFrom and one getExceptionFrom method for VFunctions that take a given number of arguments, but there may be several
* overloaded methods for NormUnboundMethods that take a given number of arguments, one for each unbound method analog of a Java function type that normalizes
* to the same normalized type (for example {@literal RunnableUnboundMethod<C>, CallableUnbound<C,R> and Supplier<C,R> for getValueFromNoArgNormUnboundMethod
* and getExceptionFromNoArgNormUnboundMethod, and ConsumerUnboundMethod<C,A> and Function<C,A,R>} for getValueFromOneArgNormUnboundMethod and 
* getExceptionFromArgNormUnboundMethod.)
* <p>
* A paralled set of static methods, applicable to VFunctions ("voidable" functions) and NormFunctions (normalized functions) are
* bundled in the interface {@link testHelp.NormalizedFunctionCallers}.
* 
* @author John Armstrong
*/
public interface NormalizedUnboundMethodCallers {
	
	/*
	 * Base getValueFrom..Function methods - non-function types can be converted to function types and passed to them
	 */

	public static <C,R> R getReturnValueFromNoArgVUnboundMethod(VUnboundMethod<C,VoidA,R> noArgUnboundMethod, C classInstance, String desc) {
		try {
			return noArgUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,voidA);
		} catch (Throwable ex) {
			failWithExceptionF("Unbound method %s called with no args threw exception %s",desc,ex,ex); // XXX fix messages
			return null;
		}
	}

	public static <C,A,R> R getReturnValueFromOneArgVUnboundMethod(VUnboundMethod<C,A,R> oneArgUnboundMethod, C classInstance, A arg, String desc) {
		try {
			return oneArgUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg);
		} catch (Throwable ex) {
			failWithExceptionF("Unbound method %s called with arg [%s] threw exception %s",desc,arg,ex,ex);
			return null;
		}
	}

	public static <C,A1,A2,R> R getReturnValueFromVBiUnboundMethod(VBiUnboundMethod<C,A1,A2,R> twoArgUnboundMethod, C classInstance,A1 arg1, A2 arg2, String desc) {
		try {
			return twoArgUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg1,arg2);
		} catch (Throwable ex) {
			failWithExceptionF("Unbound method %s called with arg1 [%s] arg2 [%s] threw exception %s",desc,arg1,arg2,ex,ex);
			return null;
		}
	}

//	public static <C,A1,A2,A3,R> R getReturnValueFromVTriUnboundMethod(VTriUnboundMethod<C,A1,A2,A3,R> threeArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, A3 arg3, String desc) {
//		try {
//			return threeArgUnboundMethod.apply(classInstance,arg1,arg2,arg3);
//		} catch (Throwable ex) {
//			failWithExceptionF("Unbound method %s called with arg1 [%s] arg2 [%s] arg2 [%s] threw exception %s",desc,arg1,arg2,arg3,ex,ex);
//			return null;
//		}
//	}

	/*
	 * Base getExceptionFrom..Function methods - non-function types can be converted to function types and passed to them
	 */
	
	public static <C,R> Throwable getExceptionFromNoArgVUnboundMethod(VUnboundMethod<C,VoidA,R> noArgUnboundMethod, C classInstance, String desc) {
		try {
			R value = noArgUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,voidA);
			failF("Unbound method %s called with no args and returned value [%s] instead of throwing exception",desc,value);
			return null;
		} catch (java.lang.AssertionError ex) {
			throw ex;
		} catch (Throwable ex) {
			return ex;
		}
	}

	public static <C,A,R> Throwable getExceptionFromOneArgVUnboundMethod(VUnboundMethod<C,A,R> oneArgUnboundMethod, C classInstance, A arg, String desc) {
		try {
			R value = oneArgUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg);
			failF("Unbound method %s called with arg [%s] returned value [%s] instead of throwing exception",desc,arg,value);
			return null;
		} catch (java.lang.AssertionError ex) {
			throw ex;
		} catch (Throwable ex) {
			return ex;
		}
	}

	public static <C,A1,A2,R> Throwable getExceptionFromVBiUnboundMethod(VBiUnboundMethod<C,A1,A2,R> twoArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, String desc) {
		try {
			R value = twoArgUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg1,arg2);
			failF("Unbound method %s called with arg1 [%s] arg2 [%s] returned value [%s] instead of throwing exception",desc,arg1,arg2,value);
			return null;
		} catch (java.lang.AssertionError ex) {
			throw ex;
		} catch (Throwable ex) {
			return ex;
		}
	}

//	public static <C,A1,A2,A3,R> Throwable getExceptionFromVTriArgUnboundMethod(VTriUnboundMethod<C,A1,A2,A3,R> threeArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, A3 arg3, String desc) {
//		try {
//			R value = threeArgUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg1,arg2,arg3);
//			failF("Unbound method %s called with arg1 [%s] arg2 [%s] arg2 [%s] returned value [%s] instead of throwing exception",desc,arg1,arg2,arg3,value);
//			return null;
//		} catch (java.lang.AssertionError ex) {
//			throw ex;
//		} catch (Throwable ex) {
//			return ex;
//		}
//	}
	
	/* 
	 * Overloaded getValueFrom..UnboundMethod and getExceptionFrom..UnboundMethod methods that do conversions to V..Function types
	 */
	
	// Runnable
	
	public static <C> VoidR getReturnValueFromNoArgNormUnboundMethod(RunnableUnboundMethod<C> noArgUnboundMethod, C classInstance, String desc) {
		VUnboundMethod<C,VoidA,VoidR> vUnboundMethod = makeVUnboundMethod(noArgUnboundMethod);
		return getReturnValueFromNoArgVUnboundMethod(vUnboundMethod,classInstance,desc);

	}
	
	public static <C> Throwable getExceptionFromNoArgNormUnboundMethod(RunnableUnboundMethod<C> noArgUnboundMethod, C classInstance, String desc) {
		VUnboundMethod<C,VoidA,VoidR> vUnboundMethod = makeVUnboundMethod(noArgUnboundMethod);
		return getExceptionFromNoArgVUnboundMethod(vUnboundMethod,classInstance,desc);

	}
	
	// Supplier
	
	public static <C,R> R getReturnValueFromNoArgNormUnboundMethod(SupplierUnboundMethod<C,R> noArgUnboundMethod, C classInstance, String desc) {
		VUnboundMethod<C,VoidA,R> vUnboundMethod = makeVUnboundMethod(noArgUnboundMethod);
		return getReturnValueFromNoArgVUnboundMethod(vUnboundMethod,classInstance,desc);
	}

	public static <C,R> Throwable getExceptionFromNoArgNormUnboundMethod(SupplierUnboundMethod<C,R> noArgUnboundMethod, String desc, C classInstance) {
		VUnboundMethod<C,VoidA,R> vUnboundMethod = makeVUnboundMethod(noArgUnboundMethod);
		return getExceptionFromNoArgVUnboundMethod(vUnboundMethod,classInstance,desc);
	}
	
	// Callable
	
	public static <C,R> R getReturnValueFromNoArgNormUnboundMethod(CallableUnboundMethod<C,R> noArgUnboundMethod, C classInstance, String desc) {
		VUnboundMethod<C,VoidA,R> vUnboundMethod = makeVUnboundMethod(noArgUnboundMethod);
		return getReturnValueFromNoArgVUnboundMethod(vUnboundMethod,classInstance,desc);
	}

	public static <C,R> Throwable getExceptionFromNoArgUnboundMethod(CallableUnboundMethod<C,R> noArgUnboundMethod, C classInstance, String desc) {
		VUnboundMethod<C,VoidA,R> vUnboundMethod = makeVUnboundMethod(noArgUnboundMethod);
		return getExceptionFromNoArgVUnboundMethod(vUnboundMethod,classInstance,desc);
	}
	
	// Consumer
	
	public static <C,A> VoidR getReturnValueFromOneArgUnboundMethod(ConsumerUnboundMethod<C,A> oneArgUnboundMethod, C classInstance, A arg, String desc) {
		VUnboundMethod<C,A,VoidR> vUnboundMethod = makeVUnboundMethod(oneArgUnboundMethod);
		return getReturnValueFromOneArgVUnboundMethod(vUnboundMethod,classInstance,arg,desc);
	}

	public static <C,A> Throwable getExceptionFromOneArgUnboundMethod(ConsumerUnboundMethod<C,A> oneArgUnboundMethod, C classInstance, A arg, String desc) {
		VUnboundMethod<C,A,VoidR> vUnboundMethod = makeVUnboundMethod(oneArgUnboundMethod);
		return getExceptionFromOneArgVUnboundMethod(vUnboundMethod,classInstance,arg,desc);
	}

	// BiConsumer
	
	public static <C,A1,A2> VoidR getReturnValueFromBiUnboundMethod(BiConsumerUnboundMethod<C,A1,A2> twoArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, String desc) {
		VBiUnboundMethod<C,A1,A2,VoidR> vBiUnboundMethod = makeVBiUnboundMethod(twoArgUnboundMethod);
		return getReturnValueFromBiUnboundMethod(vBiUnboundMethod,classInstance,arg1,arg2,desc);
	}

	public static <C,A1,A2> Throwable getExceptionFromBiUnboundMethod(BiConsumerUnboundMethod<C,A1,A2> twoArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, String desc) {
		VBiUnboundMethod<C,A1,A2,VoidR> vBiUnboundMethod = makeVBiUnboundMethod(twoArgUnboundMethod);
		return getExceptionFromVBiUnboundMethod(vBiUnboundMethod,classInstance,arg1,arg2,desc);
	}
	
	// TriConsumer
	
//	public static <C,A1,A2,A3> VoidR getReturnValueFromNormTriUnboundMethod(TriConsumerUnboundMethod<C,A1,A2,A3> threeArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, A3 arg3, String desc) {
//		VTriFunctionUnboundMethod<C,A1,A2,A3,VoidR> vTriUnboundMethod = makeUnboundVTriFunctionMethod(threeArgUnboundMethod);
//		return getValueFromVTriUnboundMethod(vTriUnboundMethod,classInstance,arg1,arg2,arg3,desc);
//	}

//	public static <C,A1,A2,A3> Throwable getExceptioneFromTriUnboundMethod(UnboundTriConsumerUnboundMethod<C,A1,A2,A3> threeArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, A3 arg3, String desc) {
//		VTriUnboundMethod<C,A1,A2,A3,VoidR> vTriUnboundMethod = makeUnboundVTriFunctionMethod(threeArgUnboundMethod);
//		return getExceptionFromVTriUnboundMethod(vTriUnboundMethod,classInstance,arg1,arg2,arg3,desc);
//	}
	
	// Function
	
	public static <C,A,R> R getReturnValueFromOneArgUnboundMethod(FunctionUnboundMethod<C,A,R> oneArgUnboundMethod, C classInstance, A arg, String desc) {
		VUnboundMethod<C,A,R> vUnboundMethod = makeVUnboundMethod(oneArgUnboundMethod);
		return getReturnValueFromOneArgVUnboundMethod(vUnboundMethod,classInstance,arg,desc);
	}

	public static <C,A,R> Throwable getExceptionFromOneArgUnboundMethod(FunctionUnboundMethod<C,A,R> oneArgUnboundMethod, C classInstance, A arg, String desc) {
		VUnboundMethod<C,A,R> vUnboundMethod = makeVUnboundMethod(oneArgUnboundMethod);
		return getExceptionFromOneArgVUnboundMethod(vUnboundMethod,classInstance,arg,desc);
	}
	
	// BiFunction
	
	public static <C,A1,A2,R> R getReturnValueFromBiUnboundMethod(BiFunctionUnboundMethod<C,A1,A2,R> twoArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, String desc) {
		VBiUnboundMethod<C,A1,A2,R> vBiUnboundMethod = makeVBiUnboundMethod(twoArgUnboundMethod);
		return getReturnValueFromVBiUnboundMethod(vBiUnboundMethod,classInstance,arg1,arg2,desc);
	}

	public static <C,A1,A2,R> Throwable getExceptionFromBiUnboundMethod(BiFunctionUnboundMethod<C,A1,A2,R> twoArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, String desc) {
		VBiUnboundMethod<C,A1,A2,R> vBiUnboundMethod = makeVBiUnboundMethod(twoArgUnboundMethod);
		return getExceptionFromVBiUnboundMethod(vBiUnboundMethod,classInstance,arg1,arg2,desc);
	}
	
	// TriFunction

//	public static <C,A1,A2,A3,R> R getReturnValueFromTriUnboundMethod(UnboundTriFunctionMethod<C,A1,A2,A3,R> threeArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, A3 arg3, String desc) {
//		VTriUnboundMethod<C,A1,A2,A3,R> vTriUnboundMethod = makeUnboundVTriFunctionMethod(threeArgUnboundMethod);
//		return getValueFromVTriUnboundMethod(vTriUnboundMethod,classInstance,arg1,arg2,arg3,desc);
//	}

//	public static <C,A1,A2,A3,R> Throwable getExceptionFromTriUnboundMethod(UnboundTriFunctionMethod<C,A1,A2,A3,R> threeArgUnboundMethod, C classInstance, A1 arg1, A2 arg2, A3 arg3, String desc) {
//		VTriUnboundMethod<C,<C,A1,A2,A3,R> vTriUnboundMethod = makeUnboundVTriFunctionMethod(threeArgUnboundMethod);
//		return getExceptionFromVTriUnboundMethod(vTriUnboundMethod,classInstance,arg1,arg2,arg3,desc);
//	}
	


}