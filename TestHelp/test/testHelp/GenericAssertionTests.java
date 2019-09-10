package testHelp;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

/**
 * @author John Armstrong
 */

class GenericAssertionTests {
	static {
		ConsoleTester.setVerboseLevel(2);
	}
	
	static class SomeContainerS<E> {
		private Supplier<E> supplier;

		SomeContainerS(Supplier<E> supplier) {
			this.supplier = supplier;
		}

		E createContents() {
			return supplier.get();
		}
	}

	static class SomeContainerC<E> {
		private Callable<E> callable;

		SomeContainerC(Callable<E> callable) {
			this.callable = callable;
		}

		E createContents() throws Exception {
			return callable.call();
		}
	}

	
	@Test
	void test_SomeContainerSConstruction()
	{
		SomeContainerS<String> someContainer = new SomeContainerS<>(
				() -> "Hello"
				);
		
		String contents = someContainer.createContents(); // doesn't throw checked exception
		verify.that(contents).equals("Hello");
	}

	@Test
	void test_SomeContainerCConstruction()
	{
		SomeContainerC<String> someContainer = new SomeContainerC<>(
				() -> "Hello"
				);
		
		String contents = null;
		try {
			contents = someContainer.createContents(); // may throw checked exception
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify.that(contents).equals("Hello");
	}
	
	
	@Test
	void test_failF()
	{
		try
		{
			AssertionTools.syncInfo();
			AssertionTools.failF("String '%s' int %d double %f object %s","hello",42,3.14,new Object().toString());
		}
		catch (AssertionError ex)
		{
			System.out.println("AssertionTools.fail threw expected exception");
			return;
		}
		org.junit.Assert.fail("AssertionTools.fail did not throw expected exception");
	}
	


}


