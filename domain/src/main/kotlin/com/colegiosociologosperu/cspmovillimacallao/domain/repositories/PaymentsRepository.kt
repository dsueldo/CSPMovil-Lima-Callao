package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.payment.PaymentsItem

interface PaymentsRepository {
    suspend fun getAllPayments(): List<PaymentsItem>
}
