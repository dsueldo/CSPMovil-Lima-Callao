package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@Composable
fun SignUpHeader(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun SignUpHeaderPreview() {
    SignUpHeader()
}