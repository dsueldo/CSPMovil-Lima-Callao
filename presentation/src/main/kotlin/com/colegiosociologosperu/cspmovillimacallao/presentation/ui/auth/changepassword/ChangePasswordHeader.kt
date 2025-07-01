package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.changepassword

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.HeaderComponente
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordHeader(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    HeaderComponente(
        title = stringResource(R.string.change_password),
        onBack = onBack,
        modifier = modifier
    )
}

@Preview
@Composable
private fun ChangePasswordHeaderPreview() {
    ChangePasswordHeader(
        onBack = {}
    )
}