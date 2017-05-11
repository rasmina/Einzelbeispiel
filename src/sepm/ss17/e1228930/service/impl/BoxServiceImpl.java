package sepm.ss17.e1228930.service.impl;

import sepm.ss17.e1228930.dao.BoxDAO;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.dao.DBManager;
import sepm.ss17.e1228930.dao.impl.JDBSBoxDAO;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.service.BoxService;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;

/**
 * Created by rasmina on 10/03/2017.
 */
public class BoxServiceImpl implements BoxService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DBManager.class);
    private static BoxServiceImpl singleInstance= null;
    private BoxDAO bd= JDBSBoxDAO.getInstance();

    public BoxServiceImpl(){}
    public static BoxServiceImpl getInstance(){
        if(singleInstance==null){
            singleInstance= new BoxServiceImpl();
        }
        return singleInstance;
    }

    @Override
    public Box create(Box b)  throws NotFoundException{
        logger.debug("entering serviceCreateMethod with {}",b);
        validateBox(b);
         return bd.create(b);
    }

    @Override
    public ArrayList<Box> find(boolean window) {
        logger.debug("Searching for boxes having windows");
        ArrayList<Box> narrowed = new ArrayList<Box>();

        try {
            narrowed = bd.find(window);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return narrowed;
    }

    @Override
    public ArrayList<Box> findAll() {
        logger.debug("Entering findAll method");
        ArrayList<Box> all = new ArrayList<Box>();
        try {
            all = bd.findAll();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return all;
    }

    @Override
    public void update(Box b)  {
        logger.debug("Trying to update the box {}",b);
        try {
            validateBox(b);
            bd.update(b);
        }catch (NotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Box b) {
        logger.debug("Trying to delete the box {}", b);
        try {
            bd.delete(b);
        } catch (NotFoundException e) {
            e.printStackTrace();

        }
    }
    private void validateBox(Box box) throws NotFoundException{
            if(box.getHorseName()==null){
                throw new NotFoundException("Box name is null");
            }
            if(box.getHorseName().isEmpty()){
                throw new NotFoundException("Box name is empty");
            }
            if(box.getBid()==0){
                throw new NotFoundException("Box ID name is null");
            }
            if(box.getLocation().isEmpty()){
                throw new NotFoundException("Location is empty");
            }
            if(box.getLitter()==null){
                throw new NotFoundException("Litter is null");
            }
            if(box.getPreisHour()<=0){
                throw new NotFoundException("Price is null");
            }
            if(box.getPicture()==null){
                throw new NotFoundException("Box does not have a picture");
            }
            if(box.getSize()==0) {
                throw new NotFoundException("Box size is null");
            }
    }
}
