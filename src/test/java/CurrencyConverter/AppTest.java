package CurrencyConverter;

import CurrencyConverter.WindowController.AdminWindowController;
import CurrencyConverter.WindowController.MainWindowController;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import static org.junit.Assert.*;


public class AppTest extends ApplicationTest {

    private App app;
    private MainWindowController mainWindow;

    @Before
    public void testAppHasAGreeting() {
        app = new App();

    }

    @Override
    public void start(Stage stage) {
        mainWindow = app.getMainWindowController();
    }

}
