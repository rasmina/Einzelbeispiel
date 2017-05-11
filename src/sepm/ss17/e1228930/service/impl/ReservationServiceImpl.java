package sepm.ss17.e1228930.service.impl;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.dao.ReservationDAO;
import sepm.ss17.e1228930.dao.impl.JDBSReservationDAO;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.service.BoxService;
import sepm.ss17.e1228930.service.ReservationService;

import java.lang.Long;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by rasmina on 11/03/2017.
 */
public class ReservationServiceImpl implements ReservationService{
    private static final Logger logger= LoggerFactory.getLogger(ReservationServiceImpl.class);
    public static ReservationServiceImpl singleInstance;
    public  ReservationDAO rd= JDBSReservationDAO.getInstance();
    private BoxService boxService = BoxServiceImpl.getInstance();

    public static ReservationService getInstance(){
        if(singleInstance==null){
            singleInstance= new ReservationServiceImpl();
        }
        return singleInstance;
    }
    @Override
    public void create(Reservation reservation) throws NotFoundException{
        logger.debug("Entering createServiceMethode with {}", reservation);
        reserve(reservation);
    }

    @Override
    public void update(Reservation reservation) throws NotFoundException {
        logger.debug("Entering updateService Reservation Methode with {}", reservation);
            validate(reservation);
            try {
                rd.update(reservation);
            }catch (NotFoundException es)
            {
                logger.debug("Could not Reserve the Box");
            }

    }

    @Override
    public void reserve(Reservation reservation) throws NotFoundException{
        logger.debug("Entering reserve service Reservation");
        validate(reservation);
        try {

            rd.reserve(reservation);
        }catch (NotFoundException es)
        {
            logger.error("Couldn't reserve the Box");
        }

    }

    @Override
    public ArrayList<Reservation> showAll() throws NotFoundException{
        logger.debug("Entering show all reservation methode");
        return rd.showAll();
    }

    @Override
    public ArrayList<Reservation> reservationPeriod(Timestamp start, Timestamp ende) throws NotFoundException {
        logger.debug("Finding the reservations in a given period from {} to {}", start, ende);
        return rd.reservationPeriod(start,ende);
    }
    @Override
    public void deleteReservation(Reservation reservation) throws NotFoundException{
        logger.debug("Deleteing the reservation {}", reservation);
        rd.deleteReservation(reservation);
    }
    @Override
    public void checkHorseAvailability(Reservation reservation) throws NotFoundException{
        logger.debug("Checking the availability of a box to reserve");
        validate(reservation);
        for (int i = 0; i <rd.showAll().size() ; i++) {
            if (reservation.getStart().before(rd.showAll().get(i).getStart()) &&reservation.getende().after(rd.showAll().get(i).getStart()) && reservation.getHorseName().equals(rd.showAll().get(i).getHorseName()) ||
                    reservation.getStart().after(rd.showAll().get(i).getStart()) && reservation.getStart().before(rd.showAll().get(i).getende()) && reservation.getHorseName().equals(rd.showAll().get(i).getHorseName())||
                    reservation.getStart().before(rd.showAll().get(i).getende()) && reservation.getende().after(rd.showAll().get(i).getende())&& reservation.getHorseName().equals(rd.showAll().get(i).getHorseName())) {

                throw new NotFoundException("Box is already reserved");

            }
        }
    }

    @Override
    public XYChart.Series mostbooked(ObservableList<String> horsename) throws NotFoundException {
        logger.debug("Evaluating the statistics {}");
        if (!horsename.isEmpty()) {
                long[] myArray = new long[horsename.size()];
                for (int i = 0; i < rd.showAll().size(); i++) {

                    for (int j = 0; j < horsename.size(); j++) {

                        if (horsename.get(j).equals(rd.showAll().get(i).getHorseName()))
                        {
                            long mili = rd.showAll().get(i).getende().getTime() - rd.showAll().get(i).getStart().getTime();
                            long sec =  mili/1000;
                            long hrs = sec/3600;
                            long day = hrs/24;
                            System.out.println(day);
                            myArray[j] = day;
                        }

                    }

                }

                XYChart.Series<String, Long> series = new XYChart.Series<>();
                for (int i = 0; i < myArray.length; i++) {
                    series.getData().add(new XYChart.Data<>(horsename.get(i),myArray[i]));
                }
                return series;
            }else
            {
                logger.error("Failed evaluating the Errors");
            }



        return null;
    }

    @Override
    public void validate(Reservation reservation) throws NotFoundException {
        logger.debug("Validation reservation {}", reservation);
        if(reservation.getHorseName()==null){
            throw new NotFoundException("Horse name is null");
        }
        if(reservation.getHorseName().isEmpty()){
            throw new NotFoundException("Horse name is empty");
        }
        if(reservation.getClientName()==null){
            throw new NotFoundException("Client name is null");
        }
        if(reservation.getClientName().isEmpty()){
            throw new NotFoundException("Client name is empty");
        }
        if(reservation.getStart()==null){
            throw new NotFoundException("Start date is null");
        }
        if(reservation.getende()==null){
            throw new NotFoundException("End date is null");
        }
        if(reservation.getStart().after(reservation.getende())){
            throw new NotFoundException("Start date is after end date");
        }
        if(LocalDate.now().isAfter(reservation.getStart().toLocalDateTime().toLocalDate()))
        {
            throw new NotFoundException("The reservation date is before the current date");
        }
    }
}
