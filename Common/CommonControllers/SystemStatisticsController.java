package UI.Common.CommonControllers;

import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import models.*;

import java.util.Map;

public class SystemStatisticsController {

    @FXML
    private Label lblTotalUsers;

    @FXML
    private Label lblTotalMovies;

    @FXML
    private Label lblActiveSubs;

    @FXML
    private Label lblStartedMonth;

    @FXML
    private Label lblEndedMonth;

    @FXML
    private PieChart genrePieChart;

    @FXML
    public void initialize() {
        loadStatistics();
        clsAdminLog.InsertingAdminLog(
                enAction.CHECKING_SYSTEM_STATS,
                enTargetModel.SYS,
                "NONE",
                "CHECKING SYSTEM STATS"
        );
    }

    private void loadStatistics() {

        lblTotalUsers.setText(String.valueOf(clsUser.CountUsers()));
        lblTotalMovies.setText(String.valueOf(clsMovie.CountofMovies()));
        lblActiveSubs.setText(String.valueOf(clsSubscription.NumofActiveSup()));
        lblStartedMonth.setText(String.valueOf(clsSubscription.SubsStartedThisMonth()));
        lblEndedMonth.setText(String.valueOf(clsSubscription.SubsExpiredThisMonth()));

        loadGenreChart();
    }

    private void loadGenreChart() {

        genrePieChart.getData().clear();

        Map<String,Integer> genres = Movie_Genere.MoviePerGenre();

        for (Map.Entry<String,Integer> entry : genres.entrySet()) {

            PieChart.Data slice =
                    new PieChart.Data(entry.getKey(), entry.getValue());

            genrePieChart.getData().add(slice);
        }
    }

    @FXML
    private void onRefresh() {
        loadStatistics();
    }

    @FXML
    private void onBack() {
        CNavigators.ShowAdminMainMenu();
    }
}