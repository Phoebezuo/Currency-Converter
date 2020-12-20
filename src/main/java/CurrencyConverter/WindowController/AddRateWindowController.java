package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddRateWindowController {
    private MainWindowController main;
    private Processor processor;
    private Stage stage;
    private AnchorPane pane;
    private Scene scene;
    private LocalDate date;
    private ComboBox<String> curA;
    private ComboBox<String> curB;
    private TextField in1;
    private TextField in2;
    private Button addBtn;


    public AddRateWindowController(Processor processor, MainWindowController main) {
        this.processor = processor;
        this.main = main;
        this.stage = new Stage();
        this.pane = new AnchorPane();
        this.scene = new Scene(pane, 400, 210);
        stage.setTitle("Add new exchange rate");
        stage.setScene(scene);
        stage.show();

        date = LocalDate.now();
        initLabel();
        initCombo();
        initInputField();
        initButton();
    }

    private void initButton() {
        addBtn = new Button();
        addBtn.setLayoutX(130);
        addBtn.setLayoutY(170);
        addBtn.setPrefWidth(125);
        addBtn.setText("Add");
        addBtn.setOnAction(event -> addAction());
        pane.getChildren().add(addBtn);
    }

    private void initLabel() {
        Label dateLabel = new Label("Today: " + date.toString());
        dateLabel.setLayoutX(130);
        dateLabel.setLayoutY(15);
        pane.getChildren().add(dateLabel);
    }

    private void initCombo() {
        curA = new ComboBox<>();
        curA.setLayoutX(40);
        curA.setLayoutY(75);
        curA.setPrefWidth(125);
        pane.getChildren().add(curA);

        curB = new ComboBox<>();
        curB.setLayoutX(210);
        curB.setLayoutY(75);
        curB.setPrefWidth(125);
        pane.getChildren().add(curB);
        setComboData();

        curA.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                validateCurrencySelection(oldValue, newValue, curB, curA));

        curB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                validateCurrencySelection(oldValue, newValue, curA, curB));
    }

    private void initInputField() {
        in1 = new TextField();
        in1.setLayoutX(40);
        in1.setLayoutY(130);
        in1.setPrefWidth(125);
        in1.textProperty().addListener((observable, oldValue, newValue) -> numberOnly(newValue,
                in1));
        in1.setText("0");
        pane.getChildren().add(in1);

        in2 = new TextField();
        in2.setLayoutX(210);
        in2.setLayoutY(130);
        in2.setPrefWidth(125);
        in2.textProperty().addListener((observable, oldValue, newValue) -> numberOnly(newValue,
                in2));
        in2.setText("0");
        pane.getChildren().add(in2);
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

    public void addAction() {
        if (Double.parseDouble(in1.getText()) <= 0 || Double.parseDouble(in2.getText()) <= 0) {
            wrongAlert("Can't enter rate 0.");
            return;
        }

        try {
            String curAName = curA.getValue();
            String curBName = curB.getValue();
            if (processor.hasRateAtDate(curAName, curBName, date)) {
                wrongAlert("Already have exchange rate between " + curAName + " and " + curBName + " at date " + date.toString());
                return;
            }

            if (processor.addRate(curAName, curBName, date, Double.parseDouble(in1.getText())) &&
                    processor.addRate(curBName, curAName, date,
                            Double.parseDouble(in2.getText()))) {
                this.main.updateTable();
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Add exchange rate successfully.");
                alert.show();
                stage.close();
            } else {
                wrongAlert("Fail to add exchange rate");
            }
        } catch (IOException e) {
            wrongAlert("Fail to save new data.");
        }
    }

    private void wrongAlert(String msg) {
        ButtonType tryAgain = new ButtonType("Try Again", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType returnMain = new ButtonType("Return Main", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, tryAgain, returnMain);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(400, 150);
        alert.showAndWait();

        if (alert.getResult() == returnMain) {
            stage.close();
        }
    }

    private void numberOnly(String newValue, TextField textField) {
        if (!newValue.matches("[0-9.]*")) {
            // number only
            newValue = newValue.replaceAll("[^0-9.]", "");
            textField.setText(newValue);
        }

        if ("".equals(newValue)) {
            // disallow empty text
            newValue = "0";
            textField.setText(newValue);
        }

        if (newValue.startsWith("0") && !newValue.startsWith("0.") && newValue.length() > 1) {
            // remove leading zero when entering
            newValue = newValue.substring(1);
            textField.setText(newValue);
        }

        if (newValue.chars().filter(ch -> ch == '.').count() > 1) {
            // only one dot allowed
            newValue = newValue.substring(0, newValue.length() - 1);
            textField.setText(newValue);
        }
    }

    public void validateCurrencySelection(String oldValue, String newValue, ComboBox<String> curA,
                                           ComboBox<String> curB) {
        if (newValue.equals(curA.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You can't select two same currency.");
            alert.show();
            curB.setValue(oldValue);
        }
    }

    public ComboBox<String> getComboBox1() {
        return curA;
    }

    public ComboBox<String> getComboBox2() {
        return curB;
    }

    public TextField getText1() {
        return in1;
    }

    public TextField getText2() {
        return in2;
    }

    public Button getAddBtn() {
        return addBtn;
    }
}
