package CurrencyConverter.User;


public class AdminUserImpl implements User {

    /**
     * Allow the user to maintain currencies
     *
     * @return a true value
     */
    @Override
    public boolean canMaintainCurrency() {
        return true;
    }
}
