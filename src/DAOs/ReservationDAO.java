package DAOs;

import java.util.List;

public interface ReservationDAO
{
	public List<Reservation> getReservationsByName();
	public void addReservation();
	public void deleteReservation();
	public void changeReservation();
}
