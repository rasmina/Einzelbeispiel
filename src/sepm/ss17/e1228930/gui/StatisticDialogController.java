package sepm.ss17.e1228930.gui;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.domain.Box;
import sepm.ss17.e1228930.domain.Reservation;
import sepm.ss17.e1228930.service.ReservationService;
import sepm.ss17.e1228930.service.impl.ReservationServiceImpl;
import sun.util.calendar.BaseCalendar;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


public class StatisticDialogController {


    @FXML
    private BarChart barChart;

    @FXML
    private CategoryAxis x;

    private ObservableList<String> days = FXCollections.observableArrayList();
    private ObservableList<String> names = FXCollections.observableArrayList();
    private ReservationService reservationService = ReservationServiceImpl.getInstance();

    Calendar c = Calendar.getInstance();

    private MainApp mainApp;
    private Stage dialogStage;

    public void setMainApp(MainApp mainApp){
        this.mainApp=mainApp;
        for(Box b: mainApp.data){
            names.add(b.getHorseName());
        }
    }

    public void setDialogStage(Stage stage){
        this.dialogStage= stage;
    }

    @FXML
    private void initialize()
    {
        barChart.setTitle("");

        x.setCategories(names);
    }

    /**
     * Shows which box is most booked
     */

    @FXML
    private void show(){

        if (!barChart.getData().isEmpty())
            barChart.getData().clear();

        try {
            barChart.getData().addAll(reservationService.mostbooked(names));
        }catch (NotFoundException es)
        {

        }

    }

    @FXML
    private void close(){
        dialogStage.close();
    }
}
