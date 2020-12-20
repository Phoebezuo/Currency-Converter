package CurrencyConverter;

import CurrencyConverter.User.AdminUserImpl;
import CurrencyConverter.User.NormalUserImpl;
import CurrencyConverter.User.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    /**
     * Test all functions in User interface and override functions in subclasses
     */
    @Test
    public void testAdminUserImpl() {
        User admin = new AdminUserImpl();
        User normal = new NormalUserImpl();

        assertTrue(admin.canConvert());
        assertTrue(normal.canConvert());

        assertTrue(admin.canDisplayPopularCurrency());
        assertTrue(normal.canDisplayPopularCurrency());

        assertTrue(admin.canMaintainCurrency());

        assertFalse(normal.canMaintainCurrency());
    }
}
