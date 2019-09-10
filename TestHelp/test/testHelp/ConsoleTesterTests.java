package testHelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;

import org.junit.jupiter.api.Test;


public class ConsoleTesterTests
{
	static {
		ConsoleTester.setVerboseLevel(2);
	}
	
	static String input = "";
        static String [] noArgs = {};
        static String [] oneArg = {"arg"};
        static String [] twoArgs = {"arg1","arg2"};
        static String [] threeArgs = {"arg","arg2","arg3"}; // triggers exception
	static List<String []> argLists;

	static {
		argLists = new ArrayList<String []>();
	        argLists.add(null);
	        argLists.add(noArgs);
	        argLists.add(oneArg);
	        argLists.add(twoArgs);
	        argLists.add(threeArgs);
	}
	
	// =============================================================
	//
	// Tests with MainClass1 (non-interactive)
	//
	// =============================================================

	
	static class MainClass1 
	{
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
		
	}




	@Test
	void test_runMainClass_noGetOutputNoAssertions()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted(); // so testname appears before other output to System.out
		
		ConsoleTester.setDescription("desc (won't be printed since output info won't be printed)");

		for (String [] argList : argLists)
		{

			System.out.println("Calling main with argList " + Arrays.toString(argList));
			try
			{
				MainClass1.main(argList);
			}
			catch (Exception ex)
			{
				System.out.println("main threw exception: " + ex);
			}
			
		}
	}
	
	@Test
	void test_runMainClass_noGetOutputNoAssertions_printInfo()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted(); // so testname appears before other output to System.out
		
		ConsoleTester.setDescription("desc (should appear in printed output info)");
		
		for (String [] argList : argLists)
		{

			System.out.println("Calling main with argList " + Arrays.toString(argList));
			try
			{
				MainClass1.main(argList);
			}
			catch (Exception ex)
			{
				System.out.println("main threw exception: " + ex);
			}
			
			ConsoleTester.printInfoIfNotAlreadyPrinted();
			
		}
	}
	
	@Test
	void test_getOutput_ClassName_implicit_nullArgList_noDesc()
	{
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", input);
		verify.that(output).matches("Null args");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}

	@Test
	void test_getOutput_ClassName_implicit_nullArgList_desc()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted(); // so testname appears before other output to System.out
		
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", input, "testHelp.ConsoleTesterTests$MainClass1 + implicitNullArgList");
		verify.that(output).matches("Null args");
	}

	@Test
	void test_getOutput_ClassName_nullArgList_noDesc()
	{
		//String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", null, input); // The method getOutput(String, String, String[]) is ambiguous for the type ConsoleTester
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", (String [])null, input); // cast to resolve ambiguity
		verify.that(output).matches("Null args");

	}

	@Test
	void test_getOutput_ClassName_nullArgList_desc()
	{
		//String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", null, input,"testHelp.ConsoleTesterTests$MainClass1 + null)"); // The method getOutput(String, String, String[]) is ambiguous for the type ConsoleTester
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", (String [])null, input,"testHelp.ConsoleTesterTests$MainClass1 + (String [])null)"); // cast to resolve ambiguity
		verify.that(output).matches("Null args");

	}

	@Test
	void test_getOutput_ClassName_noArgs()
	{
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", noArgs, input);
		verify.that(output).matches("0 args");

	}
	
	@Test
	void test_getOutput_ClassName_twoArgs()
	{
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", twoArgs, input);
		verify.that(output).matches("2 args");

	}
	
	@Test
	void test_getOutput_ClassName_twoArgs_mainArgs_literal()
	{
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", new String[] {"arg1","arg2"},"main + new String[] {\"arg1\",\"arg2\"}");
		verify.that(output).matches("2 args");

	}

	@Test
	void test_getOutput_ClassName_threeArgs_exception()
	{
		//String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", threeArgs, input);
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", threeArgs, null);
		verify.exceptionThrown().withMessage("Too many args", CompareType.Substring);
		verify.info("SUCCESS");

	}
	
	// getoutput with main method reference arg

	@Test
	void test_getOutput_MethodReference_nullArgList()
	{
		String output = ConsoleTester.getOutput(MainClass1::main, null, input,"MainClass::main + null argList");
		verify.that(output).matches("Null args");

	}

	@Test
	void test_getOutput_MethodReference_noArgs()
	{
		String output = ConsoleTester.getOutput(MainClass1::main, noArgs, input,"MainClass::main + no args");
		verify.that(output).matches("0 args");

	}

	@Test
	void test_getOutput_MethodReference_twoArgs()
	{
		String output = ConsoleTester.getOutput(MainClass1::main, twoArgs, input, "MainClass::main + two args");
		verify.that(output).matches("2 args");
	}

	@Test
	void test_getOutput_MethodReference_threeArgs_exception()
	{
		String output = ConsoleTester.getOutput(MainClass1::main,threeArgs, input, "MainClass::main + three args -> exception");
		verify.exceptionThrown().withMessage("Too many args", CompareType.Substring);
		verify.info("SUCCESS");
		
	}
	
	// Unbundled getOutput components with direct main call 
	
	@Test
	void test_unbundledGetOutput_getOutputReset()
	{

		String output; 
		try
		{
			ConsoleTester.getOutputStart("","First call");
			MainClass1.main(null);
			output = ConsoleTester.getOutputFinish();
			ConsoleTester.getOutputReset();
			
			System.out.println("testClassName should not be reprinted here");
			
			ConsoleTester.getOutputStart("","Second call");
			MainClass1.main(null);
			output = ConsoleTester.getOutputFinish();
			ConsoleTester.getOutputReset();
			
		}
		catch (Throwable ex)
		{
			output = ConsoleTester.getOutputFromException(ex);
		}
		
		verify.that(output).matches("Null args");
		
	}
	
	@Test
	void test_unbundledGetOutput_nullArglist()
	{
		String output;
		try
		{
			ConsoleTester.getOutputStart("","MainClass1.main(null);");
			MainClass1.main(null);
			output = ConsoleTester.getOutputFinish();
		}
		catch (Throwable ex)
		{
			output = ConsoleTester.getOutputFromException(ex);
		}
		
		verify.that(output).matches("Null args");
		
	}

	@Test
	void test_unbundledGetOutput_noArgs()
	{
		String output;
		try
		{
			ConsoleTester.getOutputStart("","MainClass1.main(noArgs);");
			MainClass1.main(noArgs);
			output = ConsoleTester.getOutputFinish();
		}
		catch (Throwable ex)
		{
			output = ConsoleTester.getOutputFromException(ex);
		}
		
		verify.that(output).matches("0 args");
		
	}

	@Test
	void test_unbundledGetOutput_twoArgs()
	{
		String output;
		try
		{
			ConsoleTester.getOutputStart("","MainClass1.main(twoArgs);");
			MainClass1.main(twoArgs);
			output = ConsoleTester.getOutputFinish();
		}
		catch (Throwable ex)
		{
			output = ConsoleTester.getOutputFromException(ex);
		}
		
		verify.that(output).matches("2 args");
		
	}

	@Test
	void test_unbundledGetOutput_threeArgs_exception()
	{
		String output;
		try
		{
			ConsoleTester.getOutputStart("","MainClass1.main(threeArgs);");
			MainClass1.main(threeArgs);
			output = ConsoleTester.getOutputFinish();
		}
		catch (Throwable ex)
		{
			output = ConsoleTester.getOutputFromException(ex);
		}
		
		verify.exceptionThrown().withMessage("Too many args", CompareType.Substring);
		verify.info("SUCCESS");
		
	}
	
	// =============================================================
	//
	// Tests with MainClass2 (interactive)
	//
	// =============================================================
	
	static class MainClass2
	{
		public static void main(String[] args)
		{
			Scanner scanner = new Scanner(System.in);

			while (true)
			{
				System.out.println("A or B or return to quit");
				String nextLine = scanner.nextLine();
				if (nextLine.equals(""))
				{
					System.out.println("Bye");
					return;
				}
				if (nextLine.equals("A") || nextLine.equals("B"))
				{
					System.out.println("You entered " + nextLine);
				} else
				{
					throw new IllegalArgumentException(
							"Bad input: " + nextLine);

				}
			}
		}

	}
		
	@Test
	void test_getOutput_MethodReference_input_good()
	{
		String output = ConsoleTester.getOutput(MainClass2::main,null,"A\n\n","MainClass2::main");
		verify.that(output).matches("You entered A");
	}

	@Test
	void test_getOutput_MethodReference_input_bad_exception()
	{
		String output = ConsoleTester.getOutput(MainClass2::main,null,"a\n\n","MainClass2::main");
		verify.exceptionThrown().withMessage("Bad input", CompareType.Substring);
		ConsoleTester.printInfoIfNotAlreadyPrinted();
		verify.info("SUCCESS");
	}
	
	@Test
	void test_getOutput_MethodReference_input_bad_exception_unexpected()
	{
		try
		{
        		String output = ConsoleTester.getOutput(MainClass2::main,null,"a\n\n","MainClass2::main");
        		verify.exceptionThrown().withMessage("Bad input", CompareType.Substring);
        		verify.that(output).matches("You entered A");
		}
		catch (java.lang.AssertionError ex)
		{
			System.out.println("Caught exception:");
			ConsoleTester.printChunkedStackTrace(ex);
			verify.info("SUCCESS");
		}
	}
	
}
