package sepm.ss17.e1228930.test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import sepm.ss17.e1228930.dao.DBManager;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Receipt;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.service.BoxService;
import sepm.ss17.e1228930.service.ReceiptService;
import sepm.ss17.e1228930.service.ReservationService;
import sepm.ss17.e1228930.service.impl.BoxServiceImpl;
import sepm.ss17.e1228930.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1228930.service.impl.ReservationServiceImpl;

import javax.xml.bind.ValidationException;

public class ServiceTest {

   private static ReservationService rs= new ReservationServiceImpl();
    private static Reservation reservation;
    private static ReceiptService receiptService= new ReceiptServiceImpl();
    private static BoxService bs= new BoxServiceImpl();
    private static Box box;
    private static Connection connection;

    private static Receipt receipt;

    /**
     * Get the connection to database and create a new box, reservation and receipt
     * @throws SQLException
     * @throws NotFoundException
     */
    @BeforeClass
    public static void beforeMethode() throws SQLException,NotFoundException{
        connection= DBManager.getInstance();
        connection.setAutoCommit(false);
        box= new Box(false,19,true,"straw", "Kiko", 15, "inside",14, "/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/1.jpg");

        reservation=new Reservation(1,"Andy", "Rebeca", Timestamp.valueOf("2017-04-11 13:23:44"), Timestamp.valueOf("2017-04-11 15:23:44"));

        receipt= new Receipt(1,19,2,28,"Andy", Timestamp.valueOf("2017-04-11 13:23:44"));

    }

    /**
     * Test making a reservation
     * @throws NotFoundException
     * @throws ValidationException
     */
    @Test
    public void reserve() throws NotFoundException,ValidationException{
        int size= rs.showAll().size();
        rs.reserve(reservation);
        bs.create(box);
        assertEquals(size+1, rs.showAll().size());
    }

    /**
     * Update the reservation
     * @throws NotFoundException
     */
    @Test
    public void update() throws NotFoundException{
        rs.reserve(reservation);
        reservation.setHorseName("Anil");
        rs.update(reservation);
        assertEquals("Anil", rs.showAll().get(0).getHorseName());
        reservation.setHorseName("Rebeca");
        rs.update(reservation);
    }

    /**
     * Delete the reservation
     * @throws NotFoundException
     */
    @Test
    public void delete() throws NotFoundException{
        int size= rs.showAll().size();
        rs.reserve(reservation);
        assertEquals(size+1, rs.showAll().size());
        rs.deleteReservation(reservation);
        assertEquals(size, rs.showAll().size());
    }

    /**
     *
     * @throws NotFoundException
     */
    @Test(expected = NullPointerException.class)
    public void createReceiptWithNullValue() throws NotFoundException{
        receiptService.create(null,null);
    }

    /**
     * Save the Receipt in database
     * @throws NotFoundException
     */
    @Test
    public void saveReceiptInDatabase() throws NotFoundException{
        int size= receiptService.showAll().size();
        receiptService.create(reservation,box);
        assertEquals(size+1, receiptService.showAll().size());

    }

    /**
     * Delete the receipt
     * @throws NotFoundException
     */
    @Test
    public void deleteReceipt() throws NotFoundException{
        int size= receiptService.showAll().size();
        receiptService.create(reservation,box);
        assertEquals(size+1, receiptService.showAll().size());
        receiptService.delete(reservation);
        assertEquals(size, receiptService.showAll().size());
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
