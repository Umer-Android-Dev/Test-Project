package com.appozee.technologies.mytestproject.utils

import org.junit.Assert.*

import org.junit.Test

class HelperTest {

    @Test
    fun isValidEmail() {
        // Arrange
        val helper = Helper()
        // Act
        val result = helper.isValidEmail("umer1234@gmail.com")
        // Assert
        assertEquals(true,result)
    }
}