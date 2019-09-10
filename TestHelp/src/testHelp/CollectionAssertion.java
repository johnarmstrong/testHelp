package testHelp;

import static testHelp.AssertionTools.*;

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
	
	CollectionAssertion(Collection<T> subject, String ... subjectDescArg)
	{
		super(subject,getDescAsDescArg(subjectDescArg,"collection"));
		this.collection = subject;
	}
	
	/**
	 * Verifies that this collection contains the same objects as the specified one,
	 * regardless of order, using the Object.equals method to check for equivalence
	 * of elements.  The types of the objects in the collections will be the same {@literal (type T)},
	 * and both collections will be implementations of interface {@literal Collection<T>}, but they may be
	 * different types, for example {@literal ArrayList<T>} and {@literal hashSet<T>}.    
	 * <p>
	 * Note that currently the implementation does not handle the case where the collection
	 * contains different numbers of the same object.
	 *  
	 * @param other another collection that the subject collection is expected to be equivalent to
	 * @return this if the subject collection is equivalent to the other collection
	 * @throws java.lang.AssertionError if the subject collection not equivalent to the other collection
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
	 * Verifies that the collection contains a particular item.  The comparison of the item with items in the collection
	 * is done with the equals method defined for the generic type.  As long as an override to Object.equals
	 * has been defined for the type  does not require identity an item need not be the same object as any of the objects
	 * in the collection to be considered as being in the collection.
	 * 
	 * @param item the item to be compared to the items in the collection
	 * @return this if the item is contained in the collection
	 * @throws java.lang.AssertionError if the item is not contained in the collection
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
	 * Verifies that the collection does contains a particular item.  The comparison of the item with items in the collection
	 * is done with the equals method defined for the generic type.  As long as an override to Object.equals
	 * has been defined for the type  does not require identity an item need not be the same object as any of the objects
	 * in the collection to be considered as being in the collection.
	 * 
	 * @param item the item to be compared to the items in the collection.  
	 * @return this if the item is not contained in the collection
	 * @throws java.lang.AssertionError if the item is contained in the collection
	 */
	public CollectionAssertion<T> doesNotContain(T item)
	{
		for (T obj : this.collection)
			if (obj.equals(item))
				fail("Did not expect collection " + makeString(collection) + " to contain " + item.toString() + " but it did.");
		
		return this;
	}

	/**
	 * Verifies that the collection matches another collection exactly (same contents, same order). Individual items in the subject and
	 * other collections are compared using the equals method defined for the generic type of subject collection, and as long as the an
	 * override has been defined that does not require identity the objects in the two collections do not have to be the same objects.
	 * <p>
	 * Note that for some types of collections (e.g. sets), order is irrelevant so this verification is meaningless.
	 * 
	 * @param collection another collection to be compared to the subject collection
	 * @return this if the collections are considered equal
	 * @throws java.lang.AssertionError if the collections are not considered equal
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
	 * Verifies that the collection does not match another collection exactly (same contents, same order). Individual items in the subject and
	 * other collections are compared using the equals method defined for the generic type of subject collection, and as long as the an
	 * override has been defined that does not require identity the objects in the two collections do not have to be the same objects.
	 * <p>
	 * Note that for some types of collections (e.g. sets), order is irrelevant so this verification is meaningless.
	 * 
	 * @param collection another collection to be compared to the subject collection
	 * @return this if the collections are not considered equal
	 * @throws java.lang.AssertionError if the collections are considered equal
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
