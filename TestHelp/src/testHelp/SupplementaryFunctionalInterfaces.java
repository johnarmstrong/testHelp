package testHelp;

/**
 * An interface with no abstract or default methods that bundles functional interfaces not in the standard Java 8 set but named in the
 * style of the standard set.
 * 
 * More interfaces can be added, but for full integration into the library it will be necessary to add them to the codegen tables and
 * regenerate the derived Java files.
 * 
 * @author John Armstrong
 */
public interface SupplementaryFunctionalInterfaces
{

    /**
     * Superinterface of {@link testHelp.UnboundMethodInterfaces.BiConsumerUnboundMethod}
     */
    public static interface TriConsumer<T, U, V>
    {
	void accept(T t, U u, V v);
    }

    /**
     * Superinterface of {@link testHelp.UnboundMethodInterfaces.BiFunctionUnboundMethod}
     */
    public static interface TriFunction<T, U, V, R>
    {
	R apply(T t, U u, V v);
    }

    /**
     * Superinterface of {@link testHelp.UnboundMethodInterfaces.BiPredicateUnboundMethod}
     */
    public static interface TriPredicate<T, U, V>
    {
	boolean test(T t, U u, V v);
    }

}
