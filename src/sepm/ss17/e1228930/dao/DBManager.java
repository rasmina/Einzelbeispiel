package sepm.ss17.e1228930.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//singleton
public class DBManager {
    private static Connection singleCon;
    private static final Logger logger = LoggerFactory.getLogger(DBManager.class);
    private DBManager(){}

    public static Connection getInstance(){
        //if the connection was not made before try to make a new one
        if(singleCon==null){
            try{
                Class.forName("org.h2.Driver");
                logger.debug("Loading driver successful!");
            }catch (Exception e){
                logger.debug("Loading driver failed!");
                e.printStackTrace();
            }
            try{
                singleCon= DriverManager.getConnection("jdbc:h2:tcp://localhost/~/pferdepension", "sa", "");
                logger.debug("Connection to H2 Database successful!");
            }catch (SQLException s){
                logger.debug("Connection to H2 Database failed!");
                s.printStackTrace();
            }
        }
        //return the only existent connection to the database
        return singleCon;
    }

    public void close() throws Exception{
        if(singleCon!=null && !singleCon.isClosed())
            singleCon.close();
        logger.debug("Connection to H2 Database closed successfully!");
    }

}
