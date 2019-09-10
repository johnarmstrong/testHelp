package testHelp;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import static testHelp.SupplementaryFunctionalInterfaces.*;

/**
 * An interface with no abstract or default methods that bundles a set of unbound method interfaces that parallels the standard Java set
 * of functional interfaces.  
 * <p>
 * Unbound method interfaces are functional interfaces which accept unbound non-static methods as values.
 * They are defined in this library as a way of naming the types of standard Java method references of the form MyClass::nonstaticMethod
 * using in a way that leverages the naming conventions for "basic" functional types applicable to static methods (MyClass::staticMethod), 
 * bound non-static methods (aMyClassInstance::nonstaticMethod) as well as non-class-based lambda expressions.
 * <p>
 * The interfaces are named by appending {@code UnboundMethod} to the functional type that the method would have had if it had been a static rather
 * than non-static and adding an initial type parameter representing the class of the method to the type parameter list.  The methods through
 * which the methods are invoked are named by appending {@code WithInstanceAsFirstArgument} to the name of the method through which the corresponding
 * static method would be invoked. If there is a standard or supplementary functional interface with the same shape (same argument and return types),
 * the unbound method interface is defined as extending the standard or supplementary interfaces and the invocation method is defined as a default
 * method beside the inherited abstract invocation method; otherwise the unbound method interface is defined as without a superinterface and 
 * the invocation method is defined as the primary abstract method.
 * <p>
 * An example of the first type of unbound method interface (derived from a standard or supplementary interface) is {@link ConsumerUnboundMethod}, 
 * which extends {@link java.util.function.BiConsumer}.
 * <p>
 * An example of the second type of unbound method interface (not derived from a standard interface) is {@link ObjIntConsumerUnboundMethod}, for which
 * there is no corresponding standard or supplementary interface.
 * <p>
 * Note that that superinterface methods, where present, are inherited as is and will generally not be useful. 
 * 
 * @author John Armstrong
 */
public interface UnboundMethodInterfaces {



@FunctionalInterface
public static interface ObjDoubleConsumerUnboundMethod<C,T> { 
	void acceptWithClassInstanceAsFirstArg(C classInstance, T arg1, double arg2);
}



@FunctionalInterface
public static interface ObjIntConsumerUnboundMethod<C,T> { 
	void acceptWithClassInstanceAsFirstArg(C classInstance, T arg1, int arg2);
}



@FunctionalInterface
public static interface ObjLongConsumerUnboundMethod<C,T> { 
	void acceptWithClassInstanceAsFirstArg(C classInstance, T arg1, long arg2);
}



@FunctionalInterface
public static interface BiConsumerUnboundMethod<C,T,U> extends TriConsumer<C,T,U> { 
	default void acceptWithClassInstanceAsFirstArg(C classInstance, T arg1, U arg2) {
		this.accept(classInstance,arg1,arg2);
	}
}



@FunctionalInterface
public static interface DoubleBinaryOperatorUnboundMethod<C> extends ToDoubleBiFunctionUnboundMethod<C,Double,Double> { // normalized interface  = normalized superclass
	default double applyAsDoubleWithClassInstanceAsFirstArg(C classInstance, double arg1, double arg2) {
		return this.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg1,arg2);
	}
}



@FunctionalInterface
public static interface IntBinaryOperatorUnboundMethod<C> extends ToIntBiFunctionUnboundMethod<C,Integer,Integer> { // normalized interface  = normalized superclass
	default int applyAsIntWithClassInstanceAsFirstArg(C classInstance, int arg1, int arg2) {
		return this.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg1,arg2);
	}
}



@FunctionalInterface
public static interface LongBinaryOperatorUnboundMethod<C> extends ToLongBiFunctionUnboundMethod<C,Long,Long> { // normalized interface  = normalized superclass
	default long applyAsLongWithClassInstanceAsFirstArg(C classInstance, long arg1, long arg2) {
		return this.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg1,arg2);
	}
}



@FunctionalInterface
public static interface BinaryOperatorUnboundMethod<C,T> extends BiFunctionUnboundMethod<C,T,T,T> { // normalized interface  = superclass
	default T applyWithClassInstanceAsFirstArg(C classInstance, T arg1, T arg2) {
		return this.applyWithClassInstanceAsFirstArg(classInstance,arg1,arg2);
	}
}



@FunctionalInterface
public static interface ToDoubleBiFunctionUnboundMethod<C,T,U> { 
	double applyAsDoubleWithClassInstanceAsFirstArg(C classInstance, T arg1, U arg2);
}



@FunctionalInterface
public static interface ToIntBiFunctionUnboundMethod<C,T,U> { 
	int applyAsIntWithClassInstanceAsFirstArg(C classInstance, T arg1, U arg2);
}



@FunctionalInterface
public static interface ToLongBiFunctionUnboundMethod<C,T,U> { 
	long applyAsLongWithClassInstanceAsFirstArg(C classInstance, T arg1, U arg2);
}



@FunctionalInterface
public static interface BiFunctionUnboundMethod<C,T,U,R> extends TriFunction<C,T,U,R> { 
	default R applyWithClassInstanceAsFirstArg(C classInstance, T arg1, U arg2) {
		return this.apply(classInstance,arg1,arg2);
	}
}



@FunctionalInterface
public static interface BiPredicateUnboundMethod<C,T,U> extends TriPredicate<C,T,U> { 
	default boolean testWithClassInstanceAsFirstArg(C classInstance, T arg1, U arg2) {
		return this.test(classInstance,arg1,arg2);
	}
}



@FunctionalInterface
public static interface CallableUnboundMethod<C,R> extends Function<C,R> {
	default R callWithClassInstanceAsFirstArg(C classInstance) {
		return this.apply(classInstance);
	}
}



@FunctionalInterface
public static interface DoubleConsumerUnboundMethod<C> extends ObjDoubleConsumer<C> { 
	default void acceptWithClassInstanceAsFirstArg(C classInstance, double arg) {
		this.accept(classInstance,arg);
	}
}



@FunctionalInterface
public static interface IntConsumerUnboundMethod<C> extends ObjIntConsumer<C> { 
	default void acceptWithClassInstanceAsFirstArg(C classInstance, int arg) {
		this.accept(classInstance,arg);
	}
}



@FunctionalInterface
public static interface LongConsumerUnboundMethod<C> extends ObjLongConsumer<C> { 
	default void acceptWithClassInstanceAsFirstArg(C classInstance, long arg) {
		this.accept(classInstance,arg);
	}
}



@FunctionalInterface
public static interface ConsumerUnboundMethod<C,T> extends BiConsumer<C,T> { 
	default void acceptWithClassInstanceAsFirstArg(C classInstance, T arg) {
		this.accept(classInstance,arg);
	}
}



@FunctionalInterface
public static interface DoubleUnaryOperatorUnboundMethod<C> extends ToDoubleFunctionUnboundMethod<C,Double> { // normalized interface  = normalized superclass
	default double applyAsDoubleWithClassInstanceAsFirstArg(C classInstance, double arg) {
		return this.applyAsDoubleWithClassInstanceAsFirstArg(classInstance,arg);
	}
}



@FunctionalInterface
public static interface DoubleToIntFunctionUnboundMethod<C> { 
	int applyAsIntWithClassInstanceAsFirstArg(C classInstance, double arg);
}



@FunctionalInterface
public static interface DoubleToLongFunctionUnboundMethod<C> { 
	long applyAsLongWithClassInstanceAsFirstArg(C classInstance, double arg);
}



@FunctionalInterface
public static interface DoubleFunctionUnboundMethod<C,R> { 
	R applyWithClassInstanceAsFirstArg(C classInstance, double arg);
}



@FunctionalInterface
public static interface IntToDoubleFunctionUnboundMethod<C> { 
	double applyAsDoubleWithClassInstanceAsFirstArg(C classInstance, int arg);
}



@FunctionalInterface
public static interface IntUnaryOperatorUnboundMethod<C> extends ToIntFunctionUnboundMethod<C,Integer> { // normalized interface  = normalized superclass
	default int applyAsIntWithClassInstanceAsFirstArg(C classInstance, int arg) {
		return this.applyAsIntWithClassInstanceAsFirstArg(classInstance,arg);
	}
}



@FunctionalInterface
public static interface IntToLongFunctionUnboundMethod<C> { 
	long applyAsLongWithClassInstanceAsFirstArg(C classInstance, int arg);
}



@FunctionalInterface
public static interface IntFunctionUnboundMethod<C,R> { 
	R applyWithClassInstanceAsFirstArg(C classInstance, int arg);
}



@FunctionalInterface
public static interface LongToDoubleFunctionUnboundMethod<C> { 
	double applyAsDoubleWithClassInstanceAsFirstArg(C classInstance, long arg);
}



@FunctionalInterface
public static interface LongToIntFunctionUnboundMethod<C> { 
	int applyAsIntWithClassInstanceAsFirstArg(C classInstance, long arg);
}



@FunctionalInterface
public static interface LongUnaryOperatorUnboundMethod<C> extends ToLongFunctionUnboundMethod<C,Long> { // normalized interface  = normalized superclass
	default long applyAsLongWithClassInstanceAsFirstArg(C classInstance, long arg) {
		return this.applyAsLongWithClassInstanceAsFirstArg(classInstance,arg);
	}
}



@FunctionalInterface
public static interface LongFunctionUnboundMethod<C,R> { 
	R applyWithClassInstanceAsFirstArg(C classInstance, long arg);
}



@FunctionalInterface
public static interface ToDoubleFunctionUnboundMethod<C,T> { 
	double applyAsDoubleWithClassInstanceAsFirstArg(C classInstance, T arg);
}



@FunctionalInterface
public static interface ToIntFunctionUnboundMethod<C,T> { 
	int applyAsIntWithClassInstanceAsFirstArg(C classInstance, T arg);
}



@FunctionalInterface
public static interface ToLongFunctionUnboundMethod<C,T> { 
	long applyAsLongWithClassInstanceAsFirstArg(C classInstance, T arg);
}



@FunctionalInterface
public static interface FunctionUnboundMethod<C,T,R> extends BiFunction<C,T,R> { 
	default R applyWithClassInstanceAsFirstArg(C classInstance, T arg) {
		return this.apply(classInstance,arg);
	}
}



@FunctionalInterface
public static interface UnaryOperatorUnboundMethod<C,T> extends FunctionUnboundMethod<C,T,T> { // normalized interface  = superclass
	default T applyWithClassInstanceAsFirstArg(C classInstance, T arg) {
		return this.applyWithClassInstanceAsFirstArg(classInstance,arg);
	}
}



@FunctionalInterface
public static interface DoublePredicateUnboundMethod<C> { 
	boolean testWithClassInstanceAsFirstArg(C classInstance, double arg);
}



@FunctionalInterface
public static interface IntPredicateUnboundMethod<C> { 
	boolean testWithClassInstanceAsFirstArg(C classInstance, int arg);
}



@FunctionalInterface
public static interface LongPredicateUnboundMethod<C> { 
	boolean testWithClassInstanceAsFirstArg(C classInstance, long arg);
}



@FunctionalInterface
public static interface PredicateUnboundMethod<C,T> extends BiPredicate<C,T> { 
	default boolean testWithClassInstanceAsFirstArg(C classInstance, T arg) {
		return this.test(classInstance,arg);
	}
}



@FunctionalInterface
public static interface RunnableUnboundMethod<C> extends Consumer<C> {
	default void runWithClassInstanceAsFirstArg(C classInstance) {
		this.accept(classInstance);
	}
}



@FunctionalInterface
public static interface BooleanSupplierUnboundMethod<C> extends Predicate<C> {
	default boolean getAsBooleanWithClassInstanceAsFirstArg(C classInstance) {
		return this.test(classInstance);
	}
}



@FunctionalInterface
public static interface DoubleSupplierUnboundMethod<C> extends ToDoubleFunction<C> {
	default double getAsDoubleWithClassInstanceAsFirstArg(C classInstance) {
		return this.applyAsDouble(classInstance);
	}
}



@FunctionalInterface
public static interface IntSupplierUnboundMethod<C> extends ToIntFunction<C> {
	default int getAsIntWithClassInstanceAsFirstArg(C classInstance) {
		return this.applyAsInt(classInstance);
	}
}



@FunctionalInterface
public static interface LongSupplierUnboundMethod<C> extends ToLongFunction<C> {
	default long getAsLongWithClassInstanceAsFirstArg(C classInstance) {
		return this.applyAsLong(classInstance);
	}
}



@FunctionalInterface
public static interface SupplierUnboundMethod<C,T> extends Function<C,T> {
	default T getWithClassInstanceAsFirstArg(C classInstance) {
		return this.apply(classInstance);
	}
}


}
 