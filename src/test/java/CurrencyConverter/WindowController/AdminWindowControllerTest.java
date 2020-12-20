package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;

import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;


public class AdminWindowControllerTest extends ApplicationTest {

    private Processor processor;
    private MainWindowController mainWindow;
    private AdminWindowController adminWindow;
    private Button adminButton;
    private TextField inputPassword;
    private Button logButton;
    private Boolean loginStatus;

    @Override
    public void start(Stage stage) {
        mainWindow = new MainWindowController();
        processor = mainWindow.getProcessor();
        adminButton = mainWindow.getAdminButton();
        adminWindow = new AdminWindowController(processor, adminButton);

        inputPassword = adminWindow.getInputPassword();
        logButton = adminWindow.getLogButton();
        loginStatus = adminWindow.getLoginStatus();
        stage.setScene(mainWindow.getScene());
    }

    /**
     * The the button on this page have correct text
     */
    @Test
    public void testButtonText() {
        Assertions.assertThat(adminButton).hasText("Admin");
        Assertions.assertThat(adminWindow.getLabel()).hasText("Password");
        Assertions.assertThat(inputPassword).hasText("");
        Assertions.assertThat(logButton).hasText("Login");
        assertFalse(loginStatus);
    }

    /**
     * Test how the window works when the password is correct
     */
    @Test
    public void testCorrectPassword() {
        inputPassword.setText(processor.getAdminPassword());
        Assertions.assertThat(inputPassword).hasText("admin");
        clickOn(logButton);
        assertTrue(adminWindow.getLoginStatus());
        Assertions.assertThat(mainWindow.getAdminButton()).hasText("Logout");
    }

    /**
     * Test how the window works whhen the password is incorrect
     */
    @Test
    public void testWrongPassword() {
        String wrongPassword = "12345";
        inputPassword.setText(wrongPassword);
        Assertions.assertThat(inputPassword).hasText(wrongPassword);
        clickOn(logButton);
        assertFalse(adminWindow.getLoginStatus());
        clickOn(adminWindow.getReturnMainButton());
    }
}
