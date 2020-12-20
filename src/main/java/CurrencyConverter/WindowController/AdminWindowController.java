package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminWindowController {
    private Processor processor;
    private Stage stage;
    private AnchorPane pane;
    private Scene scene;
    private TextField inputPassword;
    private Button logButton;
    private Boolean loginStatus;
    private Button adminButton;
    private Label label;
    private Button returnMainButton;

    public AdminWindowController(Processor processor, Button adminButton) {
        this.processor = processor;
        this.adminButton = adminButton;

        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane, 400, 150);
        loginStatus = false;
        stage.setTitle("Admin Login");
        stage.setScene(scene);
        stage.show();

        initInputFields();
        initButton();
        initLabel();
    }

    private void initLabel() {
        label = new Label("Password");
        label.setLayoutX(165);
        label.setLayoutY(15);
        pane.getChildren().add(label);
    }


    private void initInputFields() {
        inputPassword = new TextField();
        inputPassword.setLayoutX(100);
        inputPassword.setLayoutY(50);
        inputPassword.setPrefWidth(200);
        inputPassword.setPromptText("password");
        pane.getChildren().add(inputPassword);
    }

    private void initButton() {
        logButton = new Button();
        logButton.setLayoutX(175);
        logButton.setLayoutY(100);
        logButton.setPrefWidth(50);
        logButton.setText("Login");
        logButton.requestFocus();
        pane.getChildren().add(logButton);

        logButton.setOnAction(event -> logAction());
    }

    public boolean logAction() {
        String input = inputPassword.getText();
        if (processor.loginAdmin(input)) {
            loginStatus = true;
            adminButton.setText("Logout");
            stage.close();
            return true;
        } else {
            ButtonType tryAgain = new ButtonType("Try Again", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType returnMain = new ButtonType("Return Main", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(AlertType.ERROR, "Wrong Password", tryAgain, returnMain);
            returnMainButton = (Button) alert.getDialogPane().lookupButton(returnMain);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(400, 150);
            alert.showAndWait();

            if (alert.getResult() == returnMain) {
                stage.close();
            }
            return false;
        }
    }

    public TextField getInputPassword() {
        return inputPassword;
    }

    public Button getLogButton() {
        return logButton;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public Scene getScene() {
        return scene;
    }

    public Label getLabel() {
        return label;
    }

    public Button getReturnMainButton() {
        return returnMainButton;
    }
}
