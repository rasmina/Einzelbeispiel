package sepm.ss17.e1228930.gui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Receipt;

/**
 * Created by rasmina on 23/03/2017.
 */
public class ReceiptDialogController {

    @FXML
    private TableColumn<Receipt, String> receipt_id;
    @FXML
    private TableColumn <Receipt, String> client_name_column;
    @FXML
    private Label id;
    @FXML
    private Label clientName;
    @FXML
    private Label totalPrice;
    @FXML
    private Label horseName;
    @FXML
    public TableView<Receipt> receiptTableView;

    private Stage dialogStage;
    private MainApp mainApp;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage=dialogStage;
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp=mainApp;
        receiptTableView.setItems(mainApp.refreshReceipt());
    }
    @FXML
    private void initialize(){

        receipt_id.setCellValueFactory(celldata -> new ReadOnlyStringWrapper(celldata.getValue().getReceipt_id().toString()));
        client_name_column.setCellValueFactory(celldata -> new ReadOnlyStringWrapper(celldata.getValue().getClientName()));
        receiptTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> setReceipt(newValue)));
    }

    private void setReceipt(Receipt receipt){

        id.setText(Integer.toString(receipt.getReceipt_id()));
        clientName.setText(receipt.getClientName());
        totalPrice.setText(Double.toString(receipt.getTotalPreis()));

        for (int i = 0; i < mainApp.data.size(); i++) {
            if(receipt.getBid()== mainApp.data.get(i).getBid()){
                horseName.setText(mainApp.data.get(i).getHorseName());
            }
        }
    }

    @FXML
    private void close(){
        dialogStage.close();
    }

}
