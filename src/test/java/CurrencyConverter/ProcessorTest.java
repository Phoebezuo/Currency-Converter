package CurrencyConverter;

import CurrencyConverter.Model.ExchangeRecord;
import CurrencyConverter.Model.ExchangeRecordPair;
import CurrencyConverter.User.AdminUserImpl;
import CurrencyConverter.User.NormalUserImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test Processor class
 */
public class ProcessorTest {
    Processor processor;

    /**
     * Initializing the processor for testing
     */
    @Before
    public void init() {
        try {
            processor = new Processor();
        } catch (IOException e) {
            fail("IO Exception when initializing the processor.");
        }
    }

    /**
     * Recover the resources file that make sure every testing test in same situation
     */
    @Before
    @After
    public void restoreResources() {
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
     * test loadConfig method
     */
    @Test
    public void testLoadConfig() {
        assertEquals("admin", processor.getAdminPassword());
        String[] popCur = {"USD", "HKD", "JPY", "AUD"};
        assertArrayEquals(popCur, processor.getPopularCurrencyNames());
    }

    /**
    * test cahngePopularCurrency method for correct input
    */
    @Test
    public void testChangePopularCurrency1() throws IOException {
        processor.loginAdmin("admin");

        String[] popCur = {"USD", "HKD", "JPY", "AUD"};
        assertArrayEquals(popCur, processor.getPopularCurrencyNames());
        boolean result = processor.changePopularCurrency("JPY", "CAD");
        assertTrue(result);

        popCur[2] = "CAD";
        assertArrayEquals(popCur, processor.getPopularCurrencyNames());
    }

    /**
    * test cahngePopularCurrency method without login admin
    */
    @Test
    public void testChangePopularCurrency2() throws IOException {
        String[] popCur = {"USD", "HKD", "JPY", "AUD"};
        assertArrayEquals(popCur, processor.getPopularCurrencyNames());
        boolean result = processor.changePopularCurrency("JPY", "CAD");
        assertFalse(result);
    }

    @Test
    public void testUpdatePopularCurrency() throws IOException {
        String[] popCur = {"USD", "HKD", "JPY", "AUD"};
        assertArrayEquals(popCur, processor.getPopularCurrencyNames());

        processor.loginAdmin(processor.getAdminPassword());
        processor.changePopularCurrency("AUD", "CAD");

        // test the currency is updated in code
        popCur[3] = "CAD";
        assertArrayEquals(popCur, processor.getPopularCurrencyNames());

        // test the currency is updated in config file
        String[] strings = new String[popCur.length + 1];
        strings[0] = processor.getAdminPassword();
        System.arraycopy(popCur, 0, strings, 1, strings.length - 1);
        assertArrayEquals(FileHandler.loadConfig("src/main/resources/config.csv"), strings);
    }

    /**
    * test getHistory method with exsisting date
    */
    @Test
    public void testGetHistory1() {
        //set the start date and end date
        LocalDate start = LocalDate.of(2020, 9, 25);
        LocalDate end = LocalDate.of(2020, 9, 26);


        //construct the expected result
        List<ExchangeRecordPair> expectedHistoryList = new ArrayList<>();
        List<ExchangeRecordPair> actualHistoryList = processor.getHistory("JPY", "CAD", start, end);

        ExchangeRecord expectedFirstDayFirstCurrency = new ExchangeRecord("JPY", "CAD", LocalDate.of
                (2020, 9, 25), 0.0127);
        ExchangeRecord expectedSecondDayFirstCurrency = new ExchangeRecord("JPY", "CAD", LocalDate.of
                (2020, 9, 26), 0.012679);

        ExchangeRecord expectedFirstDaySecondCurrency = new ExchangeRecord
                ("CAD", "JPY", expectedFirstDayFirstCurrency.getDate(), 78.740698);
        ExchangeRecord expectedSecondDaySecondCurrency = new ExchangeRecord
                ("CAD", "JPY", expectedSecondDayFirstCurrency.getDate(), 78.871945);

        ExchangeRecordPair expectedFirstDay = new ExchangeRecordPair
                ("JPY", "CAD", expectedFirstDayFirstCurrency.getDate(),
                        expectedFirstDayFirstCurrency, expectedFirstDaySecondCurrency);
        ExchangeRecordPair expectedSecondDay = new ExchangeRecordPair
                ("JPY", "CAD", expectedSecondDayFirstCurrency.getDate(),
                        expectedSecondDayFirstCurrency, expectedSecondDaySecondCurrency);
        expectedHistoryList.add(expectedFirstDay);
        expectedHistoryList.add(expectedSecondDay);


        assertEquals(expectedHistoryList.size(), actualHistoryList.size());
        for (int i = 0; i < expectedHistoryList.size(); i++) {
            assertEquals(expectedHistoryList.get(i), actualHistoryList.get(i));
        }
    }

    /**
    * test getHistory method with nonexistent start date
    */
    @Test
    public void testGetHistory2() {
        //set the start date and end date
        LocalDate start = LocalDate.of(2020, 9, 24);
        LocalDate end = LocalDate.of(2020, 9, 26);


        //construct the expected result
        List<ExchangeRecordPair> expectedHistoryList = new ArrayList<>();
        List<ExchangeRecordPair> actualHistoryList = processor.getHistory("JPY", "CAD", start, end);

        ExchangeRecord expectedFirstDayFirstCurrency = new ExchangeRecord("JPY", "CAD", LocalDate.of
                (2020, 9, 25), 0.0127);
        ExchangeRecord expectedSecondDayFirstCurrency = new ExchangeRecord("JPY", "CAD", LocalDate.of
                (2020, 9, 26), 0.012679);

        ExchangeRecord expectedFirstDaySecondCurrency = new ExchangeRecord
                ("CAD", "JPY", expectedFirstDayFirstCurrency.getDate(), 78.740698);
        ExchangeRecord expectedSecondDaySecondCurrency = new ExchangeRecord
                ("CAD", "JPY", expectedSecondDayFirstCurrency.getDate(), 78.871945);

        ExchangeRecordPair expectedFirstDay = new ExchangeRecordPair
                ("JPY", "CAD", expectedFirstDayFirstCurrency.getDate(),
                        expectedFirstDayFirstCurrency, expectedFirstDaySecondCurrency);
        ExchangeRecordPair expectedSecondDay = new ExchangeRecordPair
                ("JPY", "CAD", expectedSecondDayFirstCurrency.getDate(),
                        expectedSecondDayFirstCurrency, expectedSecondDaySecondCurrency);
        expectedHistoryList.add(expectedFirstDay);
        expectedHistoryList.add(expectedSecondDay);


        assertEquals(expectedHistoryList.size(), actualHistoryList.size());
        for (int i = 0; i < expectedHistoryList.size(); i++) {
            assertEquals(expectedHistoryList.get(i), actualHistoryList.get(i));
        }
    }


    /**
    *Test loginAdmin method for an incorrect password
    */
    @Test
    public void testFailLoginAdmin() {
      boolean result = processor.loginAdmin("admi");
      assertFalse(result);
    }

    /**
    *Test loginAdmin method for a correct password
    */
    @Test
    public void testLoginAdmin() {
        processor.loginAdmin("admin");
        assertTrue(processor.getCurrentUser() instanceof AdminUserImpl);
    }

    /**
    *Test logoutAdmin method
    */
    @Test
    public void testLogOutAdmin() {
        processor.logoutAdmin();
        assertTrue(processor.getCurrentUser() instanceof NormalUserImpl);
    }

    /**
    *Test logoutAdmin method by logging in first
    */
    @Test
    public void testLoginAndLogout() {
        processor.loginAdmin("admin");
        assertTrue(processor.getCurrentUser() instanceof AdminUserImpl);
        processor.logoutAdmin();
        assertTrue(processor.getCurrentUser() instanceof NormalUserImpl);
    }

    @Test
    public void testHistoryStatistic() {

        // set the date of start and end
        LocalDate start = LocalDate.of(2020, 9, 25);
        LocalDate end = LocalDate.of(2020, 9, 26);

        //create a List<ExchangeRecordPair>
        List<ExchangeRecordPair> hist = processor.getHistory("CAD", "JPY", start, end);
        assertEquals(hist.size(), 2);

        //the actual Map of history statistic
        Map<String, Double> histStat = processor.historyStatistic(processor.getHistory("CAD", "JPY",
                start, end));

        //put the expected value into the expected Map
        Map<String, Double> expected = new HashMap<>();
        expected.put("Average 1", 78.8063215);
        expected.put("Average 2", 0.0126895);
        expected.put("Median 1", 78.8063215);
        expected.put("Median 2", 0.0126895);
        expected.put("Maximum 1", 78.871945);
        expected.put("Maximum 2", 0.0127);
        expected.put("Minimum 1", 78.740698);
        expected.put("Minimum 2", 0.012679);
        expected.put("Standard Deviation 1", 0.06562350000000094);
        expected.put("Standard Deviation 2", 1.0500000000000093E-5);

        //in this point we need to make sure the assertEquals has same object to compare
        //in the situation below we have same Map<String,Double> type to compare
        assertEquals(expected.get("Average 1"), histStat.get("Average 1"));
        assertEquals(expected.get("Average 2"), histStat.get("Average 2"));
        assertEquals(expected.get("Median 1"), histStat.get("Median 1"));
        assertEquals(expected.get("Median 2"), histStat.get("Median 2"));
        assertEquals(expected.get("Maximum 1"), histStat.get("Maximum 1"));
        assertEquals(expected.get("Maximum 2"), histStat.get("Maximum 2"));
        assertEquals(expected.get("Minimum 1"), histStat.get("Minimum 1"));
        assertEquals(expected.get("Minimum 2"), histStat.get("Minimum 2"));
        assertEquals(expected.get("Standard Deviation 1"), histStat.get("Standard Deviation 1"));
        assertEquals(expected.get("Standard Deviation 2"), histStat.get("Standard Deviation 2"));
    }

    /**
    * Test the convert method
    */
    @Test
    public void testConvert() {
        double result = processor.convert("AUD", "CAD", 100.0);
        assertEquals(95.4143, result, 0.00000000);
    }

    /**
    *Test getDateAtoB method
    */
    @Test
    public void testGetDatesAToB() {
      List<LocalDate> result = processor.getDatesAToB("AUD","CAD");
      List<LocalDate> expected = new ArrayList<LocalDate>();
      expected.add(LocalDate.of(2020,9,25));
      expected.add(LocalDate.of(2020,9,26));
      expected.add(LocalDate.of(2020,9,27));
      expected.add(LocalDate.of(2020,9,28));
      expected.add(LocalDate.of(2020,9,29));
      assertEquals(expected, result);
    }

    /**
    *Test getAllCurNames method
    */
    @Test
    public void testGetAllCurNames() {
      String[] result = processor.getAllCurNames();
      String[] expected = new String[6];
      expected[0] = "AUD";
      expected[1] = "HKD";
      expected[2] = "JPY";
      expected[3] = "GBP";
      expected[4] = "USD";
      expected[5] = "CAD";
      assertArrayEquals(expected, result);
    }

    /**
    *Test hasRateAtDate method for exsisting data
    */
    @Test
    public void testHasRateAtDate1(){
      boolean result = processor.hasRateAtDate("AUD","CAD",LocalDate.of(2020,9,26));
      assertTrue(result);
    }

    /**
    *Test hasRateAtDate method for nonexistent data
    */
    @Test
    public void testHasRateAtDate2(){
      boolean result = processor.hasRateAtDate("AUD","CAD",LocalDate.of(2020,9,30));
      assertFalse(result);
    }

    /**
    * test addRate method for correct input value
    */
    @Test
    public void testAddRate1() throws IOException {
      processor.loginAdmin("admin");
      boolean result = processor.addRate("AUD","CAD",LocalDate.of(2020,9,30),0.95);
      assertTrue(result);
    }

    /**
    * test addRate method for incorrect input value
    */
    @Test
    public void testAddRate2() throws IOException {
      processor.loginAdmin("admin");
      boolean result = processor.addRate("CNY","AUD",LocalDate.of(2020,9,30),0.95);
      assertFalse(result);
    }

    /**
    * test addRate method without login admin
    */
    @Test
    public void testAddRate3() throws IOException {
      boolean result = processor.addRate("AUD","CAD",LocalDate.of(2020,9,30),0.95);
      assertFalse(result);
    }

    /**
    * test addCurrency method for correct input
    */
    @Test
    public void testAddCurrency1() throws IOException {
      processor.loginAdmin("admin");
      boolean result = processor.addCurrency("CNY");
      assertTrue(result);
    }

    /**
    * test addCurrency method for incorrect input
    */
    @Test
    public void testAddCurrency2() throws IOException {
      processor.loginAdmin("admin");
      boolean result = processor.addCurrency("AUD");
      assertFalse(result);
    }

    /**
    * test addCurrency method without login admin
    */
    @Test
    public void testAddCurrency3() throws IOException {
      boolean result = processor.addCurrency("CNY");
      assertFalse(result);
    }

    /**
    * test calMedian method with odd length of array
    */
    @Test
    public void testCalMedian() {
      double[] arr = {1,3,5};
      double result = processor.calMedian(arr);
      assertEquals(3,result,0.000);
    }
}
