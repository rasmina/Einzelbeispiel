package sepm.ss17.e1228930.gui;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.gui.GUIEntities.BoxProperty;
import sepm.ss17.e1228930.service.BoxService;
import sepm.ss17.e1228930.service.ReceiptService;
import sepm.ss17.e1228930.service.ReservationService;
import sepm.ss17.e1228930.service.impl.BoxServiceImpl;
import sepm.ss17.e1228930.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1228930.service.impl.ReservationServiceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BoxReserveDialogController {
    @FXML
    private TextField clientName;
    @FXML
    private ChoiceBox horseName;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;


    private Timestamp startTimeStamp;
    private Timestamp endTimeStamp;

    private static final String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";


    public Stage dialogStage;
    private MainApp mainApp;
    private Pattern pattern;
    private Matcher matcher;

    private Reservation reservation;
    private ReservationService rs = ReservationServiceImpl.getInstance();
    private ReceiptService receiptService = ReceiptServiceImpl.getInstance();
    private BoxService boxService = BoxServiceImpl.getInstance();



    public void setMainApp(MainApp mainApp) throws NotFoundException{
        this.mainApp=mainApp;
        for (Box box: boxService.findAll()) {
            horseName.getItems().add(box.getHorseName());
        }
        pattern = Pattern.compile(TIME_PATTERN);
    }

    /**
     * Sets the stage of this dialog
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage){ this.dialogStage= dialogStage; }

    /**
     * Reserve a box. A box can be reserved if it is free,
     * it is not reserved in this time.
     * Another reservation can be made if prefered.
     */
    @FXML
    public void handleReserve(){
        try {
            if (validateInput(startTime.getText()))
            startTimeStamp = Timestamp.valueOf(startDate.getValue() + " " + startTime.getText());
            else
            {
                Alert alert= new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Input for start date is invalid!");
                alert.showAndWait();
            }
            if (validateInput(endTime.getText()))
            endTimeStamp = Timestamp.valueOf(endDate.getValue() + " " + endTime.getText());
            else
            {
                Alert alert= new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Input for end date is invalid!");
                alert.showAndWait();
            }

            reservation = new Reservation(1, clientName.getText(), horseName.getValue().toString(), startTimeStamp, endTimeStamp);


            rs.checkHorseAvailability(reservation); //if box reserved is then you cannot reserve it(dialog)
            rs.reserve(reservation);

            //Reservation appears in reservationData
            mainApp.reservationData.add(reservation);

            //show in receipt
           for (int i = 0; i < mainApp.updateBox().size(); i++) {
               if (mainApp.data.get(i).getHorseName().equals(horseName.getValue().toString())) {
                    receiptService.create(rs.showAll().get(rs.showAll().size()-1), mainApp.data.get(i));
                }

            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to make another Reservation");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                startTime.deleteText(0, startTime.getLength());
                endTime.deleteText(0, endTime.getLength());
            } else {
                dialogStage.close();
            }
        }catch (NotFoundException e){
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    private boolean validateInput(final String time) {

        matcher = pattern.matcher(time);
        return matcher.matches();
    }

    /**
     * Close the dialog stage
     */
    public void handleCancel(){
        dialogStage.close();
    }
}
