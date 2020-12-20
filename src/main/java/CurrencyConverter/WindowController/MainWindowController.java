package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import CurrencyConverter.User.NormalUserImpl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class MainWindowController {
    private Scene scene;
    private AnchorPane pane;
    private Button convertButton;
    private Button historyButton;
    private Button addCurrencyButton;
    private Button addRateButton;
    private Button adminButton;
    private Button changePopularCurButton;
    private TableView<PopularTableEntry> popularTable;
    private Processor processor;

    public MainWindowController() {
        try {
            this.processor = new Processor();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.pane = new AnchorPane();
        this.scene = new Scene(pane, 600, 300);
        initButtons();
        initPopularTable();
        setTableCols();
        setTableData();
        initBtnActions();
    }

    public void updateTable() {
        popularTable.getItems().clear();
        popularTable.getColumns().clear();
        setTableCols();
        setTableData();
    }

    private void initBtnActions() {
        convertButton.setOnAction((event -> new ConvertWindowController(processor)));
        historyButton.setOnAction((event -> new HistoryWindowController(processor)));
        adminButton.setOnAction((event -> {
            if (processor.getCurrentUser() instanceof NormalUserImpl) {
                new AdminWindowController(processor, adminButton);
            } else {
                processor.logoutAdmin();
                adminButton.setText("Admin");
            }
        }));
        addCurrencyButton.setOnAction(event -> {
            if (processor.getCurrentUser().canMaintainCurrency()) {
                new AddCurrencyWindowController(processor);
            } else {
                noPermissionAction();
            }
        });

        addRateButton.setOnAction(event -> {
            if (processor.getCurrentUser().canMaintainCurrency()) {
                new AddRateWindowController(processor, this);
            } else {
                noPermissionAction();
            }
        });

        changePopularCurButton.setOnAction(event -> {
            if (processor.getCurrentUser().canMaintainCurrency()) {
                new ChangePopCurWindowController(processor, this);
            } else {
                noPermissionAction();
            }
        });
    }

    private void noPermissionAction() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have permission to do this " +
                "action.");
        alert.show();
    }

    private void initButtons() {
        convertButton = new Button();
        historyButton = new Button();
        addCurrencyButton = new Button();
        addRateButton = new Button();
        adminButton = new Button();
        changePopularCurButton = new Button();

        Button[] buttons = {convertButton, historyButton, addCurrencyButton, addRateButton,
                changePopularCurButton, adminButton};
        String[] texts = {"Convert", "History", "Add Currency", "Add Rate", "Change Pop.",
                "Admin"};

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setLayoutX(25);
            button.setLayoutY(10 + 50 * i);
            button.setPrefWidth(100);
            button.setPrefHeight(30);
            button.setText(texts[i]);
            pane.getChildren().add(button);
        }
    }

    private void initPopularTable() {
        popularTable = new TableView<>();
        popularTable.setLayoutX(145);
        popularTable.setLayoutY(15);
        popularTable.setPrefHeight(259);
        popularTable.setPrefWidth(452);
        popularTable.setEditable(true);
        popularTable.setFixedCellSize(55);
        this.pane.getChildren().add(popularTable);
    }

    private void setTableCols() {
        String[] popularNames = processor.getPopularCurrencyNames();
        // add the first column displaying currency names
        TableColumn<PopularTableEntry, String> column = new TableColumn<>("From/To");
        column.setPrefWidth(90);
        column.setSortable(false);
        column.setCellValueFactory(new PropertyValueFactory<>("type"));
        column.setStyle("-fx-alignment: CENTER; -fx-font-size: 18px;");
        popularTable.getColumns().add(column);

        // add currency columns
        for (int i = 0; i < popularNames.length; i++) {
            String popularName = popularNames[i];
            column = new TableColumn<>(popularName);
            column.setPrefWidth(90);
            column.setSortable(false);
            column.setCellValueFactory(new PropertyValueFactory<>("rate" + i));
            column.setStyle("-fx-alignment: CENTER;");
            popularTable.getColumns().add(column);
        }
    }

    private void setTableData() {
        String[] popularNames = processor.getPopularCurrencyNames();

        for (int i = 0; i < popularNames.length; i++) {
            String currencyA = popularNames[i];
            PopularTableEntry row = new PopularTableEntry(currencyA);
            for (int j = 0; j < popularNames.length; j++) {
                if (i == j) {
                    row.setRate(j, 0, 0);
                } else {
                    String currencyB = popularNames[j];
                    row.setRate(j, processor.getLatestRate(currencyA, currencyB),
                            processor.getSecondLatestRate(currencyA, currencyB));
                }
            }
            popularTable.getItems().add(row);
        }

    }

    public Scene getScene() {
        return scene;
    }

    public Processor getProcessor() {
        return processor;
    }

    public Button getConvertButton() {
        return this.convertButton;
    }

    public Button getHistoryButton() {
        return this.historyButton;
    }

    public Button getAddCurrencyButton() {
        return this.addCurrencyButton;
    }

    public Button getAddRateButton() {
        return this.addRateButton;
    }

    public Button getAdminButton() {
        return this.adminButton;
    }

    public Button getChangePopularCurButton() {
        return this.changePopularCurButton;
    }
}


