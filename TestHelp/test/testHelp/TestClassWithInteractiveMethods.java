package testHelp;

import java.util.Scanner;

class TestClassWithInteractiveMethods
{

	// main - cloned from ConsoleTester.MainClass2
	
	public static void main2(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		while (true)
		{
			System.out.println("A or B or return to quit");
			String nextLine = scanner.nextLine();
			if (nextLine.equals(""))
			{
				System.out.println("Bye");
				return;
			}
			if (nextLine.equals("A") || nextLine.equals("B"))
			{
				System.out.println("You entered " + nextLine);
			} else
			{
				throw new IllegalArgumentException(
						"Bad input: " + nextLine);

			}
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			int sum = sumNumbersFromInput();
			System.out.println("Sum of numbers: " + sum);
		}
		catch (Throwable ex)
		{
			System.err.println("Sum of numbers threw exception: " + ex);
			ex.printStackTrace();
		}
	}
	
	static int sumNumbersFromInput()
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter numbers one per line or = to return sum");
		
		int sum = 0;
		
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			try
			{
				line = line.trim();
				if (line.isEmpty())
				{
					continue;
				}
				if (line.equals("=") || line.equalsIgnoreCase("sum"))
				{
					break;
				}
				if (line.equals("~") || line.equalsIgnoreCase("avg"))
					throw new IllegalArgumentException("average (~,avg) not implemented");
				int number = Integer.parseInt(line);
				System.out.println(number);
				sum += number;
			}
			catch (NumberFormatException ex)
			{
				System.err.println("Bad number, skipping: " + line);
			}
		}
		
		return sum;
	}

}
