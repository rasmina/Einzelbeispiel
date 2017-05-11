package sepm.ss17.e1228930.gui;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.gui.GUIEntities.BoxProperty;
import sepm.ss17.e1228930.service.ReceiptService;
import sepm.ss17.e1228930.service.ReservationService;
import sepm.ss17.e1228930.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1228930.service.impl.ReservationServiceImpl;
import sun.applet.Main;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


public class ViewReservationBoxController {
    private static final Logger logger= LoggerFactory.getLogger(ViewReservationBoxController.class);

    @FXML
    private Label Id;
    @FXML
    private Label clientNameField;
    @FXML
    private Label horseNameField;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private TableColumn<Reservation, String> clientName;
    @FXML
    private TableColumn<Reservation, String> horseName;

    @FXML
    private TableView<Reservation> reservation;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;


    private Timestamp startDateStamp;
    private Timestamp endDateStamp;

    ObservableList<Reservation> resData = FXCollections.observableArrayList();
    private ReceiptService receiptService= ReceiptServiceImpl.getInstance();



    private Stage dialogStage;
    private MainApp mainApp;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage= dialogStage;
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        reservation.setItems(mainApp.updateReservation());
    }


    @FXML
    private void initialize(){
        clientName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getClientName()));
        horseName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getHorseName()));

        viewReservation(null);

        reservation.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> viewReservation(newValue)));

    }

    private void viewReservation(Reservation reservation){

        if (reservation != null)
        {
            Id.setText(Integer.toString(reservation.getR_id()));
            clientNameField.setText(reservation.getClientName());
            horseNameField.setText(reservation.getHorseName());
            startDate.setText(reservation.getStart().toString());
            endDate.setText(reservation.getende().toString());
        }
        else
        {
            Id.setText("");
            clientNameField.setText("");
            horseNameField.setText("");
            startDate.setText("");
            endDate.setText("");
        }

    }

    /**
     * Search the reserved boxes in the given period of time.
     * @throws NotFoundException
     */
    @FXML
    public void onSearchClicked() throws NotFoundException{
        ReservationService rs= ReservationServiceImpl.getInstance();
        startDateStamp= Timestamp.valueOf(startDatePicker.getValue() +" " + "00:01:00");
        endDateStamp= Timestamp.valueOf(endDatePicker.getValue()+ " " + "23:59:59");

        ArrayList<Reservation> narrowedList= rs.reservationPeriod(startDateStamp, endDateStamp);
        if(!narrowedList.isEmpty()){
            for (Reservation r: narrowedList) {
                resData.add(r);
            }
            reservation.setItems(resData);
        }else{
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("There are no available Boxes! Try another box!");
            alert.showAndWait();
        }
    }

    /**
     * Edit the selected Reservation but the reservation must be after today's date
     * You can not reserve before the today date
     */
    @FXML
    public void onEditClicked(){
        try {
            Reservation selectedReservation = reservation.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                if(!selectedReservation.getStart().toLocalDateTime().toLocalDate().isBefore(LocalDate.now()) &&
                        !selectedReservation.getStart().toLocalDateTime().toLocalDate().isEqual(LocalDate.now())) {
                    mainApp.showEditDialog(selectedReservation);

                    //Show the edited reservation
                    refreshReservationTable();
                }else{
                    Alert alert= new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText("The reservation has already been certified!");
                    alert.showAndWait();
                }
            }else{
                Alert alert= new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING!");
                alert.setHeaderText("Invalid Selection");
                alert.setContentText("Please be sure you have selected the reservation you want to edit!");
                alert.showAndWait();
            }
        }catch (NotFoundException e){
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }catch (IOException i) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText(i.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Delete the selected reservation
     * @throws NotFoundException
     */
    @FXML
    private void onDeleteClicked() throws NotFoundException{
        Reservation selectedReservestion = reservation.getSelectionModel().getSelectedItem();
        if(selectedReservestion!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to delete the selected reservation?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if(selectedReservestion.getStart().toLocalDateTime().toLocalDate().isAfter(LocalDate.now())) {
                    ReservationServiceImpl.getInstance().deleteReservation(selectedReservestion);
                    refreshReservationTable();

                    //it should be deleted from receipt too
                    receiptService.delete(selectedReservestion);
                    alert.close();
                }else {
                    Alert alert1= new Alert(Alert.AlertType.WARNING);
                    alert1.setTitle("WARNING");
                    alert1.setContentText("You cannot delete the reservation! It is already certified!");
                    alert1.showAndWait();
                }
            } else {
                alert.close();
            }

            //the reservation must not be present in the view reservation tables now
            refreshReservationTable();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid selection!");
            alert.setContentText("Please make sure you selected the reservation you want to delete!");
            alert.showAndWait();
        }
    }


    /**
     * reconstructs the observable list, so that it appears correctly in the table view,
     * in case some records have been deleted from or edited in the database
     */
    public void refreshReservationTable() throws NotFoundException {

        ReservationService rs= ReservationServiceImpl.getInstance();
        ArrayList<Reservation> allReservation = rs.showAll();

        resData = FXCollections.observableArrayList();

        for (Reservation r : allReservation) {
            if (!r.isDeleted())
                resData.add(r);
        }

        reservation.setItems(resData);
    }



    @FXML
    private void exit(){
        dialogStage.close();
    }
}
