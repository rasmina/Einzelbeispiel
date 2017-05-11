package sepm.ss17.e1228930.gui;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Box;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sepm.ss17.e1228930.domain.Receipt;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.gui.GUIEntities.BoxProperty;
import sepm.ss17.e1228930.service.BoxService;
import sepm.ss17.e1228930.service.ReceiptService;
import sepm.ss17.e1228930.service.ReservationService;
import sepm.ss17.e1228930.service.impl.BoxServiceImpl;
import sepm.ss17.e1228930.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1228930.service.impl.ReservationServiceImpl;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class MainFrameController {
    public static Logger logger= LoggerFactory.getLogger(MainFrameController.class);

    private MainApp mainApp; //references to MainApp class in order to be able to communicate with more Controllers

    private BoxService bs= BoxServiceImpl.getInstance();
    private Stage stage;
    private String imagePath;

    //use this so the controllers know where it was opened from
    public void setMainApp(MainApp mainApp) throws NotFoundException{

        this.mainApp=mainApp;
        //tableBox.setItems(mainApp.getData());

        windowField_Search.getItems().addAll("With Window", "Without Window");
        windowField_Search.getSelectionModel().selectFirst();
        windowField.getItems().addAll("With Window", "Without Window");
        windowField.getSelectionModel().selectFirst();
        litterField.getItems().addAll("straw", "sawdust");
        litterField.getSelectionModel().selectFirst();
        locationField.getItems().addAll("inside", "outside");
        locationField.getSelectionModel().selectFirst();


    }

   //=========Data for Box TAB=================
    private ObservableList<BoxProperty> boxData;

    @FXML
    private TableView<BoxProperty> boxTable;
    @FXML
    private TableView<Reservation> reservationTableView;
    @FXML
    private TableColumn id;
    @FXML
    private TableColumn horseName;
    @FXML
    private TableColumn  size;
    @FXML
    private TableColumn window;
    @FXML
    private TableColumn litter;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn loc;

    @FXML
    ImageView imageView;

    @FXML
    private TextField idField;
    @FXML
    private TextField horseNameField;
    @FXML
    private TextField sizeField;
    @FXML
    private ChoiceBox windowField;
    @FXML
    private ChoiceBox litterField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField imageField;
    @FXML
    private ChoiceBox locationField;
    private List<Reservation> temp;
    @FXML
    private ChoiceBox windowField_Search;

    //=================================================//


    //===========Operations for Box TAB==================//

    /**
     * reconstructs the observable list, so that it appears correctly in the table view,
     * in case some records have been deleted from or edited in the database
     */
    public void refreshBoxTable() {
        ArrayList<Box> allBoxes = bs.findAll();
        ArrayList<BoxProperty> allBoxProperty = new ArrayList<BoxProperty>();
        for (Box b : allBoxes) {
            if (!b.isDeleted())
            allBoxProperty.add(new BoxProperty(b));
        }
        boxData = FXCollections.observableArrayList(allBoxProperty);
        boxTable.setItems(boxData);

    }

    /**
     * Called whe the user clicks the edit button. Opens a dialog to
     * edit the details for the selected box.
     */
    @FXML
    private void onEditBoxClicked() throws IOException {
        BoxProperty selectedBox = boxTable.getSelectionModel().getSelectedItem();
        if (selectedBox != null) {
            mainApp.showBoxEditDialog(selectedBox);

            refreshBoxTable();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid selection!");
            alert.setContentText("Please make sure you select the box you want to edit!");
            alert.showAndWait();
        }
    }

    @FXML
    public void onAddBoxCLicked(){
        Box b = null;
        try {
            b = new Box(Integer.parseInt(idField.getText()), windowField.getValue()=="With Window",litterField.getValue().toString(), horseNameField.getText(),Double.parseDouble(sizeField.getText()), locationField.getValue().toString(),Double.parseDouble(priceField.getText()) ,imagePath);
            bs.create(b);

            //in case the new box has been created, also add it to the table of boxes
            boxData.add(new BoxProperty(b));

            //feedback
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText("A new Box has been added to the database!");
            alert.showAndWait();

        } catch(NotFoundException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid parameters!");
            alert.setContentText("Please make sure both attributes input are correct!");
            alert.showAndWait();
        }finally {
            //in each case delete the text that was typed in
            idField.deleteText(0, idField.getText().length());
            horseNameField.deleteText(0, horseNameField.getText().length());
            sizeField.deleteText(0, sizeField.getText().length());
            priceField.deleteText(0, priceField.getText().length());
            imagePath=null;

        }
    }

    @FXML
    private String chooseImage(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image");
            File file= fileChooser.showOpenDialog(stage);
            if(file!=null){
                logger.info("File chosen {}", file.getAbsoluteFile());
                imagePath= file.getAbsolutePath();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return imagePath;
    }

    private void displayImage(BoxProperty box) {
        if(box != null) {
            File file = new File(box.getPicture());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);

        }

    }
    @FXML
    public void onDeleteBoxClicked() {
        //get the selected Box
        BoxProperty selectedBox = boxTable.getSelectionModel().getSelectedItem();
        //if it's not null, try to delete it from the database
        if(selectedBox!=null ) {
            for (int i = 0; i < mainApp.reservationData.size(); i++) {

                //if the box is reserved, dont delete it
                if (selectedBox.getHorseName().equals(mainApp.reservationData.get(i).getHorseName())) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("The Box is reserved!");
                    alert.showAndWait();
                    return;

                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to delete the selected box?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                BoxServiceImpl.getInstance().delete(BoxProperty.convertToBox(selectedBox));

                //the box must not be present in the box tables now
                refreshBoxTable();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid selection!");
            alert.setContentText("Please make sure you select the box you want to delete!");
            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the Search button
     * This method will display the boxes that fullfill the given constraint
     */

    @FXML
    public void onSearchBoxClicked() {
            ArrayList<Box> narrowedList = BoxServiceImpl.getInstance().find((windowField_Search.getValue().toString().equals("With Window")));
            ArrayList<BoxProperty> allNarrowedBoxProperty = new ArrayList<BoxProperty>();
                for (Box b : narrowedList) {
                    System.out.println(b.isWindow());
                    allNarrowedBoxProperty.add(new BoxProperty(b));

                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText(allNarrowedBoxProperty.size() + " Boxes found with the given constraint!");
                boxData = FXCollections.observableArrayList(allNarrowedBoxProperty);
                boxTable.setItems(boxData);
                alert.showAndWait();
    }


    @FXML
    public void onReserveBoxClicked() throws IOException, NotFoundException{

        mainApp.showReservation();
    }
    @FXML
    public void onViewReservationClicked() throws IOException{
        mainApp.showViewReservation();
    }

    @FXML
    private void onStatisticsClicked() throws IOException{
        mainApp.showStatistics();
    }

    //=====================================================//

    //=================Operations for Receipt ==========//

    @FXML
    public void onReceiptClicked()throws IOException{
        mainApp.showReceipt();
    }


    //==========================================================//




    @FXML
    private void initialize () throws NotFoundException {

        //box table
        id.setCellValueFactory(new PropertyValueFactory<BoxProperty, String>("id"));
        horseName.setCellValueFactory(new PropertyValueFactory<>("horseName"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        window.setCellValueFactory(new PropertyValueFactory<>("window"));
        litter.setCellValueFactory(new PropertyValueFactory<>("litter"));
        loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        boxTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> displayImage(newValue)));
        refreshBoxTable();



    }

}
