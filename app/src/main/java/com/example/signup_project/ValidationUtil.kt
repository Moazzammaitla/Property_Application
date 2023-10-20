package com.example.signup_project

import android.util.Patterns

class ValidationUtil {
    companion object {
        fun isValidName(name: String): Boolean {
            return name.isNotEmpty()
        }

        fun isValidPhoneNumber(phone: String): Boolean {
            return phone.isNotEmpty() && phone.length == 11
        }

        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            // Password must contain at least one special character, one uppercase letter, and one lowercase letter
            val specialCharRegex = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>?]")
            val uppercaseRegex = Regex("[A-Z]")
            val lowercaseRegex = Regex("[a-z]")

            val hasSpecialChar = specialCharRegex.containsMatchIn(password)
            val hasUppercase = uppercaseRegex.containsMatchIn(password)
            val hasLowercase = lowercaseRegex.containsMatchIn(password)

            return password.length >= 8 && hasSpecialChar && hasUppercase && hasLowercase
        }
    }
}