package CurrencyConverter.WindowController;

import CurrencyConverter.Model.ExchangeRecordPair;
import CurrencyConverter.Processor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class HistoryWindowController {
    private Processor processor;
    private Stage stage;
    private AnchorPane pane;
    private Scene scene;
    private ComboBox<String> curA;
    private ComboBox<String> curB;
    private ComboBox<LocalDate> start;
    private ComboBox<LocalDate> end;
    private TableView<HistoryTableEntry> table;


    public HistoryWindowController(Processor processor) {
        this.processor = processor;
        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane, 620, 330);
        stage.setTitle("History");
        stage.setScene(scene);
        stage.show();

        initLabels();
        initComboBox();
        setComboData();
        initTable();
    }

    private void initTable() {
        table = new TableView<>();
        table.setLayoutX(240);
        table.setLayoutY(15);
        table.setPrefWidth(360);
        table.setPrefHeight(300);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.getChildren().add(table);
        String[] colNames = {"Statistics", curA.getValue(), curB.getValue()};
        String[] properties = {"type", "value1", "value2"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<HistoryTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(column);
        }


    }

    private void initComboBox() {
        curA = new ComboBox<>();
        curA.setLayoutX(100);
        curA.setLayoutY(75);
        curA.setPrefWidth(125);
        pane.getChildren().add(curA);

        curB = new ComboBox<>();
        curB.setLayoutX(100);
        curB.setLayoutY(125);
        curB.setPrefWidth(125);
        pane.getChildren().add(curB);

        start = new ComboBox<>();
        start.setLayoutX(75);
        start.setLayoutY(200);
        start.setPrefWidth(150);
        pane.getChildren().add(start);

        end = new ComboBox<>();
        end.setLayoutX(75);
        end.setLayoutY(245);
        end.setPrefWidth(150);
        pane.getChildren().add(end);

        setComboActions();
    }

    public void setComboActions() {
        curA.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                validateCurrencySelection(oldValue, newValue, curB, curA));

        curB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                validateCurrencySelection(oldValue, newValue, curA, curB));

        start.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!end.getSelectionModel().isEmpty()) {
                if (newValue != null && newValue.isAfter(end.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid date duration.");
                    alert.show();
                    start.setValue(oldValue);
                } else {
                    table.getItems().clear();

                    setHistoryData();
                }
            }
        });

        end.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
                                                                    newValue) -> {
            if (!start.getSelectionModel().isEmpty()) {
                if (newValue != null && newValue.isBefore(start.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid date duration.");
                    alert.show();
                    end.setValue(oldValue);
                } else {
                    table.getItems().clear();
                    setHistoryData();
                }
            }
        });
    }

    public void validateCurrencySelection(String oldValue, String newValue, ComboBox<String> curA,
                                           ComboBox<String> curB) {
        if (newValue.equals(curA.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You can't select two same currency.");
            alert.show();
            curB.setValue(oldValue);
        } else {
            start.getSelectionModel().clearSelection();
            start.getItems().clear();
            end.getSelectionModel().clearSelection();
            end.getItems().clear();
            loadDates();
        }
    }

    private void setHistoryData() {
        if (start.getSelectionModel().isEmpty() || end.getSelectionModel().isEmpty()) {
            // if one date is not selected
            return;
        }

        List<ExchangeRecordPair> history = processor.getHistory(curA.getValue(), curB.getValue(),
                start.getValue(), end.getValue());
        Map<String, Double> stat = processor.historyStatistic(history);
        String[] statNames = {"Average", "Median", "Maximum", "Minimum", "Standard Deviation"};
        for (String statName : statNames) {
            table.getItems().add(new HistoryTableEntry(statName,
                    String.format("%.4f", stat.get(statName + " 1")),
                    String.format("%.4f", stat.get(statName + " 2"))));
        }
        table.getItems().add(new HistoryTableEntry("Date", "-", "-"));

        for (ExchangeRecordPair exchangeRecordPair : history) {
            table.getItems().add(new HistoryTableEntry(exchangeRecordPair.getDate().toString(),
                    String.format("%.4f", exchangeRecordPair.getFirstExchange().getRateValue()),
                    String.format("%.4f", exchangeRecordPair.getSecondExchange().getRateValue())));
        }
    }


    private void setComboData() {
        String[] curNames = processor.getAllCurNames();
        for (String curName : curNames) {
            curA.getItems().add(curName);
            curB.getItems().add(curName);
        }
        curA.getSelectionModel().select(0);
        curB.getSelectionModel().select(1);
    }

    private void loadDates() {
        if (curA.getValue() == null || curB.getValue() == null) {
            return;
        }

        List<LocalDate> dates = processor.getDatesAToB(curA.getValue(), curB.getValue());
        for (LocalDate date : dates) {
            start.getItems().add(date);
            end.getItems().add(date);
        }
    }

    private void initLabels() {
        Label curA = new Label("Currency A");
        curA.setLayoutX(20);
        curA.setLayoutY(80);
        pane.getChildren().add(curA);

        Label curB = new Label("Currency B");
        curB.setLayoutX(20);
        curB.setLayoutY(130);
        pane.getChildren().add(curB);

        Label start = new Label("Start");
        start.setLayoutX(20);
        start.setLayoutY(200);
        pane.getChildren().add(start);

        Label end = new Label("End");
        end.setLayoutX(20);
        end.setLayoutY(250);
        pane.getChildren().add(end);
    }

    public ComboBox<String> getCur1() {
        return curA;
    }

    public ComboBox<String> getCur2() {
        return curB;
    }

    public ComboBox<LocalDate> getStart() {
        return start;
    }

    public ComboBox<LocalDate> getEnd() {
        return end;
    }
}
