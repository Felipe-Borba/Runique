package com.plcoding.auth.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UserDataValidatorTest {
    private lateinit var userDataValidator: UserDataValidator

    @BeforeEach
    fun setUp() {
        userDataValidator = UserDataValidator(
            patternValidator = object : PatternValidator { // this is called test double
                override fun matches(value: String): Boolean {
                    return true
                }
            }
        )
    }

    @Test
    fun testValidateEmail() {
        val email = "test@email.com"

        // because this just delegate to the dependency is a bit pointless to test, but I will leave just increase coverage test metrics
        // and because it's quick to test so I think worth it
        val isValid = userDataValidator.isValidEmail(email)

        assertThat(isValid).isTrue()
    }

    @ParameterizedTest
    @CsvSource(
        "Test12345, true",
        "test12345, false",
        "12345, false",
        "Test-12345, true",
        "'', false",
        "Te-15, false",
        "TEST12345, false",
    )
    fun testValidatePassword(password: String, expectedValue: Boolean) {
        val state = userDataValidator.validatePassword(password)

        assertThat(state.isValidPassword).isEqualTo(expectedValue)
    }
}