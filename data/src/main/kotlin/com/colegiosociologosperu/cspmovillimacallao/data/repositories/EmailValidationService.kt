package com.colegiosociologosperu.cspmovillimacallao.data.repositories

import androidx.compose.ui.text.input.TextFieldValue
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.ValidationService

class EmailValidationService : ValidationService {

    private var errorMessage: String = ""

    override fun validate(input: TextFieldValue): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (input.text.isEmpty()) {
            errorMessage = "Ingrese el correo"
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input.text).matches()) {
            errorMessage = "Formato de correo inválido"
            false
        } else if (!input.text.matches(emailPattern.toRegex())) {
            errorMessage = "Formato de correo inválido"
            false
        } else {
            true
        }
    }

    override fun getErrorMessage(): String {
        return errorMessage
    }
}