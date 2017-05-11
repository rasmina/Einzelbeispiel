package sepm.ss17.e1228930.gui;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.service.ReceiptService;
import sepm.ss17.e1228930.service.ReservationService;
import sepm.ss17.e1228930.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1228930.service.impl.ReservationServiceImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;


public class EditReservationDialogController {
    @FXML
    private TextField id;
    @FXML
    private TextField clientName;
    @FXML
    private ChoiceBox horseName;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;

    private Reservation reservation;

    private Timestamp startTimestamp;
    private Timestamp endTimestamp;
    private MainApp mainApp;


    private Stage dialogStage;
    private ViewReservationBoxController controller= new ViewReservationBoxController();

    private ReservationService rs= ReservationServiceImpl.getInstance();
    private ReceiptService receiptService= ReceiptServiceImpl.getInstance();
    private boolean okClicked;

    public void setMainApp(MainApp mainApp){
        this.mainApp=mainApp;
        if (!horseName.getItems().isEmpty())
            horseName.getItems().clear();
        for (int i = 0; i <mainApp.data.size() ; i++) {
            horseName.getItems().add(mainApp.updateBox().get(i).getHorseName());
        }
    }

    public void setDialogStage(Stage stage){
        this.dialogStage=stage;
    }

    public void setReservation(Reservation r){
        id.setText(Integer.toString(r.getR_id()));
        clientName.setText(r.getClientName());
        startDate.setText(r.getStart().toString());
        endDate.setText(r.getende().toString());
        clientName.setEditable(false);
        id.setEditable(false);
        this.reservation=r;
    }

    @FXML
    private void handleOk(){

        startTimestamp= Timestamp.valueOf(startDate.getText());
        endTimestamp = Timestamp.valueOf(endDate.getText());
        try {
            if  (horseName.getSelectionModel().getSelectedItem()!= null)
                reservation.setHorseName(horseName.getValue().toString());
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Be sure you have selected a horse name!");
                alert.showAndWait();
            }
            reservation.setStart(startTimestamp);
            reservation.setende(endTimestamp);


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to edit this reservation?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                okClicked = true;
                rs.validate(reservation);
                rs.update(reservation);
                receiptService.update(reservation);
                //controller.refreshReservationTable();
                dialogStage.close();

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

    @FXML
    private void cancel(){
        dialogStage.close();
    }
}
