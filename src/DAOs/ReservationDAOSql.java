package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import connection.ConnectionManager;

public class ReservationDAOSql implements ReservationDAO
{
	private Connection conn = ConnectionManager.getConnection();
	private String[][] theater = {{"A", "A", "A", "A", "A"},
								  {"A", "A", "A", "A", "A"},
								  {"A", "A", "A", "A", "A"},
								  {"A", "A", "A", "A", "A"},
								  {"A", "A", "A", "A", "A"}};


	@Override
	public List<Reservation> getReservationsByName()
	{
		Scanner input = new Scanner(System.in);
		List<Reservation> resList = new ArrayList<Reservation>();
		int count = 0;
		
		System.out.print("What name are your reservations under: ");
		String name = getName(input);
		
		try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reservation WHERE res_name = ?"))
		{
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				String resName = rs.getString("res_name");
				int row = rs.getInt("row_num");
				int col = rs.getInt("col_num");
				count++;
				
				resList.add(new Reservation(resName, row, col));
			}
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if (count == 0)
			System.out.println("You have no reservations.");
		else
			System.out.println("Here are your existing reservations\n");
		return resList;
	}
	
	public void addReservation()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Sure! Lets reserve you a seat.");
		System.out.println("Anything with a hyphen is unavailable.");
		
		// show the user the theater so they know what's available
		viewTheater();
		
		System.out.print("Select the row you want: ");
		String rowChoice = input.nextLine();
		System.out.print("Select the column you want: ");
		String columnChoice = input.nextLine();
		
		// check for availability of the seat
		while (!isAvailable(rowChoice, columnChoice))
		{
			System.out.println("That seat is not available. Try again.");
			System.out.print("Select the row you want: ");
			rowChoice = input.nextLine();
			System.out.print("Select the column you want: ");
			columnChoice = input.nextLine();
		}
		
		System.out.print("What was the name for the reservation: ");
		String name = input.nextLine();
		
		try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO reservation VALUES (?, ?, ?)"))
		{
			pstmt.setString(1, name);
			pstmt.setInt(2, Integer.parseInt(rowChoice));
			pstmt.setInt(3, Integer.parseInt(columnChoice));
			
			pstmt.executeUpdate();
			System.out.println("Your reservation has been made! Thank you!");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteReservation()
	{
		// Scanner object
		Scanner input = new Scanner(System.in);
		System.out.println("Let's cancel a reservation for you.");
		
		// gets all reservations under a given name
		List<Reservation> resList = getReservationsByName();
		System.out.println("Here are your existing reservations...\n");
		System.out.println(cleanUp(resList));
		
		// get seat info
		System.out.print("Enter the row # of the seat you wish to unreserve: ");
		String rowNum = input.nextLine();
		System.out.print("Now, the column #: ");
		String colNum = input.nextLine();
		
		try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM reservation " +
															  "WHERE row_num = ? AND col_num = ?"))
		{
			pstmt.setInt(1, Integer.parseInt(rowNum));
			pstmt.setInt(2, Integer.parseInt(colNum));
			
			pstmt.executeUpdate();
			this.theater[Integer.parseInt(rowNum)-1][Integer.parseInt(colNum)-1] = "A";
			System.out.println("Your reservation has been cancelled. Thank you!");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void changeReservation()
	{
		System.out.println("Let's change a reservation for you.");
		System.out.println("First we'll cancel the one you want to change, and then create a new one.");
		deleteReservation();
		addReservation();
	}
	
	public void viewTheater()
	{
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM reservation"))
		{
			while (rs.next())
			{
				int row = rs.getInt("row_num");
				int col = rs.getInt("col_num");
				this.theater[row-1][col-1] = "-";
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String row = 1 + "|";
		System.out.println("  1 2 3 4 5");
		
		for (int i = 0; i < 5; i++)
		{
			if (i > 0)
				row += (i+1) + "|";
			for (int j = 0; j < 5; j++)
			{
				row += theater[i][j] + " ";
			}
			row = row.trim() + "|\n";
		}
		System.out.println(row);
	}	
	public String cleanUp(List<Reservation> r)
	{
		return r.toString().replace("[", "").replace("]", "").replace(", ", "");
	}
	
	public boolean isAvailable(String row, String column)
	{
		if (theater[Integer.parseInt(row) - 1][Integer.parseInt(column) - 1].equals("A"))
			return true;
		return false;
	}
	
	public String getName(Scanner sc)
	{
		String resName = sc.nextLine();
		
		while (!resName.matches("^[a-zA-Z]+$"))
		{
			System.out.println("Not a valid name. Try again.");
			System.out.print("What name are your reservations under: ");
			resName = sc.nextLine();
		}
		return resName;
	}
}





