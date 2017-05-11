package sepm.ss17.e1228930.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.DBManager;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.dao.ReservationDAO;
import sepm.ss17.e1228930.domain.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class JDBSReservationDAO implements ReservationDAO {
    public static final Logger logger= LoggerFactory.getLogger(JDBSReservationDAO.class);
    private static JDBSReservationDAO singleInstance=null;

    private JDBSReservationDAO(){}

    public static JDBSReservationDAO getInstance(){
        if(singleInstance==null){
            singleInstance=new JDBSReservationDAO();
        }
        return singleInstance;
    }

    private void create(Reservation reservation) throws IllegalArgumentException {
        logger.debug("Entering create Method with parameters {}", reservation);
        int rowsAffected= 0; //Affected Rows by the insert;

        if(reservation==null){
            throw new IllegalArgumentException("NULL Value");
        }
        PreparedStatement pstm= null;
        try{
            pstm= DBManager.getInstance().prepareStatement("INSERT INTO Reservation (clientName, horseName, start, end, isDeleted) Values (?,?,?,?,?); ");
            //pstm.setInt(1,reservation.getR_id());
            pstm.setString(1,reservation.getClientName());
            pstm.setString(2,reservation.getHorseName());
            pstm.setTimestamp(3,reservation.getStart());
            pstm.setTimestamp(4,reservation.getende());
            pstm.setBoolean(5,reservation.isDeleted());

            rowsAffected= pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        logger.debug("inserted "+ rowsAffected + " Horse in the database: {}", reservation);
        pstm=null;
    }
    @Override
    public void reserve(Reservation reservation) throws NotFoundException {
        logger.debug("Entering reserve method");
        create(reservation);

    }

    @Override
    public void update(Reservation reservation) throws NotFoundException {
        logger.debug("Entering update methode {}", reservation);
            int rowAffected;
        try {
            PreparedStatement updateStm = DBManager.getInstance().prepareStatement("UPDATE Reservation SET horsename=?, start=?, end=?, isDeleted = FALSE WHERE r_id=?;");
            updateStm.setString(1, reservation.getHorseName());
            updateStm.setTimestamp(2,reservation.getStart());
            updateStm.setTimestamp(3,reservation.getende());
            updateStm.setInt(4,reservation.getR_id());
            rowAffected= updateStm.executeUpdate();
            logger.debug(rowAffected+" Row affected!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Reservation> showAll() throws NotFoundException {
        logger.debug("Entering showAll method");
        ArrayList<Reservation> listOfReservations= new ArrayList<Reservation>();
        ResultSet rs=null;
        try {
            Statement s= DBManager.getInstance().createStatement();
            rs= s.executeQuery("SELECT * FROM Reservation where isDeleted = FALSE;");

        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            //for every entry found in the database in the table containing the reservations,
            //create a new Object of the corresponding type
            //and add them to a list
            while(rs.next()){
                listOfReservations.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4), rs.getTimestamp(5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listOfReservations;
    }

    @Override
    public ArrayList<Reservation> reservationPeriod(java.sql.Timestamp start, java.sql.Timestamp ende) throws NotFoundException{
        logger.debug("Entering reservationPeriod method");
        ArrayList<Reservation> myReservations= new ArrayList<Reservation>();
        PreparedStatement pstm;
        ResultSet rs=null;
        try{
            pstm=DBManager.getInstance().prepareStatement("SELECT * FROM Reservation WHERE start BETWEEN ? and ? and end BETWEEN ? and ? and isDeleted = FALSE;");
            pstm.setTimestamp(1,start);
            pstm.setTimestamp(2,ende);
            pstm.setTimestamp(3,start);
            pstm.setTimestamp(4,ende);
            rs=pstm.executeQuery();

            while(rs.next()){
                myReservations.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4), rs.getTimestamp(5),rs.getBoolean(6)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return myReservations;
    }

    @Override
    public void deleteReservation(Reservation reservation) throws NotFoundException{
        logger.debug("Entering deleteMethod trying to delete the box: {}", reservation);

        ResultSet rs = null;

        //try to find the reservation
        try {
            PreparedStatement searchStmt = DBManager.getInstance().prepareStatement("SELECT * FROM Reservation Where r_id=? ;");
            searchStmt.setInt(1, reservation.getR_id());
            rs = searchStmt.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }

        PreparedStatement deleteStmt = null;
        //if it could not be found throw an exception to the upper layer
        try {
            if(!rs.next()) {
                logger.debug("Box {} could NOT be found!", reservation);
                throw new NotFoundException();

            }else{
                //if it could be found, delete it from the database
                deleteStmt = DBManager.getInstance().prepareStatement("UPDATE Reservation SET isDeleted = true where r_id= ? ;");
                deleteStmt.setInt(1,reservation.getR_id());
                deleteStmt.executeUpdate();
                logger.debug("Reservation {} was deleted!", reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        deleteStmt = null;
    }

}
