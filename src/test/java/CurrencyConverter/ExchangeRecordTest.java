package CurrencyConverter;

import CurrencyConverter.Model.Currency;
import CurrencyConverter.Model.ExchangeRecord;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class ExchangeRecordTest {

    LocalDate date = LocalDate.of(2020, 9, 25);
    LocalDate date1 = LocalDate.of(2020, 9, 26);


    /**
     * Test all functions in ExchangeRecord
     */
    @Test
    public void testBasicFunctions() {
        ExchangeRecord exchangeRecord = new ExchangeRecord("USD","HKD", date, 1);

        assertEquals("USD",exchangeRecord.getSrc());
        assertEquals(date,exchangeRecord.getDate());
        assertEquals(1,exchangeRecord.getRateValue(),1);
        assertEquals("HKD",exchangeRecord.getTarget());


    }

    /**
     * Test equals method and some edge situations
     */
    @Test
    public void testEquals() {
        ExchangeRecord exchangeRecord = new ExchangeRecord("USD","HKD", date, 1);
        ExchangeRecord exchangeRecord1 = new ExchangeRecord("USD","HKD", date, 1);
        ExchangeRecord exchangeRecord2 = new ExchangeRecord("CAD","USD", date, 1);
        ExchangeRecord exchangeRecord3 = new ExchangeRecord("USD","HKD", date1, 1);
        ExchangeRecord exchangeRecord4 = new ExchangeRecord("USD","AUD", date1, 1);
        ExchangeRecord exchangeRecord5 = new ExchangeRecord("USD","HKD", date, 2);

        assertTrue(exchangeRecord.equals(exchangeRecord1));
        assertTrue(exchangeRecord.equals(exchangeRecord));

        Currency usd = new Currency("USD");
        assertFalse(exchangeRecord.equals(usd));
        assertFalse(exchangeRecord.equals(null));
        assertFalse(exchangeRecord.equals(exchangeRecord2));
        assertFalse(exchangeRecord.equals(exchangeRecord4));
        assertFalse(exchangeRecord.equals(exchangeRecord3));
        assertFalse(exchangeRecord.equals(exchangeRecord5));

    }
}
