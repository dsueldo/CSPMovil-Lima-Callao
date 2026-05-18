package com.colegiosociologosperu.cspmovillimacallao.domain.entities.payment

data class PaymentsItem(
    val image: String = "",
    val title: String = "",
    val content: String = "",
    val note: String = "",
    val order: Int = 0,
)
