package CurrencyConverter;

import CurrencyConverter.WindowController.MainWindowController;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    private MainWindowController mainWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // You can uncomment the below line to write data for testing
        mainWindow = new MainWindowController();

        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(mainWindow.getScene());
        primaryStage.show();
    }

    public MainWindowController getMainWindowController() {
        return mainWindow;
    }

}