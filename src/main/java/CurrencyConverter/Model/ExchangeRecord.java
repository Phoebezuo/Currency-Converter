package CurrencyConverter.Model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class is restoring the record of exchange from the src currency to the target currency
 * contain the date and the rateValue
 */
public class ExchangeRecord {
    private final String src;
    private final String target;
    private final LocalDate date;
    private final double rateValue;

    /**
     * A constructor of ExchangeRecord that initialize the basic filed value
     *
     * @param src       the currency that want to exchange from
     * @param target    the currency that want to exchange for
     * @param date      the date of exchange
     * @param rateValue the rate of src to target exchange
     */
    public ExchangeRecord(String src, String target, LocalDate date, double rateValue) {
        this.src = src;
        this.target = target;
        this.date = date;
        this.rateValue = rateValue;
    }

    /**
     * An override method that return true or false if two object is the same
     *
     * @param o a dynamic Object
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
        // if all the situation below are true then return true
        ExchangeRecord that = (ExchangeRecord) o;
        return Double.compare(that.rateValue, rateValue) == 0 &&
                Objects.equals(src, that.src) &&
                Objects.equals(target, that.target) &&
                Objects.equals(date, that.date);
    }

    /**
     * Return a String that is src currency name
     *
     * @return String the src currency name
     */
    public String getSrc() {
        return src;
    }

    /**
     * Return a String that is target currency name
     *
     * @return String the target currency name
     */
    public String getTarget() {
        return target;
    }

    /**
     * Return the data of exchange
     *
     * @return LocalDate that happening the exchange
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Return a double of the rate of exchange
     *
     * @return double of rateValue
     */
    public double getRateValue() {
        return rateValue;
    }

}
