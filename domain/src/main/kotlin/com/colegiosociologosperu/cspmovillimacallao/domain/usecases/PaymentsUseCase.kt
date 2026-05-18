package com.colegiosociologosperu.cspmovillimacallao.domain.usecases

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.payment.PaymentsItem
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.PaymentsRepository
import javax.inject.Inject

class PaymentsUseCase @Inject constructor(
    private val repository: PaymentsRepository
) {
    suspend fun getAllPayments(): List<PaymentsItem> = repository.getAllPayments()
}
