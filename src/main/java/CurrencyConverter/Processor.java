package CurrencyConverter;

import CurrencyConverter.Model.Currency;
import CurrencyConverter.Model.ExchangeRecord;
import CurrencyConverter.Model.ExchangeRecordPair;
import CurrencyConverter.User.AdminUserImpl;
import CurrencyConverter.User.NormalUserImpl;
import CurrencyConverter.User.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Processor {
    private final String ratesDataFilename = "src/main/resources/exchangeRates.json";
    private final String configFilename = "src/main/resources/config.csv";

    private final Map<String, CurrencyConverter.Model.Currency> currencies;
    private final String adminPassword;
    private User currentUser;
    private String[] popularCurrencies;

    public Processor() throws IOException {
        this.currentUser = new NormalUserImpl();
        this.popularCurrencies = new String[4];

        this.currencies = FileHandler.importRatesData(ratesDataFilename);
        String[] config = FileHandler.loadConfig(configFilename);
        adminPassword = config[0];
        popularCurrencies = Arrays.copyOfRange(config, 1, 5);
    }

    /**
     * Save both currency file and configuration file
     */
    public void save() throws IOException {
        FileHandler.exportRatesData(currencies, ratesDataFilename);
        FileHandler.saveConfig(adminPassword, popularCurrencies, configFilename);
    }

    /**
     * Add the new currency to the database
     *
     * @param name The name of the new currency
     * @return true if add successfully; false if the currency already exists
     */
    public boolean addCurrency(String name) throws IOException {
        if (currentUser.canMaintainCurrency() && !currencies.containsKey(name)) {
            currencies.put(name, new Currency(name));
            this.save();
            return true;
        }
        return false;
    }

    /**
     * Add a new exchange rate to the database
     *
     * @param src    Convert from this currency
     * @param target To this currency
     * @param date   On the specific date
     * @param rate   Exchange rate
     * @return true if add rates successfully; false if fails
     */
    public boolean addRate(String src, String target, LocalDate date, double rate) throws IOException {
        if (currentUser.canMaintainCurrency() && currencies.containsKey(src)) {
            currencies.get(src).addRate(target, date, rate);
            this.save();
            return true;
        }
        return false;
    }

    /**
     * Get the exchange rate from [src] to [target] on [date]
     *
     * @param src    Convert from this currency
     * @param target To this currency
     * @param date   On this date
     * @return the exchange rate
     */
    public double getExchangeRate(String src, String target, LocalDate date) {
        return currencies.get(src).getRate(target, date);
    }

    /**
     * Get the latest exchange rate from [src] to [target]
     *
     * @param src    Convert from this currency
     * @param target To this currency
     * @return the exchange rate
     */
    public double getLatestRate(String src, String target) {
        return currencies.get(src).getLatestRate(target);
    }

    public double getSecondLatestRate(String src, String target) {
        return currencies.get(src).getSecondLatestRate(target);
    }

    /**
     * Get the value of [amount] from [src] to [target]
     *
     * @param src    Convert from this currency
     * @param target To this currency
     * @param amount How much money from src
     * @return the equivalence value of amount in target
     */
    public double convert(String src, String target, double amount) {
        LocalDate latestDate = this.currencies.get(src).getLatestDate(target);
        double rate = this.getExchangeRate(src, target, latestDate);
        return amount * rate;
    }

    /**
     * Update popular currencies that change [src] to [target] and save to config file
     *
     * @param src    original popular currency
     * @param target latest popular currency
     */
    public boolean changePopularCurrency(String src, String target) throws IOException {
        if (currentUser.canMaintainCurrency()) {
            for (int i = 0; i < popularCurrencies.length; i++) {
                if (popularCurrencies[i].equals(src)) {
                    popularCurrencies[i] = target;
                    break;
                }
            }
            this.save();
            return true;
        }
        return false;
    }

    public boolean hasRateAtDate(String curA, String curB, LocalDate date) {
        return currencies.get(curA).getRate(curB, date) != 0;
    }

    /**
     * Check whether the password is correct, if yes, then login
     *
     * @param password the string that user types in
     * @return true if the password is correct, false otherwise.
     */
    public boolean loginAdmin(String password) {
        if (password.equals(this.getAdminPassword())) {
            this.currentUser = new AdminUserImpl();
            return true;
        }
        return false;
    }

    /**
     * log out from admin user to normal user
     */
    public void logoutAdmin() {
        this.currentUser = new NormalUserImpl();
    }

    /**
     * @param src the name of original popular currency
     * @param target the name of original popular currency
     * @param start the start date of the currency rate
     * @param end the end date of the currency rate
     * @return Return a list of ExchangeRecordPair from the start date to end date
     */
    public List<ExchangeRecordPair> getHistory(String src, String target,
                                               LocalDate start, LocalDate end) {
        //create the list to restore the history record
        List<ExchangeRecordPair> getHistoryRecordList = new ArrayList<>();

        //for loop to add the current date ExchangeRecordPair to the list
        while (start.isBefore(end) || start.isEqual(end)) {
            ExchangeRecord firstCurrencyRate = this.currencies.get(src).getExchangeRecordAtDate(target, start);
            ExchangeRecord secondCurrencyRate = this.currencies.get(target).getExchangeRecordAtDate(src, start);
            // if src and target do not have current day's currency rate then go next day.
            if (firstCurrencyRate == null || secondCurrencyRate == null) {
                start = start.plusDays(1);
                continue;
            }
            //create current day's ExchangeRecordPair
            ExchangeRecordPair currentDayExchangeRecordPair = new ExchangeRecordPair(src, target, start, firstCurrencyRate, secondCurrencyRate);
            getHistoryRecordList.add(currentDayExchangeRecordPair);
            start = start.plusDays(1);
        }

        return getHistoryRecordList;
    }

    /**
     * return a Map that contains the specific value of exchange value by a user in two currencies
     *
     * @param history A list of ExchangeRecordPair restore the rateValue and specific date of exchange
     * @return Map<String, Double> return the mean,median,max,min and standard deviation
     */
    public Map<String, Double> historyStatistic(List<ExchangeRecordPair> history) {
        // create a 2D array to restore the rateValue
        // and the size is dynamically depends on the history.size()
        double[][] exchangeRates = new double[2][history.size()];

        // for loop to add the rateValue into the 2D array
        for (int i = 0; i < history.size(); i++) {
            ExchangeRecordPair exchangeRecordPair = history.get(i);
            exchangeRates[0][i] = exchangeRecordPair.getFirstExchange().getRateValue();
            exchangeRates[1][i] = exchangeRecordPair.getSecondExchange().getRateValue();
        }

        //sorting the array make sure calMedian() works
        Arrays.sort(exchangeRates[0]);
        Arrays.sort(exchangeRates[1]);

        //creat a Map<String,Double> to restore the value of mean,median,max,min,sd
        Map<String, Double> historyStatistic = new HashMap<>();

        //two for loops to calculate the value separately and efficiently
        //first loops is for looping the first to second currencies exchange value when i = 0
        //second loops is for looping the second to first currencies exchange value when i = 1
        for (int i = 0; i < exchangeRates.length; i++) {

            //initial the sum = 0 when the first loop finished
            double sum = 0;
            for (int j = 0; j < exchangeRates[i].length; j++) {
                sum += exchangeRates[i][j];
            }
            //put value into the Map
            historyStatistic.put("Average " + (i + 1), sum / exchangeRates[i].length);
            historyStatistic.put("Median " + (i + 1), calMedian(exchangeRates[i]));
            historyStatistic.put("Maximum " + (i + 1),
                    exchangeRates[i][exchangeRates[i].length - 1]);
            historyStatistic.put("Minimum " + (i + 1), exchangeRates[i][0]);
            historyStatistic.put("Standard Deviation " + (i + 1),
                    calStandardDeviation(sum / exchangeRates[i].length, exchangeRates[i]));
        }
        //return Map
        return historyStatistic;
    }

    /**
     * The basic standard deviation function
     *
     * @param mean    the mean of data
     * @param numbers the data set
     * @return standard deviation
     */
    public double calStandardDeviation(double mean, double[] numbers) {
        double sd;
        double variance = 0;

        //calculate the variance
        for (double v : numbers) {
            variance += Math.pow((v - mean), 2);
        }
        variance = variance / numbers.length;

        sd = Math.sqrt(variance);
        return sd;
    }

    /**
     * The basic calculate median function
     *
     * @param array a sorted array
     * @return a median from a sorted array
     */
    public double calMedian(double[] array) {

        double median;
        int mid = array.length / 2;

        //depending on the odd or even length of array
        //to use different median calculations
        if (array.length % 2 == 0) {
            median = (array[mid] + array[(mid - 1)]) / 2;
        } else {
            median = array[mid];
        }
        return median;
    }

    public List<LocalDate> getDatesAToB(String a, String b) {
        List<ExchangeRecord> records = currencies.get(a).getRecordsTo(b);
        List<LocalDate> dates = new ArrayList<>();
        for (ExchangeRecord record : records) {
            dates.add(record.getDate());
        }
        Collections.sort(dates);
        return dates;
    }

    public String[] getAllCurNames() {
        return this.currencies.keySet().toArray(new String[0]);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String[] getPopularCurrencyNames() {
        return popularCurrencies;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

}
