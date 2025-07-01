package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import androidx.compose.ui.text.input.TextFieldValue

interface ValidationService {
    fun validate(input: TextFieldValue): Boolean
    fun getErrorMessage(): String
}