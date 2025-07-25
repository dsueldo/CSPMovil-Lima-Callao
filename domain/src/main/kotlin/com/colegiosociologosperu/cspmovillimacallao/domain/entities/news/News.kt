package com.colegiosociologosperu.cspmovillimacallao.domain.entities.news

data class News (
    var id: String = "",
    val image: String = "",
    val title: String = "",
    val content: String = "",
    val description: String = "",
    val order: Int = 0,
    val source: String = "",
    val date: String = "",
)