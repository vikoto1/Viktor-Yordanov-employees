package com.example.demo3;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloController {

    @FXML
    private Button chooseFileButton;

    @FXML
    private TableView<PairData> tableView;


    List<PairData> pairDataList = new ArrayList<>();
    private csvReaderData csvReaderData;
    private List<EmployeeProject> employeeProject;



    public void ChooseFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set the extension filter to only allow CSV files
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());

        if (selectedFile != null) {
            try {
                employeeProject = csvReaderData.read(selectedFile.getAbsolutePath());
                updateProjectTable(employeeProject);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateProjectTable(List<EmployeeProject> employeeProject) {
        Map<String, Integer> employeePairsDuration = new HashMap<>();
        for (int i = 0; i < employeeProject.size(); i++) {
            for (int j = i + 1; j < employeeProject.size(); j++) {
                EmployeeProject e1 = employeeProject.get(i);
                EmployeeProject e2 = employeeProject.get(j);
                if (e1.getProjectId() == e2.getProjectId()) {
                    String employeePair = e1.getEmpId() < e2.getEmpId() ?
                            e1.getEmpId() + "-" + e2.getEmpId() + "-" + e1.getProjectId() :
                            e2.getEmpId() + "-" + e1.getEmpId() + "-" + e2.getProjectId();
                    long duration = ChronoUnit.DAYS.between(e1.getDateFrom(), e1.getDateTo().plusDays(1)) +
                            ChronoUnit.DAYS.between(e2.getDateFrom(), e2.getDateTo().plusDays(1));
                    if (employeePairsDuration.containsKey(employeePair)) {
                        int currentDuration = employeePairsDuration.get(employeePair);
                        if (duration > currentDuration) {
                            employeePairsDuration.put(employeePair, (int) duration);
                        }
                    } else {
                        employeePairsDuration.put(employeePair, (int) duration);
                    }
                }
            }
        }

        // Create a list of PairData objects from the employeePairsDuration map
        List<PairData> pairDataList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : employeePairsDuration.entrySet()) {
            String[] employeePair = entry.getKey().split("-");
            int employee1 = Integer.parseInt(employeePair[0]);
            int employee2 = Integer.parseInt(employeePair[1]);
            int daysWorked = entry.getValue();
            int projectID = Integer.parseInt(employeePair[2]);

            PairData pairData = new PairData(employee1, employee2, projectID, daysWorked);
            pairDataList.add(pairData);
        }

        // Creating the table columns
        TableColumn<PairData, Integer> empID1Col = new TableColumn<>("Employee#1");
        empID1Col.setCellValueFactory(new PropertyValueFactory<>("employee1"));

        TableColumn<PairData, Integer> empID2Col = new TableColumn<>("Employee#2");
        empID2Col.setCellValueFactory(new PropertyValueFactory<>("employee2"));

        TableColumn<PairData, Integer> projectIDCol = new TableColumn<>("Project ID");
        projectIDCol.setCellValueFactory(new PropertyValueFactory<>("projectID"));

        TableColumn<PairData, Integer> daysWorkedCol = new TableColumn<>("Days worked");
        daysWorkedCol.setCellValueFactory(new PropertyValueFactory<>("daysWorked"));

        // Adding the table columns to the table view
        tableView.getColumns().addAll(empID1Col, empID2Col, projectIDCol, daysWorkedCol);

        // Adding the data to the table view
        tableView.setItems(FXCollections.observableArrayList(pairDataList));

        //Sorting the data by daysWorkedCol
        daysWorkedCol.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(daysWorkedCol);


    }

}
