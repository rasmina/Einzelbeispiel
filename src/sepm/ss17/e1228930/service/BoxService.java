package sepm.ss17.e1228930.service;

import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Box;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;


public interface BoxService {
    /**
     * sends the specified box farther in the DAO layer
     * this method calls the corresponding method from the DAO layer
     *
     * @param b the Box that needs to be saved in the DB
     * @throws NotFoundException if the specified box is NULL
     */
    public Box create(Box b) throws NotFoundException;

    /**
     * find all the boxes with window
     * this method calls the corresponding method from the DAO layer
     *
     * @param window
     * @return ArrayList containing all the boxes that fullfill the constraint
     */
    public ArrayList<Box> find(boolean window);

    /**
     * this method calls the corresponding method from the DAO layer
     *
     * @return an ArrayList containing all deleted and non-deleted Boxes
     */
    public ArrayList<Box> findAll();

    /**
     * update the information in the DB of a certain box
     * this method calls the corresponding method from the DAO layer
     *
     * @param b object containing the info that needs to be updated
     * @throws javax.xml.bind.ValidationException if the box contains invalid attributes
     */
    public void update(Box b) throws ValidationException;

    /**
     * remove the specified box from the database
     * this method calls the corresponding method from the DAO layer
     *
     * @param b box that needs to be deleted from the DB
     */
    public void delete(Box b);

}
