package testHelp;

class TestClassWithNormalizedMethods {
	
	// main - clones from ConsoleTester.MainClass1
	
	public static void main(String [] args) // Does not read input
	{
		if (args == null)
		{
			System.out.println("Null args");
		}
		else
		{
			System.out.format("%d args\n",args.length);
			if (args.length > 2)
			{
				throw new IllegalArgumentException("Too many args");
			}
			for (int i = 0; i < args.length; i++)
			{
				System.out.format("arg[%d] \"%s\"\n",i,args[i]);
			}
		}
	}


	// static methods
	
	static void staticRunnable() {
		System.out.println("staticRunnable invoked");
	}
	
	static void staticRunnableThrowsException() {
		System.out.println("staticRunnableThrowsException about to throw exception");
		throw new RuntimeException("Exception from staticRunnableThrowsException");
	}
	
	static String staticSupplierOrCallable_return_String() {
		return "staticSupplierOrCallable invoked";
	}
	
	static void staticConsumer_arg_String(String s) {
		System.out.println("staticConsumer_arg_String invoked with arg " + s);
	}
	
	static void staticBiConsumer_args_String_String(String s1, String s2) {
		System.out.println("staticBiConsumer_args_String_String invoked with args " + s1 + " and " + s2);
	}
	
	static void staticTriConsumer_args_String_String_String(String s1, String s2, String s3) {
		System.out.println("staticBiConsumer_args_String_String invoked with args " + s1 + " and two other strings");
	}
	
	static String staticFunction_arg_String_return_String(String s) {
		return "staticFunction_arg_String_return_String invoked with arg " + s;
	}

	static String staticBiFunction_args_String_String_return_String(String s1, String s2) {
		return "staticBiFunction_args_String_String_return_String invoked with args " + s1 + " and " + s2;
	}

	static String staticTriFunction_args_String_String_String_return_String(String s1, String s2, String s3) {
		return "staticTriFunction_args_String_String_String_return_String invoked with args " + s1 + " and two other strings";
	}

	// instance methods
	
	void instanceRunnable() {
		System.out.println("instanceRunnable invoked");
	}
	
	void instanceRunnableThrowsException() {
		System.out.println("staticRunnableThrowsException about to throw exception");
		throw new RuntimeException("Exception from staticRunnableThrowsException");
	}
	
	String instanceSupplierOrCallable_return_String() {
		return "instanceSupplierOrCallable_return_String invoked";
	}

	void instanceConsumer_arg_String(String s) {
		System.out.println("instanceConsumer_arg_String invoked with arg " + s);
	}
	
	void instanceBiConsumer_args_String_String(String s1, String s2) {
		System.out.println("instanceBiConsumer_args_String_String invoked with args " + s1 + " and " + s2);
	}

	String instanceFunction_arg_String_return_String(String s) {
		return "instanceFunction_arg_String invoked with arg " + s;
	}
	
	String instanceBiFunction_args_String_String_return_String(String s1, String s2) {
		return "instanceBiFunction_args_String_String invoked with args " + s1 + " and " + s2;
	}
	
	// Generic
	
	static Object returnArgument(Object arg) // For comparison with next
	{
		return arg;
	}
	
	static <T> T genericReturnArgument(T arg)
	{
		return arg;
	}
	
}