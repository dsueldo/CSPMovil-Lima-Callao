package com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits

data class Benefits(
    var id: String = "",
    val image: String = "",
    val title: String = "",
    val content: String = "",
    val order: Int = 0,
    val source: String = "",
    val date: String = "",
)