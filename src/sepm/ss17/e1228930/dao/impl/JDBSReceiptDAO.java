package sepm.ss17.e1228930.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.DBManager;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.dao.ReceiptDAO;
import sepm.ss17.e1228930.domain.Receipt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class JDBSReceiptDAO implements ReceiptDAO{
    public static final Logger logger= LoggerFactory.getLogger(JDBSReceiptDAO.class);
    private static JDBSReceiptDAO singleInstance=null;

    private JDBSReceiptDAO(){}

    public static JDBSReceiptDAO getInstance(){
        if(singleInstance==null){
            singleInstance=new JDBSReceiptDAO();
        }
        return singleInstance;
    }
    @Override
    public void create(Receipt receipt) throws IllegalArgumentException {
        logger.debug("Entering create method");
        int rowsAffected= 0; //Affected Rows by the insert;

        if(receipt==null){
            throw new IllegalArgumentException("NULL Value");
        }
        PreparedStatement pstm= null;
        try{
            pstm= DBManager.getInstance().prepareStatement("INSERT INTO Receipt(R_id, bid,totalPreis, clientName, start) VALUES(?,?,?,?, ?);");
            pstm.setInt(1,receipt.getR_id());
            pstm.setInt(2,receipt.getBid());
            pstm.setDouble(3,receipt.getTotalPreis());
            pstm.setString(4,receipt.getClientName());
            pstm.setTimestamp(5, receipt.getStart());

            rowsAffected= pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        logger.debug("inserted "+ rowsAffected + " Receipt in the database: {}", receipt);
        pstm=null;
    }

    @Override
    public void delete(Receipt receipt) throws NotFoundException { //receipt oder reservation?
        logger.debug("Entering delete methode");
        ResultSet rs=null;
        try {
            PreparedStatement stmt = DBManager.getInstance().prepareCall("UPDATE receipt SET isdeleted = TRUE WHERE receipt_id = ?;");
            stmt.setInt(1, receipt.getReceipt_id());
            stmt.executeUpdate();

                logger.debug("Receipt{} is deleted, receipt");

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(Receipt receipt) throws NotFoundException {
        logger.debug("Entering Update methode");
        ResultSet rs=null;
        try {

            PreparedStatement stmt = DBManager.getInstance().prepareCall("UPDATE receipt set totalpreis = ?, start = ? WHERE receipt_id = ?;");
            stmt.setDouble(1, receipt.getTotalPreis());
            stmt.setTimestamp(2, receipt.getStart());
            stmt.setInt(3, receipt.getReceipt_id());
            stmt.executeUpdate();

            logger.debug("Receipt{} is updated, receipt");

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Receipt> showAll() throws NotFoundException {
        logger.debug("Entering show all method");
        ArrayList<Receipt> allReceipts= new ArrayList<Receipt>();
        try{
            PreparedStatement preparedStatement= DBManager.getInstance().prepareStatement("SELECT receipt.r_id, receipt.bid, receipt.receipt_id, receipt.totalpreis, receipt.clientname, receipt.start FROM receipt where receipt.isdeleted = FALSE;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                allReceipts.add(new Receipt(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getString(5), rs.getTimestamp(6)));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return allReceipts;
    }
}
