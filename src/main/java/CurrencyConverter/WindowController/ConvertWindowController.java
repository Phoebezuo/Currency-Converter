package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ConvertWindowController {
    private Processor processor;
    private Stage stage;
    private AnchorPane pane;
    private Scene scene;
    private ComboBox<String> comboBox1;
    private ComboBox<String> comboBox2;
    private TextField input1;
    private TextField input2;

    public ConvertWindowController(Processor processor) {
        this.processor = processor;

        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane, 400, 150);
        stage.setTitle("Convert");
        stage.setScene(scene);
        stage.show();

        initComboBox();
        initInputFields();
    }

    private void initComboBox() {
        // create combobox
        comboBox1 = new ComboBox<>();
        comboBox1.setLayoutX(250);
        comboBox1.setLayoutY(40);
        comboBox2 = new ComboBox<>();
        comboBox2.setLayoutX(250);
        comboBox2.setLayoutY(90);

        String[] names = processor.getAllCurNames();
        for (String s : names) {
            comboBox1.getItems().add(s);
            comboBox2.getItems().add(s);
        }
        comboBox1.getSelectionModel().select(0);
        comboBox2.getSelectionModel().select(1);

        comboBox1.setOnAction(event -> convertAction(input1, input2, comboBox1, comboBox2));
        comboBox2.setOnAction(event -> convertAction(input2, input1, comboBox2, comboBox1));

        pane.getChildren().add(comboBox1);
        pane.getChildren().add(comboBox2);
    }

    private void initInputFields() {
        input1 = new TextField();
        input1.setLayoutX(30);
        input1.setLayoutY(40);
        input1.setText("0");

        input2 = new TextField();
        input2.setLayoutX(30);
        input2.setLayoutY(90);
        input2.setText("0");

        pane.getChildren().add(input1);
        pane.getChildren().add(input2);

        setInputFieldsActions();
    }

    private void setInputFieldsActions() {
        // Convert while typing
        input1.setOnKeyReleased(event -> convertAction(input1, input2, comboBox1, comboBox2));
        input2.setOnKeyReleased(event -> convertAction(input2, input1, comboBox2, comboBox1));

        // The input fields receive number only
        input1.textProperty().addListener((observable, oldValue, newValue) -> numberOnly(newValue, input1));
        input2.textProperty().addListener((observable, oldValue, newValue) -> numberOnly(newValue, input2));
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

    private void convertAction(TextField inField, TextField outField, ComboBox<String> combo1,
                               ComboBox<String> combo2) {
        String curA = combo1.getValue();
        String curB = combo2.getValue();

        if (curA.equals(curB)) {
            // if select same Currency type
            outField.setText(inField.getText());
            return;
        }

        String input = inField.getText();
        double amount = Double.parseDouble(input);
        double result = processor.convert(curA, curB, amount);
        String resultStr = String.format("%.3f", result);
        // remove trailing zeros
        resultStr = !resultStr.contains(".") ? resultStr : resultStr.replaceAll("0*$", "").replaceAll("\\.$", "");
        outField.setText(resultStr);
    }

    public TextField getInput1() {
        return this.input1;
    }

    public TextField getInput2() {
        return this.input2;
    }

    public ComboBox<String> getComboBox1() {
        return this.comboBox1;
    }

    public ComboBox<String> getComboBox2() {
        return this.comboBox2;
    }
}
