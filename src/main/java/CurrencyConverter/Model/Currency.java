package CurrencyConverter.Model;

import java.time.LocalDate;
import java.util.*;

public class Currency {
    private final String name;
    private final Map<String, List<ExchangeRecord>> rateTo;

    public Currency(String name) {
        this.name = name;
        rateTo = new HashMap<>();
    }

    public void addRate(String target, LocalDate date, double rateValue) {
        List<ExchangeRecord> rate;
        if (rateTo.get(target) == null) {
            // If the target currency doesn't have any rates on any date,
            // create a new HashMap to store data.
            rate = new ArrayList<>();
            rateTo.put(target, rate);
        } else {
            rate = rateTo.get(target);
        }
        rate.add(new ExchangeRecord(this.name, target, date, rateValue));
    }

    public double getRate(String target, LocalDate date) {
        List<ExchangeRecord> records = rateTo.get(target);
        if (records == null) { return 0; }

        for (ExchangeRecord record : records) {
            if (record.getDate().isEqual(date)) {
                return record.getRateValue();
            }
        }

        return 0;
    }

    /**
     * Get the latest date of the target from the  database
     *
     * @param target the currency we want convert to
     * @return the latest date
     */
    public LocalDate getLatestDate(String target) {
        List<ExchangeRecord> rates = this.rateTo.get(target);
        if (rates == null) { return null; }
        LocalDate latestDate = rates.get(0).getDate();
        for (ExchangeRecord ex : rates) {
            LocalDate date = ex.getDate();
            if (latestDate.isBefore(date)) {
                latestDate = date;
            }
        }
        return latestDate;
    }

    public LocalDate getSecondLatestDate(String target) {
        List<ExchangeRecord> rates = this.rateTo.get(target);
        if (rates == null) {
            return null;
        }
        if (rates.size() == 1) {
            return rates.get(0).getDate();
        }
        LocalDate first = rates.get(0).getDate();
        LocalDate second = first;
        for (ExchangeRecord rate : rates) {
            LocalDate cur = rate.getDate();
            if (cur.isAfter(first)) {
                second = first;
                first = cur;
            } else if (cur.isAfter(second)) {
                second = cur;
            }
        }
        return second;
    }

    public double getSecondLatestRate(String target) {
        return getRate(target, getSecondLatestDate(target));
    }


    /**
     * Get the latest rate of the target from the  database
     *
     * @param target the currency we want convert to
     * @return the latest rate
     */
    public double getLatestRate(String target) {
        return getRate(target, getLatestDate(target));
    }

    public ExchangeRecord getExchangeRecordAtDate(String target, LocalDate date) {
        List<ExchangeRecord> records = rateTo.get(target);
        for (ExchangeRecord record : records) {
            if (record.getDate().isEqual(date)) {
                return record;
            }
        }
        return null;
    }

    public List<ExchangeRecord> getRecordsTo(String target) {
        List<ExchangeRecord> records = this.rateTo.get(target);

        return records != null ? records : new ArrayList<ExchangeRecord>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Currency currency = (Currency) o;
        return name.equals(currency.name);
    }

}
