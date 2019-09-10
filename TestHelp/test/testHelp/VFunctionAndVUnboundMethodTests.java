package testHelp;
import static testHelp.VFunctionsAndVUnboundMethods.*;
import static testHelp.SupplementaryFunctionalInterfaces.*;
import static testHelp.UnboundMethodInterfaces.*;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


import org.junit.jupiter.api.Test;

/**
 * @author John Armstrong
 */
class VFunctionAndVUnboundMethodTests {
	
	
	/*
	 * Method assignment tests - static methods
	 */
	
	@Test
	void test_assignStaticMethodToFunctionalInterface() {
		
		Runnable runnable = TestClassWithNormalizedMethods::staticRunnable;
		
		Supplier<String> supplier_return_String = TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String;
		Callable<String> callable_return_String = TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String;
		
		Consumer<String> consumer_args_string = TestClassWithNormalizedMethods::staticConsumer_arg_String;
		BiConsumer<String,String> biConsumer_args_string_String = TestClassWithNormalizedMethods::staticBiConsumer_args_String_String;
		
		Function<String,String> function_arg_String_return_String = TestClassWithNormalizedMethods::staticFunction_arg_String_return_String;
		BiFunction<String,String,String> biFunction_args_String_String_return_String = TestClassWithNormalizedMethods::staticBiFunction_args_String_String_return_String;
		TriFunction<String,String,String,String> triFunction_args_String_String_String_return_String = TestClassWithNormalizedMethods::staticTriFunction_args_String_String_String_return_String;
	}
	
	/*
	 * Method assignment tests - bound instance methods
	 */

	@Test
	void test_assignBoundInstanceMethodToFunctionalInterface() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		Runnable runnable = instance::instanceRunnable;
		
		Supplier<String> supplier_return_String = instance::instanceSupplierOrCallable_return_String;
		Callable<String> callable_return_String = instance::instanceSupplierOrCallable_return_String;
		Consumer<String> consumer_args_string = instance::instanceConsumer_arg_String;
		BiConsumer<String,String> biConsumer_args_string_String = instance::instanceBiConsumer_args_String_String;
		Function<String,String> function_arg_String_return_String = instance::instanceFunction_arg_String_return_String;
		BiFunction<String,String,String> biFunction_args_String_return_String = instance::instanceBiFunction_args_String_String_return_String;
	}
	
	
	@Test
	void test_assignUnboundInstanceMethodToFunctionalInterface() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		Consumer<TestClassWithNormalizedMethods> runnable = TestClassWithNormalizedMethods::instanceRunnable;
		
		Function<TestClassWithNormalizedMethods,String> supplier_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		Function<TestClassWithNormalizedMethods,String> callable_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		BiConsumer<TestClassWithNormalizedMethods,String> consumer_args_string = TestClassWithNormalizedMethods::instanceConsumer_arg_String;
		TriConsumer<TestClassWithNormalizedMethods,String,String> biConsumer_args_string_String = TestClassWithNormalizedMethods::instanceBiConsumer_args_String_String;
		BiFunction<TestClassWithNormalizedMethods,String,String> function_arg_String_return_String = TestClassWithNormalizedMethods::instanceFunction_arg_String_return_String;
		TriFunction<TestClassWithNormalizedMethods,String,String,String> biFunction_args_String_return_String = TestClassWithNormalizedMethods::instanceBiFunction_args_String_String_return_String;
	}
	@Test
	void test_assignUnboundInstanceMethodToUnboundMethodInterface() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		RunnableUnboundMethod<TestClassWithNormalizedMethods> runnable = TestClassWithNormalizedMethods::instanceRunnable;
		
		SupplierUnboundMethod<TestClassWithNormalizedMethods,String> supplier_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		CallableUnboundMethod<TestClassWithNormalizedMethods,String> callable_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		ConsumerUnboundMethod<TestClassWithNormalizedMethods,String> consumer_args_string = TestClassWithNormalizedMethods::instanceConsumer_arg_String;
		BiConsumerUnboundMethod<TestClassWithNormalizedMethods,String,String> biConsumer_args_string_String = TestClassWithNormalizedMethods::instanceBiConsumer_args_String_String;
		FunctionUnboundMethod<TestClassWithNormalizedMethods,String,String> function_arg_String_return_String = TestClassWithNormalizedMethods::instanceFunction_arg_String_return_String;
		BiFunctionUnboundMethod<TestClassWithNormalizedMethods,String,String,String> biFunction_args_String_return_String = TestClassWithNormalizedMethods::instanceBiFunction_args_String_String_return_String;
	}
	
	/*
	 * Convert Supplier to Callable - cant do
	 */
	@Test
	void test_supplierOrCallable_type()
	{
		Callable<String> callable = TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String;
		Supplier<String> supplier = TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String;
		
		verify.that(callable);
		//verify.that((Callable<String>)supplier); // Compiles but gives runtime lies 
		//verify.that((Callable<String>)(Object)supplier); // Compiles but gives runtime lies 
	}
	
	/*
	 * Method run tests
	 */
	
	@Test
	void test_unboundInstanceRunnable()
	{
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		Consumer<TestClassWithNormalizedMethods> rawUnboundInstanceRunnable = TestClassWithNormalizedMethods::instanceRunnable;
		rawUnboundInstanceRunnable.accept(instance);
		
		RunnableUnboundMethod<TestClassWithNormalizedMethods> unboundInstanceRunnable = TestClassWithNormalizedMethods::instanceRunnable;
		
		unboundInstanceRunnable.accept(instance); // same as raw, still works
		unboundInstanceRunnable.runWithClassInstanceAsFirstArg(instance);
		
	}
	
	@Test
	void test_boundInstanceRunnable()
	{
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		Runnable boundInstanceRunnable = instance::instanceRunnable;
		
		boundInstanceRunnable.run();
	}
	
	
	@Test
	void test_unboundInstanceConsumer_arg_String()
	{
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		BiConsumer<TestClassWithNormalizedMethods,String> rawUnboundInstanceConsumer = TestClassWithNormalizedMethods::instanceConsumer_arg_String;
		
		rawUnboundInstanceConsumer.accept(instance,"hello");
	
		ConsumerUnboundMethod<TestClassWithNormalizedMethods,String> unboundInstanceConsumer = TestClassWithNormalizedMethods::instanceConsumer_arg_String;
		
		unboundInstanceConsumer.accept(instance,"hello"); // same as raw, still works
		unboundInstanceConsumer.acceptWithClassInstanceAsFirstArg(instance,"hello");

		
	}

	@Test
	void test_boundInstanceConsumer_arg_String()
	{
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		Consumer<String> consumer = instance::instanceConsumer_arg_String;
		
		consumer.accept("hello");
	}
	
	/*
	 * makeVoidR... method run tests
	 */
	
	@Test
	void test_unboundRunnableAsVFunction()
	{
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		Consumer<TestClassWithNormalizedMethods> rawUnboundRunnable = TestClassWithNormalizedMethods::instanceRunnable;
		
		rawUnboundRunnable.accept(instance);
		
		VFunction<TestClassWithNormalizedMethods,VoidR> unboundRunnableAsVFunction = makeVFunction(TestClassWithNormalizedMethods::instanceRunnable);
		
		unboundRunnableAsVFunction.apply(instance);
		//unboundRunnableAsVFunction.applyWithClassInstanceAsFirstArg(instance);
		
		VUnboundMethod<TestClassWithNormalizedMethods,VoidA,VoidR> unboundRunnableAsBoundVFunctionMethod = makeVUnboundMethod(TestClassWithNormalizedMethods::instanceRunnable);
	}

	
	/* 
	 * makeVFunction function (represented by static methods) run tests
	 */
	
	// From Runnable
	
	@Test
	void test_makeVFunction_from_Runnable() {
		VFunction<VoidA,VoidR> vFun = makeVFunction(TestClassWithNormalizedMethods::staticRunnable);
		
		Object ret = vFun.apply(voidA);
		verify.thatObject(ret).isEqualTo(voidR);
	}
	
	@Test
	void test_makeVFunction_from_Runnable_noarg_appy() {
		VFunction<VoidA,VoidR> vFun = makeVFunction(TestClassWithNormalizedMethods::staticRunnable);
		
		Object ret = vFun.apply();
		verify.thatObject(ret).isEqualTo(voidR);
	}
	
	// From Callable
	@Test
	void test_makeVFunction_from_Callable() {
		VFunction<VoidA,String> vFun = makeVFunction((Callable<String>)TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String);
		
		Object ret = vFun.apply(voidA);
		verify.thatObject(ret).isInstanceOf(String.class);
	}
	
	@Test
	void test_makeVFunction_from_Callable_noarg_apply() {
		VFunction<VoidA,String> vFun = makeVFunction((Callable<String>)TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String);
		
		Object ret = vFun.apply();
		verify.thatObject(ret).isInstanceOf(String.class);
	}

	// From Supplier
	
	@Test
	void test_makeVFunction_from_Cupplier() {
		VFunction<VoidA,String> vFun = makeVFunction((Supplier<String>)TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String);
		
		Object ret = vFun.apply(voidA);
		verify.thatObject(ret).isInstanceOf(String.class);
	}


	// From Consumer
	
	@Test
	void test_makeVFunction_from_Consumer() {
		VFunction<String,VoidR> vFun = makeVFunction(TestClassWithNormalizedMethods::staticConsumer_arg_String);
		Object ret = vFun.apply("Hello from caller");
		verify.thatObject(ret).isEqualTo(voidR);
	}
	
	@Test
	void test_makeVFunction_from_BiConsumer() {
		VBiFunction<String,String,VoidR> vFun = makeVBiFunction(TestClassWithNormalizedMethods::staticBiConsumer_args_String_String);
		Object ret = vFun.apply("Hello from caller","Hello again from caller");
		verify.thatObject(ret).isEqualTo(voidR);
	}
			
	@Test
	void test_makeVFunction_from_Function() {
		VFunction<String,String> vFun = makeVFunction(TestClassWithNormalizedMethods::staticFunction_arg_String_return_String);
		Object ret = vFun.apply("Hello from caller");
		verify.thatObject(ret).isInstanceOf(String.class);
	}

	@Test
	void test_makeVFunction_from_BiFunction() {
		VBiFunction<String,String,String> vFun = makeVBiFunction(TestClassWithNormalizedMethods::staticBiFunction_args_String_String_return_String);
		Object ret = vFun.apply("Hello from caller","Hello again from caller");
		verify.thatObject(ret).isInstanceOf(String.class);
	}
			
	
	@Test
	void test__makeUnboundMethod_from_unboundInstanceMethod() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		RunnableUnboundMethod<TestClassWithNormalizedMethods> unboundRunnableMethod = TestClassWithNormalizedMethods::instanceRunnable;
		VFunction<TestClassWithNormalizedMethods,VoidR> vFunction1 = makeVFunction(unboundRunnableMethod);
		
		SupplierUnboundMethod<TestClassWithNormalizedMethods,String> unboundSupplierMethod_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		VFunction<TestClassWithNormalizedMethods,String> vFunction2 = makeVFunction(unboundSupplierMethod_return_String);
		
		CallableUnboundMethod<TestClassWithNormalizedMethods,String> unboundCallableMethod_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		VFunction<TestClassWithNormalizedMethods,String> vFunction3 = makeVFunction(unboundCallableMethod_return_String);
		
		ConsumerUnboundMethod<TestClassWithNormalizedMethods,String> unboundConsumerMethod_arg_string = TestClassWithNormalizedMethods::instanceConsumer_arg_String;
		VBiFunction<TestClassWithNormalizedMethods,String,VoidR> vFunction4 = makeVBiFunction(unboundConsumerMethod_arg_string);
		
		BiConsumerUnboundMethod<TestClassWithNormalizedMethods,String,String> unboundBiConsumerMethod_args_String_String = TestClassWithNormalizedMethods::instanceBiConsumer_args_String_String;
		VTriFunction<TestClassWithNormalizedMethods,String,String,VoidR> vFunction5 = makeVTriFunction(unboundBiConsumerMethod_args_String_String);
		
		FunctionUnboundMethod<TestClassWithNormalizedMethods,String,String> functionMethod_arg_String_return_String = TestClassWithNormalizedMethods::instanceFunction_arg_String_return_String;
		VBiFunction<TestClassWithNormalizedMethods,String,String> vFunction6 = makeVBiFunction(functionMethod_arg_String_return_String);
		
		BiFunctionUnboundMethod<TestClassWithNormalizedMethods,String,String,String> unboundBiFunctionMethod_args_String_String_return_String = TestClassWithNormalizedMethods::instanceBiFunction_args_String_String_return_String;
		VTriFunction<TestClassWithNormalizedMethods,String,String,String> vFunction7 = makeVTriFunction(unboundBiFunctionMethod_args_String_String_return_String);
	}

	@Test
	void test_makeVXFunctionUnboundMethod_from_fromUnboundInstanceMethod() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		{
		RunnableUnboundMethod<TestClassWithNormalizedMethods> unboundRunnableMethod = TestClassWithNormalizedMethods::instanceRunnable;
		VUnboundMethod<TestClassWithNormalizedMethods,VoidA,VoidR> unboundVFunctionMethod = makeVUnboundMethod(unboundRunnableMethod);
		Object res = unboundVFunctionMethod.applyWithClassInstanceAsFirstArg(instance);
		}
		
		{
		SupplierUnboundMethod<TestClassWithNormalizedMethods,String> unboundSupplierMethod_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		VUnboundMethod<TestClassWithNormalizedMethods,VoidA,String> vFunctionUnboundMethod = makeVUnboundMethod(unboundSupplierMethod_return_String);
		Object res = vFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(instance);
		}
		
		{
		CallableUnboundMethod<TestClassWithNormalizedMethods,String> unboundCallableMethod_return_String = TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String;
		VUnboundMethod<TestClassWithNormalizedMethods,VoidA,String> vFunctionUnboundMethod = makeVUnboundMethod(unboundCallableMethod_return_String);
		Object res = vFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(instance);
		}
		
		{
		ConsumerUnboundMethod<TestClassWithNormalizedMethods,String> unboundConsumerMethod_arg_string = TestClassWithNormalizedMethods::instanceConsumer_arg_String;
		VUnboundMethod<TestClassWithNormalizedMethods,String,VoidR> vFunctionUnboundMethod = makeVUnboundMethod(unboundConsumerMethod_arg_string);
		Object res = vFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(instance,"hello");
		}
		
		{
		BiConsumerUnboundMethod<TestClassWithNormalizedMethods,String,String> unboundBiConsumerMethod_args_String_String = TestClassWithNormalizedMethods::instanceBiConsumer_args_String_String;
		VBiUnboundMethod<TestClassWithNormalizedMethods,String,String,VoidR> vBiFunctionUnboundMethod = makeVBiUnboundMethod(unboundBiConsumerMethod_args_String_String);
		Object res = vBiFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(instance,"hello","hello again");
		}
		
		{
		FunctionUnboundMethod<TestClassWithNormalizedMethods,String,String> functionMethod_arg_String_return_String = TestClassWithNormalizedMethods::instanceFunction_arg_String_return_String;
		VUnboundMethod<TestClassWithNormalizedMethods,String,String> vFunctionUnboundMethod = makeVUnboundMethod(functionMethod_arg_String_return_String);
		Object res = vFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(instance,"hello");
		}

		{
		BiFunctionUnboundMethod<TestClassWithNormalizedMethods,String,String,String> unboundBiFunctionMethod_args_String_String_return_String = TestClassWithNormalizedMethods::instanceBiFunction_args_String_String_return_String;
		VBiUnboundMethod<TestClassWithNormalizedMethods,String,String,String> vBiFunctionUnboundMethod = makeVBiUnboundMethod(unboundBiFunctionMethod_args_String_String_return_String);
		Object res = vBiFunctionUnboundMethod.applyWithClassInstanceAsFirstArg(instance,"hello","hello again");
		}
	}



}
