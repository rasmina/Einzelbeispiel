package sepm.ss17.e1228930.dao;

import sepm.ss17.e1228930.domain.Box;

import java.util.ArrayList;


public interface BoxDAO {
    /**
     * saves the Box Box in the database
     *

     * @param box the Box that needs to be saved in the DB
     * @throws java.lang.IllegalArgumentException if the specified Box is NULL
     */
    public Box create(Box box) throws IllegalArgumentException;


    /**
     * find all the boxes with window
     * @param window
     * @return ArrayList containing all the boxes that fullfill the constraint
     */
    public ArrayList<Box> find(boolean window) throws NotFoundException;

    /**
     * search in the database for a specific Box
     *
     * @param id the unique id of the box to be searched after
     * @return  true when found
     *          NotFoundException when not found
     * @throws sepm.ss17.e1228930.dao.NotFoundException
     */
    public boolean search(int id) throws NotFoundException;

    /**
     * update the information in the DB of a certain Box
     *
     * @param box object containing the info that needs to be updated
     * @throws sepm.ss17.e1228930.dao.NotFoundException if such a Box could not be found
     */
    public void update(Box box) throws NotFoundException;

    /**
     * remove the specified Box from the database
     *
     * @param box
     * @throws sepm.ss17.e1228930.dao.NotFoundException if such a Box could not be found
     */
    public void delete(Box box) throws NotFoundException;

    /**
     * Find all the boxes
     * @return an ArrayList all deleted and non-deleted Boxes
     */
    public ArrayList<Box> findAll() throws NotFoundException;


}
