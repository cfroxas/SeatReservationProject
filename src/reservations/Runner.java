package reservations;

import java.util.Scanner;
import DAOs.ReservationDAOSql;

public class Runner
{
	private static ReservationDAOSql rds = new ReservationDAOSql();
	
	public static void main(String[] args)
	{
		run();
	}
	
	public static void run()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Reserv-A-Seat!");
		
		while (true)
		{
			System.out.println("Select from the following");
			System.out.println("1. Add a reservation");
			System.out.println("2. Cancel a reservation");
			System.out.println("3. Change a reservation");
			System.out.println("4. View your reservations");
			System.out.println("5. View theater");
			System.out.println("6. Exit program");
			System.out.println();
			System.out.print("Your choice: ");
			String choice = getValidMenuChoice(input.nextLine());
			
			switch (choice)
			{
			case "1":
				rds.addReservation();
				break;
			case "2":
				rds.deleteReservation();
				break;
			case "3":
				rds.changeReservation();
				break;
			case "4":
				System.out.println(rds.cleanUp(rds.getReservationsByName()));
				break;
			case "5":
				rds.viewTheater();
				break;
			case "6":
				System.out.println("Have a great day!");
				input.close();
				return;
			}
		}
	}
	
	public static String getValidMenuChoice(String choice)
	{
		Scanner input = new Scanner(System.in);
		while (!choice.matches("^[1-6]$"))
		{
			System.out.println("That's not a valid menu choice. Try again.");
			System.out.print("Your choice: ");
			choice = input.nextLine();
		}
		return choice;
	}
}








































































