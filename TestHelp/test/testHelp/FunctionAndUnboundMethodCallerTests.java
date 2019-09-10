package testHelp;

import org.junit.jupiter.api.Test;

import static testHelp.VFunctionsAndVUnboundMethods.*;

import static testHelp.NormalizedFunctionCallers.*; 

import static testHelp.NormalizedUnboundMethodCallers.*; 

/**
 * @author John Armstrong
 */
class FunctionAndUnboundMethodCallerTests { // Currently tests void noArg functions and methods only - all effectively normalized
	
	static {
		ConsoleTester.setVerboseLevel(2);
	}

	
	// Basic tests with static method - noArg
	
	@Test
	void test_static_method_getValueFromNoArgFunction_value_expected() {
		Object value = getReturnValueFromNoArgNormFunction(TestClassWithNormalizedMethods::staticRunnable,"TestClass::staticRunnable");
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}

	@Test
	void test_static_method_getValueFromNoArgFunction_value_expected_description() {
		ConsoleTester.setDescription("Call getValueFromNoArgFunction on a static method");
		Object value = getReturnValueFromNoArgNormFunction(TestClassWithNormalizedMethods::staticRunnable,"TestClass::staticRunnable");
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}

	@Test
	void test_static_method_getExceptionFromNoArgFunction_exception_expected() {
		Object value = getExceptionFromNoArgNormFunction(TestClassWithNormalizedMethods::staticRunnableThrowsException,"TestClass::staticRunnableThrowsException");
		verify.thatObject(value).isInstanceOf(Throwable.class);
		
	}

	@Test
	void test_static_method_getValueFromNoArgFunction_exception_unexpected() {
		try {
		Object value = getReturnValueFromNoArgNormFunction(TestClassWithNormalizedMethods::staticRunnableThrowsException,"TestClass::staticRunnableThrowsException");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}
	@Test
	void test_static_method_getValueFromNoArgFunction_exception_unexpected_description() {
		ConsoleTester.setDescription("Call getValueFromNoArgFunction on a static method");
		try {
		Object value = getReturnValueFromNoArgNormFunction(TestClassWithNormalizedMethods::staticRunnableThrowsException,"TestClass::staticRunnableThrowsException");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}

	@Test
	void test_static_method__getExceptionFromNoArgFunction_value_unexpected() {
		try {
			Object value = getExceptionFromNoArgNormFunction(TestClassWithNormalizedMethods::staticRunnable,"TestClass::staticRunnable");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}
	
	// Basic tests with bound instance method - noArg

	@Test
	void test_bound_method_getValueFromNoArgFunction_value_expected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		Object value = getReturnValueFromNoArgNormFunction(instance::instanceRunnable,"instance::instanceRunnable");
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}

	@Test
	void test_bound_method_getExceptionFromNoArgFunction_exception_expected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		Object value = getExceptionFromNoArgNormFunction(instance::instanceRunnableThrowsException,"instance::instanceRunnableThrowsException");
		verify.thatObject(value).isInstanceOf(Throwable.class);
		
	}

	@Test
	void test_bound_instance_method_getValueFromNoArgFunction_exception_unexpected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		try {
		Object value = getReturnValueFromNoArgNormFunction(instance::instanceRunnableThrowsException,"TestClass::instanceRunnableThrowsException");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}

	@Test
	void test_bound_instance_method_getExceptionFromNoArgFunction_value_unexpected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		try {
			Object value = getExceptionFromNoArgNormFunction(instance::instanceRunnable,"instance::staticRunnable");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}
	
	// Basic tests with unbound instance method - noARg instance method represented as oneArg function
	
	@Test
	void test_unbound_instance_method_getValueFromNoArgFunction_value_expected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		Object value = getReturnValueFromOneArgNormFunction(TestClassWithNormalizedMethods::instanceRunnable,instance,"TestClass::instanceRunnable");
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}

	@Test
	void test_unbound_instance_method_getExceptionFromOneArgFunction_exception_expected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		Object value = getExceptionFromOneArgNormFunction(TestClassWithNormalizedMethods::instanceRunnableThrowsException,"TestClass::instanceRunnableThrowsException",instance);
		verify.thatObject(value).isInstanceOf(Throwable.class);
		
	}

	@Test
	void test_unbound_instance_method_getValueFromOneArgFunction_exception_unexpected() {
		try {
			TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		Object value = getReturnValueFromOneArgNormFunction(TestClassWithNormalizedMethods::instanceRunnableThrowsException,instance,"TestClass::instanceRunnableThrowsException");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}

	@Test
	void test_unbound_instance_method_getExceptionFromOneArgFunction_value_unexpected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		try {
			Object value = getExceptionFromOneArgNormFunction(TestClassWithNormalizedMethods::instanceRunnable,"TestClass::instanceRunnable",instance);
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}

	// Basic tests with unbound instance method - noArg instance method represented as noArg UnboundMethod
	
	@Test
	void test_unbound_instance_method_getValueFromNoArgUnboundMethod_value_expected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		Object value = getReturnValueFromNoArgNormUnboundMethod(TestClassWithNormalizedMethods::instanceRunnable,instance,"TestClass::instanceRunnable");
		verify.thatObject(value).isEqualTo(voidR,"voidR");
		
	}

	@Test
	void test_unbound_instance_method_getExceptionFromNoArgUnboundMethod_exception_expected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		Object value = getExceptionFromNoArgNormUnboundMethod(TestClassWithNormalizedMethods::instanceRunnableThrowsException,instance,"TestClass::instanceRunnableThrowsException");
		verify.thatObject(value).isInstanceOf(Throwable.class);
		
	}

	@Test
	void test_unbound_instance_method_getValueFromNoArgUnboundMethod_exception_unexpected() {
		try {
			TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
			Object value = getReturnValueFromNoArgNormUnboundMethod(TestClassWithNormalizedMethods::instanceRunnableThrowsException,instance,"TestClass::instanceRunnableThrowsException");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}

	@Test
	void test_unbound_instance_method_getExceptionFromNoArgUnboundMethod_value_unexpected() {
		TestClassWithNormalizedMethods instance = new TestClassWithNormalizedMethods();
		try {
			Object value = getExceptionFromNoArgNormUnboundMethod(TestClassWithNormalizedMethods::instanceRunnable,instance,"TestClass::instanceRunnable");
			verify.fail("Should not get here (expected exception)");
		} catch (java.lang.AssertionError ex) {
			verify.infoF("Success: assertion error thrown: %s",ex);
		}
		
	}
	
}
