package testHelp;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

public class MethodReferenceToFunctionalTypeAssignments
{
	
	static <A,R> R callFunction(Function<A,R> function, A arg) {
		return function.apply(arg);

	}

	static int callIntUnaryOperator(IntUnaryOperator function, int arg) {
		return function.applyAsInt(arg);

	}

	static int staticFunction_arg_int_return_int(int i) {
		return i;
	}
	
	static Integer staticFunction_arg_Integer_return_Integer(Integer i) {
		return i;
	}
	
	
	static Integer staticFunction_arg_Integer_array_return_Integer(Integer [] arg) {
		return arg.length;
	}
	
	static Integer staticFunction_arg_int_array_return_Integer(int [] arg) {
		return arg.length;
	}
	
	
	public static void main(String [] args) {
		
		// Doesn't compile
		// System.out.println(CompilerGeneratedLambdaExample::staticFunction_arg_int_return_int);
		
		//Doesn't compile
		//Object value0 = CompilerGeneratedLambdaExample::staticFunction_arg_int_return_int.apply(10);
		
		/* (A) This compiles and executes as expected - stack looks like:
		 * 
		Thread [main] (Suspended (breakpoint at line 16 in CompilerGeneratedLambdaExample))	
		CompilerGeneratedLambdaExample.staticFunction_arg_int_return_int(int) line: 16	
		1268650975.apply(Object) line: not available	
		CompilerGeneratedLambdaExample.callFunction(Function<A,R>, A) line: 11	
		CompilerGeneratedLambdaExample.main(String[]) line: 33	
		*
		*/
		
		Object value = callFunction(MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_int_return_int,new Integer(10)); 
		System.out.println("(A) value type: " + value.getClass().getName());
		System.out.println("(A) value: " + value);
		
		/* (B) This will not compile- error message is:
		* 
		The method callFunction(Function<A,R>, A) in the type CompilerGeneratedLambdaExample is not applicable for the arguments (IntUnaryOperator, Integer)
		*
		*/ 
		
		IntUnaryOperator specializedFunction = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_int_return_int; // OK
		System.out.println("(B) specializedFunction: " + specializedFunction);

		//Object value2 = callFunction(specializedFunction,new Integer(10)); // produces compiler error quoted above
		
		/* (C) 	genericForSpecializedFunction  Works - stacktrace:
		 * 
		Thread [main] (Suspended (breakpoint at line 15 in CompilerGeneratedLambdaExample))	
		CompilerGeneratedLambdaExample.staticFunction_arg_int_return_int(int) line: 15	
		575593575.apply(Object) line: not available <- lambda	
		CompilerGeneratedLambdaExample.callFunction(Function<A,R>, A) line: 10	
		CompilerGeneratedLambdaExample.main(String[]) line: 67	

		Output:
		genericForSpecializedFunction: testHelp.CompilerGeneratedLambdaExample$$Lambda$2/575593575@14acaea5 <- same lambda
		*/ 
	
		Function<Integer,Integer> genericForSpecializedFunction = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_int_return_int; // OK
		System.out.println("(C) genericForSpecializedFunction: " + genericForSpecializedFunction);
		Object value3 = callFunction(genericForSpecializedFunction,new Integer(10));
		System.out.println("(C) value type: " + value3.getClass().getName());
		System.out.println("(C) value: " + value3);
		
		/* (D) with genericFunction - works - stacktrace
		 * 
		Thread [main] (Suspended (breakpoint at line 19 in CompilerGeneratedLambdaExample))	
		CompilerGeneratedLambdaExample.staticFunction_arg_Integer_return_Integer(Integer) line: 19	
		226710952.apply(Object) line: not available	
		CompilerGeneratedLambdaExample.callFunction(Function<A,R>, A) line: 10	
		CompilerGeneratedLambdaExample.main(String[]) line: 84
		
		Output:
		genericFunction: testHelp.CompilerGeneratedLambdaExample$$Lambda$3/226710952@59fa1d9b
		 */
		
		Function<Integer,Integer> genericFunction = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_Integer_return_Integer; // OK
		System.out.println("(D) genericFunction: " + genericFunction);
		Object value4 = callFunction(genericFunction,new Integer(10));
		System.out.println("(D) value type: " + value4.getClass().getName());
		System.out.println("(D) value: " + value4);

		
		/* (E) specialiedForGenericFunction - stack
		 * 
		Thread [main] (Suspended (breakpoint at line 24 in CompilerGeneratedLambdaExample))	
		CompilerGeneratedLambdaExample.staticFunction_arg_Integer_return_Integer(Integer) line: 24	
		684874119.applyAsInt(int) line: not available <- lambda
		CompilerGeneratedLambdaExample.callIntUnaryOperator(IntUnaryOperator, int) line: 15	
		CompilerGeneratedLambdaExample.main(String[]) line: 109	
		
		output:
		specializedForGenericFunction: testHelp.CompilerGeneratedLambdaExample$$Lambda$4/684874119@4501b7af <- same lambda
		 */
		
		IntUnaryOperator specializedForGenericFunction = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_Integer_return_Integer; // OK
		System.out.println("(E) specializedForGenericFunction: " + specializedForGenericFunction);
		int value5 = callIntUnaryOperator(specializedForGenericFunction,10);
		//System.out.println("(E) value type: " + value5.getClass().getName());
		System.out.println("(E) value: " + value5);
		
		// (F) Behavior with lambda expressions - (a) parameter types
		
		IntUnaryOperator specializedLambda = (int i) -> {return i;}; // OK
		Function<Integer,Integer> genericLambda = (Integer i) -> {return i;}; // OK
		
		IntUnaryOperator specializedForIndeterminateLambda = (i) -> {return i;}; // OK
		Function<Integer,Integer> genericForIndeterminateLambda = (Integer i) -> {return i;}; // OK
		
		//IntUnaryOperator specializedForGenericLambda = (Integer i) -> {return i;}; // Bad 'Lambda expression's parameter i is expected to be of type int'
		//Function<Integer,Integer> genericForSpecializedLambda = (int i) -> {return i;}; // Bad 'Lambda expression's parameter i is expected to be of type Integer'

		// (F) Behavior with lambda expressions - (a) return types
		
		IntUnaryOperator specializedLambda2 = (int i) -> {return (Integer)i;}; // OK
		Function<Integer,Integer> genericLambda2 = (Integer i) -> {return (int)i;}; // OK
 
		//Function<Integer,Integer> genericLambda3 = (Integer i) -> {return Integer.toString(i);}; // Bad - 'Type mismatch: cannot convert from String to Integer' 
		Function<Integer,String> genericLambda4 = (Integer i) -> {return Integer.toString(i);}; // OK 
		
		Consumer<Integer> genericConsumerLambdaAsConsumer = (Integer i) -> {System.out.println(i);}; // OK
		//Consumer<Integer> genericLambdaAsConsumer2 = (Integer i) -> {return i;}; // Bad - 'Void methods cannot return a value' 
		//Consumer<Integer> genericLambdaAsConsumer3 = (Integer i) -> i; // Bad - 'Void methods cannot return a value'
		
		//Function<Integer,?> genericConumerLambdaAsFunvtion = (Integer i) -> {System.out.println(i);}; // Bad - 'This method must return a result of type Object'

		// (G) method references with array arguments - no conversion - none needed int can be used in generic but int [] can (because an array is an object)
		
		Consumer<String []> mainFunction = MethodReferenceToFunctionalTypeAssignments::main;
		
		Function<Integer[],Integer> genericArrayFunction = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_Integer_array_return_Integer;
		//Function<Integer[],Integer> specializedArrayFunction = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_int_array_return_Integer;

		Function<int[],Integer> genericArrayFunction2 = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_int_array_return_Integer;
		//Function<int[],Integer> specializedArrayFunction2 = MethodReferenceToFunctionalTypeAssignments::staticFunction_arg_Integer_array_return_Integer;
	}

}
