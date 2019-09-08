package testHelp;

import org.junit.Test;

public class SmokeTests
{

	@Test
	public void test_verify_pass()
	{
		verify.that("foo bar baz").matches("bar");
	}

	@Test
	public void test_verify_fail()
	{
		try
		{
			verify.that("foo bar baz").matches("bark");
		} catch (java.lang.AssertionError ex)
		{

		}

	}

}
