package CurrencyConverter;
import CurrencyConverter.Model.ExchangeRecord;
import CurrencyConverter.Model.ExchangeRecordPair;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ExchangeRecordPairTest {
    LocalDate date1 = LocalDate.of(2020, 9, 25);
    LocalDate date2 = LocalDate.of(2020, 9, 26);

    /**
     * Test all functions in ExchangeRecordPair
     */
    @Test
    public void testBasicFunctions()  {
        ExchangeRecord exchangeRecord1 = new ExchangeRecord("USD","HKD", date1, 1);
        ExchangeRecord exchangeRecord2 = new ExchangeRecord("HKD","USY", date2, 2);
        ExchangeRecordPair exchangeRecordPair = new ExchangeRecordPair(
                "USD","HKD",date1,exchangeRecord1,exchangeRecord2);

        assertEquals("USD",exchangeRecordPair.getFirstCurrency());
        assertEquals("HKD",exchangeRecordPair.getSecondCurrency());
        assertEquals(date1,exchangeRecordPair.getDate());
        assertEquals(exchangeRecord1,exchangeRecordPair.getFirstExchange());
        assertEquals(exchangeRecord2,exchangeRecordPair.getSecondExchange());
    }

    /**
     * Test equals method and some edge situations
     */
    @Test
    public void testEquals() {
        ExchangeRecord exchangeRecord1 = new ExchangeRecord("USD","HKD", date1, 1);
        ExchangeRecord exchangeRecord2 = new ExchangeRecord("HKD","USY", date2, 2);
        ExchangeRecord exchangeRecord3 = new ExchangeRecord("HKD","CAD", date2, 3);
        ExchangeRecord exchangeRecord4 = new ExchangeRecord("CAD","USY", date2, 4);
        ExchangeRecordPair exchangeRecordPair = new ExchangeRecordPair(
                "USD","HKD",date1,exchangeRecord1,exchangeRecord2);
        ExchangeRecordPair exchangeRecordPair1 = new ExchangeRecordPair(
                "USD","HKD",date2,exchangeRecord1,exchangeRecord2);
        ExchangeRecordPair exchangeRecordPair2 = new ExchangeRecordPair(
                "USD","HKD",date1,exchangeRecord1,exchangeRecord2);
        ExchangeRecordPair exchangeRecordPair3 = new ExchangeRecordPair(
                "AUD","CAD",date1,exchangeRecord1,exchangeRecord3);
        ExchangeRecordPair exchangeRecordPair4 = new ExchangeRecordPair(
                "USD","AUD",date2,exchangeRecord1,exchangeRecord4);
        ExchangeRecordPair exchangeRecordPair5 = new ExchangeRecordPair(
                "USD","HKD",date1,exchangeRecord2,exchangeRecord3);

        assertTrue(exchangeRecordPair.equals(exchangeRecordPair2));
        assertTrue(exchangeRecordPair.equals(exchangeRecordPair));

        assertFalse(exchangeRecordPair.equals(null));
        assertFalse(exchangeRecordPair.equals(exchangeRecord1));
        assertFalse(exchangeRecordPair.equals(exchangeRecordPair3));
        assertFalse(exchangeRecordPair.equals(exchangeRecordPair4));
        assertFalse(exchangeRecordPair.equals(exchangeRecordPair1));
        assertFalse(exchangeRecordPair.equals(exchangeRecordPair5));


    }



}
