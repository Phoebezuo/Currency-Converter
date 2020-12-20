package CurrencyConverter.WindowController;

import CurrencyConverter.Processor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;

import org.testfx.api.FxAssert;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.ComboBoxMatchers;

import java.time.LocalDate;

public class HistoryWindowControllerTest extends ApplicationTest {

    private MainWindowController mainWindow;
    private Processor processor;
    private Button historyButton;
    private HistoryWindowController historyWindow;
    private ComboBox<String> curA;
    private ComboBox<String> curB;
    private ComboBox<LocalDate> start;
    private ComboBox<LocalDate> end;

    @Override
    public void start(Stage stage) {
        mainWindow = new MainWindowController();
        processor = mainWindow.getProcessor();
        historyButton = mainWindow.getHistoryButton();
        historyWindow = new HistoryWindowController(processor);
        curA = historyWindow.getCur1();
        curB = historyWindow.getCur2();
        start = historyWindow.getStart();
        end = historyWindow.getEnd();
        stage.setScene(mainWindow.getScene());
    }

    /**
     * test the number of currencies and dates that can be selected
     */
    @Test
    public void testButtonText() {
        assertThat(curA, ComboBoxMatchers.hasItems(6));
        assertThat(curB, ComboBoxMatchers.hasItems(6));
        assertThat(start, ComboBoxMatchers.hasItems(5));
        assertThat(end, ComboBoxMatchers.hasItems(5));
    }


    /**
     * test the window do correct functionality with correct input on different dates
     */
    @Test
    public void testHistoryOnDifferentDay() {
        curA.getSelectionModel().select("AUD");
        curB.getSelectionModel().select("HKD");

        assertThat(curA, ComboBoxMatchers.hasSelectedItem("AUD"));
        assertThat(curB, ComboBoxMatchers.hasSelectedItem("HKD"));

        clickOn(start);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn(end);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        assertThat(start, ComboBoxMatchers.hasSelectedItem(LocalDate.of(2020, 9, 25)));
        assertThat(end, ComboBoxMatchers.hasSelectedItem(LocalDate.of(2020, 9, 26)));
    }

    /**
     * test the window do correct functionality with correct input on same dates
     */
    @Test
    public void testHistoryOnSameDay() {
        curA.getSelectionModel().select("AUD");
        curB.getSelectionModel().select("HKD");

        assertThat(curA, ComboBoxMatchers.hasSelectedItem("AUD"));
        assertThat(curB, ComboBoxMatchers.hasSelectedItem("HKD"));

        clickOn(start);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn(end);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        assertThat(start, ComboBoxMatchers.hasSelectedItem(LocalDate.of(2020, 9, 25)));
        assertThat(end, ComboBoxMatchers.hasSelectedItem(LocalDate.of(2020, 9, 25)));
    }
}
