package testHelp;

import static testHelp.VFunctionsAndVUnboundMethods.*;

import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static testHelp.UnboundMethodInterfaces.*;


import org.junit.jupiter.api.Test;


/**
 * @author John Armstrong
 */
class ConsoleTesterVFunctionAndVUnboundMethodTests { // Currently tests void noArg functions and methods only - all effectively normalized

	static {
		ConsoleTester.setVerboseLevel(2);
	}
	
	static Integer Ten = 10;
	
	// =====================================================
	//
	// 	Runnable
	//
	// =====================================================

	// static method
	

	@Test
	void test_getOutputFromVFunction_static_Runnable() {
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithNormalizedMethods::staticRunnable),null,"","TestClass::staticRunnable");
		verify.that(output,"output").isNotNull();
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}

	@Test
	void test_getOutputFromVFunction_static_Runnable_exception() {
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithNormalizedMethods::staticRunnableThrowsException),null,"","TestClass::staticRunnableThrowsException");
		verify.exceptionThrown();
		verify.info("success");
		
	}
	
	// bound instance method
	
	@Test
	void test_getOutputFromVFunction_bound_Runnable() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(instance::instanceRunnable),null,"","instance::instanceRunnable");
		verify.that(output,"output").isNotNull();
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}
	
	// unbound static method - treat as simple (non-class) method 
	
	@Test
	void test_getOutputFromVFunction_unbound_instance_Runnable() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithNormalizedMethods::instanceRunnable),instance,"","instance::instanceRunnable");
		verify.that(output,"output").isNotNull();
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}
	
	// unbound instance method - treat was UnboundMethod

	@Test
	void test_getOutputFromVUnboundMethod_unbound_instance_Runnable() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		// public static <C,A,R> String getOutputFromVUnboundMethod(VUnboundMethod<C,A,R> vUnboundMethod, C instance, A arg,  String input, String ...descArg)
		
		String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod(TestClassWithNormalizedMethods::instanceRunnable),instance,null,"","instance::instanceRunnable");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}
	
	// =====================================================
	//
	// 	Supplier and specialization IntSupplier
	//
	// =====================================================

	// static method
	

	@Test
	void test_getOutputFromVFunction_static_Supplier() {
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Supplier<?>)TestClassWithNormalizedMethods::staticSupplierOrCallable_return_String),null,"","TestClass::staticSupplierOrCallable_return_String");
		verify.that(output,"output").isNotNull();
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo("staticSupplierOrCallable invoked");
		
	}

	
	// bound instance method
	
	@Test
	void test_getOutputFromVFunction_bound_Supplier() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Supplier<?>)instance::instanceSupplierOrCallable_return_String),null,"","instance::instanceSupplierOrCallable_return_String");
		verify.that(output,"output").isNotNull();
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo("instanceSupplierOrCallable_return_String invoked");
		
	}
	
	// unbound static method - treat as simple (non-class) method 
	
	@Test
	void test_getOutputFromVFunction_unbound_instance_Supplier() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String),instance,"","instance::instanceSupplierOrCallable_return_String");
		verify.that(output,"output").isNotNull();
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo("instanceSupplierOrCallable_return_String invoked");
		
	}
	
	// unbound instance method - treat was UnboundMethod

	@Test
	void test_getOutputFromVUnboundMethod_unbound_instance_Supplier() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		// public static <C,A,R> String getOutputFromVUnboundMethod(VUnboundMethod<C,A,R> vUnboundMethod, C instance, A arg,  String input, String ...descArg)
		
		String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod((SupplierUnboundMethod<TestClassWithNormalizedMethods,?>)TestClassWithNormalizedMethods::instanceSupplierOrCallable_return_String),instance,null,"","instance::instanceSupplierOrCallable_return_String");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value).isEqualTo("instanceSupplierOrCallable_return_String invoked");
		
	}



	// =====================================================
	//
	// 	Function and specializations IntFunction, ToIntFunction and IntUnaryOperator
	//
	// =====================================================	

	// static method
	
	@Test
	void test_getOutputFromVFunction_static_Function() {
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithNormalizedMethods::staticFunction_arg_String_return_String),"ARG","","TestClass::staticFunction_arg_String_return_String");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(String.class, "String");
		verify.that((String)value).matches("ARG$", "ARG$");
		
	}
	
	@Test
	void test_getOutputFromVFunction_static_method_IntFunction() {
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithSpecializedMethods::staticFunction_arg_int_return_Integer),10,"","TestClass::staticFunction_arg_int_return_Integer");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}

	@Test
	void test_getOutputFromVFunction_specialized_static_ToIntFunction() {
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithSpecializedMethods::staticFunction_arg_Integer_return_int),Ten,"","TestClass::staticFunction_arg_Integer_return_int");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}
	
	@Test
	void test_getOutputFromVFunction_specialized_static_method_IntUnaryOperator() {
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithSpecializedMethods::staticFunction_arg_int_return_int),10,"","TestClass::staticFunction_arg_int_return_int");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}
	
	// bound instance method

	@Test
	void test_getOutputFromVFunction_bound_Function() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(instance::instanceFunction_arg_String_return_String),"ARG","","instance::instanceFunction_arg_String_return_String");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(String.class, "String");
		verify.that((String)value).matches("ARG$", "ARG$");
		
	}
	
	@Test
	void test_getOutputFromVFunction_bound_IntFunction() {
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(instance::instanceFunction_arg_int_return_Integer),10,"","instance::instanceFunction_arg_int_return_Integer");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}

	@Test
	void test_getOutputFromVFunction_bound_ToIntFunction() {
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();		
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(instance::instanceFunction_arg_Integer_return_int),Ten,"","instance::instanceFunction_arg_Integer_return_int");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}


	@Test
	void test_getOutputFromVFunction_bound_IntUnarytOperator() {
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();		
		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(instance::instanceFunction_arg_int_return_int),Ten,"","instance::instanceFunction_arg_int_return_int");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}


	
	// unbound instance method - treat as simple (non-class) method -  - generic (non-specialized) only
	
	@Test
	void test_getOutputFromVFunction_unbound_IntFunction() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		String output = ConsoleTester.getOutputFromVBiFunction(makeVBiFunction(TestClassWithNormalizedMethods::instanceFunction_arg_String_return_String),instance,"ARG","","instance::instanceRunnable");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(String.class, "String");
		verify.that((String)value).matches("ARG$", "ARG$");
		
	}
	

	// unbound instance method - treat as unbound method
	
	@Test
	void test_getOutputFromVUnboundMethod_unbound_Function() {
		
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		
		// public static <C,A,R> String getOutputFromVUnboundMethod(VUnboundMethod<C,A,R> vUnboundMethod, C instance, A arg,  String input, String ...descArg)

		String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod(TestClassWithNormalizedMethods::instanceFunction_arg_String_return_String),instance,"ARG","","TestClass::instanceFunction_arg_String_return_String");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(String.class, "String");
		verify.that((String)value).matches("ARG$", "ARG$");
		
	}
	
	@Test
	void test_getOutputFromVUnboundMethod_unbound_IntFunction() {
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();
		
		String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod(TestClassWithSpecializedMethods::instanceFunction_arg_int_return_Integer),instance,10,"","instance::instanceFunction_arg_int_return_Integer");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}
	

	@Test
	void test_getOutputFromVFunction_specialized_unbound_ToIntFunction() {
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();		
		
		String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod(TestClassWithSpecializedMethods::instanceFunction_arg_Integer_return_int),instance,10,"","instance::instanceFunction_arg_Integer_return_int");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
	
	}


	@Test
	void test_getOutputFromVFunction_specialized_unbound_IntUnaryOperator() {
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod(TestClassWithSpecializedMethods::instanceFunction_arg_int_return_int),instance,10,"","TestClassWithSpecializedMethods::instanceFunction_arg_int_return_int");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isInstanceOf(Integer.class, "Integer");
		verify.that((Integer)value).equals(Ten);
		
	}
	
	// =====================================================
	//
	// 	Consumer and ConsumerUnboundMethod - println
	//
	// =====================================================
	
	@Test
	void test_getOutputFromVFunction_instance_println_with_System_out()
	{

		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Consumer<Object>)System.out::println),"Hello","","System.out::println + \"hello\"");
		verify.that(output,"output").isEqualTo("");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isEqualTo(voidR, "voidR");
	}


	@Test
	void test_getOutputFromVFunction_instance_println_with_ConsoleTester_systemOut()
	{ 
		PrintStream systemOut = ConsoleTester.getSystemOut(); 
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Consumer<Object>)systemOut::println),"Hello","","systemOut::println + \"hello\"");
		verify.that(output,"output").isEqualTo("Hello");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isEqualTo(voidR, "voidR");
	}

	@Test
	void test_getOutputFromVFunction_unbound_println_with_ConsoleTester_systemOut()
	{ 
		PrintStream systemOut = ConsoleTester.getSystemOut(); 
		String output = ConsoleTester.getOutputFromVUnboundMethod(makeVUnboundMethod((ConsumerUnboundMethod<PrintStream,Object>)PrintStream::println),systemOut,"Hello","","systemOut::println + \"hello\"");
		verify.that(output,"output").isEqualTo("Hello");
		Object value = ConsoleTester.getValueFromInfo();
		verify.thatObject(value, "return value").isEqualTo(voidR, "voidR");
	}



}