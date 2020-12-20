package CurrencyConverter.WindowController;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.ComboBoxMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConvertWindowControllerTest extends ApplicationTest {
    private Button convert;
    private ConvertWindowController convertControl;

    @Override
    public void start(Stage stage) {
        MainWindowController main = new MainWindowController();
        convertControl = new ConvertWindowController(main.getProcessor());

        convert = main.getConvertButton();
        stage.setScene(main.getScene());
    }


    /**
     *  Test if the number of items of ComboBox are 6
     */
    @Test
    public void testComboBoxItems() {
        assertThat(convertControl.getComboBox1(), ComboBoxMatchers.hasItems(6));
        assertThat(convertControl.getComboBox2(), ComboBoxMatchers.hasItems(6));
    }


    /**
     * To check if the Convert window is showing after click convert on the mainWindow
     * and check if the JPY is selected after clicking the combobox and select the country
     * two below of the AUD
     */
    @Test
    public void testComboBox1() {

        // go in Convert window:
        clickOn(convert);
        FxAssert.verifyThat(window("Convert"), WindowMatchers.isShowing());

        // given:
        assertThat(convertControl.getComboBox1(), ComboBoxMatchers.hasSelectedItem("AUD"));

        // when:
        clickOn(convertControl.getComboBox1());

        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // then:
        assertThat(convertControl.getComboBox1(), ComboBoxMatchers.hasSelectedItem("JPY"));
    }


    /**
     *  The same as testComboBox1(), test ComboBox2
     */
    @Test
    public void testComboBox2() {

        // go in Convert window:
        clickOn(convert);
        FxAssert.verifyThat(window("Convert"), WindowMatchers.isShowing());

        // given:
        assertThat(convertControl.getComboBox2(), ComboBoxMatchers.hasSelectedItem("HKD"));

        // when:
        clickOn(convertControl.getComboBox2());

        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // then:
        assertThat(convertControl.getComboBox2(), ComboBoxMatchers.hasSelectedItem("GBP"));
    }

    /**
     * Test if the input1 can accept strings(expect not)
     */
    @Test
    public void testInput1() {
        // go in Convert window:
        clickOn(convert);
        FxAssert.verifyThat(window("Convert"), WindowMatchers.isShowing());

        // given:
        assertThat(convertControl.getInput1(), TextInputControlMatchers.hasText("0"));
        clickOn(convertControl.getInput1());

        type(KeyCode.S, 2);
        // then:
        assertThat(convertControl.getInput1(), TextInputControlMatchers.hasText("0"));
    }

    /**
     * Test if input2 is changed after type numbers in input1
     */
    @Test
    public void testInput2() {
        // go in Convert window:
        clickOn(convert);
        FxAssert.verifyThat(window("Convert"), WindowMatchers.isShowing());

        // given:
        assertThat(convertControl.getInput1(), TextInputControlMatchers.hasText("0"));
        assertThat(convertControl.getInput2(), TextInputControlMatchers.hasText("0"));

        clickOn(convertControl.getInput1());

        type(KeyCode.DIGIT4, 2);

        //then:
        assertThat(convertControl.getInput2(), TextInputControlMatchers.hasText("242.77"));
    }

    /**
     *  Test if the content is still 0 after delete in input
     */
    @Test
    public void testDelete() {
        // given:
        assertThat(convertControl.getInput1(), TextInputControlMatchers.hasText("0"));
        assertThat(convertControl.getInput2(), TextInputControlMatchers.hasText("0"));

        clickOn(convertControl.getInput1());
        type(KeyCode.DELETE);

        clickOn(convertControl.getInput2());
        type(KeyCode.DELETE);

        //then:
        assertThat(convertControl.getInput1(), TextInputControlMatchers.hasText("0"));
        assertThat(convertControl.getInput2(), TextInputControlMatchers.hasText("0"));
    }

    /**
     *  Test if the content can only has one dot
     */
    @Test
    public void testMultiDots1() {
        // given:
        assertThat(convertControl.getInput1(), TextInputControlMatchers.hasText("0"));

        clickOn(convertControl.getInput1());
        type(KeyCode.DIGIT4);
        type(KeyCode.PERIOD,2);
        type(KeyCode.DIGIT5);

        // then:
        assertThat(convertControl.getInput1(), TextInputControlMatchers.hasText("4.5"));
    }

    /**
     *  Test if the content can only has one dot
     */
    @Test
    public void testMultiDots2() {
        // given:
        assertThat(convertControl.getInput2(), TextInputControlMatchers.hasText("0"));

        clickOn(convertControl.getInput2());
        type(KeyCode.DIGIT4);
        type(KeyCode.PERIOD,2);
        type(KeyCode.DIGIT5);

        // then:
        assertThat(convertControl.getInput2(), TextInputControlMatchers.hasText("4.5"));
    }


}