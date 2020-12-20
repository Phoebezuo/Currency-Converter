package CurrencyConverter.WindowController;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;

import org.testfx.api.FxAssert;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

public class MainWindowControllerTest extends ApplicationTest {

    private MainWindowController mainWindow;
    private Button convertButton;
    private Button historyButton;
    private Button addCurrencyButton;
    private Button addRateButton;
    private Button adminButton;
    private Button changePopularCurButton;


    @Override
    public void start(Stage stage) {
        mainWindow = new MainWindowController();
        convertButton = mainWindow.getConvertButton();
        historyButton = mainWindow.getHistoryButton();
        addCurrencyButton = mainWindow.getAddCurrencyButton();
        addRateButton = mainWindow.getAddRateButton();
        adminButton = mainWindow.getAdminButton();
        changePopularCurButton = mainWindow.getChangePopularCurButton();

        ConvertWindowController convertControl = new ConvertWindowController(mainWindow.getProcessor());
        HistoryWindowController historyWindowController = new HistoryWindowController(mainWindow.getProcessor());
        AddCurrencyWindowController addCurrencyWindowController = new AddCurrencyWindowController(mainWindow.getProcessor());
        AddRateWindowController addRateWindowController = new AddRateWindowController(mainWindow.getProcessor(), mainWindow);
        ChangePopCurWindowController changePopCurWindowController = new ChangePopCurWindowController(mainWindow.getProcessor(), mainWindow);
        AdminWindowController adminWindowController = new AdminWindowController(mainWindow.getProcessor(), adminButton);
        mainWindow.updateTable();
        stage.setScene(mainWindow.getScene());
    }

    /**
     * The the button on this page have correct text
     */
    @Test
    public void testButtonText() {
        Assertions.assertThat(convertButton).hasText("Convert");
        Assertions.assertThat(historyButton).hasText("History");
        Assertions.assertThat(addCurrencyButton).hasText("Add Currency");
        Assertions.assertThat(addRateButton).hasText("Add Rate");
        Assertions.assertThat(changePopularCurButton).hasText("Change Pop.");
        Assertions.assertThat(adminButton).hasText("Admin");
    }

    /**
     * The convert button can click to convert window
     */
    @Test
    public void testClickConvertButton() {
        clickOn(convertButton);
        FxAssert.verifyThat(window("Convert"), WindowMatchers.isShowing());
    }

    /**
     * The history button can click to history window
     */
    @Test
    public void testClickHistoryButton() {
        clickOn(historyButton);
        FxAssert.verifyThat(window("History"), WindowMatchers.isShowing());
    }

    /**
     * The add currency button can click to add currency window
     */
    @Test
    public void testClickAddCurrButton() {
        clickOn(addCurrencyButton);
        FxAssert.verifyThat(window("Add new currency"), WindowMatchers.isShowing());
    }

    /**
     * The add rate button can click to add rate window
     */
    @Test
    public void testClickAddRateButton() {
        clickOn(addRateButton);
        FxAssert.verifyThat(window("Add new exchange rate"), WindowMatchers.isShowing());
    }

    /**
     * The change popular currencies button can click to change popular currency window
     */
    @Test
    public void testClickChangePopButton() {
        clickOn(changePopularCurButton);
        FxAssert.verifyThat(window("Change popular currency"), WindowMatchers.isShowing());
    }

    /**
     * The change popular currencies button can click to change popular currency window
     */
    @Test
    public void testClickAdminButton() {
        clickOn(adminButton);
        FxAssert.verifyThat(window("Admin Login"), WindowMatchers.isShowing());
    }


    /**
     * Test the normal user cannot change to add currency, add rate, change popular currency window
     */
    @Test
    public void testNormalUser() {
        mainWindow.getProcessor().logoutAdmin();
        clickOn(addCurrencyButton);
        clickOn(addRateButton);
        clickOn(changePopularCurButton);
    }
}
