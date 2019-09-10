package testHelp;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * One of two interfaces - the other is {@link UnboundMethodNormalizers} - with no abstract methods that bundle static methods which normalize specialized
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
 * (this interface) and UnboundMethodNormalizers can be used to convert specialized functional objects with primitive types to the corresponding functional objects
 *  with object types - referred to in this library normalized types.  
 * 
 * @author John Armstrong
 */
public interface FunctionNormalizers {



public static <T> BiConsumer<T,Double> normalizeObjDoubleConsumer(ObjDoubleConsumer<T> objDoubleConsumer) { 
	return (T arg1, Double arg2) -> 
	{ objDoubleConsumer.accept(arg1,arg2); };
}    



public static <T> BiConsumer<T,Integer> normalizeObjIntConsumer(ObjIntConsumer<T> objIntConsumer) { 
	return (T arg1, Integer arg2) -> 
	{ objIntConsumer.accept(arg1,arg2); };
}    



public static <T> BiConsumer<T,Long> normalizeObjLongConsumer(ObjLongConsumer<T> objLongConsumer) { 
	return (T arg1, Long arg2) -> 
	{ objLongConsumer.accept(arg1,arg2); };
}    



public static <T,U> BiConsumer<T,U> normalizeBiConsumer(BiConsumer<T,U> biConsumer) { // passthrough
	return biConsumer;
}    



public static BiFunction<Double,Double,Double> normalizeDoubleBinaryOperator(DoubleBinaryOperator doubleBinaryOperator) { 
	return (Double arg1, Double arg2) -> 
	{ return (Double)doubleBinaryOperator.applyAsDouble(arg1,arg2); };
}    



public static BiFunction<Integer,Integer,Integer> normalizeIntBinaryOperator(IntBinaryOperator intBinaryOperator) { 
	return (Integer arg1, Integer arg2) -> 
	{ return (Integer)intBinaryOperator.applyAsInt(arg1,arg2); };
}    



public static BiFunction<Long,Long,Long> normalizeLongBinaryOperator(LongBinaryOperator longBinaryOperator) { 
	return (Long arg1, Long arg2) -> 
	{ return (Long)longBinaryOperator.applyAsLong(arg1,arg2); };
}    



public static <T> BiFunction<T,T,T> normalizeBinaryOperator(BinaryOperator<T> binaryOperator) { // passthrough - normalized interface  = superclass
	return binaryOperator;
}    



public static <T,U> BiFunction<T,U,Double> normalizeToDoubleBiFunction(ToDoubleBiFunction<T,U> toDoubleBiFunction) { 
	return (T arg1, U arg2) -> 
	{ return (Double)toDoubleBiFunction.applyAsDouble(arg1,arg2); };
}    



public static <T,U> BiFunction<T,U,Integer> normalizeToIntBiFunction(ToIntBiFunction<T,U> toIntBiFunction) { 
	return (T arg1, U arg2) -> 
	{ return (Integer)toIntBiFunction.applyAsInt(arg1,arg2); };
}    



public static <T,U> BiFunction<T,U,Long> normalizeToLongBiFunction(ToLongBiFunction<T,U> toLongBiFunction) { 
	return (T arg1, U arg2) -> 
	{ return (Long)toLongBiFunction.applyAsLong(arg1,arg2); };
}    



public static <T,U,R> BiFunction<T,U,R> normalizeBiFunction(BiFunction<T,U,R> biFunction) { // passthrough
	return biFunction;
}    



public static <T,U> BiPredicate<T,U> normalizeBiPredicate(BiPredicate<T,U> biPredicate) { // passthrough
	return biPredicate;
}    



public static <R> Callable<R> normalizeCallable(Callable<R> callable) { // passthrough - java.util.concurrent.Callable - throws checked exception
	return callable;
}    



public static Consumer<Double> normalizeDoubleConsumer(DoubleConsumer doubleConsumer) { 
	return (Double arg) -> 
	{ doubleConsumer.accept(arg); };
}    



public static Consumer<Integer> normalizeIntConsumer(IntConsumer intConsumer) { 
	return (Integer arg) -> 
	{ intConsumer.accept(arg); };
}    



public static Consumer<Long> normalizeLongConsumer(LongConsumer longConsumer) { 
	return (Long arg) -> 
	{ longConsumer.accept(arg); };
}    



public static <T> Consumer<T> normalizeConsumer(Consumer<T> consumer) { // passthrough
	return consumer;
}    



public static Function<Double,Double> normalizeDoubleUnaryOperator(DoubleUnaryOperator doubleUnaryOperator) { 
	return (Double arg) -> 
	{ return (Double)doubleUnaryOperator.applyAsDouble(arg); };
}    



public static Function<Double,Integer> normalizeDoubleToIntFunction(DoubleToIntFunction doubleToIntFunction) { 
	return (Double arg) -> 
	{ return (Integer)doubleToIntFunction.applyAsInt(arg); };
}    



public static Function<Double,Long> normalizeDoubleToLongFunction(DoubleToLongFunction doubleToLongFunction) { 
	return (Double arg) -> 
	{ return (Long)doubleToLongFunction.applyAsLong(arg); };
}    



public static <R> Function<Double,R> normalizeDoubleFunction(DoubleFunction<R> doubleFunction) { 
	return (Double arg) -> 
	{ return doubleFunction.apply(arg); };
}    



public static Function<Integer,Double> normalizeIntToDoubleFunction(IntToDoubleFunction intToDoubleFunction) { 
	return (Integer arg) -> 
	{ return (Double)intToDoubleFunction.applyAsDouble(arg); };
}    



public static Function<Integer,Integer> normalizeIntUnaryOperator(IntUnaryOperator intUnaryOperator) { 
	return (Integer arg) -> 
	{ return (Integer)intUnaryOperator.applyAsInt(arg); };
}    



public static Function<Integer,Long> normalizeIntToLongFunction(IntToLongFunction intToLongFunction) { 
	return (Integer arg) -> 
	{ return (Long)intToLongFunction.applyAsLong(arg); };
}    



public static <R> Function<Integer,R> normalizeIntFunction(IntFunction<R> intFunction) { 
	return (Integer arg) -> 
	{ return intFunction.apply(arg); };
}    



public static Function<Long,Double> normalizeLongToDoubleFunction(LongToDoubleFunction longToDoubleFunction) { 
	return (Long arg) -> 
	{ return (Double)longToDoubleFunction.applyAsDouble(arg); };
}    



public static Function<Long,Integer> normalizeLongToIntFunction(LongToIntFunction longToIntFunction) { 
	return (Long arg) -> 
	{ return (Integer)longToIntFunction.applyAsInt(arg); };
}    



public static Function<Long,Long> normalizeLongUnaryOperator(LongUnaryOperator longUnaryOperator) { 
	return (Long arg) -> 
	{ return (Long)longUnaryOperator.applyAsLong(arg); };
}    



public static <R> Function<Long,R> normalizeLongFunction(LongFunction<R> longFunction) { 
	return (Long arg) -> 
	{ return longFunction.apply(arg); };
}    



public static <T> Function<T,Double> normalizeToDoubleFunction(ToDoubleFunction<T> toDoubleFunction) { 
	return (T arg) -> 
	{ return (Double)toDoubleFunction.applyAsDouble(arg); };
}    



public static <T> Function<T,Integer> normalizeToIntFunction(ToIntFunction<T> toIntFunction) { 
	return (T arg) -> 
	{ return (Integer)toIntFunction.applyAsInt(arg); };
}    



public static <T> Function<T,Long> normalizeToLongFunction(ToLongFunction<T> toLongFunction) { 
	return (T arg) -> 
	{ return (Long)toLongFunction.applyAsLong(arg); };
}    



public static <T,R> Function<T,R> normalizeFunction(Function<T,R> function) { // passthrough
	return function;
}    



public static <T> Function<T,T> normalizeUnaryOperator(UnaryOperator<T> unaryOperator) { // passthrough - normalized interface  = superclass
	return unaryOperator;
}    



public static Predicate<Double> normalizeDoublePredicate(DoublePredicate doublePredicate) { 
	return (Double arg) -> 
	{ return (Boolean)doublePredicate.test(arg); };
}    



public static Predicate<Integer> normalizeIntPredicate(IntPredicate intPredicate) { 
	return (Integer arg) -> 
	{ return (Boolean)intPredicate.test(arg); };
}    



public static Predicate<Long> normalizeLongPredicate(LongPredicate longPredicate) { 
	return (Long arg) -> 
	{ return (Boolean)longPredicate.test(arg); };
}    



public static <T> Predicate<T> normalizePredicate(Predicate<T> predicate) { // passthrough
	return predicate;
}    



public static Runnable normalizeRunnable(Runnable runnable) { // passthrough - java.lang.Runnable
	return runnable;
}    



public static Supplier<Boolean> normalizeBooleanSupplier(BooleanSupplier booleanSupplier) { 
	return () -> 
	{ return (Boolean)booleanSupplier.getAsBoolean(); };
}    



public static Supplier<Double> normalizeDoubleSupplier(DoubleSupplier doubleSupplier) { 
	return () -> 
	{ return (Double)doubleSupplier.getAsDouble(); };
}    



public static Supplier<Integer> normalizeIntSupplier(IntSupplier intSupplier) { 
	return () -> 
	{ return (Integer)intSupplier.getAsInt(); };
}    



public static Supplier<Long> normalizeLongSupplier(LongSupplier longSupplier) { 
	return () -> 
	{ return (Long)longSupplier.getAsLong(); };
}    



public static <T> Supplier<T> normalizeSupplier(Supplier<T> supplier) { // passthrough
	return supplier;
}    


}
  