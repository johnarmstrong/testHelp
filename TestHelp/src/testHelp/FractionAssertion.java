package testHelp;

import static org.junit.Assert.*;

/** 
 * Represents an assertion related to a Fraction. It should
 * not be in the testHelp library as fractions are not part of the basic Java
 * language, but I added it to make it simpler for students to write 
 * FractionalCalculator tests.
 * 
 * @author ejewart
 *
 */
public class FractionAssertion extends StringAssertion
{
	private Fraction fraction;
	
	FractionAssertion(String subject)
	{
		super(subject);
		fraction = new Fraction(subject); 
	}
	
	/**
	 * Verifies that a fraction is equivalent to another fraction (they reduce
	 * to the same lowest terms)
	 * 
	 * @param other a fraction in string form, must be numerator/denominator or
	 * whole_numerator/denominator
	 * 
	 * @return
	 */
	public FractionAssertion isEquivalentTo(String other)
	{
		Fraction otherFraction = new Fraction(other);
		if (!otherFraction.equals(fraction))
			fail("Expected " + subject + " to be equivalent to " + otherFraction.toMixed());

		return this;
	}
	
	/**
	 * Verifies that a fraction is equivalent to the specified fraction, expressed as 
	 * integer numerator and denominators
	 * @param n numerator
	 * @param d denominator
	 * @return
	 */
	public FractionAssertion isEquivalentTo(int n, int d)
	{
		Fraction other = new Fraction(n, d);
		if (!fraction.equals(other))
			fail("Expected " + subject + " to be equivalent to " + other.toMixed());
			
		return this;
	}
	
	private class Fraction
	{
		private int numerator;
		private int denominator;
		
		public Fraction(int n, int d)
		{
			int gcd = findGCD(n, d);
			numerator = n / gcd;
			denominator = d / gcd;
		}
		
		public Fraction(String f)
		{
			convertToFraction(f);
		}

		public String toMixed()
		{
			if (numerator % denominator == 0)
				return "" + (numerator / denominator);
			else if (numerator > denominator)
				return (numerator / denominator) + "_" + (numerator % denominator) + "/" + denominator;
			else
				return numerator + "/" + denominator;
		}
		
		public boolean equals(Object other)
		{
			if (other == null || !(other instanceof Fraction))
				return false;
			
			Fraction f = (Fraction)other;
			return f.numerator == this.numerator && f.denominator == this.denominator;
		}
		
		private void convertToFraction(String input)
		{
			boolean negative = input.startsWith("-");
			if (negative)
				input = input.substring(1);
			
			int underscore = input.indexOf("_");
			int slash = input.indexOf("/");
			
			int num = 0;
			int den = 1;
			int whole = 0;
			
			if (underscore != -1 && slash != -1) // has underscore and slash
			{
				whole = Integer.parseInt(input.substring(0, underscore));
				num = Integer.parseInt(input.substring(underscore + 1, slash));
				den = Integer.parseInt(input.substring(slash + 1));
			}
			else if (slash != -1) // only a slash
			{
				num = Integer.parseInt(input.substring(0, slash));
				den = Integer.parseInt(input.substring(slash + 1));
			}
			else // must be a whole number
			{
				whole = Integer.parseInt(input);
			}
			
			num = whole * den + num;
			int gcd = findGCD(num, den);
			
			numerator = (negative ? -1 : 1) * (num / gcd);
			denominator = den / gcd;
		}
		
		private int findGCD(int num1, int num2)
		{
			if (num2 == 0)
				return num1;
			
			return findGCD(num2, num1 % num2);
		}
	}
}
