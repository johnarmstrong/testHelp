package testHelp;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static testHelp.VFunctionsAndVUnboundMethods.*;

import org.junit.jupiter.api.Test;

class SampleOutputInfoPrintingTests
{
	static {
		ConsoleTester.setVerboseLevel(2);
	}
	
	// ======================================
	//
	//	testClassName and testName
	//
	// ======================================

	@Test
	void test_printTestClassName()
	{
		ConsoleTester.printTestClassNameIfNotAlreadyPrinted();
	}

	@Test
	void test_printTestName()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();
	}
	
	// ======================================
	//
	//	info
	//
	// ======================================

	@Test
	void test_printInfoIfNotAlreadyPrinted_minimalContent()
	{
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}

	@Test
	void test_printInfoIfNotAlreadyPrinted__minimalContenDescrption()
	{
		ConsoleTester.setDescription("Description set by ConsoleTester.setDescription");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_success()
	{
		String output = ConsoleTester.getOutput(TestClassWithNormalizedMethods::main,new String[] {"hello","goodbye"},"","main called with two args");
		ConsoleTester.printInfoIfNotAlreadyPrinted();

	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_exception()
	{
		String output = ConsoleTester.getOutput(TestClassWithNormalizedMethods::main,new String[] {"hello","goodbye","hello again"},"","main called with three args - throws exception");
		ConsoleTester.printInfoIfNotAlreadyPrinted();

	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_exception_caught()
	{
		Consumer<String[]> wrappedMain =
			(String[] args) -> {
				try
				{
					TestClassWithNormalizedMethods.main(args);
				}
				catch (Throwable ex)
				{
					ConsoleTester.getSystemOut().println("Caught exception in wrapper");
					ex.printStackTrace();
				}
			};
		String output = ConsoleTester.getOutput(wrappedMain,new String[] {"hello","goodbye","hello again"},"","main called with three args - throws exception - caught by wrapper");
		ConsoleTester.printInfoIfNotAlreadyPrinted();

	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_returnObject_String()
	{
		// Following works but needs cast for disambiguation
		//String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Function<String,String>)TestClassWithNormalizedMethods::genericReturnArgument),"String object","","TestClassWithNormalizedMethods::genericReturnArgument with String arg");
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithNormalizedMethods::returnArgument),"String object","","TestClassWithNormalizedMethods::returnArgument with String arg");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_returnObject_Integer()
	{		
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction(TestClassWithNormalizedMethods::returnArgument),42,"","TestClassWithNormalizedMethods::returnArgument with Integer arg");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	// Call interactive method through main wrapper
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_interactive_sumNumbers_in_main_clean()
	{
		String output = ConsoleTester.getOutput(TestClassWithInteractiveMethods::main,null,
				"1\n2\n3\n=\n",
				"TestClassWithInteractiveMethods::main - clean");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_interactive_sumNumbers_in_main_dirty()
	{
		String output = ConsoleTester.getOutput(TestClassWithInteractiveMethods::main,null,
				"1\n2\nr\n3\n\nadd\n=\n",
				"TestClassWithInteractiveMethods::main - dirty input");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_interactive_sumNumbers_in_main_dirty_exception()
	{
		String output = ConsoleTester.getOutput(TestClassWithInteractiveMethods::main,null,
				"1\n2\nr\n3\n\naverage\n~\n",
				"TestClassWithInteractiveMethods::main - input triggers exception");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	// Call interactive  method directly
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_interactive_sumNumbers_clean()
	{
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Supplier<Integer>)TestClassWithInteractiveMethods::sumNumbersFromInput),null,
				"1\n2\n3\n=\n",
				"TestClassWithInteractiveMethods::sumNumbersFromInput - clean input");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_interactive_sumNumbers_dirty()
	{
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Supplier<Integer>)TestClassWithInteractiveMethods::sumNumbersFromInput),null,
				"1\n2\nr\n3\n\nadd\n=\n",
				"TestClassWithInteractiveMethods::sumNumbersFromInput - dirty input");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	

	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_interactive_sumNumbers_clean_exception()
	{
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Supplier<Integer>)TestClassWithInteractiveMethods::sumNumbersFromInput),null,
				"1\n2\n3\n~\n",
				"TestClassWithInteractiveMethods::sumNumbersFromInput - input triggers exception");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	

	@Test
	void test_printInfoIfNotAlreadyPrinted_getOutput_interactive_sumNumbers_dirty_exception()
	{
		String output = ConsoleTester.getOutputFromVFunction(makeVFunction((Supplier<Integer>)TestClassWithInteractiveMethods::sumNumbersFromInput),null,
				"1\n2\nr\n3\n\naverage\n~\n",
				"TestClassWithInteractiveMethods::sumNumbersFromInput - input triggers exception");
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	

	@Test
	void test_getOutput_ClassName_threeArgs_exception_truncation_after_target_caller()
	{
		//String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", threeArgs, input);
		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", new String[] {"arg1","arg2","arg3"}, null);
		verify.exceptionThrown().withMessage("Too many args", CompareType.Substring);
		verify.info("SUCCESS");

	}

	@Test
	void test_getOutput_ClassName_threeArgs_exception_truncation_before_target_caller()
	{
		int savedDefaultMaxLinesPerChunk = ErrorOutputChunker.getDefaultMaxLinesPerChunk();
		try
		{
        		ErrorOutputChunker.setDefaultMaxLinesPerChunk(6);
        		//String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", threeArgs, input);
        		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", new String[] {"arg1","arg2","arg3"}, null);
        		verify.exceptionThrown().withMessage("Too many args", CompareType.Substring);
        		verify.info("SUCCESS");
		}
		finally
		{
			ErrorOutputChunker.setDefaultMaxLinesPerChunk(savedDefaultMaxLinesPerChunk);
		}

	}

	
	@Test
	void test_getOutput_ClassName_threeArgs_exception_truncation_no_caller()
	{
		
		int savedDefaultMaxLinesPerChunk = ErrorOutputChunker.getDefaultMaxLinesPerChunk();
		try
		{
			ErrorOutputChunker.setDefaultMaxLinesPerChunk(16);
        		//String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", threeArgs, input);
        		String output = ConsoleTester.getOutput("testHelp.ConsoleTesterTests$MainClass1", new String[] {"arg1","arg2","arg3"}, null);
        		verify.exceptionThrown().withMessage("Too many args", CompareType.Substring);
        		verify.info("SUCCESS");
        		Throwable ex = ConsoleTester.getExceptionFromInfo();
        		System.out.println("\nPrinting exception from getOutput:\n");
        		ErrorOutputChunker.printChunkedStackTrace(ex, System.out, null);
		}
		finally
		{
			ErrorOutputChunker.setDefaultMaxLinesPerChunk(savedDefaultMaxLinesPerChunk);
		}


	}

	

	
	// ======================================
	//
	//	Messages
	//
	// ======================================
	
	@Test
	void test_printInfoMessage_AssertionTools()
	{
		AssertionTools.info("Info message printed with AssertionTools.info");
	}

	@Test
	void test_printInfoMessage_verify()
	{
		verify.info("Info message printed with verify.info");
	}

	@Test
	void test_printWarningMessage_AssertionTools()
	{
		AssertionTools.warning("Warning message printed with AssertionTools.warn");
	}

	@Test
	void test_printWarningMessage_verify()
	{
		verify.warning("Warning message printed with verify.info");
	}

	@Test
	void test_printFailMessage_AssertionTools()
	{
		try
		{
			verify.fail("Fail message printed with AssertionTools.fail");
		}
		catch (java.lang.AssertionError ex)
		{
			// Expected
		}
	}

	@Test
	void test_printFailMessage_verify()
	{
		try
		{
			AssertionTools.fail("Fail message printed with verify.fail");
		}
		catch (java.lang.AssertionError ex)
		{
			// Expected
		}
	}
	
	@Test
	void test_printFailMessage_assertionFailure_int()
	{
		try
		{
			verify.that(43,"actual number").isEqualTo(42,"expected number");
		}
		catch (java.lang.AssertionError ex)
		{
			// Expected
		}
	}
	
	@Test
	void test_printFailMessage_assertionFailure_double()
	{
		try
		{
			verify.that(22.0/7.0,"22/7").isEqualTo(Math.PI,"pi");
		}
		catch (java.lang.AssertionError ex)
		{
			// Expected
		}
	}
	
	@Test
	void test_printFailMessage_assertionFailure_string()
	{
		try
		{
			verify.that("foo").isEqualTo("food");
		}
		catch (java.lang.AssertionError ex)
		{
			// Expected
		}
	}
	
	@Test
	void test_printFailMessage_assertionFailure_string_pattern()
	{
		try
		{
			verify.that("some food for you").matches("foo"); // OK
			verify.that("some foo for you").matches("food");
		}
		catch (java.lang.AssertionError ex)
		{
			// Expected
		}
	}
	



}
