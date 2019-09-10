/**
 *  The testHelp library consists of a single package testHelp which includes four groups of Java classes and interfaces.
<p>
(1)  OUTPUT (capture output printed to System.out and System.err)
<p>
Original, refactored and extended
<ul>
<li>ConsoleTester</li>
</ul>
<p>
Extensions
<ul>
<li>DelegatingInputStream</li>
<li>DelegatingPrintStream</li>
<li>ErrorOutputChunker</li>
<li>OutputInfo</li>
</ul>
<p>
Unittest
<ul>
<li>ConsoleTesterTests</li>
<li>ConsoleTesterVFunctionAndVUnboundMethodTests</li>
<li>MiscTests</li>
<li>SampleOutputInfoPrintingTests</li>
</ul>
<p>
(2)  ASSERTIONS (all called directly or indirectly through fluent verify interface)
<p>
Original
<ul>
<li>BooleanAssertion</li>
<li>CallableAssertion</li>
<li>CollectionAssertion</li>
<li>CompareType</li>
<li>DoubleAssertion</li>
<li>ExceptionAssertion</li>
<li>ExecutableAssertion</li>
<li>FractionAssertion</li>
<li>IntAssertion</li>
<li>ObjectAssertion</li>
<li>RunnableAssertion</li>
<li>StringAssertion</li>
<li>verify</li>
</ul>
<p>
Extensions
<ul>
<li>AssertionTools</li>
<li>EnumAssertion</li>
<li>GenericExceptionAssertion</li>
<li>GenericObjectAssertion</li>
<li>GenericObjectAssertionBase</li>
<li>NumericCompareType</li>
</ul>
<p>
Unittest
<ul>
<li>GenericAssertionTests</li>
</ul>
<p>
(3)  FUNCTIONAL OBJECTS (lambda expressions, member references and other implementations of functional interfaces) - all extensions
<ul>
<li>FunctionNormalizers</li>
<li>NormalizedFunctionCallers</li>
<li>NormalizedUnboundMethodCallers</li>
<li>SupplementaryFunctionalInterfaces</li>
<li>UnboundMethodInterfaces</li>
<li>UnboundMethodNormalizers</li>
<li>VFunctionsAndVUnboundMethods</li>
</ul>
<p>
Unittests
<ul>
<li>FunctionAndUnboundMethodCallerSpecializedTests</li>
<li>FunctionAndUnboundMethodCallerTests</li>
<li>MethodReferenceToFunctionalTypeAssignments</li>
<li>TestClassWithNormalizedMethods</li>
<li>TestClassWithSpecializedMethods</li>
<li>VFunctionAndVUnboundMethodTests</li>
</ul>
<p>
(4)  EXCEPTIONS - all extensions
<ul>
<li>TestHelpError</li>
<li>WrappedException</li>
</ul>


 * @author eJewart
 * @author John Armstrong 
 */
package testHelp;