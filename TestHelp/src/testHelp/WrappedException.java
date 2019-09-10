package testHelp;

/**
 * Wraps an exception - typically but not necessarily a checked one - in a new non-checked exception.
 * If the exception is already wrapped, its contents are transferred to the new one.  The objects
 * and stacktraces of the old and new wrapped exceptions will be different, but they will be 
 * equivalent for practical purposes.
 * <p>
 * The exception object includes a field internal, which is normally false but will be true if the 
 * exception to be wrapped was thrown from within the framework rather than from within code 
 * that the framework is executing. A common situation which can produce internal exceptions is 
 * attempting to execute code through the Java reflection APIs.
 * 
 * @author John Armstrong
 */
public class WrappedException extends RuntimeException
{

    private static final long serialVersionUID = 1L;
	
    protected boolean internal;
    
    /**
     * Construct a new WrappedException.  If the exception to be wrapped already wrapped, transfer
     * its cause and internal flag to the new exception; otherwise, use the passed in values. The wrapped
     * exception is stored as the cause of the container exception.
     * 
     * @param ex the exception to wrapped
     * @param internal true if the exception was thrown from within the framework rather than from
     * code that the framework is executing.
     * @throws NullPointerException if ex is null
     */
    public WrappedException(Throwable ex, boolean internal)
    {
	super("[WrappedException]",(ex.getClass() == WrappedException.class) ? ex.getCause() : ex);
	this.internal = ((ex.getClass() == WrappedException.class)) ? 
		((WrappedException)ex).getInternal() : internal;
    }
    
    /**
     * Get the exception that has been wrapped (synonym for getCause)
     * 
     * @return wrapped exception
     */
    public Throwable getWrappedException()
    {
	    return getCause();
    }

    /**
     * Get the value of the internal field.
     * 
     * @return true or false
     */
    public boolean getInternal() {
	return internal;
    }
    
    // // Copied from Throwable
    //public String toString() {
    //    String s = getClass().getName();
    //    String message = getLocalizedMessage();
    //    return (message != null) ? (s + ": " + message) : s;
    //}
    
    /**
     * Override Throwable.toString()
     */
    @Override
    public String toString() {
	Throwable cause = getCause();
        String s = String.format("[Wrapped%s] %s",internal ? " - internal" : "", cause.getClass().getName());
        String message = cause.getLocalizedMessage();

        return (message != null) ? (s + ": " + message) : s;
    }

}
