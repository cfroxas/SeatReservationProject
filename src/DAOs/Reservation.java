package DAOs;

public class Reservation
{
	private String name;
	private int rowNumber;
	private int columnNumber;
	
	public Reservation(String name, int rowNumber, int columnNumber)
	{
		if (rowNumber < 1 || rowNumber > 5 || columnNumber < 1 || columnNumber > 5)
			throw new IndexOutOfBoundsException("Cannot have a non-existent seat number");
		this.name = name;
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getRowNumber()
	{
		return this.rowNumber;
	}
	
	public void setRowNumber(int number)
	{
		this.rowNumber = number;
	}
	
	public int getColumnNumber()
	{
		return this.columnNumber;
	}
	
	public void setColumnNumber(int number)
	{
		this.columnNumber = number;
	}
	
	public String toString()
	{
		return "Name: " + this.name + "\n" +
			   "Row#: " + this.rowNumber + "\n" +
			   "Col#: " + this.columnNumber + "\n\n";
	}
}


















