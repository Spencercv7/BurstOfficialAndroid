package betalab.ca.burstofficialandroid

import android.content.Context
import betalab.ca.burstofficialandroid.ui.application.MainApplication
import betalab.ca.burstofficialandroid.ui.util.ValidationUtils
import org.junit.Test
import org.junit.Assert.*

class ValidationUnitTests {
    @Test
    fun isUsernameValid() {
        assertNull(ValidationUtils.isUsernameValid("f"))
        assertNull(ValidationUtils.isUsernameValid("F".repeat(31)))
        assertNotNull(ValidationUtils.isUsernameValid(""))
        assertNotNull(ValidationUtils.isUsernameValid("F".repeat(32)))
    }
    @Test
    fun isEmailValid() {
        assertNull(ValidationUtils.isEmailValid("test@g.ca"))
        assertNotNull(ValidationUtils.isEmailValid(""))
        assertNotNull(ValidationUtils.isEmailValid("test@"))
        assertNotNull(ValidationUtils.isEmailValid("test@g"))
    }
    @Test
    fun isPasswordValid() {
        assertNotNull(ValidationUtils.isPasswordValid(""))
        assertNotNull(ValidationUtils.isPasswordValid("12345678"))
        assertNotNull(ValidationUtils.isPasswordValid("1234567f"))
        assertNotNull(ValidationUtils.isPasswordValid("1234567F"))
        assertNotNull(ValidationUtils.isPasswordValid("1Ffa".repeat(32))) //128 chars
        assertNull(ValidationUtils.isPasswordValid("123456Ff"))
        assertNull(ValidationUtils.isPasswordValid("Some Dumb password124"))

    }
}
