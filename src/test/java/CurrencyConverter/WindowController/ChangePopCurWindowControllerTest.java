package CurrencyConverter.WindowController;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

public class ChangePopCurWindowControllerTest extends ApplicationTest {

    private ChangePopCurWindowController change;
    private Button c;

    public void start(Stage stage) {
        MainWindowController main = new MainWindowController();
        change = new ChangePopCurWindowController(main.getProcessor(), main);
        c = main.getChangePopularCurButton();

        stage.setScene(main.getScene());
    }

    /**
     *  Test if the window comes out
     */
    @Test
    public void testWindow() {
        clickOn(c);
        FxAssert.verifyThat(window("Change popular currency"), WindowMatchers.isShowing());
    }

}