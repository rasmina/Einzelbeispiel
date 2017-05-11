package sepm.ss17.e1228930.test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.SQLException;

import sepm.ss17.e1228930.dao.DBManager;
import sepm.ss17.e1228930.dao.impl.*;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.dao.NotFoundException;


public class DAOTest {
    private static Connection connection;
    private static JDBSBoxDAO boxDAO;
    private static Box box;

    /**
     * Get the connection with the database and create a new box
     * @throws SQLException
     * @throws NotFoundException
     */

    @BeforeClass
    public static void beforeMethode() throws SQLException,NotFoundException{
        connection= DBManager.getInstance();
        connection.setAutoCommit(false);
        boxDAO= JDBSBoxDAO.getInstance();
        box= new Box(false,19,true,"straw", "Kiko", 15, "inside",14, "/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/1.jpg");
    }

    /**
     * If the box is saved in the database,
     * the size of the array list with boxes is changed +1
     */

    @Test
    public void saveInDatabase() throws SQLException,NotFoundException{
        int size= boxDAO.findAll().size();
        boxDAO.create(box);
        assertEquals(size+1, boxDAO.findAll().size());
    }

    /**
     * It should be thrown a IllegalArgumentException
     * @throws SQLException
     * @throws NotFoundException
     */

    @Test(expected = IllegalArgumentException.class)
    public void createBoxWithNullValue() throws SQLException,NotFoundException{
        boxDAO.create(null);
    }

    /**
     *
     * @throws SQLException
     * @throws NotFoundException
     */
    @Test
    public void readDataBox() throws SQLException,NotFoundException{
        boxDAO.create(box);
        int id=boxDAO.findAll().get(boxDAO.findAll().size()-1).getBid();
       assertEquals(true,boxDAO.search(id));

    }

    /**
     * Test update Box
     * @throws SQLException
     * @throws NotFoundException
     */
    @Test
    public void updateBox() throws SQLException,NotFoundException{

        boxDAO.create(box);
        box.setHorseName("Nimo");
        boxDAO.update(box);
        assertEquals("Nimo", boxDAO.findAll().get(0).getHorseName());
        box.setHorseName("Kiko");
        boxDAO.update(box);
    }

    /**
     * Test delete box
     * @throws SQLException
     * @throws NotFoundException
     */
    @Test
    public void delete() throws SQLException,NotFoundException{
        int size= boxDAO.findAll().size();
        boxDAO.create(box);
        assertEquals(size+1, boxDAO.findAll().size());
        boxDAO.delete(box);
        assertEquals(size,boxDAO.findAll().size());
    }


    /**
     *
     * @throws SQLException
     */
    @After
    public void doRollback() throws SQLException{
        connection.rollback();
    }

    /**
     * Close the connection
     * @throws SQLException
     */
    @AfterClass
    public static void connectionClosed() throws SQLException{
        connection.close();
    }



}
