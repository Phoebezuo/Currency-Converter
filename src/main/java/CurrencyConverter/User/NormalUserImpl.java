package CurrencyConverter.User;


public class NormalUserImpl implements User {

    /**
     * Disallow the user to maintain currencies
     *
     * @return a false value
     */
    @Override
    public boolean canMaintainCurrency() {
        return false;
    }

}
