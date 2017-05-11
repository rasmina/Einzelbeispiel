package sepm.ss17.e1228930.service;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Reservation;

import java.util.ArrayList;


public interface ReservationService {

    /**
     * sends the specified Reservation farther in the DAO layer
     * this method calls the corresponding method from the DAO layer
     * @param reservation the reservation that needs to be saved in the DB
     * @throws NotFoundException if the reservation is null
     */
    public void create(Reservation reservation) throws NotFoundException;

    /**
     *
     * @param reservation
     * @throws NotFoundException
     */
    public void reserve(Reservation reservation) throws NotFoundException;

    /**
     * this method calls the corresponding method from the DAO layer
     * @return all the deleted and non-deleted reservations
     * @throws NotFoundException
     */
    public ArrayList<Reservation> showAll() throws NotFoundException;

    /**
     * this method calls the corresponding method from the DAO layer
     * @param start
     * @param ende
     * @return all the reservations in the given period
     * @throws NotFoundException
     */
    public ArrayList<Reservation> reservationPeriod(java.sql.Timestamp start, java.sql.Timestamp ende) throws NotFoundException;

    /**
     * remove the specified reservation from the database
     * this method calls the corresponding method from the DAO layer
     * @param reservation that needs to be deleted
     * @throws NotFoundException
     */
    public void deleteReservation(Reservation reservation) throws NotFoundException;

    /**
     * update the information in the DB of a certain reservation
     * this method calls the corresponding method from the DAO layer
     * @param reservation object contains the information to be updated
     * @throws NotFoundException
     */
    public void update(Reservation reservation) throws NotFoundException;

    /**
     * Validate the reservation
     * @param reservation
     * @throws NotFoundException
     */
    public void validate(Reservation reservation) throws NotFoundException;

    /**
     * Check Horse availability for a reservation
     * @param reservation
     * @throws NotFoundException
     */
    public void checkHorseAvailability(Reservation reservation) throws NotFoundException;

    /**
     * Shows a chart with the most booked boxes(horseName)
     * @param horsename
     * @return
     * @throws NotFoundException
     */
    public XYChart.Series mostbooked(ObservableList<String> horsename) throws NotFoundException;
}
