package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;

import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.ComboBoxMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class AddRateWindowControllerTest extends ApplicationTest {

    private Processor processor;
    private MainWindowController mainWindow;
    private AddRateWindowController addRateWindow;
    private Button addRateButton;
    private ComboBox<String> curA;
    private ComboBox<String> curB;
    private TextField in1;
    private TextField in2;
    private Button addBtn;

    @Override
    public void start(Stage stage) {
        mainWindow = new MainWindowController();
        processor = mainWindow.getProcessor();
        addRateWindow = new AddRateWindowController(processor, mainWindow);
        addRateButton = mainWindow.getAddRateButton();
        curA = addRateWindow.getComboBox1();
        curB = addRateWindow.getComboBox2();
        in1 = addRateWindow.getText1();
        in2 = addRateWindow.getText2();
        addBtn = addRateWindow.getAddBtn();
        stage.setScene(mainWindow.getScene());
    }

    /**
     * test the number of currencies that can be selected
     */
    @Test
    public void testButtonText() {
        assertThat(curA, ComboBoxMatchers.hasItems(6));
        assertThat(curB, ComboBoxMatchers.hasItems(6));
    }

    /**
     * test the window do correct functionality with correct input
     */
    @Test
    public void testAddCorrectRate() {
        curA.getSelectionModel().select("AUD");
        curB.getSelectionModel().select("HKD");

        assertThat(curA, ComboBoxMatchers.hasSelectedItem("AUD"));
        assertThat(curB, ComboBoxMatchers.hasSelectedItem("HKD"));

        in1.setText("5");
        in2.setText("0.2");

        Assertions.assertThat(in1).hasText("5");
        Assertions.assertThat(in2).hasText("0.2");

        clickOn(addBtn);
    }

    /**
     * input rate only accept number and one dot point
     */
    @Test
    public void testAddWrongRate() {
        in1.setText("hello");
        Assertions.assertThat(in1).hasText("0");

        in1.setText("11.11.0");
        Assertions.assertThat(in1).hasText("11.11");
    }

    /**
     * test add rate with number start with 0
     */
    @Test
    public void testAddRateStartWithZero() {
        in1.setText("0.0");
        Assertions.assertThat(in1).hasText("0.0");

        in1.setText("05");
        Assertions.assertThat(in1).hasText("5");
    }

    /**
     * Test add rate with 0
     */
    @Test
    public void testAddRateWithZero() {
        curA.getSelectionModel().select("AUD");
        curB.getSelectionModel().select("HKD");

        assertThat(curA, ComboBoxMatchers.hasSelectedItem("AUD"));
        assertThat(curB, ComboBoxMatchers.hasSelectedItem("HKD"));

        in1.setText("5");
        in2.setText("0");

        Assertions.assertThat(in1).hasText("5");
        Assertions.assertThat(in2).hasText("0");

        clickOn(addBtn);
    }
}
