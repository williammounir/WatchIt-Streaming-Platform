package UI.Common.CommonControllers;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import models.clsAdminLog;
import models.clsSubscription;

import java.util.Map;

public class MonthlyRevenueController {

    @FXML private TableView<MonthRevenue> revenueTable;

    @FXML private TableColumn<MonthRevenue, String> monthColumn;
    @FXML private TableColumn<MonthRevenue, Integer> revenueColumn;

    @FXML private Label totalRevenueLabel;
    @FXML private Label highestMonthLabel;
    @FXML private Label lowestMonthLabel;


    private String getMonthName(int month) {

        String[] months = {
                "January","February","March","April",
                "May","June","July","August",
                "September","October","November","December"
        };

        return months[month - 1];
    }


    @FXML
    private void initialize(){

        monthColumn.setCellValueFactory(
                new PropertyValueFactory<>("month"));

        revenueColumn.setCellValueFactory(
                new PropertyValueFactory<>("revenue"));

        loadRevenueData();
    }


    private void loadRevenueData(){

        Map<Integer,Integer> monthsRevenue =
                clsSubscription.RevenuForEachMonth();

        ObservableList<MonthRevenue> data =
                FXCollections.observableArrayList();

        int totalRevenue = 0;

        int maxMonth = 1;
        int minMonth = 1;

        int maxRevenue = Integer.MIN_VALUE;
        int minRevenue = Integer.MAX_VALUE;

        for(int month = 1; month <= 12; month++){

            int revenue = monthsRevenue.get(month);

            data.add(new MonthRevenue(
                    getMonthName(month),
                    revenue));

            totalRevenue += revenue;

            if(revenue > maxRevenue){
                maxRevenue = revenue;
                maxMonth = month;
            }

            if(revenue < minRevenue){
                minRevenue = revenue;
                minMonth = month;
            }
        }

        revenueTable.setItems(data);

        totalRevenueLabel.setText(
                "Total Yearly Revenue : " + totalRevenue);

        highestMonthLabel.setText(
                "Highest Revenue Month : "
                        + getMonthName(maxMonth)
                        + " (" + maxRevenue + ")");

        lowestMonthLabel.setText(
                "Lowest Revenue Month : "
                        + getMonthName(minMonth)
                        + " (" + minRevenue + ")");
        clsAdminLog.InsertingAdminLog(
                enAction.CHECKING_REVENU_PER_MONTH,
                enTargetModel.SYS,
                "NONE",
                "CHECKING THE MONTHLY REVENUE"
        );
    }


    @FXML
    private void handleBack(){

        System.out.println("Back to admin menu");
    }



    public static class MonthRevenue {

        private String month;
        private int revenue;

        public MonthRevenue(String month,int revenue){
            this.month = month;
            this.revenue = revenue;
        }

        public String getMonth(){
            return month;
        }

        public int getRevenue(){
            return revenue;
        }
    }
}
