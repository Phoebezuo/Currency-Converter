package CurrencyConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Test FileHandler class
 */
public class FileHandlerTest {
    Processor processor;

    /**
     * Initializing the processor for testing
     * @Before  Initializing this processor before the test
     */
    @Before
    public void init(){
        try {
            processor = new Processor();
        } catch (IOException e) {
            // identify the IO Exception
            fail("IO Exception when initializing the processor.");
        }
    }

    /**
     * Recover the resources file that make sure every testing test in same situation
     * @Before recover it before the testing
     * @After recover it after the testing
     */
    @Before
    @After
    public void restoreResources(){
        try {
            Files.copy(new File("src/main/resources/config_backup.csv").toPath(),
                    new File("src/main/resources/config.csv").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            Files.copy(new File("src/main/resources/exchangeRates_backup.json").toPath(),
                    new File("src/main/resources/exchangeRates.json").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing the saveConfig() and loadConfig()
     * @throws FileNotFoundException when the file address is not found
     */
    @Test
    public void testSaveConfig() throws FileNotFoundException {
        String adminPas = "admin";
        String[] currencies = {"CNY", "JPY", "EUR", "AUD"};

        //saving config to the filename address
        FileHandler.saveConfig(adminPas,currencies,"src/main/resources/config.csv");

        //keep same format of the output of loadConfig()
        int size = currencies.length;
        String[] strings = new String[size + 1];
        strings[0] = adminPas;

        //the actual output
        for (int i = 1; i < strings.length; i++) {
            strings[i] = currencies[i - 1];
        }

        assertArrayEquals(FileHandler.loadConfig("src/main/resources/config.csv"),strings);
    }
}
