package testHelp;

import java.io.OutputStreamWriter;

import org.junit.jupiter.api.Test;

class MiscTests
{
	static {
		ConsoleTester.setVerboseLevel(2);
	}
	
	// =====================================================
	//
	//	Console encoding
	//
	// =====================================================
	
	static final String latinWithDiacritic = "café";
	static final String nonLatin = "한국어"; // Saved file as UTF-8

	
	@Test
	void test_string_literal_utf8()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();
		
		System.out.println("Latin with diacritic: " + latinWithDiacritic);
		System.out.println("Non-Latin: " + nonLatin);

	}

	@Test
	void test_getConsoleEncoding()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();
		
		OutputStreamWriter osw = new OutputStreamWriter(System.out);
		System.out.println("System.out encoding: " + osw.getEncoding());
	}

	@Test
	void test_getOutput_encoding()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();
		
		System.out.println("System.out encoding: " + ConsoleTester.getConsoleEncoding());

		ConsoleTester.getOutputStart("","System.out.println(\"Latin with diacritic: \" + latinWithDiacritic);\\r\\n" + "System.out.println(\"Non-Latin: \" + nonLatin);");
		System.out.println("Latin with diacritic: " + latinWithDiacritic);
		System.out.println("Non-Latin: " + nonLatin);
		ConsoleTester.getOutputFinish();
		ConsoleTester.printInfoIfNotAlreadyPrinted();
	}
	
	// =====================================================
	//
	//	Error output chunking
	//
	// =====================================================

	@Test
	void test_chunkStacktraceWithMultilineMessage() {
		
		ConsoleTester.printTestNameIfNotAlreadyPrinted();
		
		Throwable exception = new Exception("Exception: message begin\r\nmessage continue on next line");
		//Throwable exception = new Exception("Exception: message begin ... message continue on next line");

		//exception.printStackTrace();
		
		ErrorOutputChunker.printChunkedStackTrace(exception, System.out,ConsoleTester.getTestName());
		
	}
	
	@Test
	void getOutput_throwExceptionWithMultilineMessage_verboseLevel2()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();

		ConsoleTester.setVerboseLevel(2);
		String output;
		try {
			ConsoleTester.getOutputStart("","code that throws exception wih cause");

			Throwable exception = new Exception("Exception: message begin\r\nmessage continue on next line");
			//Throwable exception = new Exception("Exception: message begin ... message continue on next line");
			
			throw exception;
		} catch (Throwable ex) {
			output = ConsoleTester.getOutputFromException(ex);
		}
		System.out.println("Output: " + output);
		ConsoleTester.setVerboseLevel(0);
		
	}

	@Test
	void test_chunkStacktraceWithCause_caller() {
		
		ConsoleTester.printTestNameIfNotAlreadyPrinted();
		
		Throwable exception1 = new Exception("Exception1");
		Throwable exception2 = new Exception("Exception2WithException1AsCause",exception1);
		
		//exception2.printStackTrace();
		
		ErrorOutputChunker.printChunkedStackTrace(exception2, System.out,ConsoleTester.getTestName());
		
	}
	
	@Test
	void test_chunkStacktraceWithCause_noCaller_defaultMaxLinesPerChunk() {
		
		ConsoleTester.printTestNameIfNotAlreadyPrinted();

		Throwable exception1 = new Exception("Exception1");
		Throwable exception2 = new Exception("Exception2WithException1AsCause",exception1);
		
		//exception2.printStackTrace();
		
		ErrorOutputChunker.printChunkedStackTrace(exception2,System.out,null);
		
	}
	@Test
	void test_chunkStacktraceWithCause_noCaller_nonDefaultMaxLinesPerChunk() {
		
		ConsoleTester.printTestNameIfNotAlreadyPrinted();

		Throwable exception1 = new Exception("Exception1");
		Throwable exception2 = new Exception("Exception2WithException1AsCause",exception1);
		
		//exception2.printStackTrace();
		
		ErrorOutputChunker.printChunkedStackTrace(exception2,System.out,null,4);
		
	}
	
	@Test
	void test_chunkStacktraceWithCause_noCaller_changedDefaultMaxLinesPerChunk() {
		
		ConsoleTester.printTestNameIfNotAlreadyPrinted();

		Throwable exception1 = new Exception("Exception1");
		Throwable exception2 = new Exception("Exception2WithException1AsCause",exception1);
		
		//exception2.printStackTrace();
		
		ErrorOutputChunker.setDefaultMaxLinesPerChunk(6);
		
		ErrorOutputChunker.printChunkedStackTrace(exception2, System.out,null);
		
	}
	
	@Test
	void getOutput_throwExceptionWithCause_verboseLevel0()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();

		ConsoleTester.setVerboseLevel(0);
		String output;
		try {
			ConsoleTester.getOutputStart("","code that throws exception wih cause");

			Throwable exception1 = new Exception("Exception1");
			Throwable exception2 = new Exception("Exception2WithException1AsCause",exception1);
			throw exception2;
		} catch (Throwable ex) {
			output = ConsoleTester.getOutputFromException(ex);
		}
		System.out.println("Output: " + output);
		ConsoleTester.setVerboseLevel(0);
		
	}
	
	@Test
	void getOutput_throwExceptionWithCause_verboseLevel2()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();

		ConsoleTester.setVerboseLevel(2);
		String output;
		try {
			ConsoleTester.getOutputStart("","code that throws exception wih cause");

			Throwable exception1 = new Exception("Exception1");
			Throwable exception2 = new Exception("Exception2WithException1AsCause",exception1);
			throw exception2;
		} catch (Throwable ex) {
			output = ConsoleTester.getOutputFromException(ex);
		}
		System.out.println("Output: " + output);
		ConsoleTester.setVerboseLevel(0);
		
	}
	
	// =====================================================
	//
	//	TestHelpError
	//
	// =====================================================
	
	@Test
	void test_assertion_throwsTestHelpError()
	{
		ConsoleTester.printTestNameIfNotAlreadyPrinted();
		
		try
		{
			//verify.that(10).isBetween(9,11); // OK
			//verify.that(12).isBetween(9,11); // bad - throws assertion error
			verify.that(-10).isBetween(-9,-11); // bad - throws TestHelpError

		}
		catch (TestHelpError ex)
		{
			verify.info("Caught TestHelpError as expected: " + ex);
			return;
		}
		catch (Throwable ex)
		{
			verify.fail("Caught exception other than TestHelpError as expected: " + ex);
		}
		verify.fail("Expected TestHelpError, no exception thrown");

	}

	// =====================================================
	//
	//	WrappedException
	//
	// =====================================================


}
