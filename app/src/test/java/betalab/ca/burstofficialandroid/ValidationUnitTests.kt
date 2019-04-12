package betalab.ca.burstofficialandroid

import android.content.Context
import betalab.ca.burstofficialandroid.ui.application.MainApplication
import betalab.ca.burstofficialandroid.ui.util.ValidationUtils
import org.junit.Test
import org.junit.Assert.*

class ValidationUnitTests {
    @Test
    fun isUsernameValid() {
        assertNull(ValidationUtils.instance.isUsernameValid("f"))
        assertNull(ValidationUtils.instance.isUsernameValid("F".repeat(31)))
        assertNotNull(ValidationUtils.instance.isUsernameValid(""))
        assertNotNull(ValidationUtils.instance.isUsernameValid("F".repeat(32)))
    }
    @Test
    fun isEmailValid() {
        assertNull(ValidationUtils.instance.isEmailValid("test@g.ca"))
        assertNotNull(ValidationUtils.instance.isEmailValid(""))
        assertNotNull(ValidationUtils.instance.isEmailValid("test@"))
        assertNotNull(ValidationUtils.instance.isEmailValid("test@g"))
    }
    @Test
    fun isPasswordValid() {
        assertNotNull(ValidationUtils.instance.isPasswordValid(""))
        assertNotNull(ValidationUtils.instance.isPasswordValid("12345678"))
        assertNotNull(ValidationUtils.instance.isPasswordValid("1234567f"))
        assertNotNull(ValidationUtils.instance.isPasswordValid("1234567F"))
        assertNotNull(ValidationUtils.instance.isPasswordValid("1Ffa".repeat(32))) //128 chars
        assertNull(ValidationUtils.instance.isPasswordValid("123456Ff"))
        assertNull(ValidationUtils.instance.isPasswordValid("Some Dumb password124"))

    }
}
