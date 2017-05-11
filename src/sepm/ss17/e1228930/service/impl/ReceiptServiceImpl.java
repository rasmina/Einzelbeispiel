package sepm.ss17.e1228930.service.impl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.control.Alert;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.BoxDAO;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.dao.ReceiptDAO;
import sepm.ss17.e1228930.dao.ReservationDAO;
import sepm.ss17.e1228930.dao.impl.JDBSBoxDAO;
import sepm.ss17.e1228930.dao.impl.JDBSReceiptDAO;
import sepm.ss17.e1228930.dao.impl.JDBSReservationDAO;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.domain.Receipt;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.service.BoxService;
import sepm.ss17.e1228930.service.ReceiptService;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by rasmina on 11/03/2017.
 */
public class ReceiptServiceImpl implements ReceiptService {
    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(ReceiptServiceImpl.class);

    private static ReceiptServiceImpl singleInstance= null;
    private ReceiptDAO rd= JDBSReceiptDAO.getInstance();
    private BoxService boxService= BoxServiceImpl.getInstance();
    private ReservationDAO reservationd= JDBSReservationDAO.getInstance();

    public static ReceiptServiceImpl getInstance(){
        if(singleInstance==null){
            singleInstance= new ReceiptServiceImpl();
        }
        return singleInstance;
    }

    /**
     * Create a receipt with the given reservation and box
     * @param reservation
     * @param box
     */
    @Override
    public void create(Reservation reservation, Box box)  {
        logger.debug("Entering serviceCreateMethod with {}",reservation,box);
        Receipt receipt= new Receipt(reservation.getR_id(),box.getBid(),0, calculation(reservation,box),reservation.getClientName(), reservation.getStart());
        try {
            validate(receipt);
            rd.create(receipt);
        }catch (NotFoundException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(Reservation reservation) throws NotFoundException {
        logger.debug("Entering delete receipt methode");


                for (int j = 0; j < rd.showAll().size(); j++) {
                    if (rd.showAll().get(j).getR_id() == reservation.getR_id()) {
                        Receipt receipt = rd.showAll().get(j);
                        rd.delete(receipt);
                        return;
                    }
                }

            logger.error("The receipt can not be deleted ");
    }

    @Override
    public ArrayList<Receipt> showAll() throws NotFoundException {
        logger.debug("Entering showAll method");
        ArrayList<Receipt> receiptArrayList = new ArrayList<>();


        for (int i = 0; i < rd.showAll().size(); i++) {

            if (rd.showAll().get(i).getStart().toLocalDateTime().toLocalDate().isBefore(LocalDate.now()) ||
                    rd.showAll().get(i).getStart().toLocalDateTime().toLocalDate().isEqual(LocalDate.now()))
            {
                receiptArrayList.add(rd.showAll().get(i));
            }

        }
         return receiptArrayList;
    }
    @Override
    public void validate(Receipt receipt) throws NotFoundException{
        logger.debug("Validating the receipt");
        if(receipt.getClientName()==null){
            throw new NotFoundException("Receipt name is null");
        }
        if(receipt.getTotalPreis()==0){
            throw new NotFoundException("Price is 0");
        }
    }

    @Override
    public void update(Box box) throws NotFoundException {
        logger.debug("Entering update Receipt");
        for (int i = 0; i < rd.showAll().size(); i++) {

            if (rd.showAll().get(i).getBid() == box.getBid()) {
                for (int j = 0; j < reservationd.showAll().size(); j++) {

                    if (reservationd.showAll().get(j).getR_id() == rd.showAll().get(i).getR_id()) {
                       Receipt receipt =  rd.showAll().get(i);
                       receipt.setTotalPreis(calculation(reservationd.showAll().get(j),box));
                        rd.update(receipt);
                    }

                }
            }
        }
    }

    @Override
    public void update(Reservation reservation) throws NotFoundException {
        logger.debug("Entering update Receipt for Reservation");
        for (int i = 0; i <rd.showAll().size() ; i++) {
            if(reservation.getR_id()==rd.showAll().get(i).getR_id()){
                for (int j = 0; j <boxService.findAll().size() ; j++) {
                    if(boxService.findAll().get(j).getBid()== rd.showAll().get(i).getBid()){
                        Receipt receipt =  rd.showAll().get(i);
                        receipt.setTotalPreis(calculation(reservation,boxService.findAll().get(i)));
                        rd.update(receipt);
                    }
                }
            }
        }
    }

    /**
     * Calculate the total price of the receipt
     * @param reservation
     * @param box which is reserved
     * @return
     */
    private double calculation(Reservation reservation, Box box) {
        double totalPrice = 0;
        long seconds = (reservation.getende().getTime() - reservation.getStart().getTime()) / 1000;
        int hours = (int) (seconds / 3600);

        totalPrice = box.getPreisHour() * hours;
        return totalPrice;

    }

}
