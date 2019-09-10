package testHelp;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static testHelp.NormalizedFunctionCallers.*; 
import static testHelp.NormalizedUnboundMethodCallers.*;
import static testHelp.UnboundMethodInterfaces.*;

/**
 * @author John Armstrong
 */
public class FunctionAndUnboundMethodCallerSpecializedTests {

	static {
		ConsoleTester.setVerboseLevel(2);
	}
	
	// =================================================
	//
	// Supplier - need casts to disambiguate vs Callable
	//
	// ==================================================


	// Basic tests with Supplier<Integer> and IntSupplier must be cast (or normalized) to avoid ambiguity with Callable<Integer> and IntCallable
	
	// static method

	@Test
	void test_static_method_getValueFromNoArgFunction_Supplier_cast() {

		Object value = getReturnValueFromNoArgNormFunction((Supplier<?>)TestClassWithSpecializedMethods::staticSupplierOrCallable_return_Integer, "TestClass::staticSupplierOrCallable_return_Integer");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	@Test
	void test_static_method_getValueFromNoArgFunction_IntSupplier_cast() {
		Object value = getReturnValueFromNoArgNormFunction((Supplier<?>)TestClassWithSpecializedMethods::staticSupplierOrCallable_return_int, "TestClass::staticSupplierOrCallable_return_int");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	// bound instance method
	
	@Test
	void test_bound_method_getValueFromNoArgFunction_Supplier_cast() {
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		Object value = getReturnValueFromNoArgNormFunction((Supplier<?>)instance::instanceSupplierOrCallable_return_Integer, "TestClass::instanceSupplierOrCallable_return_Integer");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	@Test
	void test_bound_method_getValueFromNoArgFunction_IntSupplier_cast() {  

		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		Object value = getReturnValueFromNoArgNormFunction((Supplier<?>)instance::instanceSupplierOrCallable_return_int,"instance::instanceSupplierOrCallable_return_int");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	// unbound instance method
	
	@Test
	void test_unbbound_method_getValueFromNoArgFunction_Supplier_cast() { 
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		// Need to specify class type in cast
		Object value = getReturnValueFromNoArgNormUnboundMethod((SupplierUnboundMethod<TestClassWithSpecializedMethods,?>)TestClassWithSpecializedMethods::instanceSupplierOrCallable_return_Integer,instance,"TestClassWithSpecializedMethods::instanceSupplierOrCallable_return_Integer");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	
	@Test
	void test_unbbound_method_getValueFromNoArgFunction_IntSupplier_cast() { 
		
		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		// Need to specify class type in cast
		Object value = getReturnValueFromNoArgNormUnboundMethod((SupplierUnboundMethod<TestClassWithSpecializedMethods,?>)TestClassWithSpecializedMethods::instanceSupplierOrCallable_return_int,instance,"TestClassWithSpecializedMethods::instanceSupplierOrCallable_return_int");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}


	// =================================================
	//
	// Function
	//
	// ==================================================


	// Function<Integer,Integer>, *Function<Integer,int> = ToIntFunction, *Function<int,int> = IntUnaryPredicate
	
	// static method

	@Test
	void test_static_method_getValueFromOneArgFunction_Function() {

		Object value = getReturnValueFromOneArgNormFunction(TestClassWithSpecializedMethods::staticFunction_arg_Integer_return_Integer, 10, "TestClass::staticFunction_arg_Integer_return_Integer");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}

	@Test
	void test_static_method_getValueFromOneArgFunction_IntFunction() {
		Object value = getReturnValueFromOneArgNormFunction(TestClassWithSpecializedMethods::staticFunction_arg_int_return_Integer, 10, "TestClass::staticFunction_arg_int_return_Integer");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	@Test
	void test_static_method_getValueFromOneArgFunction_ToIntFunction() {
		Object value = getReturnValueFromOneArgNormFunction(TestClassWithSpecializedMethods::staticFunction_arg_Integer_return_int, 10, "TestClass::staticFunction_arg_Integer_return_int");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	@Test
	void test_static_method_getValueFromOneArgFunction_IntUnaryOperator() {
		Object value = getReturnValueFromOneArgNormFunction(TestClassWithSpecializedMethods::staticFunction_arg_int_return_int, 10, "TestClass::staticFunction_arg_int_return_int");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}
	
	// unbound instance method
	
	@Test
	void test_unbound_method_getValueFromOneArgFunction_IntFunction() {  

		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		Object value = getReturnValueFromOneArgUnboundMethod(TestClassWithSpecializedMethods::instanceFunction_arg_int_return_Integer,instance,10, "instanceFunction_arg_int_return_Integer");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}

	@Test
	void test_unbound_method_getValueFromOneArgFunction_ToIntFunction() {  

		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		Object value = getReturnValueFromOneArgUnboundMethod(TestClassWithSpecializedMethods::instanceFunction_arg_Integer_return_int,instance,10, "TestClass::instanceFunction_arg_Integer_return_int");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}

	@Test
	void test_unbound_method_getValueFromOneArgFunction_IntUnaryOperator() {  

		TestClassWithSpecializedMethods instance = new TestClassWithSpecializedMethods();

		Object value = getReturnValueFromOneArgUnboundMethod(TestClassWithSpecializedMethods::instanceFunction_arg_int_return_int,instance,10, "TestClass::instanceFunction_arg_int_return_int");
		verify.thatObject(value).isEqualTo(new Integer(10),"Integer(10)");

	}

        // =================================================
        //
        // BiFunction
        //
        // ==================================================

	// static method
	
	//static Boolean staticFunction_arg1_Integer_arg2_Integer_return_Boolean(Integer arg1, Integer arg2)
	//static boolean staticFunction_arg1_Integer_arg2_Integer_return_boolean(Integer arg1, Integer arg2)
	//static boolean staticFunction_arg1_int_arg2_int_return_boolean(int arg1, int arg2)
	//static boolean staticFunction_arg1_Integer_arg2_int_return_boolean(Integer arg1, int arg2)


	@Test
	void test_static_method_getValueFromOneArgFunction_BiFunction() {
		Object value = getReturnValueFromNormBiFunction(TestClassWithSpecializedMethods::staticFunction_arg1_Integer_arg2_Integer_return_Boolean, 10,10, "staticFunction_arg1_Integer_arg2_Integer_return_Boolean");
		verify.thatObject(value).isEqualTo(new Boolean(true),"Boolean true");

	}
	
	@Test
	void test_static_method_getValueFromOneArgFunction_BiPredicate() {
		Object value = getReturnValueFromNormBiFunction(TestClassWithSpecializedMethods::staticFunction_arg1_Integer_arg2_Integer_return_boolean, 10,10, "staticFunction_arg1_Integer_arg2_Integer_return_boolean");
		verify.thatObject(value).isEqualTo(new Boolean(true),"Boolean true");

	}
	
	@Test
	void test_static_method_getValueFromOneArgFunction_BiPredicateSpecialized() {
		Object value = getReturnValueFromNormBiFunction(TestClassWithSpecializedMethods::staticFunction_arg1_int_arg2_int_return_boolean ,10,10, "staticFunction_arg1_int_arg2_int_return_boolean");
		verify.thatObject(value).isEqualTo(new Boolean(true),"Boolean true");

	}

	@Test
	void test_static_method_getValueFromOneArgFunction_BiPredicatePartluSpecialized() {
		Object value = getReturnValueFromNormBiFunction(TestClassWithSpecializedMethods::staticFunction_arg1_Integer_arg2_int_return_boolean ,10,10, "staticFunction_arg1_Integer_arg2_int_return_boolean");
		verify.thatObject(value).isEqualTo(new Boolean(true),"Boolean true");

	}


	// Unbound instance method
	
	//static Boolean instanceFunction_arg1_Integer_arg2_Integer_return_Boolean(Integer arg1, Integer arg2)
	//static boolean instanceFunction_arg1_Integer_arg2_Integer_return_boolean(Integer arg1, Integer arg2)
	//static boolean instanceFunction_arg1_int_arg2_int_return_boolean(int arg1, int arg2)
	//static boolean instanceFunction_arg1_Integer_arg2_int_return_boolean(Integer arg1, int arg2)
	
}
