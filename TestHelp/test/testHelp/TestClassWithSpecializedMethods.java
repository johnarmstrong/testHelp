package testHelp;

class TestClassWithSpecializedMethods {
	
	// static methods
	
	static Integer staticSupplierOrCallable_return_Integer() {
		return 10;
	}
	
	static int staticSupplierOrCallable_return_int() {
		return 10;
	}
	
	static Integer staticFunction_arg_Integer_return_Integer(Integer i) {
		return i;
	}

	static int staticFunction_arg_Integer_return_int(Integer i) {
		return i;
	}
	
	static Integer staticFunction_arg_int_return_Integer(int i) {
		return i;
	}

	static int staticFunction_arg_int_return_int(int i) {
		return i;
	}
	
	static Boolean staticFunction_arg1_Integer_arg2_Integer_return_Boolean(Integer arg1, Integer arg2) {
		return arg1.equals(arg2);
	}
	
	static boolean staticFunction_arg1_Integer_arg2_Integer_return_boolean(Integer arg1, Integer arg2) {
		return arg1.equals(arg2);
	}
	
	static boolean staticFunction_arg1_int_arg2_int_return_boolean(int arg1, int arg2) {
		return arg1 == arg2;
	}
	
	static boolean staticFunction_arg1_Integer_arg2_int_return_boolean(Integer arg1, int arg2) {
		return arg1 == arg2; // arg1 should be auto-unboxed
	}
	
	
	// instance methods
	
	Integer instanceSupplierOrCallable_return_Integer() {
		return 10;
	}
	
	int instanceSupplierOrCallable_return_int() {
		return 10;
	}
	
	Integer instanceFunction_arg_Integer_return_Integer(Integer i) {
		return i;
	}

	int instanceFunction_arg_Integer_return_int(Integer i) {
		return i;
	}
	
	Integer instanceFunction_arg_int_return_Integer(int i) {
		return i;
	}

	int instanceFunction_arg_int_return_int(int i) {
		return i;
	}
	
	boolean instanceFunction_arg1_Integer_arg2_Integer_return_boolean(Integer arg1, Integer arg2) {
		return arg1.equals(arg2);
	}
	
	boolean instanceFunction_arg1_int_arg2_int_return_boolean(int arg1, int arg2) {
		return arg1 == arg2;
	}
	
	boolean instanceFunction_arg1_Integer_arg2_int_return_boolean(Integer arg1, int arg2) {
		return arg1 == arg2; // arg1 should be auto-unboxed
	}
	
	// Methods with array arguments - not interconvertible but both OK
	
	static Integer staticFunction_arg_Integer_array_return_Integer(Integer [] arg) {
		return arg.length;
	}
	
	static Integer staticFunction_arg_Integer_array_return_Integer(int [] arg) {
		return arg.length;
	}
	




 
}