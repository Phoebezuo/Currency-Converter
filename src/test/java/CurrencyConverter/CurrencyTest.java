package CurrencyConverter;

import CurrencyConverter.Model.Currency;
import CurrencyConverter.Model.ExchangeRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
* Test the Currency class
*/
public class CurrencyTest{
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
            fail("IO Exception when initializing the processor.");
        }
    }

    /**
     * Test the getRate method
     */
    @Test
    public void testGetRate() {
        Currency usd = new Currency("USD");
        assertEquals(usd.getRate("USD",LocalDate.of(2020,9,28)),0,0);
        usd.addRate("CNY", LocalDate.of(2020,9,28), 1);
        assertEquals(1,usd.getRate("CNY",LocalDate.of(2020,9,28)),1);
        assertEquals(0,usd.getRate("CNY",LocalDate.of(2020,9,29)),0);
    }
    /**
    * Test the getLatestDate method for one date
    */
    @Test
    public void testGetLatestDateOfOneDate() {
        Currency usd = new Currency("USD");
        assertNull(usd.getLatestDate("USD"));
        usd.addRate("CNY", LocalDate.of(2020,9,28), 1);
        assertEquals(LocalDate.of(2020,9,28),usd.getLatestDate("CNY"));
        assertEquals(1,usd.getLatestRate("CNY"),1);
    }

    /**
    * Test the getLatestDate method for two dates
    */
    @Test
    public void testGetLatestDateOfTwoDates(){
        Currency usd = new Currency("USD");
        usd.addRate("CNY", LocalDate.of(2020,9,28), 1);
        usd.addRate("CNY", LocalDate.of(2020,9,29),1);
        assertEquals(LocalDate.of(2020,9,29),usd.getLatestDate("CNY"));
    }


    /**
    * Test the getLatestDate method from two currencies
    */
    @Test
    public void testGetLatestDateOfTwoCur(){
        Currency usd = new Currency("USD");
        usd.addRate("CNY", LocalDate.of(2020,9,28), 1);
        usd.addRate("AUD", LocalDate.of(2020,9,29),1);
        assertEquals(LocalDate.of(2020,9,28),usd.getLatestDate("CNY"));
        assertEquals(LocalDate.of(2020,9,29),usd.getLatestDate("AUD"));
    }

    /**
     * Test the getRecordsTo method, it will return a new List
     */
    @Test
    public void testGetRecordsTo() {
        Currency usd = new Currency("USD");
        assertEquals(new ArrayList<ExchangeRecord>(),usd.getRecordsTo("USD")) ;
    }

    /**
     * Test the override equals method
     */
    @Test
    public void testEquals() {
        Currency usd = new Currency("USD");
        Currency usd2 = new Currency("USD");
        Currency cny = new Currency("CNY");
        assertTrue(usd.equals(usd2));
        assertFalse(usd.equals(cny));

        assertTrue(usd.equals(usd));
        assertFalse(usd.equals(null));
        ExchangeRecord exchangeRecord = new ExchangeRecord(
                "CNY","AUD", LocalDate.of(2020,9,29), 1);
        assertFalse(usd.equals(exchangeRecord));
    }

    /**
     * Test getSecondLatestDate and getSecondLatestRate methods
     */
    @Test
    public void testSecondLatestDateAndRate() {
        Currency usd = new Currency("USD");
        usd.addRate("CNY", LocalDate.of(2020,9,28), 1);
        assertEquals(LocalDate.of(2020,9,28),usd.getSecondLatestDate("CNY"));


        usd.addRate("CNY", LocalDate.of(2020,9,29),1);

        LocalDate date1 = LocalDate.of(2020, 9, 28);
        LocalDate date2 = LocalDate.of(2020, 9, 29);
        ExchangeRecord exchangeRecord1 = new ExchangeRecord("CNY","AUD", date1, 1);
        ExchangeRecord exchangeRecord2 = new ExchangeRecord("CNY","AUD", date2, 1);
        List<ExchangeRecord> exchangeRecords = new ArrayList<>();
        exchangeRecords.add(exchangeRecord1);
        exchangeRecords.add(exchangeRecord2);

        assertEquals(exchangeRecords.get(0).getDate(),usd.getSecondLatestDate("CNY"));
        assertEquals(exchangeRecords.get(1).getRateValue(),usd.getSecondLatestRate("CNY"),1);
        assertNull(usd.getSecondLatestDate("USD"));


    }

    /**
     *Test getExchangeRecordAtDate as edge situation
     */
    @Test
    public void getExchangeRecordAtDate() {
        Currency usd = new Currency("USD");
        usd.addRate("CNY", LocalDate.of(2020,9,28), 1);

        assertNull(usd.getExchangeRecordAtDate("CNY",LocalDate.of(2020,9,29)));
    }

}
