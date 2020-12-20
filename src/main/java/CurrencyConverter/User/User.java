package CurrencyConverter.User;


public interface User {

    /**
     * Allow user to convert currencies
     *
     * @return a true value
     */
    default boolean canConvert() {
        return true;
    }

    /**
     * Allow user to display the popular currencie
     *
     * @return a true value
     */
    default boolean canDisplayPopularCurrency() {
        return true;
    }


    /**
     * Allow the user to maintain currencies
     */
    boolean canMaintainCurrency();
}
