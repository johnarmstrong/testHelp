package testHelp;

import static testHelp.UnboundMethodInterfaces.*;

/**
 * One of two interfaces - the other is {@link FunctionNormalizers} - with no abstract methods that bundle static methods which normalize specialized
 * functions and unbound methods which take arguments and/or return values of primitive types (boolean, double, int, long) to generic functions
 * and methods which take arguments and return values of the corresponding object types (Boolean, Double, Integer, Long). (Note that this applies
 * to simple types; arrays of primitive and object types are can both be used without conversion in generic functions.)
 * <p>
 * Also included are static methods which accept generic functions and unbound methods and return them as is.  They extend the set
 * of normalizers to cover the complete inventory of functions and unbound methods both specialized and generic, and are especially
 * useful for passing function and unbound methods of unknown type to the overloaded versions of the normalization methods.
 * <p>
 * Important note: it is not necessary to call normalization methods on method reference functional object expressions.  This is because they are automatically compiled
 * to functional objects of the types of their target type,that is, the functional interface type of the variable, function parameter or function return type that they
 * are being assigned to.
 * <p>
 * However, if the the functional object expression is (a) a lambda expression with typed formal parameters or (b) a variable, function parameter or return value of a 
 * functional interface type, its type is fixed and if it is different from what is wanted - typically, the functional interface type of another variable, 
 * function parameter or return value that the expression is to assigned to - it will have to be explicitly converted to the type.  The methods in FunctionNormalizers
 * and UnboundMethodNormalizers (this interface) can be used to convert specialized functional objects with primitive types to the corresponding functional objects
 * with object types - referred to in this library as normalized types.
 * 
 * @author John Armstrong
 */
public interface UnboundMethodNormalizers {




public static <C,T> BiConsumerUnboundMethod<C,T,Double>  normalizeObjDoubleConsumerUnboundMethod(ObjDoubleConsumerUnboundMethod<C,T>  objDoubleConsumerUnboundMethod) { 
	return (C classInstance,T arg1, Double arg2) -> 
	{ objDoubleConsumerUnboundMethod.acceptWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C,T> BiConsumerUnboundMethod<C,T,Integer>  normalizeObjIntConsumerUnboundMethod(ObjIntConsumerUnboundMethod<C,T>  objIntConsumerUnboundMethod) { 
	return (C classInstance,T arg1, Integer arg2) -> 
	{ objIntConsumerUnboundMethod.acceptWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C,T> BiConsumerUnboundMethod<C,T,Long>  normalizeObjLongConsumerUnboundMethod(ObjLongConsumerUnboundMethod<C,T>  objLongConsumerUnboundMethod) { 
	return (C classInstance,T arg1, Long arg2) -> 
	{ objLongConsumerUnboundMethod.acceptWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C,T,U> BiConsumerUnboundMethod<C,T,U>  normalizeBiConsumerUnboundMethod(BiConsumerUnboundMethod<C,T,U>  biConsumerUnboundMethod) { // passthrough
	return biConsumerUnboundMethod;
}    



public static <C> BiFunctionUnboundMethod<C,Double,Double,Double> normalizeDoubleBinaryOperatorUnboundMethod(DoubleBinaryOperatorUnboundMethod<C> doubleBinaryOperatorUnboundMethod) { 
	return (C classInstance,Double arg1, Double arg2) -> 
	{ return (Double)doubleBinaryOperatorUnboundMethod.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C> BiFunctionUnboundMethod<C,Integer,Integer,Integer> normalizeIntBinaryOperatorUnboundMethod(IntBinaryOperatorUnboundMethod<C>  intBinaryOperatorUnboundMethod) { 
	return (C classInstance,Integer arg1, Integer arg2) -> 
	{ return (Integer)intBinaryOperatorUnboundMethod.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C> BiFunctionUnboundMethod<C,Long,Long,Long> normalizeLongBinaryOperatorUnboundMethod(LongBinaryOperatorUnboundMethod<C> longBinaryOperatorUnboundMethod) { 
	return (C classInstance,Long arg1, Long arg2) -> 
	{ return (Long)longBinaryOperatorUnboundMethod.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C,T> BiFunctionUnboundMethod<C,T,T,T>  normalizeBinaryOperatorUnboundMethod(BinaryOperatorUnboundMethod<C,T>  binaryOperatorUnboundMethod) { // passthrough - normalized interface  = superclass
	return binaryOperatorUnboundMethod;
}    



public static <C,T,U> BiFunctionUnboundMethod<C,T,U,Double>  normalizeToDoubleBiFunctionUnboundMethod(ToDoubleBiFunctionUnboundMethod<C,T,U>  toDoubleBiFunctionUnboundMethod) { 
	return (C classInstance,T arg1, U arg2) -> 
	{ return (Double)toDoubleBiFunctionUnboundMethod.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C,T,U> BiFunctionUnboundMethod<C,T,U,Integer>  normalizeToIntBiFunctionUnboundMethod(ToIntBiFunctionUnboundMethod<C,T,U>  toIntBiFunctionUnboundMethod) { 
	return (C classInstance,T arg1, U arg2) -> 
	{ return (Integer)toIntBiFunctionUnboundMethod.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C,T,U> BiFunctionUnboundMethod<C,T,U,Long>  normalizeToLongBiFunctionUnboundMethod(ToLongBiFunctionUnboundMethod<C,T,U>  toLongBiFunctionUnboundMethod) { 
	return (C classInstance,T arg1, U arg2) -> 
	{ return (Long)toLongBiFunctionUnboundMethod.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg1,arg2); };
}    



public static <C,T,U,R> BiFunctionUnboundMethod<C,T,U,R>  normalizeBiFunctionUnboundMethod(BiFunctionUnboundMethod<C,T,U,R>  biFunctionUnboundMethod) { // passthrough
	return biFunctionUnboundMethod;
}    



public static <C,T,U> BiPredicateUnboundMethod<C,T,U>  normalizeBiPredicateUnboundMethod(BiPredicateUnboundMethod<C,T,U>  biPredicateUnboundMethod) { // passthrough
	return biPredicateUnboundMethod;
}    



public static <C,R> CallableUnboundMethod<C,R> normalizeCallableUnboundMethod(CallableUnboundMethod<C,R> callableUnboundMethod) { // passthrough - java.util.concurrent.Callable - throws checked exception
	return callableUnboundMethod;
}    



public static <C> ConsumerUnboundMethod<C,Double> normalizeDoubleConsumerUnboundMethod(DoubleConsumerUnboundMethod<C>  doubleConsumerUnboundMethod) { 
	return (C classInstance, Double arg) -> 
	{ doubleConsumerUnboundMethod.acceptWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> ConsumerUnboundMethod<C,Integer> normalizeIntConsumerUnboundMethod(IntConsumerUnboundMethod<C> intConsumerUnboundMethod) { 
	return (C classInstance, Integer arg) -> 
	{ intConsumerUnboundMethod.acceptWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> ConsumerUnboundMethod<C,Long> normalizeLongConsumerUnboundMethod(LongConsumerUnboundMethod<C> longConsumerUnboundMethod) { 
	return (C classInstance, Long arg) -> 
	{ longConsumerUnboundMethod.acceptWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,T> ConsumerUnboundMethod<C,T>  normalizeConsumerUnboundMethod(ConsumerUnboundMethod<C,T>  consumerUnboundMethod) { // passthrough
	return consumerUnboundMethod;
}    



public static <C> FunctionUnboundMethod<C,Double,Double> normalizeDoubleUnaryOperatorUnboundMethod(DoubleUnaryOperatorUnboundMethod<C>  doubleUnaryOperatorUnboundMethod) { 
	return (C classInstance, Double arg) -> 
	{ return (Double)doubleUnaryOperatorUnboundMethod.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Double,Integer> normalizeDoubleToIntFunctionUnboundMethod(DoubleToIntFunctionUnboundMethod<C> doubleToIntFunctionUnboundMethod) { 
	return (C classInstance, Double arg) -> 
	{ return (Integer)doubleToIntFunctionUnboundMethod.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Double,Long> normalizeDoubleToLongFunctionUnboundMethod(DoubleToLongFunctionUnboundMethod<C> doubleToLongFunctionUnboundMethod) { 
	return (C classInstance, Double arg) -> 
	{ return (Long)doubleToLongFunctionUnboundMethod.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,R> FunctionUnboundMethod<C,Double,R>  normalizeDoubleFunctionUnboundMethod(DoubleFunctionUnboundMethod<C,R>  doubleFunctionUnboundMethod) { 
	return (C classInstance, Double arg) -> 
	{ return doubleFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Integer,Double> normalizeIntToDoubleFunctionUnboundMethod(IntToDoubleFunctionUnboundMethod<C> intToDoubleFunctionUnboundMethod) { 
	return (C classInstance, Integer arg) -> 
	{ return (Double)intToDoubleFunctionUnboundMethod.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Integer,Integer> normalizeIntUnaryOperatorUnboundMethod(IntUnaryOperatorUnboundMethod<C> intUnaryOperatorUnboundMethod) { 
	return (C classInstance, Integer arg) -> 
	{ return (Integer)intUnaryOperatorUnboundMethod.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Integer,Long> normalizeIntToLongFunctionUnboundMethod(IntToLongFunctionUnboundMethod<C> intToLongFunctionUnboundMethod) { 
	return (C classInstance, Integer arg) -> 
	{ return (Long)intToLongFunctionUnboundMethod.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,R> FunctionUnboundMethod<C,Integer,R>  normalizeIntFunctionUnboundMethod(IntFunctionUnboundMethod<C,R>  intFunctionUnboundMethod) { 
	return (C classInstance, Integer arg) -> 
	{ return intFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Long,Double> normalizeLongToDoubleFunctionUnboundMethod(LongToDoubleFunctionUnboundMethod<C>  longToDoubleFunctionUnboundMethod) { 
	return (C classInstance, Long arg) -> 
	{ return (Double)longToDoubleFunctionUnboundMethod.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Long,Integer> normalizeLongToIntFunctionUnboundMethod(LongToIntFunctionUnboundMethod<C> longToIntFunctionUnboundMethod) { 
	return (C classInstance, Long arg) -> 
	{ return (Integer)longToIntFunctionUnboundMethod.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> FunctionUnboundMethod<C,Long,Long> normalizeLongUnaryOperatorUnboundMethod(LongUnaryOperatorUnboundMethod<C>  longUnaryOperatorUnboundMethod) { 
	return (C classInstance, Long arg) -> 
	{ return (Long)longUnaryOperatorUnboundMethod.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,R> FunctionUnboundMethod<C,Long,R>  normalizeLongFunctionUnboundMethod(LongFunctionUnboundMethod<C,R>  longFunctionUnboundMethod) { 
	return (C classInstance, Long arg) -> 
	{ return longFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,T> FunctionUnboundMethod<C,T,Double>  normalizeToDoubleFunctionUnboundMethod(ToDoubleFunctionUnboundMethod<C,T>  toDoubleFunctionUnboundMethod) { 
	return (C classInstance, T arg) -> 
	{ return (Double)toDoubleFunctionUnboundMethod.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,T> FunctionUnboundMethod<C,T,Integer>  normalizeToIntFunctionUnboundMethod(ToIntFunctionUnboundMethod<C,T>  toIntFunctionUnboundMethod) { 
	return (C classInstance, T arg) -> 
	{ return (Integer)toIntFunctionUnboundMethod.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,T> FunctionUnboundMethod<C,T,Long>  normalizeToLongFunctionUnboundMethod(ToLongFunctionUnboundMethod<C,T>  toLongFunctionUnboundMethod) { 
	return (C classInstance, T arg) -> 
	{ return (Long)toLongFunctionUnboundMethod.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,T,R> FunctionUnboundMethod<C,T,R>  normalizeFunctionUnboundMethod(FunctionUnboundMethod<C,T,R>  functionUnboundMethod) { // passthrough
	return functionUnboundMethod;
}    



public static <C,T> FunctionUnboundMethod<C,T,T>  normalizeUnaryOperatorUnboundMethod(UnaryOperatorUnboundMethod<C,T> unaryOperatorUnboundMethod) { // passthrough - normalized interface  = superclass
	return unaryOperatorUnboundMethod;
}    



public static <C> PredicateUnboundMethod<C,Double> normalizeDoublePredicateUnboundMethod(DoublePredicateUnboundMethod<C>  doublePredicateUnboundMethod) { 
	return (C classInstance, Double arg) -> 
	{ return (Boolean)doublePredicateUnboundMethod.testWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> PredicateUnboundMethod<C,Integer> normalizeIntPredicateUnboundMethod(IntPredicateUnboundMethod<C> intPredicateUnboundMethod) { 
	return (C classInstance, Integer arg) -> 
	{ return (Boolean)intPredicateUnboundMethod.testWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C> PredicateUnboundMethod<C,Long> normalizeLongPredicateUnboundMethod(LongPredicateUnboundMethod<C>  longPredicateUnboundMethod) { 
	return (C classInstance, Long arg) -> 
	{ return (Boolean)longPredicateUnboundMethod.testWithClassInstanceAsFirstArg(classInstance,arg); };
}    



public static <C,T> PredicateUnboundMethod<C,T>  normalizePredicateUnboundMethod(PredicateUnboundMethod<C,T>  predicateUnboundMethod) { // passthrough
	return predicateUnboundMethod;
}    



public static <C> RunnableUnboundMethod<C> normalizeRunnableUnboundMethod(RunnableUnboundMethod<C> runnableUnboundMethod) { // passthrough - java.lang.Runnable
	return runnableUnboundMethod;
}    



public static <C> SupplierUnboundMethod<C,Boolean> normalizeBooleanSupplierUnboundMethod(BooleanSupplierUnboundMethod<C>  booleanSupplierUnboundMethod) { 
	return (C classInstance) -> 
	{ return (Boolean)booleanSupplierUnboundMethod.getAsBooleanWithClassInstanceAsFirstArg(classInstance); };
}    



public static <C> SupplierUnboundMethod<C,Double> normalizeDoubleSupplierUnboundMethod(DoubleSupplierUnboundMethod<C>  doubleSupplierUnboundMethod) { 
	return (C classInstance) -> 
	{ return (Double)doubleSupplierUnboundMethod.getAsDoubleWithClassInstanceAsFirstArg(classInstance); };
}    



public static <C> SupplierUnboundMethod<C,Integer> normalizeIntSupplierUnboundMethod(IntSupplierUnboundMethod<C> intSupplierUnboundMethod) { 
	return (C classInstance) -> 
	{ return (Integer)intSupplierUnboundMethod.getAsIntWithClassInstanceAsFirstArg(classInstance); };
}    



public static <C> SupplierUnboundMethod<C,Long> normalizeLongSupplierUnboundMethod(LongSupplierUnboundMethod<C>  longSupplierUnboundMethod) { 
	return (C classInstance) -> 
	{ return (Long)longSupplierUnboundMethod.getAsLongWithClassInstanceAsFirstArg(classInstance); };
}    



public static <C,T> SupplierUnboundMethod<C,T>  normalizeSupplierUnboundMethod(SupplierUnboundMethod<C,T>  supplierUnboundMethod) { // passthrough
	return supplierUnboundMethod;
}    


}