package com.cgpacalculator.ui;

import java.util.Locale;

import com.cgpacalculator.model.Course;
import com.cgpacalculator.model.GradeScale;
import com.cgpacalculator.service.CGPACalculator;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CGPACalculatorApp extends Application {

    private static final String DEFAULT_GRADE = "--select Letter Grade--";

    private final ObservableList<Course> courseList = FXCollections.observableArrayList();
    private int courseCounter = 1;

    private TextField titleField;
    private TextField codeField;
    private TextField creditField;
    private ComboBox<String> gradeComboBox;

    private TableView<Course> courseTable;
    private TextField totalCreditField;
    private TextField totalGradePointField;
    private TextField cgpaField;
    private TextField statusField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CGPA Calculator Pro");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.getStyleClass().add("app-root");

        VBox content = new VBox(10);
        content.getChildren().addAll(createHeader(), createInputSection(), createTableSection(), createSummarySection());
        content.setFillWidth(true);
        root.setCenter(content);

        Scene scene = new Scene(root, 740, 580);
        scene.getStylesheets().add(
                getClass().getResource("/com/cgpacalculator/ui/cgpa-theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(560);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(4);
        header.getStyleClass().add("header-card");

        Label title = new Label("Academic CGPA Dashboard");
        title.getStyleClass().add("app-title");

        Label subtitle = new Label("Track your courses, calculate performance, and monitor academic standing.");
        subtitle.getStyleClass().add("app-subtitle");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private VBox createInputSection() {
        VBox container = new VBox(8);
        container.getStyleClass().add("card");

        Label sectionTitle = new Label("Course Details");
        sectionTitle.getStyleClass().add("section-title");

        Label titleLabel = new Label("Course Title");
        titleLabel.getStyleClass().add("form-label");
        titleField = new TextField();
        titleField.setPromptText("e.g. Object Oriented Programming");
        titleField.setMaxWidth(Double.MAX_VALUE);
        titleField.getStyleClass().add("modern-field");

        Label codeLabel = new Label("Course Code");
        codeLabel.getStyleClass().add("form-label");
        codeField = new TextField();
        codeField.setPromptText("e.g. CoSc2022");
        codeField.setMaxWidth(Double.MAX_VALUE);
        codeField.getStyleClass().add("modern-field");

        Label creditLabel = new Label("Credit Hours");
        creditLabel.getStyleClass().add("form-label");
        creditField = new TextField();
        creditField.setPromptText("e.g. 5");
        creditField.setMaxWidth(Double.MAX_VALUE);
        creditField.getStyleClass().add("modern-field");

        Label gradeLabel = new Label("Letter Grade");
        gradeLabel.getStyleClass().add("form-label");
        gradeComboBox = new ComboBox<>();
        gradeComboBox.getItems().add(DEFAULT_GRADE);
        gradeComboBox.getItems().addAll(GradeScale.getAvailableGrades());
        gradeComboBox.setValue(DEFAULT_GRADE);
        gradeComboBox.setMaxWidth(Double.MAX_VALUE);
        gradeComboBox.getStyleClass().add("modern-field");

        VBox titleGroup = createFieldGroup(titleLabel, titleField);
        VBox codeGroup = createFieldGroup(codeLabel, codeField);
        VBox creditGroup = createFieldGroup(creditLabel, creditField);
        VBox gradeGroup = createFieldGroup(gradeLabel, gradeComboBox);

        Button addButton = new Button("Add Course");
        addButton.getStyleClass().add("primary-button");
        addButton.setOnAction(e -> handleAddCourse());

        Button deleteButton = new Button("Remove Selected");
        deleteButton.getStyleClass().add("danger-button");
        deleteButton.setOnAction(e -> handleRemoveCourse());

        HBox buttonBox = new HBox(8, addButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox sectionHeader = new HBox(10, sectionTitle, buttonBox);
        sectionHeader.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);

        HBox firstRow = new HBox(12, titleGroup, codeGroup);
        HBox secondRow = new HBox(12, creditGroup, gradeGroup);
        for (HBox row : new HBox[] {firstRow, secondRow}) {
            HBox.setHgrow(row.getChildren().get(0), Priority.ALWAYS);
            HBox.setHgrow(row.getChildren().get(1), Priority.ALWAYS);
        }

        container.getChildren().addAll(sectionHeader, firstRow, secondRow);
        return container;
    }

    private VBox createFieldGroup(Label label, javafx.scene.control.Control field) {
        VBox group = new VBox(3, label, field);
        group.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(field, Priority.NEVER);
        return group;
    }

    @SuppressWarnings("unchecked")
    private VBox createTableSection() {
        VBox container = new VBox(10);
        container.getStyleClass().add("card");

        Label sectionTitle = new Label("Course List");
        sectionTitle.getStyleClass().add("section-title");

        courseTable = new TableView<>();
        courseTable.setItems(courseList);
        courseTable.setPlaceholder(new Label("No courses added yet. Fill in the details above and click Add Course."));
        courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseTable.setPrefHeight(150);
        courseTable.setMinHeight(80);
        courseTable.getStyleClass().add("course-table");

        TableColumn<Course, Number> colId = new TableColumn<>("#");
        colId.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()));
        colId.setMaxWidth(50);
        colId.setMinWidth(40);
        colId.setStyle("-fx-alignment: CENTER;");

        TableColumn<Course, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        colTitle.setMinWidth(200);

        TableColumn<Course, String> colCode = new TableColumn<>("Code");
        colCode.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCode()));
        colCode.setMaxWidth(120);
        colCode.setMinWidth(90);

        TableColumn<Course, Number> colCredit = new TableColumn<>("Credit");
        colCredit.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCredit()));
        colCredit.setMaxWidth(80);
        colCredit.setMinWidth(60);
        colCredit.setStyle("-fx-alignment: CENTER;");

        TableColumn<Course, String> colGrade = new TableColumn<>("LG");
        colGrade.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLetterGrade()));
        colGrade.setMaxWidth(70);
        colGrade.setMinWidth(50);
        colGrade.setStyle("-fx-alignment: CENTER;");

        TableColumn<Course, Number> colGP = new TableColumn<>("GP");
        colGP.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getWeightedGradePoint()));
        colGP.setMaxWidth(90);
        colGP.setMinWidth(70);
        colGP.setStyle("-fx-alignment: CENTER;");

        courseTable.getColumns().addAll(colId, colTitle, colCode, colCredit, colGrade, colGP);

        container.getChildren().addAll(sectionTitle, courseTable);
        VBox.setVgrow(courseTable, Priority.ALWAYS);
        VBox.setVgrow(container, Priority.ALWAYS);
        return container;
    }

    private VBox createSummarySection() {
        VBox container = new VBox(8);
        container.getStyleClass().add("card");

        Label sectionTitle = new Label("Academic Summary");
        sectionTitle.getStyleClass().add("section-title");

        Label totalCreditLabel = new Label("Total Credit");
        totalCreditLabel.getStyleClass().add("form-label");
        totalCreditField = new TextField();
        totalCreditField.setEditable(false);
        totalCreditField.getStyleClass().add("summary-field");

        Label totalGPLabel = new Label("Total Grade Point");
        totalGPLabel.getStyleClass().add("form-label");
        totalGradePointField = new TextField();
        totalGradePointField.setEditable(false);
        totalGradePointField.getStyleClass().add("summary-field");

        Label cgpaLabel = new Label("CGPA");
        cgpaLabel.getStyleClass().add("form-label");
        cgpaField = new TextField();
        cgpaField.setEditable(false);
        cgpaField.getStyleClass().add("summary-field");

        Label statusLabel = new Label("Academic Status");
        statusLabel.getStyleClass().add("form-label");
        statusField = new TextField();
        statusField.setEditable(false);
        statusField.getStyleClass().add("summary-field");

        HBox metrics = new HBox(10,
                createFieldGroup(totalCreditLabel, totalCreditField),
                createFieldGroup(totalGPLabel, totalGradePointField),
                createFieldGroup(cgpaLabel, cgpaField),
                createFieldGroup(statusLabel, statusField));
        for (javafx.scene.Node metric : metrics.getChildren()) {
            HBox.setHgrow(metric, Priority.ALWAYS);
        }

        Button calculateBtn = new Button("Calculate");
        calculateBtn.getStyleClass().add("secondary-button");
        calculateBtn.setOnAction(e -> handleCalculate());

        Button cancelBtn = new Button("Clear All");
        cancelBtn.getStyleClass().add("neutral-button");
        cancelBtn.setOnAction(e -> handleCancel());

        HBox buttonBox = new HBox(8, calculateBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox sectionHeader = new HBox(10, sectionTitle, buttonBox);
        sectionHeader.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);

        container.getChildren().addAll(sectionHeader, metrics);
        return container;
    }

    private void handleAddCourse() {
        String title = titleField.getText().trim();
        String code = codeField.getText().trim();
        String creditStr = creditField.getText().trim();
        String grade = gradeComboBox.getValue();

        if (title.isEmpty() || code.isEmpty() || creditStr.isEmpty() || grade == null || DEFAULT_GRADE.equals(grade)) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all course fields and select a valid Letter Grade.");
            return;
        }

        int credit;
        try {
            credit = Integer.parseInt(creditStr);
            if (credit <= 0) {
                showAlert(Alert.AlertType.WARNING, "Invalid Credit", "Credit hours must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Number", "Credit hours must be a valid integer number.");
            return;
        }

        Course course = new Course(courseCounter++, title, code, credit, grade);
        courseList.add(course);

        titleField.clear();
        codeField.clear();
        creditField.clear();
        gradeComboBox.setValue(DEFAULT_GRADE);
        titleField.requestFocus();
    }

    private void handleRemoveCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            courseList.remove(selected);
            int idx = 1;
            for (Course c : courseList) {
                c.setId(idx++);
            }
            courseCounter = idx;
            courseTable.refresh();
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select a course from the table to remove.");
        }
    }

    private void handleCalculate() {
        if (courseList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Data", "Please add at least one course before calculating.");
            return;
        }

        int totalCredits = CGPACalculator.calculateTotalCredits(courseList);
        double totalPoints = CGPACalculator.calculateTotalGradePoints(courseList);
        double cgpa = CGPACalculator.calculateCGPA(courseList);
        String status = CGPACalculator.determineAcademicStatus(cgpa);

        totalCreditField.setText(String.valueOf(totalCredits));
        totalGradePointField.setText(String.format(Locale.US, "%.2f", totalPoints));
        cgpaField.setText(String.format(Locale.US, "%.2f", cgpa));
        statusField.setText(status);
        applyStatusStyle(status);
    }

    private void handleCancel() {
        titleField.clear();
        codeField.clear();
        creditField.clear();
        gradeComboBox.setValue(DEFAULT_GRADE);

        courseList.clear();
        courseCounter = 1;
        courseTable.refresh();

        totalCreditField.clear();
        totalGradePointField.clear();
        cgpaField.clear();
        statusField.clear();
        applyStatusStyle(null);
    }

    private void applyStatusStyle(String status) {
        statusField.getStyleClass().removeAll("status-pass", "status-warning", "status-danger");

        if (status == null || status.isBlank()) {
            statusField.getStyleClass().add("summary-field");
            return;
        }

        if ("Pass".equalsIgnoreCase(status)) {
            statusField.getStyleClass().add("status-pass");
        } else if ("Warning".equalsIgnoreCase(status)) {
            statusField.getStyleClass().add("status-warning");
        } else {
            statusField.getStyleClass().add("status-danger");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
