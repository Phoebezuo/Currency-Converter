package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCurrencyWindowController {
    private Processor processor;
    private Stage stage;
    private AnchorPane pane;
    private Scene scene;
    private TextField newCurField;
    private Button addBtn;

    public AddCurrencyWindowController(Processor processor) {
        this.processor = processor;
        this.stage = new Stage();
        this.pane = new AnchorPane();
        this.scene = new Scene(pane, 400, 150);
        stage.setTitle("Add new currency");
        stage.setScene(scene);
        stage.show();

        initLabel();
        initInputFields();
        initButton();
    }

    private void initLabel() {
        Label label = new Label("Currency name");
        label.setLayoutX(160);
        label.setLayoutY(15);
        pane.getChildren().add(label);
    }


    private void initInputFields() {
        newCurField = new TextField();
        newCurField.setLayoutX(100);
        newCurField.setLayoutY(50);
        newCurField.setPrefWidth(200);
        pane.getChildren().add(newCurField);
    }

    private void initButton() {
        addBtn = new Button();
        addBtn.setLayoutX(175);
        addBtn.setLayoutY(100);
        addBtn.setPrefWidth(50);
        addBtn.setText("Add");
        addBtn.requestFocus();
        pane.getChildren().add(addBtn);

        addBtn.setOnAction(event -> addAction());
    }

    private void addAction() {
        try {
            if (processor.addCurrency(newCurField.getText())) {
                Alert alert = new Alert(AlertType.INFORMATION,
                        "Add currency " + newCurField.getText() + " successfully.");
                alert.show();
                stage.close();
            } else {
                wrongAlert("Currency already exists.");
            }
        } catch (IOException e) {
            wrongAlert("Fail to save new data.");

        }
    }

    private void wrongAlert(String msg) {
        ButtonType tryAgain = new ButtonType("Try Again", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType returnMain = new ButtonType("Return Main", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, tryAgain,
                returnMain);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(400, 150);
        alert.showAndWait();

        if (alert.getResult() == returnMain) {
            stage.close();
        }
    }

    public Button getAddBtn() {
        return this.addBtn;
    }
}
