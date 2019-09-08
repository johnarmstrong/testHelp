package testHelp;

import static org.junit.Assert.*;
import java.util.Collection;

/**
 * Represents an assertion about a collection, like an array or ArrayList.
 * 
 * @author ejewart
 *
 * @param <T> the type of object the collection contains
 */
public class CollectionAssertion<T> extends ObjectAssertion 
{
	private Collection<T> collection;
	
	CollectionAssertion(Collection<T> subject)
	{
		super(subject);
		this.collection = subject;
	}
	
	/**
	 * Verifies that this collection contains the same objects as the specified one,
	 * regardless of order, using the Object.equals method to check for equivalence
	 * of elements.
	 * <p>
	 * Note that currently the implementation does not handle the case where the collection
	 * contains different numbers of the same object.
	 *  
	 * @param other
	 * @return
	 */
	public CollectionAssertion<T> isEquivalentTo(Collection<T> other)
	{
		// TODO: make sure we fail if there are dups--each collection should contain
		// the same number of each T as the other does
		for (T obj : other)
		{
			if (!collection.contains(obj))
				fail("Expected collection " + makeString(collection) + " to contain " + obj.toString() + " but it did not.");
		}
		
		for (T obj : collection)
		{
			if (!other.contains(obj))
				fail("Expected collection " + makeString(collection) + " not to contain " + obj.toString() + " but it did.");
		}
		
		return this;
	}
	
	/**
	 * Verifies that the collection contains a particular item.
	 * 
	 * @param item
	 * @return
	 */
	public CollectionAssertion<T> contains(T item)
	{
		for (T obj : this.collection)
			if (obj.equals(item))
				return this;
		
		fail("Expected collection " + makeString(collection) + " to contain " + item.toString() + " but it did not");
		return this;
	}
	
	/**
	 * Verifies that the collection does not contain a particular item
	 * @param item
	 * @return
	 */
	public CollectionAssertion<T> doesNotContain(T item)
	{
		for (T obj : this.collection)
			if (obj.equals(item))
				fail("Did not expect collection " + makeString(collection) + " to contain " + item.toString() + " but it did.");
		
		return this;
	}

	/**
	 * Verifies that the collection matches another collection exactly (same
	 * contents, same order). Note that for some types of collections (e.g.
	 * sets), order is irrelevant so this verification is meaningless.
	 * 
	 * @param collection
	 * @return
	 */
	public CollectionAssertion<T> isEqualTo(Collection<?> collection)
	{
		if (collection.size() != this.collection.size())
		{
			fail("Expected collection " + makeString(this.collection) + 
					" to equal " + makeString(collection) + 
					" but it contains " + this.collection.size() + 
					" elements instead of " + collection.size() + ".");
		}
		
		Object[] actual = this.collection.toArray();
		Object[] expected = collection.toArray();
		
		for (int i = 0; i < actual.length; i++)
		{
			if (!actual[i].equals(expected[i]))
			{
				fail("Expected collection " + makeString(this.collection) +
						" to equal " + makeString(collection) +
						" but they differed at element " + i + ".");
			}
		}
		
		return this;
	}

	/**
	 * Verifies that the collection is not identical to another collection 
	 * (has different contents or a different ordering of elements). Note 
	 * that for some types of collections (e.g. sets), order is irrelevant 
	 * so this verification is meaningless.
	 * 
	 * @param collection
	 * @return
	 */
	public CollectionAssertion<T> isNotEqualTo(Collection<?> collection)
	{
		if (this.collection.size() != collection.size())
			return this;
		
		Object[] actual = this.collection.toArray();
		Object[] expected = collection.toArray();
		
		for (int i = 0; i < actual.length && i < expected.length; i++)
		{
			if (!actual[i].equals(expected[i]))
				return this;
		}
		
		fail("Did not expect collection " + makeString(this.collection) +
				" to equal " + makeString(collection) +
				" but they were identical.");
		return this;
	}

	private static String makeString(Collection<?> c)
	{
		String result = "[";
		for (Object obj : c)
			result = result + obj.toString() + ",";
		result = result.substring(0, result.length() - 1);
		return result + "]";
	}
}
