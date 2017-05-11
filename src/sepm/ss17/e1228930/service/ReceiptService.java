package sepm.ss17.e1228930.service;

import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.domain.Receipt;
import sepm.ss17.e1228930.domain.Reservation;

import java.util.ArrayList;


public interface ReceiptService  {

    /**
     *
     * this method calls the corresponding method from the DAO layer
     * @param reservation
     * @param box
     * @throws IllegalArgumentException
     * @throws NotFoundException
     */
    public void create(Reservation reservation, Box box) throws IllegalArgumentException, NotFoundException;

    /**
     *
     * @param reservation that should be deleted
     * @throws NotFoundException
     */
    public void delete(Reservation reservation) throws NotFoundException;

    /**
     * this method calls the corresponding method from the DAO layer
     * @return all the receipts
     * @throws NotFoundException
     */
    public ArrayList<Receipt> showAll() throws NotFoundException;

    /**
     * Validate the receipt
     * @param receipt
     * @throws NotFoundException
     */
    public void validate(Receipt receipt) throws NotFoundException;

    /**
     * Update the receipt (new totalPrice)
     * when the price of the box is changed
     * @param box with the new price
     * @throws NotFoundException
     */
    public void update(Box box) throws NotFoundException;

    /**
     * Update the receipt (new totalPrice)
     * when the dates for the reservation are changed.
     * @param reservation with the new dates
     * @throws NotFoundException
     */
    public void update(Reservation reservation) throws NotFoundException;


}
