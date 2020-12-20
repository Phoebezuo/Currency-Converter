package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePopCurWindowController {
    private Processor processor;
    private Stage stage;
    private AnchorPane pane;
    private Scene scene;
    private ComboBox<String> fromCombo;
    private ComboBox<String> toCombo;
    private Button changeBtn;
    private MainWindowController main;

    public ChangePopCurWindowController(Processor processor, MainWindowController main) {
        this.processor = processor;
        this.main = main;
        this.stage = new Stage();
        this.pane = new AnchorPane();
        this.scene = new Scene(pane, 280, 150);
        stage.setTitle("Change popular currency");
        stage.setScene(scene);
        stage.show();

        initLabel();
        initCombo();
        initButton();
    }

    private void initLabel() {
        Label curLabel = new Label("Currency");
        curLabel.setLayoutX(20);
        curLabel.setLayoutY(20);
        pane.getChildren().add(curLabel);

        Label changeToLabel = new Label("Change to");
        changeToLabel.setLayoutX(20);
        changeToLabel.setLayoutY(60);
        pane.getChildren().add(changeToLabel);
    }

    private void initCombo() {
        fromCombo = new ComboBox<>();
        fromCombo.setLayoutX(110);
        fromCombo.setLayoutY(15);
        fromCombo.setPrefWidth(125);
        pane.getChildren().add(fromCombo);

        toCombo = new ComboBox<>();
        toCombo.setLayoutX(110);
        toCombo.setLayoutY(55);
        toCombo.setPrefWidth(125);
        pane.getChildren().add(toCombo);
        setComboData();
    }

    private void initButton() {
        changeBtn = new Button();
        changeBtn.setText("Change");
        changeBtn.setLayoutX(60);
        changeBtn.setLayoutY(100);
        changeBtn.setPrefWidth(90);
        pane.getChildren().add(changeBtn);
        changeBtn.setOnAction(event -> changeAction());
    }

    private void setComboData() {
        String[] allNames = processor.getAllCurNames();
        String[] popNames = processor.getPopularCurrencyNames();
        for (String popName : popNames) {
            fromCombo.getItems().add(popName);
        }

        for (String curName : allNames) {
            boolean found = false;
            for (String popName : popNames) {
                if (curName.equals(popName)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                toCombo.getItems().add(curName);
            }
        }
    }

    private void changeAction() {
        try {
            processor.changePopularCurrency(fromCombo.getValue(), toCombo.getValue());
            this.main.updateTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Change popular currency successfully.");
            alert.show();
            stage.close();
        } catch (IOException e) {
            wrongAlert("Fail to change the most popular currency.");
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

    public Button getChangeBtn() {
        return this.changeBtn;
    }
}
