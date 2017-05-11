package sepm.ss17.e1228930.dao;

import sepm.ss17.e1228930.domain.Reservation;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public interface ReservationDAO {

    /**
     * Make a reservation
     * @param reservation
     * @throws NotFoundException
     */
    public void reserve(Reservation reservation) throws NotFoundException;

    /**
     * Show all the reservation
     * @return a list of all the reservations
     * @throws NotFoundException
     */
    public ArrayList<Reservation> showAll() throws NotFoundException;

    /**
     * Show all the reservations for a given Period
     * @param start of the reservation
     * @param ende of the reservation
     * @return a list of all the reservations in this period
     */
    public ArrayList<Reservation> reservationPeriod(java.sql.Timestamp start, java.sql.Timestamp ende) throws NotFoundException;

    /**
     * Delete a reservation
     * @param reservation that has to be deleted
     * @throws NotFoundException
     */
    public void deleteReservation(Reservation reservation) throws NotFoundException;

    /**
     * Update the given reservation
     * @param reservation object that has the info to be updated
     * @throws NotFoundException
     */
    public void update(Reservation reservation) throws NotFoundException;

}
