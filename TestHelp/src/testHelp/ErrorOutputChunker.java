package testHelp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import java.util.List;

/**
 * This class bundles a collection of static types and methods for parsing text that may contain one or more exception stacktraces and/or non-stacktrace
 * content into chunks. The chunks are of two types:
 * <br><br>
 * <ul>
 * 	<li>An exception stacktrace written by Throwable.printStacktrace</li>
 * 	<li>Text that is not part of an exception stacktrace</li>
 * </ul>
 * <p>
 * Note that Throwable.printStackTrace writes not only the base stacktrace but also, recursively, contained cause stacktraces (which are often truncated
 * by the print method with trailing '... [number of lines] more'). Base + cause stacktrace sequences are chunked as separate stacktraces.
 * <p>
 * The text may be output to System.err (the default PrintStream for the Throwable.printStackTrace method) captured by ConsoleTester.getOutput
 * and relatives, which may include stacktrace as well as non-stacktrace output, or it may be output of Throwable.printStacktrace captured 
 * specifically to be parsed (the case with {@link #printChunkedStackTrace}).
 * <p>
 * The chunks, of both kinds, are subject to truncation involving lines at the end of the raw chunk being replaced by a single line of the form "...".
 * Truncation is triggered in two ways:  
 * <br>
 * <ul>
 * 	<li>All chunks - a default ({@link #defaultMaxLinesPerChunk}) or explicitly specified (method param {@code maxLinesPerChunk}) maximum number of lines per chunk
 * 	is reached</li>
 * 	<li>Stacktrace chunks - a target caller (method param {@code caller}) has been explicitly specified and that caller appears in the stacktrace</li>
 * </ul>
 * <p>
 * The two forms of triggering combine to produce four forms of truncation:
 * <br>
 * <ol start="1"><li>(a) Non-stacktrace chunk or (b) stacktrace chunk with no specified target caller or (c) stacktrace with a specified target caller which
 * does not appear in the stacktrace - total chunk length is less than or equal to max lines per chunk</li></ol>
 * <p>
 * No truncation will occur and the chunk will end without "...".  This can easily occur with non-stacktrace chunks but not with stacktrace chunks,
 * or at least, not with ones arising in unittest contexts, unless max lines per chunk has been set to a high value.
 * Example (non-stacktrace):
<pre>
 ERROR_OUTPUT: Bad number, skipping: r
 Bad number, skipping: add
</pre>
 *  <ol start="2"><li>(a) Non-stacktrace chunk or (b) stacktrace chunk with no specified target caller or (c) stacktrace with a specified
 *  target caller which does not appear in the stacktrace - total chunk length is greater than max lines per chunk</li></ol>
 * <p>
 * Truncation will occur after max lines and a line containing "..." will be appended.  Example (stacktrace with no specified target caller,
 * maxLinesPerChunk 16):
 * 
<pre>
  (1) java.lang.IllegalArgumentException: Too many args
	  at testHelp.ConsoleTesterTests$MainClass1.main(ConsoleTesterTests.java:54)
	  at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	  at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	  at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	  at java.lang.reflect.Method.invoke(Unknown Source)
	  at testHelp.ConsoleTester.lambda$0(ConsoleTester.java:511)
	  at testHelp.VFunctionsAndVUnboundMethods.lambda$3(VFunctionsAndVUnboundMethods.java:248)
	  at testHelp.ConsoleTester.getOutputFromVFunction(ConsoleTester.java:561)
	  at testHelp.ConsoleTester.getOutput(ConsoleTester.java:202)
	  at testHelp.SampleOutputInfoPrintingTests.test_getOutput_ClassName_threeArgs_exception_truncation_no_caller(SampleOutputInfoPrintingTests.java:206)
	  at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	  at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	  at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	  at java.lang.reflect.Method.invoke(Unknown Source)
	  at org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:436)
</pre>
 *  <ol start="3"><li>Stacktrace chunk with specified target caller which occurs before or as max lines is reached</li></ol>
 * <p>
 * Truncation will occur after the line containing the target caller and a line containing "..." will be appended.  Example:
 <pre>
   ERROR_OUTPUT: (1) java.lang.IllegalArgumentException: Too many args
	  at testHelp.TestClassWithNormalizedMethods.main(TestClassWithNormalizedMethods.java:18)
	  at testHelp.VFunctionsAndVUnboundMethods.lambda$3(VFunctionsAndVUnboundMethods.java:248)
 	  at testHelp.ConsoleTester.getOutputFromVFunction(ConsoleTester.java:561)
	  at testHelp.ConsoleTester.getOutput(ConsoleTester.java:221)
	  at testHelp.SampleOutputInfoPrintingTests.test_printInfoIfNotAlreadyPrinted_getOutput_exception(SampleOutputInfoPrintingTests.java:65)
	  ...
 </pre>
  * <p>
  * Another example:
  * <br>
 <pre>
   ERROR_OUTPUT: (1) java.lang.IllegalArgumentException: Too many args
	  at testHelp.ConsoleTesterTests$MainClass1.main(ConsoleTesterTests.java:54)
	  at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	  at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	  at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	  at java.lang.reflect.Method.invoke(Unknown Source)
	  at testHelp.ConsoleTester.lambda$0(ConsoleTester.java:511)
	  at testHelp.VFunctionsAndVUnboundMethods.lambda$3(VFunctionsAndVUnboundMethods.java:248)
	  at testHelp.ConsoleTester.getOutputFromVFunction(ConsoleTester.java:561)
	  at testHelp.ConsoleTester.getOutput(ConsoleTester.java:202)
	  at testHelp.SampleOutputInfoPrintingTests.test_getOutput_ClassName_threeArgs_exception_truncation_after_target_caller(SampleOutputInfoPrintingTests.java:171)
	...
 </pre>
 * <ol start="3"><li>Stacktrace chunk with specified target caller which occurs after max lines</li></ol>
 * <p>
 * Truncation will occur after max lines and the line containing the target caller, preceded and followed by lines containing "...", will be appended. Example (maxLinesPerChunk 6):
 * 
<pre>
 ERROR_OUTPUT: (1) java.lang.IllegalArgumentException: Too many args
	  at testHelp.ConsoleTesterTests$MainClass1.main(ConsoleTesterTests.java:54)
	  at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	  at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	  at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	  at java.lang.reflect.Method.invoke(Unknown Source)
	  ...
	  at testHelp.SampleOutputInfoPrintingTests.test_getOutput_ClassName_threeArgs_exception_truncation_before_target_caller(SampleOutputInfoPrintingTests.java:185)
	  ...
</pre>
 *
 * @author John Armstrong
 */
public class ErrorOutputChunker
{
	/**
	 * Never called private do-nothing constructor to suppress default constructor in javadoc
	 */
	private ErrorOutputChunker()
	{
		
	}
	
	static int defaultMaxLinesPerChunk = 12;
	
	/**
	 * Get the current value of defaultMaxLinesPerChunk.
	 * 
	 * @return the value
	 */
	public static int getDefaultMaxLinesPerChunk()
	{
		return defaultMaxLinesPerChunk;
	}
	
	/**
	 * Set the new value of defaultMaxLinesPerChunk.
	 * @param value the new value
	 */
	public static void setDefaultMaxLinesPerChunk(int value)
	{
		defaultMaxLinesPerChunk = value;
	}
	
	public static enum LT // LINE_TYPE
	{
		STACKTRACE_TOP,
		STACKTRACE_CAUSE,
		STACKTRACE_CONT,
		STACKTRACE_END,
		STACKTRACE_CALLER,
		OTHER
	}
	
	/**
	 * Classifies the first line of a piece of text as part of a stacktrace or something else.
	 * <p>
	 * One of:
	 * <ul>
	 * 	<li>STACKTRACE_TOP - first line of a stacktrace = contains 'Exception:' or 'Error:'</li>
	 * 	<li>STACKTRACE_CAUSE - first line of a stacktrace cause - starts with 'Caused by:'</li>
	 * 	<li>STACKTRACE_CONT - continuation of a stacktrace - starts with TAB + 'at '</li>
	 * 	<li>STACKTRACE_END - continuation of a stacktrace - starts with TAB + '... [number] more'</li>
	 * 	<li>STACKTRACE_CALLER - line in a stacktrace that contains name of target caller (or part of it) - normally a continuation line</li>
	 * 	<li>OTHER - something else - presumably not part of a core stacktrace</li>
	 * </ul>
	 * 
	 * @param s text to classify the first line of
	 * @param caller name of target caller or null for none 
	 * @return an enum value representing one of the five classifications
	 */
	public static ErrorOutputChunker.LT getErrorOutputLineType(String s, String caller)
	{

		// Why are some of the line tests on firstLine and some on s ???
		
		int firstNLOffset = s.indexOf("\n");
		String firstLine;
		if (firstNLOffset == -1)
		{
			firstLine = s;
		}
		else {
			firstLine = rtrim(s.substring(0,firstNLOffset));
		}
		if (firstLine.matches("[^ ]+\\.[^ ]+(Exception|Error).*"))
		{
			return LT.STACKTRACE_TOP;
		}
		else if (firstLine.matches("^Caused by:.*"))
		{
			return LT.STACKTRACE_CAUSE;
		}
		else if (caller != null && s.contains(caller)) // must go before LT.STACKTRACE_CONT
		{
			return LT.STACKTRACE_CALLER;
		}
		else if (s.startsWith("\tat "))
		{
			return LT.STACKTRACE_CONT;
		}
		else if (s.matches("\t\\.\\.\\. [0-9]+ more"))
		{
			return LT.STACKTRACE_END;
		}
		else
		{
			return LT.OTHER;
		}
	}
	
	/**
	 * Internal representation of a Throwable (Exception or Error) stacktrace or non-stacktrace chunk. 
	 */
	private static class ErrorOutputChunk
	{
		private ErrorOutputChunk()
		{
			
		}
		
		// Set values directly, check for remaining null values at end
		private Integer maxLinesPerChunk;
		private Integer firstLineOffset;
		private Integer nextFirstLineOffset;
		private String chunk;
		private String firstLine;
		private Boolean isTruncated;
		private Boolean isExceptionOrError;
		private String targetCaller;
		private String foundCaller; // may be null - currently not used

		private void validate()
		{
			// targetCaller and foundCaller can both be null in fully initialized chunk
			
			if (maxLinesPerChunk == null ||
				firstLineOffset == null || 
				nextFirstLineOffset == null ||
				chunk == null ||
				firstLine == null ||
				isTruncated == null ||
				isExceptionOrError == null ||
				(targetCaller == null && foundCaller != null)) // targetCaller and foundCaller can both be null in fully initialized chunk
				{
					throw new TestHelpError("Incompletely initialized chunk");
				}
		}

	}
	
	/**
	 * Parse text and return a pair of strings - packaged as an array of length 2 - representing:
	 * <ul>
	 * 	<li>A summary ... (index 0)</li>
	 * 	<li>A concatenation ... (index 1)</li>
	 * </ul>
	 * 
	 * @param errorOutput text to be parsed
	 * @param maxLinesPerChunk maximum lines to include in a chunk
	 * @param caller name of target caller or null for none 
	 * @return a String array of length 2
	 */
	public static String[] getErrorAndExceptionSummaryAndChunkedErrorOutput(String errorOutput, String caller, int maxLinesPerChunk)
	{
		
		List<ErrorOutputChunker.ErrorOutputChunk> chunks = getErrorOutputChunks(errorOutput,caller,maxLinesPerChunk);
		
		StringBuilder errorAndExceptionSummarySB = new StringBuilder();
		
		StringBuilder chunkedErrorOutputSB = new StringBuilder();
		
		for (ErrorOutputChunker.ErrorOutputChunk chunk : chunks)
		{
			if (chunk.isExceptionOrError)
			{
				errorAndExceptionSummarySB.append(String.format("[%s ...] ",chunk.firstLine.trim()));
			}
			
			chunkedErrorOutputSB.append(rtrim(chunk.chunk) + "\n\n");
			
		}
		String [] errorAndExceptionSummaryAndChunkedErrorOutput = new String [2];
		
		errorAndExceptionSummaryAndChunkedErrorOutput[0] = errorAndExceptionSummarySB.toString();
		errorAndExceptionSummaryAndChunkedErrorOutput[1] = chunkedErrorOutputSB.toString();
		
		return errorAndExceptionSummaryAndChunkedErrorOutput;
	}
	
	/**
	 * Version of {@link #getErrorAndExceptionSummaryAndChunkedErrorOutput(String errorOutput, String caller, int maxLinesPerChunk)} that uses defaultMaxLines.
	 * 
	 * @param errorOutput text to be parsed
	 * @param caller name of target caller or null for none 
	 * @return a String array of length 2
	 */
	public static String[] getErrorAndExceptionSummaryAndChunkedErrorOutput(String errorOutput, String caller)
	{
		return getErrorAndExceptionSummaryAndChunkedErrorOutput(errorOutput,caller,defaultMaxLinesPerChunk);
	}

	
	/**
	 * Parse a string which may contains stacktraces into chunks
	 * 
	 * @param errorOutput the string to parse
	 * @param maxLinesPerChunk maximum lines to include in a chunk
	 * @param name target caller or null for none or null for none 
	 * @return a list of internal representations of the chunks
	 */
	private static List<ErrorOutputChunker.ErrorOutputChunk> getErrorOutputChunks(String errorOutput, String caller, int maxLinesPerChunk)
	{
		List<ErrorOutputChunker.ErrorOutputChunk> chunks = new ArrayList<ErrorOutputChunker.ErrorOutputChunk>();

		String[] lines = errorOutput.split("\\r?\\n");
		int firstLineOffset = 0;
		ErrorOutputChunker.ErrorOutputChunk chunk = null;
		do
		{
			chunk = getErrorOutputChunk(lines,firstLineOffset,caller,maxLinesPerChunk);
			chunks.add(chunk);
			firstLineOffset = chunk.nextFirstLineOffset; 
			
		} while (firstLineOffset < lines.length);
		
		return chunks;
	}

	/**
	 * Get the next chunk from the text, represented by an array of lines, starting a a given offset.
	 * 
	 * @param lines an array of lines representing the full text to be parsed
	 * @param firstLineOffset the index of the line to start with in getting the next chunk
	 * @param maxLinesPerChunk maximum lines to include in a chunk
	 * @param name target caller or null for none or null for none 

	 * @return an internal representation of the chunk
	 */
	private static ErrorOutputChunker.ErrorOutputChunk getErrorOutputChunk(String[] lines, int firstLineOffset, String caller, int maxLinesPerChunk)
	{
		if (firstLineOffset >= lines.length)
		{
			throw new TestHelpError("firstLineOffset past end of lines");
		}
		
		ErrorOutputChunker.ErrorOutputChunk chunk = new ErrorOutputChunk();
		chunk.maxLinesPerChunk = maxLinesPerChunk;
		chunk.firstLineOffset = firstLineOffset;
		chunk.targetCaller = caller; // will be filled with line containing caller
		chunk.foundCaller = null; // will be filled with line containing targetCaller if latter is not null
		chunk.isTruncated = false;
		
		ErrorOutputChunker.LT lineTypeToInclude = null;

		StringBuilder chunkSB = new StringBuilder();
		int len = 0;
		int i = 0; // accessed outside loop
		boolean skip = false; // for eating stacktrace after seeing LT.STACKTRACE_TEST_CALLER
		for (i = firstLineOffset; i < lines.length; i++) // for loop begin
		{
			len++;

			String line = lines[i];
			ErrorOutputChunker.LT lineType = getErrorOutputLineType(line,caller);
			
			if (i == firstLineOffset)
			{
				if (lineType == LT.STACKTRACE_TOP || lineType == LT.STACKTRACE_CAUSE)
				{
					line = String.format("(%d) %s",i + 1,line);
					chunkSB.append(line + "\n");
					chunk.isExceptionOrError = true;
					lineTypeToInclude = LT.STACKTRACE_CONT;
				}
				else if (lineType == LT.STACKTRACE_CONT)
				{
					chunkSB.append(line + "\n");
					chunk.isExceptionOrError = false;
					lineTypeToInclude = LT.STACKTRACE_CONT;
					
				}
				else if (lineType == LT.STACKTRACE_END)
				{
					chunkSB.append(line + "\n");
					chunk.isExceptionOrError = false;
					lineTypeToInclude = null;
					
				}
				else if (lineType == LT.STACKTRACE_CALLER)
				{
					chunkSB.append(line + "\n");
					chunkSB.append("\t...\n");
					chunk.isExceptionOrError = false;
					lineTypeToInclude = LT.STACKTRACE_CONT;
					skip = true;
					
				}
				else if (lineType == LT.OTHER)
				{
					chunkSB.append(line + "\n");
					chunk.isExceptionOrError = false;
					lineTypeToInclude = LT.OTHER;
				}

				else	
				{
					throw new TestHelpError(String.format("Unexpected errorOutput first line type %s first line \"%s\"",lineType.toString(),line));
				}
				chunk.firstLine = line;
				continue;
			}

			// getting here means line not first line
			
			ErrorOutputChunker.LT effectiveLineType = (lineType == LT.STACKTRACE_END || lineType == LT.STACKTRACE_CALLER) ? LT.STACKTRACE_CONT : lineType;

			if (effectiveLineType != lineTypeToInclude) 
			{
				break;
			}
			
			// Line of type to include (or skip), process it

			if (skip)
			{
				if (chunk.targetCaller != null && chunk.foundCaller == null) // we're truncating but haven't get found caller
				{
					if (lineType == LT.STACKTRACE_CALLER)
					{
						chunkSB.append(line + "\n");
						chunkSB.append("\t...");
						chunk.foundCaller = line;
					}
				}
				continue;
			}
			
			if (lineType == LT.STACKTRACE_CALLER)
			{
                                chunkSB.append(line + "\n");
                                chunkSB.append("\t...\n");
                                chunk.isTruncated = true;
                                skip = true;
                                continue;
			}

			if (lineType == LT.STACKTRACE_END)
			{
				chunkSB.append(line + "\n");
				i++; // so we don't see the line again as the beginning of the next chunk
				break;
			}

			if (len == maxLinesPerChunk + 1)
			{
				if (lineTypeToInclude == LT.STACKTRACE_CONT) {
					chunkSB.append("\t");
				}
				chunkSB.append("...\n");
				chunk.isTruncated = true;
				skip = true;
				continue;
			}
			
			chunkSB.append(line + "\n");
			
		} // end for loop
		
		chunk.nextFirstLineOffset = i;
		chunk.chunk = chunkSB.toString();
		chunk.validate();
		return chunk;
	}
	
	/**
	 * Get the stacktrace in a Throwable (Exception or Error) object and return it as a chunked stacktrace string. 
	 * Includes cause exception(s) when present.
	 * 
	 * @param ex the Exception or Error to get then stacktrace from
	 * @param maxLinesPerChunk maximum lines to include in a chunk
	 * @param caller name of target caller or null for none 
	 * @return a string representing the stacktrace in chunked form
	 */
	public static String getChunkedStackTraceFromException(Throwable ex, String caller, int maxLinesPerChunk)
	{
		
		ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
		PrintStream outputStream = ConsoleTester.makePrintStreamFromOutputBytes(outputBytes);

		ex.printStackTrace(outputStream);
		
		String stackTraceAsString = ConsoleTester.outputBytesToString(outputBytes);
		
		String[] summaryAndChunkedStackTrace = ErrorOutputChunker.getErrorAndExceptionSummaryAndChunkedErrorOutput(stackTraceAsString,caller,maxLinesPerChunk);
		return summaryAndChunkedStackTrace[1]; // [0] is summary, [1] is chunked stacktrace

	}
	
	/**
	 * A version of {@link #getChunkedStackTraceFromException(Throwable ex, String caller, int maxLinesPerChunk)} that uses defaultMaxLinesPerChunk.
	 * 
	 * @param ex the Exception or Error to get then stacktrace from
	 * @param caller name of target caller or null for none 
	 * @return a string representing the stacktrace in chunked form
	 */
	public static String getChunkedStackTraceFromException(Throwable ex, String caller)
	{
		return getChunkedStackTraceFromException(ex,caller,defaultMaxLinesPerChunk);

	}
	

	/**
	 * Print the stacktrace in a Throwable (Exception or Error) object to a PrintStream as a chunked stacktrace string. 
	 * Includes cause exception(s) when present.
	 * 
	 * @param ex the Exception or Error to get stacktrace from
	 * @param out the PrintStream to write to
	 * @param caller name of target caller or null for none
	 * @param maxLinesPerChunk maximum lines to include in a chunk
	 */
	public static void printChunkedStackTrace(Throwable ex, PrintStream out, String caller, int maxLinesPerChunk)
	{
		String chunkedStackTrace = ErrorOutputChunker.getChunkedStackTraceFromException(ex,caller,maxLinesPerChunk);
		out.println(chunkedStackTrace);
	}
	
	/**
	 * A version of {@link #printChunkedStackTrace(Throwable ex, PrintStream out, String caller, int maxLinesPerChunk)} that uses defaultMaxLinesPerChunk.
	 * 
	 * @param ex the Exception or Error to get stacktrace from
	 * @param out the PrintStream to write to
	 * @param caller name of target caller or null for none
	 */
	public static void printChunkedStackTrace(Throwable ex, PrintStream out, String caller)
	{
		printChunkedStackTrace(ex,out,caller,defaultMaxLinesPerChunk);
	}
	
	
	
	
	// from a stackoverflow question
	private static String ltrim(String s) {
	    int i = 0;
	    while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
	        i++;
	    }
	    return s.substring(i);
	}

	private static String rtrim(String s) {
	    int i = s.length()-1;
	    while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
	        i--;
	    }
	    return s.substring(0,i+1);
	}


}