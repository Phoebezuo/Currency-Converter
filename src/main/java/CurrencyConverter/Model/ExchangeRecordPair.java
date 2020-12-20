package CurrencyConverter.Model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * To restore the ExchangeRecord with double side of currencies exchange
 * make easier to use at getHistory() and HistoryStatistic() in Processor
 */
public class ExchangeRecordPair {
    private final String firstCurrency;
    private final String secondCurrency;
    private final LocalDate date;
    private final ExchangeRecord[] pair;

    /**
     * Initializing and construct the ExchangeRecordPair
     *
     * @param firstCurrency  the first exchange currency name
     * @param secondCurrency the second exchange currency name
     * @param date           the date of two currencies exchanged
     * @param firstExchange  the ExchangeRecord array that restore the rateValue of
     *                       first currency exchange to second currency
     * @param secondExchange the ExchangeRecord array that restore the rateValue of
     *                       second currency exchange to first currency
     */
    public ExchangeRecordPair(String firstCurrency, String secondCurrency,
                              LocalDate date, ExchangeRecord firstExchange,
                              ExchangeRecord secondExchange) {
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.date = date;
        pair = new ExchangeRecord[2];
        pair[0] = firstExchange;
        pair[1] = secondExchange;
    }

    /**
     * An override method to return true of false that two ExchangeRecord are the same
     *
     * @param o the dynamic Object type
     * @return boolean true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        //if all the situation is true then return true
        ExchangeRecordPair that = (ExchangeRecordPair) o;
        return Objects.equals(firstCurrency, that.firstCurrency) &&
                Objects.equals(secondCurrency, that.secondCurrency) &&
                Objects.equals(date, that.date) &&
                Arrays.equals(pair, that.pair);
    }

    /**
     * Get the name of first currency
     *
     * @return String the name of first currency
     */
    public String getFirstCurrency() {
        return firstCurrency;
    }

    /**
     * Get the name of second currency
     *
     * @return String the name of second currency
     */
    public String getSecondCurrency() {
        return secondCurrency;
    }

    /**
     * Get the first ExchangeRecord
     *
     * @return ExchangeRecord the first currency exchange to second currency record
     */
    public ExchangeRecord getFirstExchange() {
        return pair[0];
    }

    /**
     * Get the second ExchangeRecord
     *
     * @return ExchangeRecord the second currency exchange to first currency record
     */
    public ExchangeRecord getSecondExchange() {
        return pair[1];
    }

    public LocalDate getDate() {
        return date;
    }
}
