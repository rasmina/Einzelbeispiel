package sepm.ss17.e1228930.dao.impl;

import sepm.ss17.e1228930.dao.BoxDAO;
import sepm.ss17.e1228930.dao.DBManager;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Box;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBSBoxDAO implements BoxDAO {

    public static Logger logger= LoggerFactory.getLogger(JDBSBoxDAO.class);
    private static JDBSBoxDAO singleInstance= null;


    public static JDBSBoxDAO getInstance(){
        if(singleInstance==null){
            singleInstance=new JDBSBoxDAO();
        }
        return singleInstance;
    }


    @Override
    public Box create(Box box) throws IllegalArgumentException {
        logger.debug("Entering createMethod with parameters {}", box);

        int rowsAffected=0;

        if(box==null){
            throw new IllegalArgumentException("NULL Value");
        }
        PreparedStatement pstm= null;
        try{
            pstm= DBManager.getInstance().prepareStatement("INSERT INTO Box VALUES (?,?,?,?,?,?,?,?,?);");
            pstm.setBoolean(1,box.isDeleted());
            pstm.setInt(2,box.getBid());
            pstm.setBoolean(3, box.isWindow());
            pstm.setString(4, box.getLitter());
            pstm.setString(5,box.getHorseName());
            pstm.setDouble(6, box.getSize());
            pstm.setString(7, box.getLocation());
            pstm.setDouble(8,box.getPreisHour());
            pstm.setString(9,box.getPicture());
            rowsAffected=pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        logger.debug("inserted "+ rowsAffected + " Box in the database: {}", box);
        pstm=null;
        return box;
    }

    @Override
    public ArrayList<Box> find(boolean window) throws NotFoundException {
        logger.debug("Searching for boxes having windows");
        ArrayList<Box> all = findAll();
        ArrayList<Box> narrowed = new ArrayList<Box>();
        for(Box b: all){
            if(b.isWindow() == window){
                narrowed.add(b);
            }
        }
        if(narrowed==null){
            throw new NotFoundException();
        }
        return narrowed;
    }

    @Override
    public boolean search(int id) throws NotFoundException {
        logger.debug("Entering searchMethod searching for the Box with the id {}", id);
        PreparedStatement searchStmt;
        ResultSet rs = null;
        try {
            searchStmt = DBManager.getInstance().prepareStatement("SELECT * FROM Box Where bid= ? ;");
            searchStmt.setInt(1,id);
            rs = searchStmt.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try {
            if(!rs.next()) {
                logger.debug("Box with the id {} could NOT be found!", id);
                return false;
            }else{
                logger.debug("Box with the id {} found!", id );
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void update(Box box) throws NotFoundException {
        logger.debug("Entering updateMethod trying to update the box with the params {}", box);
        int rowsAffected;//number of rows affected after the passing through this method

        //if the box could not be found
        if(!this.search(box.getBid())){
            logger.debug("box with the id {} could NOT be found", box.getBid());
            //throw an exception to the upper layer
            throw new NotFoundException();
        }else{
            //if the box was found, update it in the database
            try{
                PreparedStatement updateStmt = DBManager.getInstance().prepareStatement("UPDATE Box SET horseName=?, preisHour=?, location =?, litter=?,picture=?, size=?, window=?  WHERE bid= ? ;");
                updateStmt.setString(1,box.getHorseName());
                updateStmt.setDouble(2,box.getPreisHour());
                updateStmt.setString(3, box.getLocation());
                updateStmt.setString(4,box.getLitter());
                updateStmt.setString(5,box.getPicture());
                updateStmt.setDouble(6,box.getSize());
                updateStmt.setBoolean(7,box.isWindow());
                updateStmt.setInt(8, box.getBid());
                rowsAffected = updateStmt.executeUpdate();
                logger.debug("Updated "+ rowsAffected + " Box in the database: {}", box);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Box box) throws NotFoundException {
        logger.debug("Entering deleteMethod trying to delete the box: {}", box);

        ResultSet rs = null;

        //try to find the box
        try {
            PreparedStatement searchStmt = DBManager.getInstance().prepareStatement("SELECT * FROM Box Where bid=? ;");
            searchStmt.setInt(1, box.getBid());
            rs = searchStmt.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }

        PreparedStatement deleteStmt = null;
        //if it could not be found throw an exception to the upper layer
        try {
            if(!rs.next()) {
                logger.debug("Box {} could NOT be found!", box);
                throw new NotFoundException();


            }else{
                //if it could be found, delete it from the database
                deleteStmt = DBManager.getInstance().prepareStatement("UPDATE Box SET isDeleted = true where bid= ? ;");
                deleteStmt.setInt(1,box.getBid());
                deleteStmt.executeUpdate();
                logger.debug("Box {} was deleted!", box);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        deleteStmt = null;
    }

    @Override
    public ArrayList<Box> findAll() throws NotFoundException {
        logger.debug("Entering findAll method");
        ArrayList<Box> listOfBoxes = new ArrayList<Box>();
        ResultSet rs = null;
        try {
            Statement stmt = DBManager.getInstance().createStatement();
            rs = stmt.executeQuery("SELECT * FROM Box WHERE isdeleted=FALSE ;");

        }catch(SQLException s){
            s.printStackTrace();
        }

        //for every entry found in the database in the table containing the boxes,
        //create a new Object of the corresponding type
        //and add them to a list
        try {
            while (rs.next()) {
                listOfBoxes.add(new Box(rs.getInt(2),rs.getBoolean(3), rs.getString(4), rs.getString(5), rs.getDouble(6),rs.getString(7), rs.getDouble(8), rs.getString(9)));

            }
        }catch(SQLException s){
            s.printStackTrace();
        }
        return listOfBoxes;
    }
}
