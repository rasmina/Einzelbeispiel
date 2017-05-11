package sepm.ss17.e1228930.dao;

import sepm.ss17.e1228930.domain.Receipt;

import java.util.ArrayList;


public interface ReceiptDAO {

    /**
     * Create a new receipt
     * @param receipt
     * @throws IllegalArgumentException
     */
    public void create(Receipt receipt) throws IllegalArgumentException;

    /**
     * Show the list of the receipt
     * @return all the receipts
     * @throws NotFoundException
     */
    public ArrayList<Receipt> showAll() throws NotFoundException;

    /**
     * Delete a receipt
     * @param receipt
     * @throws NotFoundException
     */
    public void delete(Receipt receipt) throws NotFoundException;

    /**
     * Update the receipt
     * @param receipt
     * @throws NotFoundException
     */
    public void update(Receipt receipt) throws NotFoundException;

}
