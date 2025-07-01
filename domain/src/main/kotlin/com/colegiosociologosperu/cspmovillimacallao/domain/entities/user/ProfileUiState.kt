package com.colegiosociologosperu.cspmovillimacallao.domain.entities.user

data class ProfileUiState(
    val name: String = "",
    val lastName: String = "",
    var email: String = "",
    val phone: String = "",
    val dni: String = "",
    val gender: String = "",
    val birthday: String = "",
    val codeNumber: String = "",
    val condition: String = "",
    val specialized: String = "",
    val payLastPeriod: String = "",
    val role: String = "",
    val accessFirstTime: String = "",
    val active: String = "",
    val createdAt: String = "",
    val modifiedAt: String = "",
    val urlImageProfile: String = "",
)