package sepm.ss17.e1228930.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.dao.DBManager;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.domain.Receipt;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.gui.GUIEntities.BoxProperty;
import sepm.ss17.e1228930.service.BoxService;
import sepm.ss17.e1228930.service.ReceiptService;
import sepm.ss17.e1228930.service.ReservationService;
import sepm.ss17.e1228930.service.impl.BoxServiceImpl;
import sepm.ss17.e1228930.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1228930.service.impl.ReservationServiceImpl;

import java.io.IOException;


public class MainApp extends Application {

    private Stage primaryStage;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DBManager.class);

    ObservableList<Box> data = FXCollections.observableArrayList();
    ObservableList<Reservation> reservationData= FXCollections.observableArrayList();
    ObservableList<Receipt> receiptData= FXCollections.observableArrayList();

    ReservationService reservationService = ReservationServiceImpl.getInstance();
    BoxService boxService= BoxServiceImpl.getInstance();
    ReceiptService receiptService = new ReceiptServiceImpl();

    public MainApp() throws NotFoundException{
        BoxService boxService= new BoxServiceImpl();
        for (int i = 0; i < boxService.findAll().size(); i++) {
            data.add(boxService.findAll().get(i));
        }

        ReceiptService receiptService= new ReceiptServiceImpl();
        for (int i = 0; i <receiptService.showAll().size() ; i++) {
            receiptData.add(receiptService.showAll().get(i));
        }


    }


    public ObservableList<Box> updateBox(){
            if (!data.isEmpty())
                data.clear();
            for (int i = 0; i < boxService.findAll().size(); i++) {
                if (!boxService.findAll().get(i).isDeleted())
                    data.add(boxService.findAll().get(i));
            }
        return data;
    }


    public ObservableList<Reservation> updateReservation() {
        try {
            if (!reservationData.isEmpty())
                reservationData.clear();
            for (int i = 0; i < reservationService.showAll().size(); i++) {
                if (!reservationService.showAll().get(i).isDeleted())
                    reservationData.add(reservationService.showAll().get(i));
            }
        }catch (NotFoundException es) {
            logger.error("an Error has occurred");
        }

        return reservationData;

    }

    public ObservableList<Receipt> refreshReceipt() {
        try {
            if (!receiptData.isEmpty())
                receiptData.clear();

            for (int i = 0; i < receiptService.showAll().size(); i++) {
                if(!receiptService.showAll().get(i).isDeleted())
                receiptData.add(receiptService.showAll().get(i));
            }
        } catch (NotFoundException es){
            logger.error("Fail!");
        }
        return receiptData;


    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Wendy's Pferdepension");
        initRootLayout();
        logger.debug("The user interface has been successfully initialised!");
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() throws NotFoundException{
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("MainFrame.fxml"));
            Parent root = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            MainFrameController mfc = loader.getController();
            mfc.setMainApp(this);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified box. If the user
     * clicks OK, the changes are saved into the provided BoxProperty object and true
     * is returned.
     *
     * @param bp the BoxProperty object to be edited from the tableview
     * @return true if the user clicked OK, false otherwise.
     */
    public void showBoxEditDialog(BoxProperty bp) throws IOException{
        logger.debug("..Opening box edit dialog..");
        //Load the fxml file and open a popup for the new dialog
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("BoxEditDialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();


        //Create the dialog stage
        Stage stage= new Stage();
        stage.setTitle("Edit Box");
        stage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        stage.setScene(scene);

        //Set the Box into the controller
        BoxEditDialogController editController= loader.getController();
        editController.setDialogStage(stage);
        editController.setBox(bp);

        //show the dialog and wait until the user closes it
        stage.showAndWait();

        logger.debug("The box edit dialog is closed.. ");
        //return editController.getUpdatedBox();
    }

    public void showEditDialog(Reservation reservation)throws IOException{
        logger.debug("..Opening reservation edit dialog..");
        //Load the fxml file and open a popup for the new dialog
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ViewReservationBoxController.class.getResource("EditReservationDialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();


        //Create the dialog stage
        Stage stage= new Stage();
        stage.setTitle("Edit Reservation");
        stage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        stage.setScene(scene);

        //Set the Box into the controller
        EditReservationDialogController editController= loader.getController();
        editController.setDialogStage(stage);
        editController.setReservation(reservation);
        editController.setMainApp(this);
        editController.setMainApp(this);

        //show the dialog and wait until the user closes it
        stage.showAndWait();

        logger.debug("The reservation edit dialog is closed.. ");
    }

    public void showReservation() throws IOException, NotFoundException
    {
        logger.debug("..Opening the Reservation Box..");
        //Load the fxml file and open a popup for the new dialog
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("BoxReserveDialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();

        Stage stage = new Stage();
        stage.setTitle("Reserve");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene= new Scene(page);
        stage.setScene(scene);


        //Set the reservation into the controller
        BoxReserveDialogController controller= loader.getController();
        controller.setDialogStage(stage);
        controller.setMainApp(this);
        stage.showAndWait();

    }

    public void showViewReservation() throws IOException{
        logger.debug("..Opening the View Reservation Box");
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("ViewReservationBox.fxml"));
        AnchorPane page= (AnchorPane)loader.load();

        Stage stage= new Stage();
        stage.setTitle("View Reservation");
        stage.initOwner(primaryStage);
        Scene scene= new Scene(page);
        stage.setScene(scene);

        ViewReservationBoxController controller=loader.getController();
        controller.setDialogStage(stage);
        controller.setMainApp(this);
        stage.showAndWait();

    }
    public void showStatistics() throws IOException{
        logger.debug("..Opening the Statistics dialog");
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("StatisticDialog.fxml"));
        AnchorPane page=(AnchorPane)loader.load();

        Stage stage= new Stage();
        stage.setTitle("Statistics");
        stage.initOwner(primaryStage);
        Scene scene= new Scene(page);
        stage.setScene(scene);

        StatisticDialogController controller= loader.getController();
        controller.setDialogStage(stage);
        controller.setMainApp(this);
        stage.showAndWait();


    }

    public void showReceipt() throws IOException{
        logger.debug("..Opening the Receipt dialog..");
        refreshReceipt();
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("ReceiptDialog.fxml"));
        AnchorPane page= (AnchorPane)loader.load();

        Stage stage= new Stage();
        stage.setTitle("Receipt");
        stage.initOwner(primaryStage);
        Scene scene= new Scene(page);
        stage.setScene(scene);

        ReceiptDialogController controller=loader.getController();
        controller.setDialogStage(stage);
        controller.setMainApp(this);
        stage.showAndWait();



    }
    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) throws Exception {
        logger.debug("Main app has started!!!");
        launch(args);
    }


}

