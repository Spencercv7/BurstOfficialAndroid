package betalab.ca.burstofficialandroid.ui.util

import java.util.regex.Pattern

class ValidationUtils {
    companion object {
        private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun isUsernameValid(username: String): String? {
            return when {
                username.isEmpty() -> "Empty username"
                username.length > 31 -> "Username must be under 32 characters"
                !isUsernameAvailable(username) -> "Username already taken"
                else -> null
            }
        }

        fun isEmailValid(email: String): String? {
            return if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches())
                "Invalid email address"
            else null
        }

        fun isPasswordValid(password: String): String? {
            return when {
                password.isEmpty() -> "Password empty"
                password.length < 8 -> "Password must be at least 8 characters"
                !hasLowerCase(password) -> "Password must contain one lowercase letter"
                !hasUpperCase(password) -> "Password must contain one uppercase letter"
                !hasNumber(password) -> "Password must contain one number"
                password.length > 127 -> "Username must be under 128 characters"
                else -> null
            }
        }

        private fun isUsernameAvailable(@Suppress("UNUSED_PARAMETER") name: String): Boolean {
            return true
        }

        private fun hasLowerCase(s: String): Boolean = s.filter { c -> c.isLowerCase() }.count() > 0
        private fun hasUpperCase(s: String): Boolean = s.filter { c -> c.isUpperCase() }.count() > 0
        private fun hasNumber(s: String): Boolean = s.contains(Regex("\\d"))
    }
}