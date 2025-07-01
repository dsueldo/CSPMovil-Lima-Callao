package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@Composable
fun LoginFooter(
    onClickRegistration: () -> Unit,
    onClickLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TextButton(
            onClick = onClickLogin,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(Red_Dark),
        ) {
            Text(
                text = stringResource(R.string.enter),
                color = MaterialTheme.colorScheme.onPrimary,
                style = Typography.titleSmall
            )
        }
        TextButton(
            onClick = onClickRegistration,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(Red_Dark),
        ) {
            Text(
                text = stringResource(R.string.register),
                color = MaterialTheme.colorScheme.onPrimary,
                style = Typography.titleSmall
            )
        }
    }
}

@Preview
@Composable
private fun LoginFooterPreview() {
    LoginFooter(onClickRegistration = {}, onClickLogin = {})
}