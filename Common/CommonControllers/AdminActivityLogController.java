package UI.Common.CommonControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;

import models.clsAdminLog;

import java.time.LocalDate;
import java.util.List;

public class AdminActivityLogController {

    @FXML
    private TableView<clsAdminLog> logsTable;

    @FXML
    private TableColumn<clsAdminLog, Integer> colLogID;

    @FXML
    private TableColumn<clsAdminLog, Integer> colSession;

    @FXML
    private TableColumn<clsAdminLog, String> colTime;

    @FXML
    private TableColumn<clsAdminLog, String> colAdmin;

    @FXML
    private TableColumn<clsAdminLog, String> colAction;

    @FXML
    private TableColumn<clsAdminLog, String> colTarget;

    @FXML
    private TableColumn<clsAdminLog, String> colTargetID;

    @FXML
    private TableColumn<clsAdminLog, String> colDescription;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTotalLogs;


    @FXML
    public void initialize() {

        colLogID.setCellValueFactory(new PropertyValueFactory<>("_Log_ID"));
        colSession.setCellValueFactory(new PropertyValueFactory<>("_Session_ID"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("_Time"));
        colAdmin.setCellValueFactory(new PropertyValueFactory<>("_idAdmin"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("_Action"));
        colTarget.setCellValueFactory(new PropertyValueFactory<>("_Target_Type"));
        colTargetID.setCellValueFactory(new PropertyValueFactory<>("_TargetID"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("_Description"));

        lblDate.setText("Date: " + LocalDate.now());

        loadLogs();
    }


    private void loadLogs() {

        List<clsAdminLog> logs = clsAdminLog.GetAllLogs();

        if (logs == null)
            logs = List.of();

        ObservableList<clsAdminLog> data =
                FXCollections.observableArrayList(logs);

        logsTable.setItems(data);

        lblTotalLogs.setText("Total Logs: " + data.size());
    }


    @FXML
    private void onRefresh() {
        loadLogs();
    }


    @FXML
    private void onBack() {

        AdminMainMenuController.loadIntoContentArea(
                "/UI/Common/CommonFXML/AdminMainMenu.fxml"
        );
    }
}
