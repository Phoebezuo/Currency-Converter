package CurrencyConverter.WindowController;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;


public class AddCurrencyWindowControllerTest extends ApplicationTest {

    private AddCurrencyWindowController addCur;

    private Button add;

    public void start(Stage stage) {
        MainWindowController main = new MainWindowController();
        addCur = new AddCurrencyWindowController(main.getProcessor());

        add = main.getAddCurrencyButton();

        stage.setScene(main.getScene());
    }

    /**
     * Test if the error window shows when adding empty currencies
     */
    @Test
    public void test() {
        clickOn(add);
        clickOn(addCur.getAddBtn());
    }


}